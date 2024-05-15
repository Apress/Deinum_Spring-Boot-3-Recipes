package com.apress.springboot3recipes.library.web;

import com.apress.springboot3recipes.library.Book;
import com.apress.springboot3recipes.library.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	void shouldReturnListOfBooks() throws Exception {

		when(bookService.findAll()).thenReturn(Arrays.asList(
						new Book("123", "Spring 5 Recipes", List.of("Marten Deinum", "Josh Long")),
						new Book("321", "Pro Spring MVC", List.of("Marten Deinum", "Colin Yates"))));

		mockMvc.perform(get("/books.html"))
						.andExpect(status().isOk())
						.andExpect(view().name("books/list"))
						.andExpect(model().attribute("books", Matchers.hasSize(2)));
	}

	@Test
	void shouldReturnNoBookWhenNotFound() throws Exception {

		when(bookService.find(anyString())).thenReturn(Optional.empty());

		mockMvc.perform(get("/books.html").param("isbn", "123"))
						.andExpect(status().isOk())
						.andExpect(view().name("books/details"))
						.andExpect(model().attributeDoesNotExist("book"));
	}

	@Test
	void shouldReturnBookWhenFound() throws Exception {

		var book = new Book("123", "Spring 5 Recipes", List.of("Marten Deinum", "Josh Long"));
		when(bookService.find(anyString())).thenReturn(
						Optional.of(book));

		mockMvc.perform(get("/books.html").param("isbn", "123"))
						.andExpect(status().isOk())
						.andExpect(view().name("books/details"))
						.andExpect(model().attribute("book", Matchers.is(book)));
	}

}
