package com.apress.springboot3recipes.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.flyway.enabled=false")
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository repository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void insertNewCustomer() {
    Assertions.assertThat(repository.findAll()).isEmpty();

	  var newCustomer = new Customer("T. Testing", "t.testing@test123.tst");
	  var customer = repository.save(newCustomer);

    assertThat(customer.getId()).isGreaterThan(-1L);
    assertThat(customer.getName()).isEqualTo("T. Testing");
    assertThat(customer.getEmail()).isEqualTo("t.testing@test123.tst");

    Assertions.assertThat(repository.findById(customer.getId())).hasValue(customer);
  }

  @Test
  void findAllCustomers() {
    Assertions.assertThat(repository.findAll()).isEmpty();

    testEntityManager.persist(new Customer("T. Testing1", "t.testing@test123.tst"));
    testEntityManager.persist(new Customer("T. Testing2", "t.testing@test123.tst"));
		testEntityManager.flush();
    Assertions.assertThat(repository.findAll()).hasSize(2);
  }
}
