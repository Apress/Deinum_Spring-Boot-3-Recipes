package com.apress.springboot3recipes.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository
				extends R2dbcRepository<Customer, Long> {
}
