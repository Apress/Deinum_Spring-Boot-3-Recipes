package com.apress.springboot3recipes.r2dbc;

import io.r2dbc.spi.Readable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
class R2dbcCustomerRepository implements CustomerRepository {

	private static final String ALL_QUERY = "SELECT id, name, email FROM customer";
	private static final String BY_ID_QUERY =
		"SELECT id, name, email FROM customer WHERE id=:id";
	private static final String INSERT_QUERY =
		"INSERT INTO customer (name, email) VALUES (:name, :email)";

	private final DatabaseClient client;

	R2dbcCustomerRepository(DatabaseClient client) {
		this.client = client;
	}

	@Override
	public Flux<Customer> findAll() {
		return client.sql(ALL_QUERY)
						.map(this::toCustomer)
						.all();
	}

	@Override
	public Mono<Customer> findById(long id) {
		return client.sql(BY_ID_QUERY)
						.bind("id", id)
						.map(this::toCustomer)
						.one();
	}

	@Override
	public Mono<Customer> save(Customer customer) {
		var result = client.sql(INSERT_QUERY)
						.filter( (s) -> s.returnGeneratedValues("id"))
						.bind("name", customer.name())
						.bind("email", customer.email())
						.map( (row) -> row.get("id", Long.class))
						.first();
		return result
						.map((id) -> new Customer(id, customer.name(), customer.email()));
	}

	private Customer toCustomer(Readable row) {
		var id = row.get(0, Long.class);
		var name = row.get(1, String.class);
		var email = row.get(2, String.class);
		return new Customer(id, name, email);
	}
}
