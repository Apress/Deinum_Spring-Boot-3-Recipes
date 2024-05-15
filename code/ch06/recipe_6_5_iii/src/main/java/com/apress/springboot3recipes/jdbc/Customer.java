package com.apress.springboot3recipes.jdbc;

import org.springframework.data.annotation.Id;

public record Customer(@Id Long id, String name, String email) {

	public Customer(String name, String email) {
		this(null, name, email);
	}

}
