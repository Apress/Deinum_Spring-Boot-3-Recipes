package com.apress.springboot3recipes.rabbit;

import com.apress.springboot3recipes.order.OrderGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    public Jackson2JsonMessageConverter jsonMessageConverter() {
      return new Jackson2JsonMessageConverter();
    }
}

@Component
class OrderSender {

  private final RabbitTemplate rabbit;

  OrderSender(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Scheduled(fixedRate = 250)
  public void sendTime() {
    var order = OrderGenerator.generate();
    rabbit.convertAndSend("orders", "new-order", order);
  }
}
