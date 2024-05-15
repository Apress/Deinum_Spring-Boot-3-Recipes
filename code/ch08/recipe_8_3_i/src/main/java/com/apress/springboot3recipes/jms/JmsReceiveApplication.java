package com.apress.springboot3recipes.jms;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class JmsReceiveApplication {

    public static void main(String[] args) {
			SpringApplication.run(JmsReceiveApplication.class, args);
    }

}

@Component
class CurrentDateTimeService {

	@JmsListener(destination = "time-queue")
	public void handle(TextMessage msg) throws JMSException {
		System.out.println("[RECEIVED] - " + msg.getText());
	}
}

@Component
class MessageSender {

	private final JmsTemplate jms;

	MessageSender(JmsTemplate jms) {
		this.jms = jms;
	}

	@Scheduled(fixedRate = 1000)
	public void sendTime() {
		var msg = "Current Date & Time is: " + LocalDateTime.now();
		jms.convertAndSend("time-queue", msg);
	}
}
