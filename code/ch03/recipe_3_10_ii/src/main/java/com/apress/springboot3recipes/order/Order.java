package com.apress.springboot3recipes.order;

import java.math.BigDecimal;

public record Order (String id, BigDecimal amount) { }

