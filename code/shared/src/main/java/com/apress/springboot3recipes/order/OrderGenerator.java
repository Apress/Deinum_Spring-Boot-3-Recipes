package com.apress.springboot3recipes.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class OrderGenerator {

	public static Order generate() {
		var rnd = ThreadLocalRandom.current().nextDouble(1000.00);
		var amount = BigDecimal.valueOf(rnd).setScale(2, RoundingMode.HALF_EVEN);
		var id = UUID.randomUUID().toString();
		return new Order(id, amount);
	}

	public static List<Order> generate(long count) {
		return LongStream.range(0, count)
			.mapToObj( (x) -> generate()).toList();
	}
}
