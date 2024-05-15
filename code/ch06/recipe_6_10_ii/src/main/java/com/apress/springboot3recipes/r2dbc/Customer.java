package com.apress.springboot3recipes.r2dbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
public record Customer(@Column("id") @Id Long id,
                       @Column("name") String name,
                       @Column("email") String email) {

	public Customer(String name, String email) {
		this(null, name, email);
	}
}
