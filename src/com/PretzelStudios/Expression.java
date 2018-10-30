package com.PretzelStudios;

import java.util.Scanner;

public class Expression {
    private ExpressionParts[] types;
    private double[] operands;
    private String[] expression;

    public Expression(String[] expression) throws Exception {
        //Save original input
        this.expression = expression;

        //Set sizes
        types = new ExpressionParts[expression.length];
        operands = new double[expression.length];

        // Separate out into expression parts
        for (int i = 0; i < this.expression.length; i++) { //Iterate through
            Scanner sc = new Scanner(expression[i]); //Will be used to test what is what
            if (sc.hasNextDouble()) { //Tests for number
                types[i] = ExpressionParts.OPERAND; //Saves that this part was a number
                operands[i] = sc.nextDouble(); //Saves the number
            } else { //might be an operator
                char[] possibility = expression[i].toCharArray(); //Might be more than 1 character
                if (possibility.length > 1) { //if so, fail
                    throw new Exception("Not a supported operator"); //cry and throw things around
                }
                switch (expression[i]) { //otherwise, determine what it is and save it
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
                    default: //unless it isn't supported
                        throw new Exception("Not a supported operator");
                }
            }


        }
        for (int j = 0; j < this.expression.length; j++) {
            if (types[j] == ExpressionParts.OPERAND) {
                System.out.print(operands[j]);
            } else {
                System.out.print(types[j]);
            }
            System.out.print(" ");
        }
        System.out.println();
    }


}
