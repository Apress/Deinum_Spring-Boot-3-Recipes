package com.apress.springboot3recipes.library;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryBookServiceTest {

	private InMemoryBookService service;

	@BeforeEach
	public void setup() {
		this.service = new InMemoryBookService();
	}

	@Test
	public void shouldReturnEmptyOptionalWhenNotFound() {
		assertThat(service.find("1234")).isEmpty();
	}

	@Test
	public void shouldFindAfterCreation() {
		assertThat(service.findAll()).isEmpty();

		Book book = new Book("1234", "Spring 6 Recipes", List.of("Marten Deinum", "Josh Long"));
		assertThat(service.create(book)).isEqualTo(book);
		assertThat(service.find(book.isbn())).contains(book);
	}

	@Test
	public void shouldReturnAllBooksAfterCreation() {
		assertThat(service.findAll()).isEmpty();

		Book book1 = new Book("1234", "Spring 6 Recipes", List.of("Marten Deinum", "Josh Long"));
		Book book2 = new Book("4321", "Spring Boot 3 Recipes", List.of("Marten Deinum"));
		service.create(book1);
		service.create(book2);

		assertThat(service.findAll()).containsExactly(book1, book2);
	}
}
