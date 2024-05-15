package com.apress.springboot3recipes.kafka;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderConfirmation;
import com.apress.springboot3recipes.order.OrderGenerator;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableScheduling
public class KafkaReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaReceiverApplication.class, args);
	}

	@Bean
	public NewTopic ordersTopic() {
		return TopicBuilder.name("orders").build();
	}

	@Bean
	public NewTopic orderConfirmationsTopic() {
		return TopicBuilder.name("order-confirmations").build();
	}

}

@Component
class OrderService {

	@KafkaListener(topics = "orders")
	@SendTo("order-confirmations")
	public OrderConfirmation handle(Order order) {
		System.out.println("[RECEIVED] - " + order);
		return new OrderConfirmation(order.id());
	}
}

@Component
class OrderConfirmationService {

	@KafkaListener(topics = "order-confirmations")
	public void handle(OrderConfirmation confirmation) {
		System.out.println("[RECEIVED] - " + confirmation);
	}
}


@Component
class OrderSender {

	private final KafkaTemplate<String, Order> kafka;

	OrderSender(KafkaTemplate<String, Order> kafka) {
		this.kafka = kafka;
	}

	@Scheduled(fixedRate = 50)
	public void sendOrder() {
		var order = OrderGenerator.generate();
		kafka.send("orders", order);
	}
}
