package com.PretzelStudios;

import java.util.Scanner;

public class Expression {
    public int length;
    ExpressionParts[] types;
    double[] operands;
    private String[] expression;

    public Expression(String[] expression) throws InvalidExpressionException {
        //Save original input
        this.expression = expression;
        this.length = expression.length;
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
                    throw new InvalidExpressionException(possibility + " is not a supported operator"); //cry and throw things around
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
                        throw new InvalidExpressionException(possibility + " is not a supported operator");
                }
            }


        }
        if (!verify()) {
            throw new InvalidExpressionException(this, "Invalid expression");
        } else {
            System.out.println("Expression " + toString() + " is valid");
        }


    }

    public Expression(Expression e1, Expression e2) throws InvalidExpressionException {
        length = e1.length + e2.length;
        types = new ExpressionParts[length];
        expression = new String[length];
        operands = new double[length];

        System.arraycopy(e1.types, 0, types, 0, e1.length);
        System.arraycopy(e2.types, 0, types, e1.length, e2.length);
        System.arraycopy(e1.operands, 0, operands, 0, e1.length);
        System.arraycopy(e2.operands, 0, operands, e1.length, e2.length);
        System.arraycopy(e1.expression, 0, expression, 0, e1.length);
        System.arraycopy(e2.expression, 0, expression, e1.length, e2.length);

        if (!verify()) {
            throw new InvalidExpressionException(this, "Invalid expression");
        } else {
            System.out.println("Expression " + toString() + " is valid");
        }
    }

    public String getOriginal() {
        return expression.toString();
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

            switch (types[i]) { //otherwise, determine what it is and save it
                case DIVISION_SYMBOL:
                    if (i+1>types.length) {
                        return false;
                    }
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
                    if (i+1>types.length) {
                        return false;
                    }
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
                    if (i+1>types.length) {
                        return false;
                    }
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
                    if (i+1>types.length) {
                        return false;
                    }
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
                    if (i+1>types.length) {
                        return false;
                    }
                    if (types[i + 1] != ExpressionParts.OPERAND) {
                        return false;
                    }
                    break;
                case SIN_SYMBOL:
                    if (i+1>types.length) {
                        return false;
                    }
                    if (types[i + 1] != ExpressionParts.OPERAND) {
                        return false;
                    }
                    break;
                case TAN_SYMBOL:
                    if (i+1>types.length) {
                        return false;
                    }
                    if (types[i + 1] != ExpressionParts.OPERAND) {
                        return false;
                    }
                    break;
                case EXPONANT_SYMBOL:
                    if (i+1>types.length) {
                        return false;
                    }
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
                    if (i+1>types.length) {
                        return false;
                    }
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
                        if (i+1 < types.length) {
                            if (types[i + 1] == ExpressionParts.OPERAND) {
                                return false;
                            }
                        }
                    } else {
                        if (i+1 < types.length) {
                            if (types[i + 1] == ExpressionParts.OPERAND) {
                                return false;
                            }
                        }
                    }
                    break;
            }

        }
        return true;
    }
}
