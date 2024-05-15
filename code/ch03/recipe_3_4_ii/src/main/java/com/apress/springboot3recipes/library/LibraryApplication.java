package com.apress.springboot3recipes.library;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public ApplicationRunner booksInitializer(BookService bookService) {
		return args -> {
			bookService.create(new Book("9780061120084", "To Kill a Mockingbird", List.of("Harper Lee")));
			bookService.create(new Book("9780451524935", "1984", List.of("George Orwell")));
			bookService.create(new Book("9780618260300", "The Hobbit", List.of("J.R.R. Tolkien")));
		};
	}

	@Bean
	public CustomizedErrorAttributes errorAttributes() {
		return new CustomizedErrorAttributes();
	}
}
