package com.apress.springboot3recipes.helloworld;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class HelloWorldControllerIntegrationTest {

  @Autowired
  private WebTestClient webClient;

  @Test
  void shouldSayHello() {
    webClient
	    .get().uri("/").accept(MediaType.TEXT_PLAIN).exchange()
      .expectStatus().isOk()
      .expectBody(String.class)
        .value(Matchers.containsString("Hello World, from Reactive Spring Boot"));
  }
}
