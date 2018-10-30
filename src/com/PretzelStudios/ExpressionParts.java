package com.PretzelStudios;

public enum ExpressionParts {
    DIVISION_SYMBOL('/'), MULTIPLICATION_SYMBOL('*'), PLUS_SYMBOL('+'), SUBTRACTION_SYMBOL('-'), SIN_SYMBOL('s'), TAN_SYMBOL('t'), COS_SYMBOL('c'), EXPONANT_SYMBOL('^'), OPEN_PARENS('('), CLOSING_PARENS(')'), OPERAND('#');

    private final char symbol;

    ExpressionParts(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return this.symbol;
    }
}