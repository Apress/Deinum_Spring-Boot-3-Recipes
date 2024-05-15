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
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public Iterable<Book> all() {
		return bookService.findAll();
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<Book> get(@PathVariable("isbn") String isbn) {
		return ResponseEntity.of(bookService.find(isbn));
	}

	@PostMapping
	public ResponseEntity<Book> create(@RequestBody Book book,
                                     UriComponentsBuilder uriBuilder) {
    var created = bookService.create(book);
    var newBookUri = uriBuilder.path("/books/{isbn}").build(created.isbn());
    return ResponseEntity
            .created(newBookUri)
            .body(created);
	}

	@GetMapping("/500")
	public void error() {
		var cause = new NullPointerException("Dummy Exception");
		throw new ServerErrorException(cause.getMessage(), cause);
	}

}
