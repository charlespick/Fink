package com.PretzelStudios.Exp;

public class UnsupportedOperatorException extends InvalidExpressionException {
    public char[] unsupportedOp;

    public UnsupportedOperatorException(Expression invalidExpression, char[] unsupportedOp){
        super(invalidExpression);
        this.unsupportedOp = unsupportedOp;
    }
}
