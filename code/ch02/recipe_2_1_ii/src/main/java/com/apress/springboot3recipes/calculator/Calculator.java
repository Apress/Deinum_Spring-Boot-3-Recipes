package com.apress.springboot3recipes.calculator;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Calculator {

	private final Collection<Operation> operations;

	public Calculator(Collection<Operation> operations) {
		this.operations = operations;
	}

	public void calculate(int lhs, int rhs, char op) {
		operations.stream()
				.filter((operation) -> operation.handles(op))
				.map((operation) -> operation.apply(lhs, rhs))
				.peek( (result) -> System.out.printf("%d %s %d = %s%n", lhs, op, rhs, result))
				.findFirst()
				.orElseThrow(() ->
				new IllegalArgumentException("Unknown operation " + op));
	}
}
