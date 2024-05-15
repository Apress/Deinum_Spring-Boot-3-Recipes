package com.apress.springboot3recipes.library;

import java.util.List;
import java.util.Objects;

public record EnrichedBook(
	String isbn,
	String title,
	String published,
	List<String> authors) {

  @Override
	public boolean equals(Object o) {
		if (o instanceof EnrichedBook book) {
			return Objects.equals(isbn, book.isbn);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}
}
