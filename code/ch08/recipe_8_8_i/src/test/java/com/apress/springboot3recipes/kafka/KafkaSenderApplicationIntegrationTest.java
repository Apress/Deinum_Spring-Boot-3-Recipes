package com.apress.springboot3recipes.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.core.ConsumerFactory;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.kafka.consumer.group-id=test-group")
@Testcontainers
class KafkaSenderApplicationIntegrationTest {

	@ServiceConnection
	@Container
	static KafkaContainer kafkaContainer =
		new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

	@Autowired
	private ConsumerFactory<String, String> consumerFactory;

  @Test
  void shouldSendAtLeastASingleMessage() {
	  try (var consumer = consumerFactory.createConsumer()) {
			consumer.subscribe(List.of("hello"));
		  var records = consumer.poll(Duration.ofSeconds(2));
		  assertThat(records).isNotNull();
		  assertThat(records).isNotEmpty();

	  }
  }
}
