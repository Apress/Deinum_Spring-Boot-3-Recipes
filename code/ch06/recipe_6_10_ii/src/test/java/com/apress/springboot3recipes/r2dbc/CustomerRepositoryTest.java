package com.apress.springboot3recipes.r2dbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataR2dbcTest
@Import(R2dbcCustomerRepository.class)
@Testcontainers
class CustomerRepositoryTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

	@Autowired
	private CustomerRepository repository;

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

		customers.flatMap(repository::save)
						.as(StepVerifier::create)
						.expectNextCount(2)
						.verifyComplete();

		repository.findAll()
						.count()
						.as(StepVerifier::create)
						.expectNext(6L)
						.verifyComplete();
	}
}
