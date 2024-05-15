package com.apress.springboot3recipes.calculator.operation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionTest {

	private final Addition addition = new Addition();

	@Test
	void shouldMatchPlusSign() {
		assertThat(addition.handles('+')).isTrue();
		assertThat(addition.handles('/')).isFalse();
	}

	@Test
	void shouldCorrectlyApplyFormula() {
		assertThat(addition.apply(2, 2)).isEqualTo(4);
		assertThat(addition.apply(12, 88)).isEqualTo(100);
	}
}
