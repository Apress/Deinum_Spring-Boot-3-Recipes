package com.apress.springboot3recipes.library.rest;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.security.user.password=s3cr3t")
class BookControllerIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

	@MockBean
	private BookService bookService;

	@Test
	void shouldReturnListOfBooks() {

		when(bookService.findAll()).thenReturn(Arrays.asList(
				new Book("123", "Spring 5 Recipes", List.of("Marten Deinum", "Josh Long")),
				new Book("321", "Pro Spring MVC", List.of("Marten Deinum", "Colin Yates"))));

    ResponseEntity<Book[]> books = testRestTemplate
            .withBasicAuth("user", "s3cr3t")
            .getForEntity("/books", Book[].class);

    assertThat(books.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(books.getBody()).hasSize(2);
	}

	@Test
	void shouldReturn404WhenBookNotFound() {

		when(bookService.find(any())).thenReturn(Optional.empty());

		ResponseEntity<Book> book = testRestTemplate
			.withBasicAuth("user", "s3cr3t")
			.getForEntity("/books/123", Book.class);

		assertThat(book.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldReturnBookWhenFound() {

		when(bookService.find(eq("123"))).thenReturn(
						Optional.of(new Book("123", "Spring 5 Recipes", List.of("Marten Deinum", "Josh Long"))));

		ResponseEntity<Book> book = testRestTemplate
			.withBasicAuth("user", "s3cr3t")
			.getForEntity("/books/123", Book.class);

		assertThat(book.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(book.getBody())
			.hasFieldOrPropertyWithValue("isbn", "123")
				.hasFieldOrPropertyWithValue("title", "Spring 5 Recipes");
	}
}
