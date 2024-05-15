package com.apress.springboot3recipes.r2dbc;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {

	Flux<Customer> findAll();
	Mono<Customer> findById(long id);
	Mono<Customer> save(Customer customer);
}
