package com.apress.springboot3recipes.helloworld;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {

  @GetMapping
  public Mono<String> hello() {
		var version= SpringBootVersion.getVersion();
		var msg = String.format("Hello World, from Reactive Spring Boot %s!", version);
    return Mono.just(msg);
  }
}
