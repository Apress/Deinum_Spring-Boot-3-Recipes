package com.apress.springboot3recipes.jpa;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private final String name;

	@Column(nullable = false)
	private final String email;

	public Customer() {
		this(null,null);
	}

	public Customer(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof Customer other) {
			return Objects.equals(this.id, other.id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, name=%s, email='%s']",
						this.id, this.name, this.email);
	}
}
