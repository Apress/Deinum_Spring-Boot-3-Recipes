package com.apress.springboot3recipes.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class EnhanchedLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnhanchedLibraryApplication.class, args);
	}

	@Bean
	public WebClient restClient(WebClient.Builder builder) {
		var httpClient = HttpClient.create().followRedirect(true);
		var connector = new ReactorClientHttpConnector(httpClient);
		return builder.clientConnector(connector).build();
	}
}
