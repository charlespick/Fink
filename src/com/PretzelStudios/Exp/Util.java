package com.PretzelStudios.Exp;

public abstract class Util {

    public static void resolveTwoOp(Expression full, Expression peice, int beginningIndex) {
        //make new arrays
        int newLen = full.length - 2;
        ExpressionParts[] parts = new ExpressionParts[newLen];
        double[] operands = new double[newLen];

        //move data before into new arrays
        for (int i = 0; i < beginningIndex; i++) {
            parts[i] = full.types[i];
            operands[i] = full.operands[i];
        }

        //move in new peice
        parts[beginningIndex] = ExpressionParts.OPERAND;
        operands[beginningIndex] = peice.operands[0];

        //move in data after
        for (int i = beginningIndex + 3; i < full.length; i++) {
            parts[i - 2] = full.types[i];
            operands[i - 2] = full.operands[i];
        }

        //save off arrays
        full.operands = operands;
        full.types = parts;
        full.length = newLen;
    }

    public static void resolveOneOp(Expression full, Expression peice, int beginningIndex) {
        //make new arrays
        int newLen = full.length - 1;
        ExpressionParts[] parts = new ExpressionParts[newLen];
        double[] operands = new double[newLen];

        //move data before into new arrays
        for (int i = 0; i < beginningIndex; i++) {
            parts[i] = full.types[i];
            operands[i] = full.operands[i];
        }

        //move in new peice
        parts[beginningIndex] = ExpressionParts.OPERAND;
        operands[beginningIndex] = peice.operands[0];

        //move in data after
        for (int i = beginningIndex + 2; i < full.length; i++) {
            parts[i - 1] = full.types[i];
            operands[i - 1] = full.operands[i];
        }

        //save off arrays
        full.operands = operands;
        full.types = parts;
        full.length = newLen;
    }

}