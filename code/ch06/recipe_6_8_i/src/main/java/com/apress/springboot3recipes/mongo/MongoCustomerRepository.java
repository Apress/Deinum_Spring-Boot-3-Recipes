package com.apress.springboot3recipes.mongo;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class MongoCustomerRepository implements CustomerRepository {

	private final MongoTemplate mongoTemplate;

	MongoCustomerRepository(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Customer> findAll() {
		return mongoTemplate.findAll(Customer.class);
	}

	@Override
	public Optional<Customer> findById(long id) {
		return Optional.ofNullable(mongoTemplate.findById(id, Customer.class));
	}

	@Override
	public Customer save(Customer customer) {
		mongoTemplate.save(customer);
		return customer;
	}
}
