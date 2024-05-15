package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.security.user.password=s3cr3t")
@AutoConfigureWebTestClient
class BookControllerIntegrationWebClientTest {

  @Autowired
  private WebTestClient webTestClient;

	@MockBean
	private BookService bookService;

	@Test
	void shouldReturnListOfBooks() {

		when(bookService.findAll()).thenReturn(Arrays.asList(
				new Book("123", "Spring 5 Recipes", List.of("Marten Deinum", "Josh Long")),
				new Book("321", "Pro Spring MVC", List.of("Marten Deinum", "Colin Yates"))));

		webTestClient
            .get()
              .uri("/books")
              .headers( httpHeaders -> httpHeaders.setBasicAuth("user", "s3cr3t"))
            .exchange()
              .expectStatus().isOk()
              .expectBodyList(Book.class).hasSize(2);
	}


	@Test
	void shouldReturn404WhenBookNotFound() {

		when(bookService.find(any())).thenReturn(Optional.empty());

		webTestClient
			.get()
			.uri("/books/123")
			.headers( httpHeaders -> httpHeaders.setBasicAuth("user", "s3cr3t"))
			.exchange()
			.expectStatus().isNotFound();

	}

	@Test
	void shouldReturnBookWhenFound() {

		when(bookService.find(eq("123"))).thenReturn(
						Optional.of(new Book("123", "Spring 5 Recipes", List.of("Marten Deinum", "Josh Long"))));

		webTestClient
			.get()
			.uri("/books/123")
			.headers( httpHeaders -> httpHeaders.setBasicAuth("user", "s3cr3t"))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
				.jsonPath("$.isbn").isEqualTo("123")
				.jsonPath("$.title").isEqualTo("Spring 5 Recipes");
	}
}
