package com.apress.springboot3recipes.jpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
class JpaCustomerRepository implements CustomerRepository {

	@PersistenceContext
	private EntityManager em;

  @Override
	public List<Customer> findAll() {
		return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
	}

	@Override
	public Optional<Customer> findById(long id) {
		var customer = em.find(Customer.class, id);
		return Optional.ofNullable(customer);
	}

	@Override
	public Customer save(Customer customer) {
		em.persist(customer);
		return customer;
	}
}
