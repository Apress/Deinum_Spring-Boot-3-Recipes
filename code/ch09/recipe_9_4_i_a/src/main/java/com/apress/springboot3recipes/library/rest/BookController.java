package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books")
	public Iterable<Book> all() {
		return bookService.findAll();
	}

	@GetMapping(value = "/books/{isbn}")
	public ResponseEntity<Book> get(@PathVariable("isbn") String isbn) {
		return ResponseEntity.of(bookService.find(isbn));
	}

	@PostMapping("/books")
	public Book create(@ModelAttribute Book book) {
		return bookService.create(book);
	}

}
