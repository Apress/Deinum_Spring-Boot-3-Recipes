package com.apress.springboot3recipes.calculator.web;

import com.apress.springboot3recipes.calculator.Calculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Calculator calculator;

    @Test
    void successfulCalculation() throws Exception {

        when(calculator.calculate(10,5,'*')).thenReturn(50);

        var request = MockMvcRequestBuilders.get("/calculate")
                .param("lhs", "10")
                .param("rhs", "5")
                        .param("op", "*");

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
								.andExpect(MockMvcResultMatchers.content().string("50"));

				verify(calculator, times(1)).calculate(10, 5, '*');
    }
}
