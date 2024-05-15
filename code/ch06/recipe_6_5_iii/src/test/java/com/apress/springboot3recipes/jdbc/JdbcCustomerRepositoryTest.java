package com.apress.springboot3recipes.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class JdbcCustomerRepositoryTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres =
		new PostgreSQLContainer<>("postgres:15-alpine");

	@Autowired
	private CustomerRepository repository;

	@Test
	void insertNewCustomer() {

		var newCustomer = new Customer("T. Testing", "t.testing@test123.tst");
		var customer = repository.save(newCustomer);

		assertThat(customer.id()).isNotNull();
		assertThat(customer.name()).isEqualTo("T. Testing");
		assertThat(customer.email()).isEqualTo("t.testing@test123.tst");

		assertThat(repository.findById(customer.id())).hasValue(customer);
	}

	@Test
	void findAllCustomers() {

		long count = repository.count();

		repository.save(new Customer("T. Testing1", "t.testing@test123.tst"));
		repository.save(new Customer("T. Testing2", "t.testing@test123.tst"));

		assertThat(repository.findAll()).hasSize( (int) count + 2);
	}
}

