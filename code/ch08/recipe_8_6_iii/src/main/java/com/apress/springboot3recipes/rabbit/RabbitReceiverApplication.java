package com.apress.springboot3recipes.rabbit;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderConfirmation;
import com.apress.springboot3recipes.order.OrderGenerator;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Marten Deinum
 */
@SpringBootApplication
@EnableScheduling
public class RabbitReceiverApplication {

    public static void main(String[] args) {
			SpringApplication.run(RabbitReceiverApplication.class, args);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
      return new Jackson2JsonMessageConverter();
    }
}

@Component
class OrderService {

  @RabbitListener(bindings = @QueueBinding(
          exchange = @Exchange(name="orders", type = ExchangeTypes.TOPIC),
          value = @Queue(name = "incoming-orders"),
          key = "new-order"

  ))
  @SendTo("orders/order-confirmation")
  public OrderConfirmation handle(Order order) {
    System.out.println("[RECEIVED] - " + order);
    return new OrderConfirmation(order.id());
  }
}

@Component
class OrderConfirmationService {

  @RabbitListener(bindings = @QueueBinding(
          exchange = @Exchange(name="orders", type = ExchangeTypes.TOPIC),
          value = @Queue(name = "order-confirmations"),
          key = "order-confirmation"
  ))
  public void handle(OrderConfirmation confirmation) {
    System.out.println("[RECEIVED] - " + confirmation);
  }
}

@Component
class OrderSender {


  private final RabbitTemplate rabbit;

  OrderSender(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Scheduled(fixedRate = 256)
  public void sendTime() {

    var order = OrderGenerator.generate();
    rabbit.convertAndSend("orders", "new-order", order);
  }

}
