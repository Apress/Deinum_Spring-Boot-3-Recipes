package com.apress.springboot3recipes.helloworld;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {

	@PostConstruct
	public void sayHello() {
		System.out.println("Hello World, from Spring Boot 3!");
	}
}
