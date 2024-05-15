package com.apress.springboot3recipes.library;

import com.apress.springboot3recipes.library.i18n.CookieLocaleContextResolver;
import com.apress.springboot3recipes.library.i18n.LocaleChangeWebFilter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	public LocaleChangeWebFilter localeChangeWebFilter(LocaleContextResolver lcr) {
		return new LocaleChangeWebFilter(lcr);
	}

	@Bean
	public CookieLocaleContextResolver localeContextResolver() {
				return new CookieLocaleContextResolver();
	}
	                                                   @Bean
	public ApplicationRunner initData(BookService books) {
		return (args) -> Flux.fromIterable(BookGenerator.all())
			.map(books::create).subscribe();
	}
}
