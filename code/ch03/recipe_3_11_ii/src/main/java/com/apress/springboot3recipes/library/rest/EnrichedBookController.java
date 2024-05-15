package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.EnrichedBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class EnrichedBookController {

	private static final String BOOKS_URL = "http://localhost:8080/books/{isbn}";
	private static final String OL_API = "https://openlibrary.org/isbn/{isbn}.json";

	private final RestClient rest;

	public EnrichedBookController(RestClient rest) {
		this.rest = rest;
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<EnrichedBook> get(@PathVariable("isbn") String isbn) {

		try {
			var book = rest.get().uri(BOOKS_URL, isbn).retrieve().body(Book.class);
			var library = rest.get().uri(OL_API, isbn).retrieve().body(Map.class);
			var published = extractPublishData(library);
			var enriched = enrich(book, published);
			return ResponseEntity.ok(enriched);
		} catch (RestClientException ex) {
			if (ex instanceof RestClientResponseException rex) {
				return ResponseEntity.status(rex.getStatusCode()).build();
			}
			return ResponseEntity.internalServerError().build();
		}
	}

	private EnrichedBook enrich(Book book, String publishDate) {
		return new EnrichedBook(book.isbn(), book.title(), publishDate, book.authors());
	}

	private String extractPublishData(Map json) {
		return (String) json.getOrDefault("publish_date", "");
	}
}
