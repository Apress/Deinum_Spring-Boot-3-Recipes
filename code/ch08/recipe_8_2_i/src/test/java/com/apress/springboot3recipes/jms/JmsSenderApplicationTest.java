package com.apress.springboot3recipes.jms;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
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
	void shouldSendMessage() throws JMSException {

		var message = jms.receive("time-queue");

		assertThat(message)
			.isInstanceOf(TextMessage.class);
		assertThat(((TextMessage) message).getText())
			.startsWith("Current Date & Time is: ");
	}
}
