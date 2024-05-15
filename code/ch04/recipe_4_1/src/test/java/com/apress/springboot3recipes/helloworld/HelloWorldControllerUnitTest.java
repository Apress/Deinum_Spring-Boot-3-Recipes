package com.apress.springboot3recipes.helloworld;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class HelloWorldControllerUnitTest {

  private final HelloWorldController controller = new HelloWorldController();

	@Test
  void shouldSayHello() {
    var result = controller.hello();

    StepVerifier.create(result)
            .expectNext("Hello World, from Reactive Spring Boot 3.2.2!")
            .verifyComplete();
  }
}
