package com.apress.springboot3recipes.calculator.operation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiplicationTest {

	private final Multiplication operation = new Multiplication();

	@Test
	public void shouldMatchSign() {
		assertTrue(operation.handles('*'));
		assertFalse(operation.handles('/'));
	}

	@Test
	public void shouldCorrectlyApplyFormula() {
		assertEquals(4, operation.apply(2, 2));
		assertEquals(120, operation.apply(12, 10));
	}
}
