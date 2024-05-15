package com.apress.springboot3recipes.library;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
class InMemoryBookService implements BookService {

	private final Map<String, Book> books = new ConcurrentHashMap<>();

  @PreAuthorize("isAuthenticated()")
	public Iterable<Book> findAll() {
		return books.values();
	}

  @PreAuthorize("hasAuthority('USER')")
	public Book create(Book book) {
		books.put(book.isbn(), book);
		return book;
	}

  @PreAuthorize("hasAuthority('ADMIN') " +
	              "or @accessChecker.hasLocalAccess(authentication)")
  public void remove(Book book) {
    books.remove(book.isbn());
  }

	public Optional<Book> find(String isbn) {
		return Optional.ofNullable(books.get(isbn));
	}
}
