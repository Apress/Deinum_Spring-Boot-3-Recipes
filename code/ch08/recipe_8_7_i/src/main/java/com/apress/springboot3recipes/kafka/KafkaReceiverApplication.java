package com.apress.springboot3recipes.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
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
	public NewTopic helloTopic() {
		return TopicBuilder.name("hello").build();
	}
}

@Component
class HelloWorldReceiver {

	@KafkaListener(
		topics = "hello",
		groupId = "recipe_8_7_i")
	public void handle(String msg) {
		System.out.println("[RECEIVED] - " + msg);
	}
}

@Component
class HelloWorldSender {

	private final KafkaTemplate<String, String> kafka;

	HelloWorldSender(KafkaTemplate<String, String> kafka) {
		this.kafka = kafka;
	}

	@Scheduled(fixedRate = 50)
	public void sayHello() {
		String msg = "Hello World, from Spring Boot 3, over Apache Kafka!";
		kafka.send("hello", msg);
	}
}
