package com.apress.springboot3recipes.order.rest;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderService;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class OrderController {

  private final OrderService orderService;
  private final TaskExecutor executor;

  public OrderController(OrderService orderService, TaskExecutor executor) {
    this.orderService = orderService;
    this.executor = executor;
  }

  @GetMapping(value = "/orders")
  public SseEmitter orders() {
    var emitter = new SseEmitter();
    executor.execute(() -> {
      var orders = orderService.findAll();
      try {
        for (var order : orders) {
          sendAndDelay(emitter, order);
        }
        emitter.complete();
      } catch (IOException e) {
        emitter.completeWithError(e);
      }
    });
    return emitter;
  }

  private void sendAndDelay(ResponseBodyEmitter emitter, Order order) throws IOException{
    var eventBuilder = SseEmitter.event();
    emitter.send(
        eventBuilder
            .data(order, MediaType.APPLICATION_JSON)
            .name("order-created")
            .id(String.valueOf(order.hashCode())).build());
    randomDelay();
  }

  private void randomDelay() {
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(150));
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
