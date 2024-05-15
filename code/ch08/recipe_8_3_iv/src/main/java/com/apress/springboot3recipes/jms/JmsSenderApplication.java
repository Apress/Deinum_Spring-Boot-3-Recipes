package com.apress.springboot3recipes.jms;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderConfirmation;
import com.apress.springboot3recipes.order.OrderGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;
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
			var messageConverter = new MappingJackson2MessageConverter();
			messageConverter.setTypeIdPropertyName("content_type");

			var typeIdMappings = Map.<String, Class<?>>of(
			 "order", Order.class,
			 "order-confirmation", OrderConfirmation.class);

			messageConverter.setTypeIdMappings(typeIdMappings);
			return messageConverter;
		}
}

@Component
class OrderService {

	@JmsListener(destination = "orders")
	@SendTo("order-confirmations")
	public OrderConfirmation handle(Order order) {
		System.out.println("[RECEIVED] - " + order);
		return new OrderConfirmation(order.id());
	}
}

@Component
class OrderConfirmationService {

	@JmsListener(destination = "order-confirmations")
	public void handle(OrderConfirmation confirmation) {
		System.out.println("[RECEIVED] - " + confirmation);
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
