package com.apress.springboot3recipes.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
// Will scan the defined package for @Entity annotated classes
@EntityScan("com.apress.springboot3recipes.order")
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
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
						.forEach( (customer) -> logger.info("{}", customer));
	}
}
