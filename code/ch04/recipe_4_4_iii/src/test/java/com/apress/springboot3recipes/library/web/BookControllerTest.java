package com.apress.springboot3recipes.library.web;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookGenerator;
import com.apress.springboot3recipes.library.BookService;
import com.apress.springboot3recipes.library.rest.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookService bookService;

	@Test
	public void shouldReturnListOfBooks() throws Exception {

		when(bookService.findAll()).thenReturn(Flux.fromIterable(BookGenerator.random(2)));

		webTestClient.get().uri("/books").exchange()
			.expectStatus().isOk()
			.expectBodyList(Book.class).hasSize(2);

	}

	@Test
	public void shouldReturnNoBookWhenNotFound() throws Exception {

		when(bookService.find(anyString())).thenReturn(Mono.empty());

		webTestClient.get().uri("/books/{isbn}", "123").exchange()
			.expectStatus().isNotFound();
	}


	@Test
	public void shouldReturnBookWhenFound() throws Exception {

		var book = BookGenerator.random();
		when(bookService.find(anyString())).thenReturn(Mono.just(book));

		webTestClient.get().uri("/books/{isbn}", "123").exchange()
			.expectStatus().isOk()
			.expectBody(Book.class).isEqualTo(book);
	}
}
