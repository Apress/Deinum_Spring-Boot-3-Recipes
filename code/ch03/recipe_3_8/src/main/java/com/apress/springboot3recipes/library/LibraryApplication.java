package com.apress.springboot3recipes.library;

import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class LibraryApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
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
	public BeanPostProcessor addHttpConnectorProcessor() {
		return new BeanPostProcessor() {
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
				if (bean instanceof TomcatServletWebServerFactory factory) {
					factory.addAdditionalTomcatConnectors(httpConnector());
					factory.addContextCustomizers(securityCustomizer());
				}
				return bean;
			}
		};
	}

	private TomcatContextCustomizer securityCustomizer() {
		return context -> {
			var collection = new SecurityCollection();
			collection.addPattern("/*");
			var securityConstraint = new SecurityConstraint();
			securityConstraint.setUserConstraint("CONFIDENTIAL");
			securityConstraint.addCollection(collection);
			context.addConstraint(securityConstraint);
		};
	}

	private Connector httpConnector() {
		var connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(8080);
		connector.setSecure(false);
		connector.setRedirectPort(8443);
		return connector;
	}
}
