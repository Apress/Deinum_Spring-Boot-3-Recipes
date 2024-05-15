package com.apress.springboot3recipes.library;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
class InMemoryBookService implements BookService {

	private final Map<String, Book> books = new ConcurrentHashMap<>();

	private final ObservationRegistry observations;

	InMemoryBookService(ObservationRegistry observations) {
		this.observations = observations;
	}

	@Override
	public Iterable<Book> findAll() {
		return books.values();
	}

	@Override
	@Observed(name = "BookService.create")
	public Book create(Book book) {
		books.put(book.isbn(), book);
		return book;
	}

	@Override
	public Optional<Book> find(String isbn) {
		var observation = Observation
			.createNotStarted("BookService.find", this.observations);
		observation.lowCardinalityKeyValue("isbn", isbn);
		return observation.observe(() -> Optional.ofNullable(books.get(isbn)));
	}
}
