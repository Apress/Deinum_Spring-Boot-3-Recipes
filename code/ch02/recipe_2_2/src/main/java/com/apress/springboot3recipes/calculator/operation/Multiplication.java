package com.apress.springboot3recipes.calculator.operation;

import com.apress.springboot3recipes.calculator.Operation;
import org.springframework.stereotype.Component;

@Component
class Multiplication implements Operation {

    @Override
    public int apply(int lhs, int rhs) {
        return lhs * rhs;
    }

    @Override
    public boolean handles(char op) {
        return '*' == op;
    }


}
