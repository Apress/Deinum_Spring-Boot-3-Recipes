package com.apress.springboot3recipes.helloworld;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(HelloWorldController.class)
class HelloWorldControllerSliceTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  void shouldSayHello() {

	  webClient
		  .get().uri("/").accept(MediaType.TEXT_PLAIN).exchange()
		  .expectStatus().isOk()
		  .expectBody(String.class)
		  .value(Matchers.startsWith("Hello World, from Reactive Spring Boot"));
  }
}
