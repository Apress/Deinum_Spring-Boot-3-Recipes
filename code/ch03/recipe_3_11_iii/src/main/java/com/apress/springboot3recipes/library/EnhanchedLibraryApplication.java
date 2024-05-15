package com.apress.springboot3recipes.library;

import com.apress.springboot3recipes.library.rest.BookServiceClient;
import com.apress.springboot3recipes.library.rest.OpenLibraryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class EnhanchedLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnhanchedLibraryApplication.class, args);
	}

	@Bean
	public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder builder) {
		var adapter = RestClientAdapter.create(builder.build());
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
