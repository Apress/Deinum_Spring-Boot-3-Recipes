package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.EnrichedBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class EnrichedBookController {

	private final BookServiceClient bookServiceClient;
	private final OpenLibraryClient openLibraryClient;

	public EnrichedBookController(BookServiceClient bookServiceClient,
	                                OpenLibraryClient openLibraryClient) {
		this.bookServiceClient = bookServiceClient;
		this.openLibraryClient = openLibraryClient;
	}

	@GetMapping("/{isbn}")
	public Mono<ResponseEntity<EnrichedBook>> get(@PathVariable("isbn") String isbn) {

			var book = bookServiceClient.getBook(isbn);
			var library = openLibraryClient.getInformation(isbn);
			var enriched = enrich(book, library);
			return enriched.map(ResponseEntity::ok).onErrorResume(this::handleError);
	}

	private Mono<ResponseEntity<EnrichedBook>> handleError(Throwable ex) {
		if (ex instanceof RestClientResponseException rex) {
			return Mono.just(ResponseEntity.status(rex.getStatusCode()).build());
		}
		return Mono.just(ResponseEntity.internalServerError().build());
	}

	private Mono<EnrichedBook> enrich(Mono<Book> book, Mono<Map> ol) {
		return Mono.zip(book, ol)
			.map( (res) -> enrich(res.getT1(), res.getT2()));
	}

	private EnrichedBook enrich(Book book, Map json) {
		var publishDate = extractPublishData(json);
		return new EnrichedBook(book.isbn(), book.title(), publishDate, book.authors());
	}

	private String extractPublishData(Map json) {
		return (String) json.getOrDefault("publish_date", "");
	}
}
