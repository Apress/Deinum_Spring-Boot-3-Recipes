package com.apress.springboot3recipes.order;

import java.util.Objects;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {

	@Id
	private Long id;
	private String number;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (this.id != null && o instanceof Order other) {
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
		return String.format("Order[id=%d, number=%s]",
						this.id, this.number);
	}
}
