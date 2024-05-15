package com.apress.springboot3recipes.r2dbc;

import static org.springframework.data.relational.core.query.Criteria.where;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
class R2dbcCustomerRepository implements CustomerRepository {

	private final R2dbcEntityTemplate template;

	R2dbcCustomerRepository(R2dbcEntityTemplate template) {
		this.template = template;
	}

	@Override
	public Flux<Customer> findAll() {
		return template.select(Customer.class).all();
	}

	@Override
	public Mono<Customer> findById(long id) {
		return template
						.selectOne(Query.query(where("id").is(id)), Customer.class);
	}

	@Override
	public Mono<Customer> save(Customer customer) {
		return template.insert(customer);
	}
}
