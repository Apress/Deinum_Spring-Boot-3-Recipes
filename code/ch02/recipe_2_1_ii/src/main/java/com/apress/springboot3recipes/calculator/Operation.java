package com.apress.springboot3recipes.calculator;

public interface Operation {

	int apply(int lhs, int rhs);
	boolean handles(char op);
}
