package com.PretzelStudios.Expression;

public class UnsupportedExpressionException extends InvalidExpressionException {
    private String reason;

    public UnsupportedExpressionException(String reason){
        this.reason = reason;
    }
}
