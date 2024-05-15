package com.apress.springboot3recipes.library.web;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

@Controller
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books.html")
	public String all(Model model) {
		var books = new ReactiveDataDriverContextVariable(bookService.findAll(), 5);
		model.addAttribute("books", books);
		return "books/list";
	}

	@GetMapping(value = "/books.html", params = "isbn")
	public String get(@RequestParam("isbn") String isbn, Model model) {

		model.addAttribute("book", bookService.find(isbn));
		return "books/details";
	}

	@PostMapping("/books")
	public Mono<Book> create(@ModelAttribute Book book) {
		return bookService.create(book);
	}
}
