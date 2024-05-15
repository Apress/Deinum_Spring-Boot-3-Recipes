package com.apress.springboot3recipes.rabbit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class RabbitSenderApplicationTest {

  @MockBean
  private RabbitTemplate rabbitTemplate;

  @Test
  void shouldSendAtLeastASingleMessage() {

    verify(rabbitTemplate, Mockito.atLeastOnce())
	    .convertAndSend("hello", "Hello World, from Spring Boot 3, over RabbitMQ!");
  }
}
