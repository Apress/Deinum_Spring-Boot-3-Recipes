package com.apress.springboot3recipes.kafka;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

@SpringBootTest
class KafkaSenderApplicationTest {

	@MockBean
	private KafkaTemplate<String, String> kafka;

	@Test
	void shouldSendAtLeastASingleMessage() {

		var msg = "Hello World, from Spring Boot 3, over Apache Kafka!";
		verify(kafka, Mockito.atLeastOnce()).send("hello", msg);
	}
}
