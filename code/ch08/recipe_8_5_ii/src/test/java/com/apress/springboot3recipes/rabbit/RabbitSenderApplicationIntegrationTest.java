package com.apress.springboot3recipes.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
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
		new RabbitMQContainer("rabbitmq:3.12.4-management-alpine");

	@Autowired
  private RabbitTemplate rabbitTemplate;

  @Test
  void shouldSendAtLeastASingleMessage() {

    var msg = rabbitTemplate.receive("new-order", 1500);

    assertThat(msg).isNotNull();
    assertThat(msg.getBody()).isNotEmpty();
    assertThat(msg.getMessageProperties().getReceivedExchange())
            .isEqualTo("orders");
    assertThat(msg.getMessageProperties().getReceivedRoutingKey())
            .isEqualTo("new-order");
    assertThat(msg.getMessageProperties().getContentType())
            .isEqualTo("application/json");
  }

	@TestConfiguration
	static class RabbitMqQueueConfiguration {

		@Bean
		public Queue newOrderQueue() {
			return QueueBuilder.durable("new-order").build();
		}

		@Bean
		public Exchange ordersExchange() {
			return ExchangeBuilder.topicExchange("orders").durable(true).build();
		}

		@Bean
		public Binding newOrderQueueBinding(Queue queue, Exchange exchange) {
			return BindingBuilder.bind(queue).to(exchange).with("new-order").noargs();
		}
	}
}
