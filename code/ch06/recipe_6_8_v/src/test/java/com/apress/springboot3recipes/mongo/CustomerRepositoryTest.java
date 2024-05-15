package com.apress.springboot3recipes.mongo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
class CustomerRepositoryTest {

	@Container
	@ServiceConnection
	static MongoDBContainer mongodb = new MongoDBContainer("mongo:6.0");

  @Autowired
  private CustomerRepository repository;

  @BeforeEach
  public void cleanUp() {
    repository.deleteAll();
  }

  @Test
  void insertNewCustomer() {
    assertThat(repository.findAll()).isEmpty();

    Customer customer = repository.save(new Customer("T. Testing", "t.testing@test123.tst"));

    assertThat(customer.id()).isNotNull();
    assertThat(customer.name()).isEqualTo("T. Testing");
    assertThat(customer.email()).isEqualTo("t.testing@test123.tst");

    assertThat(repository.findById(customer.id()))
            .contains( customer);
  }

  @Test
  void findAllCustomers() {
    assertThat(repository.findAll()).isEmpty();

    repository.save(new Customer("T. Testing1", "t.testing@test123.tst"));
    repository.save(new Customer("T. Testing2", "t.testing@test123.tst"));

    assertThat(repository.findAll()).hasSize(2);
  }
}
