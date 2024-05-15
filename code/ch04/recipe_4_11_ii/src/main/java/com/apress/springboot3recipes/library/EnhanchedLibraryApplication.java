package com.apress.springboot3recipes.library;

import com.apress.springboot3recipes.library.rest.BookServiceClient;
import com.apress.springboot3recipes.library.rest.OpenLibraryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class EnhanchedLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnhanchedLibraryApplication.class, args);
	}

	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		var httpClient = HttpClient.create().followRedirect(true);
		var connector = new ReactorClientHttpConnector(httpClient);
		return builder.clientConnector(connector).build();
	}

	@Bean
	public HttpServiceProxyFactory httpServiceProxyFactory(WebClient client) {
		var adapter = WebClientAdapter.create(client);
		return HttpServiceProxyFactory.builderFor(adapter).build();
	}

	@Bean
	public BookServiceClient bookServiceClient(HttpServiceProxyFactory factory) {
		return factory.createClient(BookServiceClient.class);
	}

	@Bean
	public OpenLibraryClient openLibraryClient(HttpServiceProxyFactory factory) {
		return factory.createClient(OpenLibraryClient.class);
	}
}
