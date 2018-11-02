package com.PretzelStudios.Exp;

public enum ExpressionParts {
    DIVISION_SYMBOL('/'), MULTIPLICATION_SYMBOL('*'), PLUS_SYMBOL('+'), SUBTRACTION_SYMBOL('-'), SIN_SYMBOL('s'), TAN_SYMBOL('t'), COS_SYMBOL('c'), EXPONANT_SYMBOL('^'), OPEN_PARENS('('), CLOSING_PARENS(')'), OPERAND('#'), ABSOLUTE_VALUE('|'), RADICAL('V');
    public final char symbol;
    ExpressionParts(char symbol) {
        this.symbol = symbol;
    }
}