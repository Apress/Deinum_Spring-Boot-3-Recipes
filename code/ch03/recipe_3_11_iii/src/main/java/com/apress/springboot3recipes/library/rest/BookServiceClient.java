package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface BookServiceClient {

	@GetExchange("http://localhost:8080/books/{isbn}")
	Book getBook(@PathVariable String isbn);

}
