package com.apress.springboot3recipes.order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

public class OrderGenerator {

	public static Order generate() {
		var amount = ThreadLocalRandom.current().nextDouble(1000.00);
		return new Order(UUID.randomUUID().toString(), BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_EVEN));
	}

	public static Flux<Order> generate(long count) {
		return Flux.generate(OrderGenerator::generate, (BiFunction<Order, SynchronousSink<Order>, Order>) (order, sink) -> {
			sink.next(order);
			return generate();
		}).take(count);
	}
}
