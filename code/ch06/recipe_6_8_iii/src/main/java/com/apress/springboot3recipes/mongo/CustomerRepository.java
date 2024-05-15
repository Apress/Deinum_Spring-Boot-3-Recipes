package com.apress.springboot3recipes.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository
				extends ReactiveMongoRepository<Customer, String> { }
