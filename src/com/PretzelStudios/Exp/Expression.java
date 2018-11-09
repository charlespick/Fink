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
                    case '|':
                        types[i] = ExpressionParts.ABSOLUTE_VALUE;
                        break;
                    case 'v':
                        types[i] = ExpressionParts.RADICAL;
                        break;
                    case '~':
                        types[i] = ExpressionParts.ROUND;
                        break;
                    case '%':
                        types[i] = ExpressionParts.MODULO;
                        break;
                    case '(':
                        types[i] = ExpressionParts.OPEN_PARENS;
                        break;
                    case ')':
                        types[i] = ExpressionParts.CLOSING_PARENS;
                        break;
                    default: //unless it isn't supported
                        throw new UnsupportedOperatorException(this, possibility);
                }
            }
        }
        if (!verify()|length == 1) { //check if the stored expression is valid
            throw new InvalidExpressionException(this);
        }
    }

    public Expression() {
        length = 0;
        types = new ExpressionParts[0];
        operands = new double[0];
    }

    public Expression(double operand) {
        length = 1;
        types = new ExpressionParts[1];
        types[0] = ExpressionParts.OPERAND;
        operands = new double[1];
        operands[0] = operand;
    }

    public void update() {
        length = types.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j <= this.length - 1; j++) {
            if (types[j] == ExpressionParts.OPERAND) {
                sb.append(operands[j]);
            } else {
                sb.append(types[j].symbol);
            }
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    public boolean verify() {
        int parenthesesCount = 0;
        for (int i = 0; i < types.length; i++) { //Iterate through arrays

            switch (types[i]) {
                case DIVISION_SYMBOL:
                    if (!verifyTwoOp(types, i)) {
                        return false;
                    }
                    break;
                case MULTIPLICATION_SYMBOL:
                    if (!verifyTwoOp(types, i)) {
                        return false;
                    }
                    break;
                case SUBTRACTION_SYMBOL:
                    if (!verifyTwoOp(types, i)) {
                        return false;
                    }
                    break;
                case PLUS_SYMBOL:
                    if (!verifyTwoOp(types, i)) {
                        return false;
                    }
                    break;
                case COS_SYMBOL:
                    if (!verifyOneOp(types, i)) {
                        return false;
                    }
                    break;
                case SIN_SYMBOL:
                    if (!verifyOneOp(types, i)) {
                        return false;
                    }
                    break;
                case TAN_SYMBOL:
                    if (!verifyOneOp(types, i)) {
                        return false;
                    }
                    break;
                case EXPONANT_SYMBOL:
                    if (!verifyTwoOp(types, i)) {
                        return false;
                    }
                    break;
                case ABSOLUTE_VALUE:
                    if (!verifyOneOp(types, i)) {
                        return false;
                    }
                    break;
                case RADICAL:
                    if (!verifyOneOp(types, i)) {
                        return false;
                    }
                    break;
                case OPEN_PARENS:
                    parenthesesCount++;
                    if(types[i+1]==ExpressionParts.CLOSING_PARENS){
                        return false;
                    }
                    break;
                case CLOSING_PARENS:
                    parenthesesCount--;
                    break;
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
                            case ABSOLUTE_VALUE:
                                return false;
                            case ROUND:
                                return false;
                            case RADICAL:
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
                        }
                    }
            }

        }
        if (parenthesesCount != 0) {
            return false;
        }
        return true;
    }

    private boolean verifyOneOp(ExpressionParts[] peices, int indexToCheck) {
        try {
            if (peices[indexToCheck + 1] != ExpressionParts.OPERAND) {
                if (peices[indexToCheck + 1] == ExpressionParts.COS_SYMBOL | peices[indexToCheck + 1] == ExpressionParts.TAN_SYMBOL | peices[indexToCheck + 1] == ExpressionParts.SIN_SYMBOL | peices[indexToCheck + 1] == ExpressionParts.ABSOLUTE_VALUE | peices[indexToCheck + 1] == ExpressionParts.RADICAL | peices[indexToCheck + 1] == ExpressionParts.ROUND) {
                    return true;
                }

                return false;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    private boolean verifyTwoOp(ExpressionParts[] peices, int indexToCheck) {
        if (indexToCheck == 0) {
            return false;
        } else if (indexToCheck + 1 == peices.length) {
            return false;
        } else {
            if (peices[indexToCheck - 1] != ExpressionParts.OPERAND & peices[indexToCheck - 1] != ExpressionParts.CLOSING_PARENS) { //if the thing before is an operator
                return false;
            }
            if (peices[indexToCheck + 1] != ExpressionParts.OPERAND & peices[indexToCheck + 1] != ExpressionParts.OPEN_PARENS) { //if the thing after is an operator
                switch (peices[indexToCheck + 1]) {
                    case COS_SYMBOL:
                        break;
                    case SIN_SYMBOL:
                        break;
                    case TAN_SYMBOL:
                        break;
                    case ABSOLUTE_VALUE:
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }
    }

}
