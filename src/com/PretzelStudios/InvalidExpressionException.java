package com.PretzelStudios;

public class InvalidExpressionException extends Exception {
    private Expression invalidExp;
    private String message;
    public  InvalidExpressionException(Expression invalidExp){
        this.invalidExp = invalidExp;
    }
    public  InvalidExpressionException(String message){
this.message = message;
    }
    public  InvalidExpressionException(Expression invalidExp, String message){
this.invalidExp = invalidExp;
this.message = message;
    }

    public String getMsg(){
        return message;
    }
    public Expression getInvalidExp(){
        return invalidExp;
    }
}
