package com.apress.springboot3recipes.library.web;

import com.apress.springboot3recipes.library.BookGenerator;
import com.apress.springboot3recipes.library.BookService;
import com.apress.springboot3recipes.library.rest.BookController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

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
						.expectStatus().isOk();
	}

//	@Test
//	public void shouldReturnNoBookWhenNotFound() throws Exception {
//
//		when(bookService.find(anyString())).thenReturn(Mono.empty());
//
//		webTestClient.get().uri("/books.html", Map.of("isbn", "123")).exchange()
//				.expectStatus().ik()
//				.expectBody(Book.class).isE
//
//		mockMvc.perform(get("/books.html").param("isbn", "123"))
//						.andExpect(status().isOk())
//						.andExpect(view().name("books/details"))
//						.andExpect(model().attributeDoesNotExist("book"));
//	}
//
//	@Test
//	public void shouldReturnBookWhenFound() throws Exception {
//
//		var book = BookGenerator.random();
//		when(bookService.find(anyString())).thenReturn(Mono.just(book));
//
//		mockMvc.perform(get("/books.html").param("isbn", "123"))
//						.andExpect(status().isOk())
//						.andExpect(view().name("books/details"))
//						.andExpect(model().attribute("book", Matchers.is(book)));
//	}
}
