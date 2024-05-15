package com.apress.springboot3recipes.order.rest;

import com.apress.springboot3recipes.order.Order;
import com.apress.springboot3recipes.order.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/orders")
	public Flux<Order> orders() {
		return orderService.findAll().delayElements(Duration.ofMillis(32));
	}

	@GetMapping(value = "/orders", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<Order>> orderEvents() {
		return orderService.findAll()
				.map(this::toEvent)
				.delayElements(Duration.ofMillis(32));
	}

	private ServerSentEvent<Order> toEvent(Order order) {
		return ServerSentEvent.builder(order)
				.event("order-event")
				.id(order.id()).build();
	}
}
