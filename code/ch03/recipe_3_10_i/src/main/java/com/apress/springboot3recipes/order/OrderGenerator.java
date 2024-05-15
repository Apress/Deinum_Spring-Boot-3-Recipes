package com.apress.springboot3recipes.order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class OrderGenerator {

  public Order generate() {
    var amount = ThreadLocalRandom.current().nextDouble(1000.00);
    return new Order(UUID.randomUUID().toString(), BigDecimal.valueOf(amount));
  }
}
