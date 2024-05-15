package com.apress.springboot3recipes.library;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class LibraryApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	public ApplicationRunner booksInitializer(BookService bookService) {
		return args -> {
			bookService.create(new Book("9780061120084", "To Kill a Mockingbird", List.of("Harper Lee")));
			bookService.create(new Book("9780451524935", "1984", List.of("George Orwell")));
			bookService.create(new Book("9780618260300", "The Hobbit", List.of("J.R.R. Tolkien")));
		};
	}
}
