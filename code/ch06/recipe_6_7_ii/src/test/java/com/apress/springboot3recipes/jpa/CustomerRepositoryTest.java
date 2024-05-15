package com.apress.springboot3recipes.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CustomerRepositoryTest {

	@Container
	@ServiceConnection
	static PostgreSQLContainer<?> postgres =
		new PostgreSQLContainer<>("postgres:15-alpine");

	@Autowired
  private CustomerRepository repository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void insertNewCustomer() {
	  var newCustomer = new Customer("T. Testing", "t.testing@test123.tst");
	  var customer = repository.save(newCustomer);

    assertThat(customer.getId()).isGreaterThan(-1L);
    assertThat(customer.getName()).isEqualTo("T. Testing");
    assertThat(customer.getEmail()).isEqualTo("t.testing@test123.tst");

		testEntityManager.flush();
		testEntityManager.clear();

    Assertions.assertThat(repository.findById(customer.getId())).hasValue(customer);
  }

  @Test
  void findAllCustomers() {
    var count = repository.count();
    testEntityManager.persist(new Customer("T. Testing1", "t.testing@test123.tst"));
    testEntityManager.persist(new Customer("T. Testing2", "t.testing@test123.tst"));
		testEntityManager.flush();
    Assertions.assertThat(repository.findAll()).hasSize( (int) count + 2);
  }
}
