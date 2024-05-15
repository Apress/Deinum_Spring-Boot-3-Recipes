package com.apress.springboot3recipes.order;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

  private final List<Order> orders = new ArrayList<>();

  public List<Order> findAll() {
    return List.copyOf(orders);
  }

  public void save(Order order) {
    this.orders.add(order);
  }
}
