package com.apress.springboot3recipes.r2dbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
public record Customer(@Id Long id, String name, String email) {

	public Customer(String name, String email) {
		this(null, name, email);
	}
}
