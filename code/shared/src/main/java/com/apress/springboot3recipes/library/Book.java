package com.apress.springboot3recipes.library;

import java.util.List;
import java.util.Objects;

public record Book(String isbn, String title, List<String> authors) {

	@Override
	public boolean equals(Object o) {
		if (o instanceof Book other) {
			return this.isbn != null && Objects.equals(this.isbn, other.isbn);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.isbn);
	}
}
