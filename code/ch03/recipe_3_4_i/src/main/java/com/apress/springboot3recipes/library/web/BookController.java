package com.apress.springboot3recipes.library.web;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;

@Controller
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books.html")
	public String all(Model model) {
		model.addAttribute("books", bookService.findAll());
		return "books/list";
	}

	@GetMapping(value = "/books.html", params = "isbn")
	public String get(@RequestParam("isbn") String isbn, Model model) {

		bookService.find(isbn)
						.ifPresent(book -> model.addAttribute("book", book));

		return "books/details";
	}

	@PostMapping("/books")
	public Book create(@ModelAttribute Book book) {
		return bookService.create(book);
	}

	@GetMapping("/books/404")
	public ResponseEntity notFound() {
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/books/400")
	public ResponseEntity foo() {
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/books/500")
	public void error() {
		var cause = new NullPointerException("Dummy Exception");
		throw new ServerErrorException(cause.getMessage(), cause);
	}
}
