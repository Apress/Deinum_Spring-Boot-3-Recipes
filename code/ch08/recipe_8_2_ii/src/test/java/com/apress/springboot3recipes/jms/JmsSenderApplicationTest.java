package com.apress.springboot3recipes.jms;

import com.apress.springboot3recipes.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.BytesMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JmsSenderApplicationTest {

	@Autowired
	private JmsTemplate jms;

	@Test
	void shouldReceiveOrderPlain() throws Exception {

		var message = jms.receive("orders");

		assertThat(message)
						.isInstanceOf(BytesMessage.class);

		var msg = (BytesMessage) message;

		var mapper = new ObjectMapper();
		var content = new byte[(int) msg.getBodyLength()];
		msg.readBytes(content);
		var order = mapper.readValue( content, Order.class);

		assertThat(order).hasNoNullFieldsOrProperties();
	}

	@Test
	void shouldReceiveOrderWithConversion() throws Exception {

		var order = (Order) jms.receiveAndConvert("orders");
		assertThat(order).hasNoNullFieldsOrProperties();
	}
}
