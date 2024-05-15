package com.apress.springboot3recipes.jms;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.Collections.singletonMap;

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
			messageConverter.setTypeIdMappings(singletonMap("order", Order.class));
			return messageConverter;
		}
}

@Component
class OrderService {

	@JmsListener(destination = "orders")
	public void handle(Order order) {
		System.out.println("[RECEIVED] - " + order);
	}
}

@Component
class OrderSender {

	private final JmsTemplate jms;

	OrderSender(JmsTemplate jms) {
		this.jms = jms;
	}

	@Scheduled(fixedRate = 500)
	public void sendTime() {
		var order = OrderGenerator.generate();
		jms.convertAndSend("orders", order);
	}
}
