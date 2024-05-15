package com.apress.springboot3recipes.r2dbc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.TestPropertySource;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataR2dbcTest
@TestPropertySource(properties =
				{ "spring.r2dbc.url=r2dbc:h2:mem://testdb",
					"spring.r2dbc.generate-unique-name=true" })
@Import(R2dbcCustomerRepository.class)
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository repository;

	@Autowired
	private DatabaseClient db;

	@BeforeEach
	public void setup() {
		db.sql("DELETE FROM customer").fetch().first().subscribe();
	}

	@Test
	void insertNewCustomer() {
		var newCustomer = new Customer("T. Testing", "t.testing@test123.tst");
		repository.save(newCustomer)
								.as(StepVerifier::create)
									.assertNext( (c) -> assertThat(c.id()).isNotNull())
									.verifyComplete();
	}

	@Test
	void findAllCustomers() {

		var customers = Flux.just(
						new Customer("T. Testing1", "t.testing@test123.tst")
						,new Customer("T. Testing2", "t.testing@test123.tst"));

		customers.doOnNext(repository::save)
						.as(StepVerifier::create)
						.expectNextCount(2)
						.verifyComplete();
	}
}
