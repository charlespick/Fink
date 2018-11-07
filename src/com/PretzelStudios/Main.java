package com.PretzelStudios;

import com.PretzelStudios.Exp.Expression;
import com.PretzelStudios.Exp.InvalidExpressionException;
import com.PretzelStudios.Exp.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to calculand!");
        Scanner sc = new Scanner(args[0]);
        if (sc.hasNextInt()) {
            switch (sc.nextInt()) {
                case 0:
                    startCalc();
                    break;
                case 1:
                    testCalc();
                    break;
                case 2:
                    testCase();
                    break;
            }
        } else {
            System.out.println("Usage: Calculand [operating mode - 0,1]");
        }


        System.out.println("Star me on GitHub!");

    }

    public static void testCase() {
        StringTokenizer st = new StringTokenizer("( 8 + 9 ) * 9");
        int length = st.countTokens();
        String[] sa = new String[length];
        for (int i = 0; i < length; i++) {
            sa[i] = st.nextToken();
        }

        try{
            new Expression(sa);
        }catch (InvalidExpressionException e){
            e.printStackTrace();
        }

    }

    public static void testCalc() throws Exception {
        ArrayList<String> problems = new ArrayList<>();
        ArrayList<String> results = new ArrayList<>();
        // load problems from a file
        File fProblems = new File("problems.txt");
        Scanner sc = new Scanner(fProblems);
        int count = 0;
        String line = "";
        int problemCount = 0;
        int resultCount = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (!line.startsWith("//") && !line.trim().equals("")) {        // ignore comments at the beginning
                problems.add(line.substring(3).trim());
                problemCount++;
                if (sc.hasNextLine()) {
                    line = sc.nextLine();
                    if (!line.startsWith("//") && !line.trim().equals("")) {
                        results.add(line.substring(3).trim());
                        resultCount++;
                    }
                }
                count++;
            }
        }
        if (problemCount == resultCount) {
            // now run the tests
            for (int i = 0; i < problemCount; i++) {
                String prob = problems.get(i);
                String result = calculate(prob);
                if (result == null) {
                    System.out.println("FAILED test " + i);
                    System.out.println("Exp: " + problems.get(i));
                    System.out.println("Expected result: " + results.get(i));
                    System.out.println("Actual: null String returned from calculate()");
                } else {
                    if (result.equals(results.get(i))) {
                        System.out.println("PASSED test " + i);
                    } else {
                        System.out.println("FAILED test " + i);
                        System.out.println("Exp: " + problems.get(i));
                        System.out.println("Expected result: " + results.get(i));
                        System.out.println("Actual: " + result);
                    }
                }

            }
        } else {
            System.out.println("problem file error");
        }

    }

    public static void startCalc() {

        // your code here to get user input, and calculate/print results. You'll call
        // the calculate(String s) as part of your code here, which returns a String
        // with the result to print.
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter an expression to calculate, or enter \"quit\" to leave");
            String proccess = sc.nextLine();
            if (proccess.toUpperCase().equals("QUIT")) {
                break;
            }
            System.out.println(calculate(proccess));
        }
    }

    public static String calculate(String s) {
        //this seems like a weird place to do this but whatever

        if (s.toUpperCase().equals("QUIT")) {
            return "quit";
        }

        //Capture input and create object
        StringTokenizer st = new StringTokenizer(s);
        int length = st.countTokens();
        String[] sa = new String[length];
        for (int i = 0; i < length; i++) {
            sa[i] = st.nextToken();
        }
        if (sa.length == 0) {
            return "error".toUpperCase();
        }

        Expression e;
        try {
            e = new Expression(sa);
        } catch (InvalidExpressionException iee) {
            return "error".toUpperCase();
        }


        //Check for and compute all one operands, right to left
        boolean oneOpMightExist = true;
        while (oneOpMightExist) {
            //search for trig ops from right to left
            for (int i = e.length - 1; i >= 0; i--) {
                switch (e.types[i]) {
                    case ABSOLUTE_VALUE:
                        Util.resolveOneOp(e, new Expression(Math.abs(e.operands[i + 1])), i);
                        i = -1;
                        break;
                    case RADICAL:
                        Util.resolveOneOp(e, new Expression(Math.sqrt(e.operands[i + 1])), i);
                        i = -1;
                        break;
                    case COS_SYMBOL:
                        Util.resolveOneOp(e, new Expression(Math.cos(e.operands[i + 1])), i);
                        i = -1;
                        break;
                    case SIN_SYMBOL:
                        Util.resolveOneOp(e, new Expression(Math.sin(e.operands[i + 1])), i);
                        i = -1;
                        break;
                    case TAN_SYMBOL:
                        Util.resolveOneOp(e, new Expression(Math.tan(e.operands[i + 1])), i);
                        i = -1;
                        break;
                    case ROUND:
                        Util.resolveOneOp(e, new Expression(Math.round(e.operands[i + 1])), i);
                        i = -1;
                        break;
                    default:
                        if (i == 0) {
                            oneOpMightExist = false;
                        }
                        break;
                }
            }
        }
        //Check for and compute all exponents, left to right
        boolean expMightExist = true;
        while (expMightExist) {
            //search for exp ops from left to right
            for (int i = 0; i < e.length; i++) {
                switch (e.types[i]) {
                    case EXPONANT_SYMBOL:
                        Util.resolveTwoOp(e, new Expression(Math.pow(e.operands[i - 1], e.operands[i + 1])), i - 1);
                        i = -1;
                        break;
                    default:
                        if (i == e.length - 1) {
                            expMightExist = false;
                        }
                        break;
                }
            }
        }

        boolean modMightExist = true;
        while (modMightExist) {
            //search for exp ops from left to right
            for (int i = 0; i < e.length; i++) {
                switch (e.types[i]) {
                    case MODULO:
                        Util.resolveTwoOp(e, new Expression(e.operands[i - 1] % e.operands[i + 1]), i - 1);
                        i = -1;
                        break;
                    default:
                        if (i == e.length - 1) {
                            modMightExist = false;
                        }
                        break;
                }
            }
        }

        //Check for and compute all multiplication, left to right
        boolean multMightExist = true;
        while (multMightExist) {
            //search for exp ops from left to right
            for (int i = 0; i < e.length; i++) {
                switch (e.types[i]) {
                    case MULTIPLICATION_SYMBOL:
                        Util.resolveTwoOp(e, new Expression(e.operands[i - 1] * e.operands[i + 1]), i - 1);
                        i = -1;
                        break;
                    default:
                        if (i == e.length - 1) {
                            multMightExist = false;
                        }
                        break;
                }
            }
        }
        //Check for and compute all division , left to right
        boolean divMightExist = true;
        while (divMightExist) {
            //search for exp ops from left to right
            for (int i = 0; i < e.length; i++) {
                switch (e.types[i]) {
                    case DIVISION_SYMBOL:
                        Util.resolveTwoOp(e, new Expression(e.operands[i - 1] / e.operands[i + 1]), i - 1);
                        i = -1;
                        break;
                    default:
                        if (i == e.length - 1) {
                            divMightExist = false;
                        }
                        break;
                }
            }
        }
        //Check for and compute all addition, left to right
        boolean addMightExist = true;
        while (addMightExist) {
            //search for exp ops from left to right
            for (int i = 0; i < e.length; i++) {
                switch (e.types[i]) {
                    case PLUS_SYMBOL:
                        Util.resolveTwoOp(e, new Expression(e.operands[i - 1] + e.operands[i + 1]), i - 1);
                        i = -1;
                        break;
                    default:
                        if (i == e.length - 1) {
                            addMightExist = false;
                        }
                        break;
                }
            }
        }
        //Check for and compute all subtraction, left to right
        boolean subMightExist = true;
        while (subMightExist) {
            //search for exp ops from left to right
            for (int i = 0; i < e.length; i++) {
                switch (e.types[i]) {
                    case SUBTRACTION_SYMBOL:
                        Util.resolveTwoOp(e, new Expression(e.operands[i - 1] - e.operands[i + 1]), i - 1);
                        i = -1;
                        break;
                    default:
                        if (i == e.length - 1) {
                            subMightExist = false;
                        }
                        break;
                }
            }
        }
        return e.toString();
    }
}
