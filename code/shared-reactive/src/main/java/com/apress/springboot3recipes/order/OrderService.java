package com.apress.springboot3recipes.order;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

  private final List<Order> orders = new ArrayList<>();

  public Flux<Order> findAll() {
    return Flux.fromIterable(orders);
  }

  public Mono<Void> save(Order order) {
    this.orders.add(order);
    return Mono.empty();
  }
}
