package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
public class EnrichedBookController {

	private static final String OPEN_LABRARY_API = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";

	private final WebClient client;
	private final ObjectMapper mapper;
	public EnrichedBookController(WebClient client, ObjectMapper mapper) {
		this.client = client;
		this.mapper = mapper;
	}

	@GetMapping("/books")
	public Flux<Book> all() {
		return client
			.get().uri("http://localhost:8081/books")
			.exchangeToFlux( (res) -> res.bodyToFlux(Book.class));
	}

	@GetMapping(value = "/books/{isbn}")
	public Mono<ResponseEntity<EnrichedBook>> get(@PathVariable("isbn") String isbn) {
		var bookResult = client
			.get().uri("http://localhost:8081/books/{isbn}", isbn)
			.exchangeToMono( (res) -> res.bodyToMono(Book.class));

		var libraryResult = client.get().uri(OPEN_LABRARY_API, isbn)
			.exchangeToMono( (res) -> res.bodyToMono(String.class))
			.map(this::extractPublishData);

		return Mono.zip(bookResult, libraryResult)
			.map( (both) -> new EnrichedBook(both.getT1().isbn(), both.getT1().title(), (String) both.getT2(), both.getT1().authors()))
			.map(ResponseEntity::ofNullable);
	}

	private String extractPublishData(String json) {

		var elements = JsonPath.parse(json, Configuration.defaultConfiguration()
				.jsonProvider(new JacksonJsonProvider(mapper))
				.mappingProvider(new JacksonMappingProvider(mapper)))
			.read("$..publish_date", new TypeRef<List<String>>(){});
		 return !elements.isEmpty() ? elements.getFirst() : "";

	}

	record EnrichedBook(String isbn, String title, String published, List authors) {

	}

}
