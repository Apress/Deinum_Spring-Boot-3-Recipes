package com.apress.springboot3recipes.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class CalculatorApplicationWithMockTests {

	@MockBean(name = "division")
	private Operation mockOperation;

	@Autowired
	private Calculator calculator;

	@Test
	public void calculatorShouldHave3Operations() {
		var operations = ReflectionTestUtils.getField(calculator,
						"operations");
		assertEquals(3, ((Collection<Operation>) operations).size());
	}

	@Test
	public void mockDivision(CapturedOutput capture) {
		when(mockOperation.handles('/')).thenReturn(true);
		when(mockOperation.apply(14, 7)).thenReturn(2);

		calculator.calculate(14, 7, '/');
		assertTrue(capture.getOut().contains("14 / 7 = 2"));
	}

	@Test
	public void doingMultiplicationShouldSucceed(CapturedOutput capture) {
		calculator.calculate(12, 13, '*');
		assertTrue(capture.getOut().contains("12 * 13 = 156"));
	}

	@Test
	public void doingAdditionShouldSucceed(CapturedOutput capture) {
		calculator.calculate(12, 13, '+');
		assertTrue(capture.getOut().contains("12 + 13 = 25"));
	}

	@Test
	public void doingDivisionShouldFail() {
		assertThrows(IllegalArgumentException.class,
						() -> calculator.calculate(12, 13, '/'));
	}
}
