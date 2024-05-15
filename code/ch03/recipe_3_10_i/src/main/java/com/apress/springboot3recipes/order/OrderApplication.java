package com.apress.springboot3recipes.order;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApplication {

  public static void main(String[] args) {
      SpringApplication.run(OrderApplication.class, args);
  }

  @Bean
  ApplicationRunner orderInitializer(OrderService orders) {
    return args -> {
      var generator = new OrderGenerator();
      for (int i = 0; i < 25; i++) {
        orders.save(generator.generate());
      }
    };
  }
}
