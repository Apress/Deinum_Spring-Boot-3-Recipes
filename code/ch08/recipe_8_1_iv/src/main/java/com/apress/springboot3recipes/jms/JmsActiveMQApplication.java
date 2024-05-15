package com.apress.springboot3recipes.jms;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;


@SpringBootApplication
public class JmsActiveMQApplication {

	private static final String MSG = "\tName: %100s, Type: %s\n";

	public static void main(String[] args) {
		var ctx =
			SpringApplication.run(JmsActiveMQApplication.class, args);

		System.out.println("# Beans: " + ctx.getBeanDefinitionCount());

		var names = ctx.getBeanDefinitionNames();
		Stream.of(names)
			.filter(name -> name.toLowerCase().contains("jms") || ctx.getType(name).getName().contains("jms"))
			.forEach(name -> {
				var bean = ctx.getBean(name);
				System.out.printf(MSG, name, bean.getClass().getSimpleName());
			});
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		var connectionFactory =
			new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
		connectionFactory.setClientID("someId");
		return connectionFactory;
	}
}
