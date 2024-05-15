package com.apress.springboot3recipes.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class RabbitSenderApplication {

    public static void main(String[] args) {
			SpringApplication.run(RabbitSenderApplication.class, args);
    }

	@Bean
	public Queue helloQueue() {
		return QueueBuilder.nonDurable("hello").build();
	}

	@Bean
	public Binding helloQueueBinding(Queue queue) {
		return BindingBuilder
			.bind(queue)
			.to(DirectExchange.DEFAULT)
			.withQueueName();
	}
}


@Component
class HelloWorldSender {

	private final RabbitTemplate rabbit;

	HelloWorldSender(RabbitTemplate rabbit) {
		this.rabbit = rabbit;
	}

	@Scheduled(fixedRate = 500)
	public void sendTime() {

		String msg = "Hello World, from Spring Boot 3, over RabbitMQ!";
		rabbit.convertAndSend( "hello", msg);
	}
}

@Component
class HelloWorldReceiver {

	@RabbitListener( queues = "hello")
	public void receive(String msg) {
		System.out.println("Received: " + msg);
	}
}
