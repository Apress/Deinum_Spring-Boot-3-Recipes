package com.apress.springboot3recipes.library.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@HttpExchange(url = "https://openlibrary.org/isbn")
public interface OpenLibraryClient {

	@GetExchange("/{isbn}.json")
	Mono<Map> getInformation(@PathVariable String isbn);
}
