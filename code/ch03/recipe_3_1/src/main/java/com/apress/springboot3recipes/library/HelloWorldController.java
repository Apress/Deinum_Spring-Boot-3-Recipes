package com.apress.springboot3recipes.library;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/")
	public String hello() {
		var version = SpringBootVersion.getVersion();
		return String.format("Hello World, from Spring Boot %s!", version);
	}
}
