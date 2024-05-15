package com.apress.springboot3recipes.jms;

import com.apress.springboot3recipes.order.OrderGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class JmsSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsSenderApplication.class, args);
	}

	@Bean
	public MappingJackson2MessageConverter messageConverter() {
		var converter = new MappingJackson2MessageConverter();
		converter.setTypeIdPropertyName("content_type"); // <1>
		converter.setTypeIdMappings(Map.of("order", Order.class)); // <2>
		return converter;
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
