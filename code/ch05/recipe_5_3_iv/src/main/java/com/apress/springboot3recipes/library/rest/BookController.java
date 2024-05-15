package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping( {"/books", "books.html"})
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
  @ResponseBody
	public Iterable<Book> all() {
		return bookService.findAll();
	}

	@GetMapping("/{isbn}")
	@ResponseBody
	public ResponseEntity<Book> get(@PathVariable("isbn") String isbn) {
		return ResponseEntity.of(bookService.find(isbn));
	}

  @PostMapping
  @ResponseBody
  public ResponseEntity<Book> create(@RequestBody Book book,
                                     UriComponentsBuilder uriBuilder) {
    var created = bookService.create(book);
    var newBookUri = uriBuilder.path("/books/{isbn}").build(created.isbn());
    return ResponseEntity
            .created(newBookUri)
            .body(created);
	}

  @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
  public String all(Model model) {
    model.addAttribute("books", bookService.findAll());
    return "books/list";
  }

  @GetMapping(params = "isbn", produces = MediaType.TEXT_HTML_VALUE)
  public String get(@RequestParam("isbn") String isbn, Model model) {

    bookService.find(isbn)
            .ifPresent(book -> model.addAttribute("book", book));

    return "books/details";
  }
}
