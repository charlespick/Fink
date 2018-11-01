package com.PretzelStudios.Exp;

import java.util.Scanner;

public class Expression {
    public int length;
    public ExpressionParts[] types;
    public double[] operands;

    public Expression(String[] expression) throws InvalidExpressionException {
        //Save original input

        this.length = expression.length;
        //Set sizes
        types = new ExpressionParts[expression.length];
        operands = new double[expression.length];

        // Separate out into expression parts
        for (int i = 0; i < this.length; i++) { //Iterate through
            Scanner sc = new Scanner(expression[i]); //Will be used to test what is what
            if (sc.hasNextDouble()) { //Tests for number
                types[i] = ExpressionParts.OPERAND; //Saves that this part was a number
                operands[i] = sc.nextDouble(); //Saves the number
            } else { //might be an operator
                char[] possibility = expression[i].toCharArray(); //Might be more than 1 character
                if (possibility.length > 1) { //if so, fail
                    throw new UnsupportedOperatorException(this, possibility); //cry and throw things around
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
                    case '^':
                        types[i] = ExpressionParts.EXPONANT_SYMBOL;
                        break;
                    case '(':
                        throw new UnsupportedOperatorException(this, possibility);
                    case ')':
                        throw new UnsupportedOperatorException(this, possibility);
                    default: //unless it isn't supported
                        throw new UnsupportedOperatorException(this, possibility);
                }
            }
        }
    }

    public Expression(double operand) {
        length = 1;
        types = new ExpressionParts[1];
        types[0] = ExpressionParts.OPERAND;
        operands = new double[1];
        operands[0] = operand;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j <= this.length - 1; j++) {
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
                if (peices[indexToCheck + 1] == ExpressionParts.COS_SYMBOL | peices[indexToCheck + 1] == ExpressionParts.TAN_SYMBOL | peices[indexToCheck + 1] == ExpressionParts.SIN_SYMBOL) {
                    return true;
                }

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
