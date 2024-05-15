package com.apress.springboot3recipes.calculator.web;

import com.apress.springboot3recipes.calculator.Calculator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    private final Calculator calculator;

    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping("/calculate")
    public int calculate(Calculation calc) {
        return calculator.calculate(calc.lhs(), calc.rhs(), calc.op());
    }
}

record Calculation(int lhs, int rhs, char op) {}
