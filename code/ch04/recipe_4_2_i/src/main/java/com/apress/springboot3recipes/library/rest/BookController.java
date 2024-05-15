package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public Flux<Book> all() {
		return bookService.findAll().delayElements(Duration.ofMillis(64));
	}

	@GetMapping("/{isbn}")
	public Mono<Book> get(@PathVariable("isbn") String isbn) {
		return bookService.find(isbn);
	}

	@PostMapping
	public Mono<ResponseEntity<Book>> create(@RequestBody Mono<Book> book,
	                                         UriComponentsBuilder uriBuilder) {
		var created = book.flatMap(bookService::create);
		return created.map((it) -> createResponse(it, uriBuilder));
	}

	private ResponseEntity<Book> createResponse(Book book,
	                                            UriComponentsBuilder ucb) {
		var newBookUri = ucb.path("/books/{isbn}").build(book.isbn());
		return ResponseEntity.created(newBookUri).body(book);
	}
}
