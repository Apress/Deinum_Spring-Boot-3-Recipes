package com.apress.springboot3recipes.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class RabbitSenderApplicationIntegrationTest {

	@ServiceConnection
	@Container
	static RabbitMQContainer rabbitmq =
		new RabbitMQContainer("rabbitmq:3.12.11-management-alpine");

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  void shouldSendAtLeastASingleMessage() {

    String msg = (String) rabbitTemplate.receiveAndConvert("hello", 1500);
    assertThat(msg).isEqualTo("Hello World, from Spring Boot 3, over RabbitMQ!");
  }

	@TestConfiguration
	static class RabbitMqQueueConfiguration {

		@Bean
		public Queue helloQueue() {
			return QueueBuilder.nonDurable("hello").build();
		}

		@Bean
		public Binding helloQueueBinding(Queue queue) {
			return BindingBuilder
				.bind(queue)
				.to(DirectExchange.DEFAULT)
				.withQueueName();
		}
	}
}
