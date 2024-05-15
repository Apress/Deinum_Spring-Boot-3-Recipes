package com.apress.springboot3recipes.library;

import java.util.List;
import java.util.Objects;

public record Book(

	String isbn,
	String title,
	List<String> authors) {

  @Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Book book = (Book) o;
		return Objects.equals(isbn, book.isbn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}
}
