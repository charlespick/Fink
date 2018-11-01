package com.PretzelStudios.Exp;

public class InvalidExpressionException extends Exception {
    private Expression invalidExp;

    public InvalidExpressionException(Expression invalidExp) {
        this.invalidExp = invalidExp;
    }
//    public InvalidExpressionException(){
//
//    }

    public Expression getInvalidExp() {
        return invalidExp;
    }
}
