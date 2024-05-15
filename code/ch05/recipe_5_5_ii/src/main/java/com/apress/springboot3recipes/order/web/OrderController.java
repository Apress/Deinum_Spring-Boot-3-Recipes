package com.apress.springboot3recipes.order.web;

import com.apress.springboot3recipes.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/orders")
class OrderController {

  private final OrderService orderService;

  OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public Mono<String> list(Model model) {
    var orders = new ReactiveDataDriverContextVariable(orderService.findAll(), 5);
    model.addAttribute("orders", orders);
    return Mono.just("orders/list");
  }
}
