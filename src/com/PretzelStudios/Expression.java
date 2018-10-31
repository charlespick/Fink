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
                    case "cos":
                        types[i] = ExpressionParts.COS_SYMBOL;
                        break;
                    case "sin":
                        types[i] = ExpressionParts.SIN_SYMBOL;
                        break;
                    case "tan":
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

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < this.expression.length; j++) {
            if (types[j] == ExpressionParts.OPERAND) {
                sb.append(operands[j]);
            } else {
                sb.append(types[j].symbol());
            }
            sb.append(" ");
        }
        return sb.toString();
    }

    public boolean verify() {
        for (int i = 0; i < types.length; i++) {
            if (types[i] != ExpressionParts.OPERAND) {
                switch (types[i]) { //otherwise, determine what it is and save it
                    case DIVISION_SYMBOL:
                        if (i < 1) {
                            return false;
                        }
                        if (types[i - 1] != ExpressionParts.OPERAND) {
                            if (types[i - 1] != ExpressionParts.CLOSING_PARENS) {
                                return false;
                            }
                        }
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            if (!(types[i + 1] == ExpressionParts.SIN_SYMBOL | types[i + 1] == ExpressionParts.TAN_SYMBOL | types[i + 1] == ExpressionParts.COS_SYMBOL)) {
                                if (types[i + 1] != ExpressionParts.OPEN_PARENS) {
                                    return false;
                                }
                            }
                        }
                        break;
                    case MULTIPLICATION_SYMBOL:
                        if (i < 1) {
                            return false;
                        }
                        if (types[i - 1] != ExpressionParts.OPERAND) {
                            if (types[i - 1] != ExpressionParts.CLOSING_PARENS) {
                                return false;
                            }
                        }
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            if (!(types[i + 1] == ExpressionParts.SIN_SYMBOL | types[i + 1] == ExpressionParts.TAN_SYMBOL | types[i + 1] == ExpressionParts.COS_SYMBOL)) {
                                if (types[i + 1] != ExpressionParts.OPEN_PARENS) {
                                    return false;
                                }
                            }
                        }
                        break;
                    case SUBTRACTION_SYMBOL:
                        if (i < 1) {
                            return false;
                        }
                        if (types[i - 1] != ExpressionParts.OPERAND) {
                            if (types[i - 1] != ExpressionParts.CLOSING_PARENS) {
                                return false;
                            }
                        }
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            if (!(types[i + 1] == ExpressionParts.SIN_SYMBOL | types[i + 1] == ExpressionParts.TAN_SYMBOL | types[i + 1] == ExpressionParts.COS_SYMBOL)) {
                                if (types[i + 1] != ExpressionParts.OPEN_PARENS) {
                                    return false;
                                }
                            }
                        }
                        break;
                    case PLUS_SYMBOL:
                        if (i < 1) {
                            return false;
                        }
                        if (types[i - 1] != ExpressionParts.OPERAND) {
                            if (types[i - 1] != ExpressionParts.CLOSING_PARENS) {
                                return false;
                            }
                        }
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            if (!(types[i + 1] == ExpressionParts.SIN_SYMBOL | types[i + 1] == ExpressionParts.TAN_SYMBOL | types[i + 1] == ExpressionParts.COS_SYMBOL)) {
                                if (types[i + 1] != ExpressionParts.OPEN_PARENS) {
                                    return false;
                                }
                            }
                        }
                        break;
                    case COS_SYMBOL:
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            return false;
                        }
                        break;
                    case SIN_SYMBOL:
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            return false;
                        }
                        break;
                    case TAN_SYMBOL:
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            return false;
                        }
                        break;
                    case EXPONANT_SYMBOL:
                        if (i < 1) {
                            return false;
                        }
                        if (types[i - 1] != ExpressionParts.OPERAND) {
                            return false;
                        }
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            if (!(types[i + 1] == ExpressionParts.SIN_SYMBOL | types[i + 1] == ExpressionParts.TAN_SYMBOL | types[i + 1] == ExpressionParts.COS_SYMBOL)) {
                                return false;
                            }
                        }
                        break;
                    case OPEN_PARENS:
                        if (types[i + 1] != ExpressionParts.OPERAND) {
                            return false;
                        }
                        break;
                    case CLOSING_PARENS:
                        if (i < 4) {
                            return false;
                        }
                        if (types[i - 1] != ExpressionParts.OPERAND) {
                            return false;
                        }
                        break;
                    case OPERAND:
                        if (i > 0) {
                            if (types[i - 1] == ExpressionParts.OPERAND) {
                                return false;
                            }
                            if (types[i + 1] == ExpressionParts.OPERAND) {
                                return false;
                            }
                        } else {
                            if (types[i + 1] == ExpressionParts.OPERAND) {
                                return false;
                            }
                        }
                        break;
                }
            }
        }
        return true;
    }
}
