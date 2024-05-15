package com.apress.springboot3recipes.library;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> indexMapping() {
		return RouterFunctions
			.route(RequestPredicates.GET("/"), (req) -> ServerResponse.ok().render("index"));
	}

	@Bean
	public ApplicationRunner initData(BookService books) {
		return (args) -> Flux.fromIterable(BookGenerator.all())
			.map(books::create).subscribe();
	}

//	@Bean
//	public CustomizedErrorAttributes errorAttributes() {
//		return new CustomizedErrorAttributes();
//	}
}
