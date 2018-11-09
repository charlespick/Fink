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

    public static Expression takeMiddle(Expression full, int beginningIndex, int endingIndex) {
        //Make new arrays
        Expression e = new Expression();
        ExpressionParts[] ep = new ExpressionParts[endingIndex - beginningIndex + 1];
        double[] operands = new double[endingIndex - beginningIndex + 1];

        //Move in targeted data
        for (int i = 0; i < endingIndex - beginningIndex + 1; i++) {
            ep[i] = full.types[i + beginningIndex];
            operands[i] = full.operands[i + beginningIndex];
        }
        //Save off arrays
        e.types = ep;
        e.operands = operands;
        e.update();
        return e;
    }

    public static void resolveAnything(Expression full, Expression peice, int beginningIndex, int endingIndex){
        int diff = endingIndex-beginningIndex;

        //make new arrays
        int newLen = full.length -(endingIndex-beginningIndex);
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
        for (int i = beginningIndex + diff+1; i < full.length; i++) {
            parts[i-diff] = full.types[i];
            operands[i-diff] = full.operands[i];
        }

        //save off arrays
        full.operands = operands;
        full.types = parts;
        full.length = newLen;
    }


}