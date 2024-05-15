package com.apress.springboot3recipes.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {

	@Value("${audience:World}")
	private String audience;

	@Scheduled(fixedRate = 2000)
	public void sayHello() {
		var version = SpringBootVersion.getVersion();
		System.out.printf("Hello %s, from Spring Boot %s!%n", audience, version);
	}
}
