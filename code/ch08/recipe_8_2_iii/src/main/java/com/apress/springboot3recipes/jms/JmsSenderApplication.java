package com.apress.springboot3recipes.jms;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class JmsSenderApplication {

    public static void main(String[] args) {
			SpringApplication.run(JmsSenderApplication.class, args);
    }

    @Bean
		public MappingJackson2MessageConverter messageConverter() {
			var messageConverter = new MappingJackson2MessageConverter();
			messageConverter.setTypeIdPropertyName("content_type");
			messageConverter.setTypeIdMappings(Map.of("order", Order.class));
		  messageConverter.setTargetType(MessageType.TEXT);
			return messageConverter;
		}
}

@Component
class OrderSender {

	private final JmsTemplate jms;

	OrderSender(JmsTemplate jms) {
		this.jms = jms;
	}

	@Scheduled(fixedRate = 500)
	public void sendOrder() {
		var order = OrderGenerator.generate();
		jms.convertAndSend("orders", order);
	}
}
