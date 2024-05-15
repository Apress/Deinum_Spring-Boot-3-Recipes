package com.apress.springboot3recipes.order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Order {

	@Id
	private Long id;

	private String number;

	public Long getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Order other) {
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
		return String.format("Order[id=%d, number='%s']",
						this.id, this.number);
	}

}
