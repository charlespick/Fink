package com.PretzelStudios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // comment out either startCalc() or testCalc() based on how you want to run the project
        // use this code to drive your interactive calculator

        // add a welcome message here

        //startCalc();   // you have to write this method below
        // it should ask the user for input and print
        // results until the user enters "quit" to stop


        // use this to validate your project (the calculator part, anyways)
        testCalc();     // testCalc will call a calculate(String s) method that you create
        // as part of your overall project. This method will take the user's
        // input, and return a String with the appropriate output.

        // add a goodbye message here

    }

    public static void testCalc() throws FileNotFoundException {
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
                    System.out.println("Expression: " + problems.get(i));
                    System.out.println("Expected result: " + results.get(i));
                    System.out.println("Actual: null String returned from calculate()");
                } else {
                    if (result.equals(results.get(i))) {
                        System.out.println("PASSED test " + i);
                    } else {
                        System.out.println("FAILED test " + i);
                        System.out.println("Expression: " + problems.get(i));
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

    }

    public static String calculate(String s) {
        String result = "error".toUpperCase();
        

        return result;
    }
}
