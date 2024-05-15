package com.apress.springboot3recipes.calculator.operation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MultiplicationTest {

	private final Multiplication addition = new Multiplication();

	@Test
	void shouldMatchSign() {
		assertThat(addition.handles('*')).isTrue();
		assertThat(addition.handles('/')).isFalse();
	}

	@Test
	void shouldCorrectlyApplyFormula() {
		assertThat(addition.apply(2, 2)).isEqualTo(4);
		assertThat(addition.apply(12, 10)).isEqualTo(120);
	}
}
