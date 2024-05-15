package com.apress.springboot3recipes.jdbc;

public record Customer(Long id, String name, String email) {

	public Customer(String name, String email) {
		this(null, name, email);
	}
}
