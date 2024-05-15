package com.apress.springboot3recipes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWorldControllerIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void testHelloWorldController() throws Exception {

    String result = testRestTemplate.getForObject("/", String.class);
    assertThat(result).startsWith("Hello World, from Spring Boot ");
  }
}
