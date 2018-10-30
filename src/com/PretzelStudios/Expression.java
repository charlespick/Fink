package com.PretzelStudios;

import java.util.Scanner;

public class Expression {
    private ExpressionParts[] types;
    private double[] operands;
    private String[] expression;

    public Expression(String[] expression) throws Exception {
        operands = new double[expression.length];
        expression = new String[expression.length];
        types = new ExpressionParts[expression.length];
        for (int i = 0; i < expression.length; i++) {
            this.expression = expression;
            Scanner sc = new Scanner(expression[i]);
            if (sc.hasNextDouble()) {
                types[i] = ExpressionParts.OPERAND;
                operands[i] = sc.nextDouble();
            } else {
                char[] possibility = expression[i].toCharArray();
                if (possibility.length > 1) {
                    throw new Exception("Not a supported operator");
                }
                switch (expression[i]) {
                    case "/":
                        types[i] = ExpressionParts.DIVISION_SYMBOL;
                        break;
                    case "*":
                        types[i] = ExpressionParts.MULTIPLICATION_SYMBOL;
                        break;
                    case "-":
                        types[i] = ExpressionParts.SUBTRACTION_SYMBOL;
                        break;
                    case "+":
                        types[i] = ExpressionParts.PLUS_SYMBOL;
                        break;
                    case "c":
                        types[i] = ExpressionParts.COS_SYMBOL;
                        break;
                    case "s":
                        types[i] = ExpressionParts.SIN_SYMBOL;
                        break;
                    case "t":
                        types[i] = ExpressionParts.TAN_SYMBOL;
                        break;
                    case "^":
                        types[i] = ExpressionParts.EXPONANT_SYMBOL;
                        break;
                    case "(":
                        types[i] = ExpressionParts.OPEN_PARENS;
                        break;
                    case ")":
                        types[i] = ExpressionParts.CLOSING_PARENS;
                        break;
                    default:
                        throw new Exception("Not a supported operator");
                }
            }

        }
    }


}
