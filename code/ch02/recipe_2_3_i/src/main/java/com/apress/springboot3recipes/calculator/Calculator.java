package com.apress.springboot3recipes.calculator;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Calculator {

    private final Collection<Operation> operations;

    public Calculator(Collection<Operation> operations) {
        this.operations = operations;
    }

    public int calculate(int lhs, int rhs, char op) {

        for (var operation : operations) {
            if (operation.handles(op)) {
                var result = operation.apply(lhs, rhs);
                System.out.printf("%d %s %d = %s%n", lhs, op, rhs, result);
                return result;
            }
        }
        throw new IllegalArgumentException("Unknown operation " + op);
    }
}
