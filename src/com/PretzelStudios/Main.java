package com.PretzelStudios;

import com.PretzelStudios.Exp.InvalidExpressionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import com.PretzelStudios.Exp.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // comment out either startCalc() or testCalc() based on how you want to run the project
        // use this code to drive your interactive calculator

        System.out.println("Welcome to calculand!");

        startCalc();   // you have to write this method below
        // it should ask the user for input and print
        // results until the user enters "quit" to stop


        // use this to validate your project (the calculator part, anyways)
        //testCalc();     // testCalc will call a calculate(String s) method that you create
        // as part of your overall project. This method will take the user's
        // input, and return a String with the appropriate output.

        System.out.println("Star me on GitHub!");

    }

    public static void testCalc() throws FileNotFoundException, Exception {
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
            try {
                System.out.println(calculate(proccess));
            } catch (UnsupportedOperatorException uoe) {
                System.out.println(new String(uoe.unsupportedOp) + " is not supported");
                System.out.println("Please enter a valid expression:");
            }catch (InvalidExpressionException iee){
                System.out.println(iee.getInvalidExp() + " is not valid");
                System.out.println("Please enter a valid expression, with each element separated by a space:");
            }
        }
    }

    public static String calculate(String s) throws InvalidExpressionException {
        String result = "processed".toUpperCase();
        StringTokenizer st = new StringTokenizer(s);
        int length = st.countTokens();
        String[] sa = new String[length];
        for (int i = 0; i < length; i++) {
            sa[i] = st.nextToken();
        }
        Expression e = new Expression(sa);
        return result;
    }


}
