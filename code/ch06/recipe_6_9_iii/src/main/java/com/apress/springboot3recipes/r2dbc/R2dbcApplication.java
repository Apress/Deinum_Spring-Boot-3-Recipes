package com.apress.springboot3recipes.r2dbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class R2dbcApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(R2dbcApplication.class, args);
		System.in.read();
	}
}

@Component
@Order(1)
class TableLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final DatabaseClient db;

	TableLister(DatabaseClient db) {
		this.db = db;
	}


	@Override
	public void run(ApplicationArguments args) {

		db.sql("SELECT * FROM information_schema.tables")
						.map( (row, rm) -> row.get(2, String.class))
						.all().subscribe( (table) -> logger.info("{}", table));
	}
}

@Component
class CustomerLister implements ApplicationRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CustomerRepository customers;

	CustomerLister(CustomerRepository customers) {
		this.customers = customers;
	}

	@Override
	public void run(ApplicationArguments args) {
		customers.findAll()
						.subscribe( (customer) -> logger.info("{}", customer));
	}
}
