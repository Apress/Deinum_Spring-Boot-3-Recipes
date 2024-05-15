package com.apress.springboot3recipes;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.contains;

@WebFluxTest(HelloWorldController.class)
@ImportAutoConfiguration(TaskExecutionAutoConfiguration.class)
public class HelloWorldControllerTest {

	@Autowired
	private WebTestClient client;

	@Test
	public void testHelloWorldController() throws Exception {
		client.get().uri("/blocking").exchange()
			.expectStatus().isOk()
			.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
			.expectBody(String.class)
				.value( (x) -> contains("Hello World, from Spring Boot 3."));
	}
}
