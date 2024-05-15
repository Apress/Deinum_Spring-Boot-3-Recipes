package com.apress.springboot3recipes;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {

	@GetMapping("/blocking")
	public String hello() {
		return sayHello();
	}

	@GetMapping("/reactive")
	public Mono<String> helloReactive() {
		return Mono.just(sayHello());
	}

	private static String sayHello() {
		var version = SpringBootVersion.getVersion();
		var thread = Thread.currentThread().getName();
		return String.format("Hello World, from Spring Boot %s on Thread '%s'!"
													, version, thread);
	}
}
