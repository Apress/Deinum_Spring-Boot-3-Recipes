package com.apress.springboot3recipes.mongo;

public record Customer(String id, String name, String email) {

	public Customer(String name, String email) {
		this(null, name, email);
	}

}
