package com.PretzelStudios.Expression;

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
                switch (possibility[0]) { //otherwise, determine what it is and save it
                    case '/':
                        types[i] = ExpressionParts.DIVISION_SYMBOL;
                        break;
                    case '*':
                        types[i] = ExpressionParts.MULTIPLICATION_SYMBOL;
                        break;
                    case '-':
                        types[i] = ExpressionParts.SUBTRACTION_SYMBOL;
                        break;
                    case '+':
                        types[i] = ExpressionParts.PLUS_SYMBOL;
                        break;
                    case 'c':
                        types[i] = ExpressionParts.COS_SYMBOL;
                        break;
                    case 's':
                        types[i] = ExpressionParts.SIN_SYMBOL;
                        break;
                    case 't':
                        types[i] = ExpressionParts.TAN_SYMBOL;
                        break;
                    //Removed support for these for now
//                    case 'cos':
//                        types[i] = ExpressionParts.COS_SYMBOL;
//                        break;
//                    case 'sin':
//                        types[i] = ExpressionParts.SIN_SYMBOL;
//                        break;
//                    case 'tan':
//                        types[i] = ExpressionParts.TAN_SYMBOL;
//                        break;
                    case '^':
                        types[i] = ExpressionParts.EXPONANT_SYMBOL;
                        break;
                    case '(':
                        throw new UnsupportedExpressionException("Open parentheses not supported not supposed at this time");
                    case ')':
                        throw new UnsupportedExpressionException("Closing parentheses not supported not supposed at this time");
                    default: //unless it isn't supported
                        throw new UnsupportedExpressionException(possibility + " is not a supported operator");
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
                    if (!verify4Funct(types, i)) {
                        return false;
                    }
                    break;
                case MULTIPLICATION_SYMBOL:
                    if (!verify4Funct(types, i)) {
                        return false;
                    }
                    break;
                case SUBTRACTION_SYMBOL:
                    if (!verify4Funct(types, i)) {
                        return false;
                    }
                    break;
                case PLUS_SYMBOL:
                    if (!verify4Funct(types, i)) {
                        return false;
                    }
                    break;
                case COS_SYMBOL:
                    if (!verifyTrig(types, i)) {
                        return false;
                    }
                    break;
                case SIN_SYMBOL:
                    if (!verifyTrig(types, i)) {
                        return false;
                    }
                    break;
                case TAN_SYMBOL:
                    if (!verifyTrig(types, i)) {
                        return false;
                    }
                    break;
                case EXPONANT_SYMBOL:
                    if (!verify4Funct(types, i)) {
                        return false;
                    }
                    break;
                case OPEN_PARENS:
                    return false; //not supported right now
                case CLOSING_PARENS:
                    return false; //not supported right now
                case OPERAND:
                    if (types.length == 1) {

                    } else if (i == 0) {
                        switch (types[i + 1]) {
                            case OPERAND:
                                return false;
                            case TAN_SYMBOL:
                                return false;
                            case SIN_SYMBOL:
                                return false;
                            case COS_SYMBOL:
                                return false;
                            case OPEN_PARENS:
                                return false;
                            case CLOSING_PARENS:
                                return false;
                        }
                    } else if (i + 1 == types.length) {
                        switch (types[i - 1]) {
                            case CLOSING_PARENS:
                                return false;
                            case OPEN_PARENS:
                                return false;
                            case OPERAND:
                                return false;
                        }
                    } else {
                        switch (types[i + 1]) {
                            case OPERAND:
                                return false;
                            case OPEN_PARENS:
                                return false;
                            case CLOSING_PARENS:
                                return false;
                        }
                    }
            }

        }
        return true;
    }

    private boolean verifyTrig(ExpressionParts[] peices, int indexToCheck) {
        try {
            if (peices[indexToCheck + 1] != ExpressionParts.OPERAND) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private boolean verify4Funct(ExpressionParts[] peices, int indexToCheck) {
        if (indexToCheck == 0) {
            return false;
        } else if (indexToCheck + 1 == peices.length) {
            return false;
        } else {
            if (peices[indexToCheck - 1] != ExpressionParts.OPERAND) { //if the thing before is an operator
                return false;
            }
            if (peices[indexToCheck + 1] != ExpressionParts.OPERAND) { //if the thing after is an operator
                switch (peices[indexToCheck + 1]) {
                    case COS_SYMBOL:
                        break;
                    case SIN_SYMBOL:
                        break;
                    case TAN_SYMBOL:
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }
    }


}
