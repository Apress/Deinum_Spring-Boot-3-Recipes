package com.apress.springboot3recipes.mongo;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

	List<Customer> findAll();
	Optional<Customer> findById(long id);
	Customer save(Customer customer);
}
