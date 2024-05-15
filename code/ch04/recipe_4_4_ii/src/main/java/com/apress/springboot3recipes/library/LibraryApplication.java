package com.apress.springboot3recipes.library;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public ApplicationRunner initData(BookService books) {
		return (args) -> Flux.fromIterable(BookGenerator.all())
				.map(books::create).subscribe();
	}

	@Bean
	public CustomizedErrorAttributes errorAttributes() {
		return new CustomizedErrorAttributes();
	}
}
