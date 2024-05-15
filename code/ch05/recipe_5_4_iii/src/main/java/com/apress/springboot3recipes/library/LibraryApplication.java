package com.apress.springboot3recipes.library;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class LibraryApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
  }

	@Bean
	public ApplicationRunner booksInitializer(BookService bookService) {
		return args -> {
			BookGenerator.all().forEach(bookService::create);
		};
	}

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }
}
