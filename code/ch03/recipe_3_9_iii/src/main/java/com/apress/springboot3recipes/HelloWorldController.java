package com.apress.springboot3recipes;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class HelloWorldController {

	@GetMapping
	public Callable<String> hello() {
		return () -> {
			Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
			var version = SpringBootVersion.getVersion();
			return String.format("Hello World, from Spring Boot %s!", version);
		};
	}
}
