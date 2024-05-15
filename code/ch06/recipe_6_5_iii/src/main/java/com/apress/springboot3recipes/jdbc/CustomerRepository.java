package com.apress.springboot3recipes.jdbc;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends
				CrudRepository<Customer, Long> {

}
