package com.apress.springboot3recipes.kafka;

import com.apress.springboot3recipes.order.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class KafkaSenderApplicationTest {

	@MockBean
	private KafkaTemplate<String, Order> kafka;

	@Test
	void shouldSendAtLeastASingleMessage() {

		verify(kafka, Mockito.atLeastOnce()).send(eq("orders"), any(Order.class));

	}
}
