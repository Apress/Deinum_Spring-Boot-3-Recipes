package com.apress.springboot3recipes.library;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
class InMemoryBookService implements BookService {

	private final Map<String, Book> books = new ConcurrentHashMap<>();

	@Override
	public Flux<Book> findAll() {
		return Flux.fromIterable(books.values());
	}

	@Override
	public Mono<Book> create(Book book) {
		books.put(book.isbn(), book);
		return Mono.just(book);
	}

	@Override
	public Mono<Book> find(String isbn) {
		return Mono.justOrEmpty(books.get(isbn));
	}
}
