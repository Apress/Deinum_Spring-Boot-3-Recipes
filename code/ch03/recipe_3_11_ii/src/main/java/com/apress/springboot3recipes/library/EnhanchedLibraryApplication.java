package com.apress.springboot3recipes.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class EnhanchedLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnhanchedLibraryApplication.class, args);
	}

	@Bean
	public RestClient restClient(RestClient.Builder builder) {
		return builder.build();
	}
}
