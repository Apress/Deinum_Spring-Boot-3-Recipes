package com.apress.springboot3recipes.library;

import java.util.Optional;

public interface BookService {

	Iterable<Book> findAll();
	Book create(Book book);
	Optional<Book> find(String isbn);
}
