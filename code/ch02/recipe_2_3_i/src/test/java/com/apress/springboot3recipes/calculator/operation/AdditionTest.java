package com.apress.springboot3recipes.calculator.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AdditionTest {

	private final Addition addition = new Addition();

	@Test
	public void shouldMatchPlusSign() {
		assertTrue(addition.handles('+'));
		assertFalse(addition.handles('/'));
	}

	@Test
	public void shouldCorrectlyApplyFormula() {
		assertEquals(4, addition.apply(2, 2));
		assertEquals(100, addition.apply(12, 88));
	}
}
