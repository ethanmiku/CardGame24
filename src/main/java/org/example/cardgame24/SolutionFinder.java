package org.example.cardgame24;

import java.util.ArrayList;

public class SolutionFinder {

    //find an expression that equals 24
    public String findSolution(ArrayList<Integer> cardValues) {

        ArrayList<Double> numbers = new ArrayList<>();
        ArrayList<String> expressions = new ArrayList<>();

        //store each card value and its expression
        for (int value : cardValues) {
            numbers.add((double) value);
            expressions.add(String.valueOf(value));
        }

        return solve(numbers, expressions);
    }

    //try different combinations until we find 24
    private String solve(ArrayList<Double> numbers,
                         ArrayList<String> expressions) {

        //if only one number remains, check if it equals 24
        if (numbers.size() == 1) {
            if (Math.abs(numbers.get(0) - 24) < 1e-9) {
                return expressions.get(0);
            }
            return null;
        }

        //choose two numbers to combine
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {

                double a = numbers.get(i);
                double b = numbers.get(j);

                String exprA = expressions.get(i);
                String exprB = expressions.get(j);

                //try addition, subtraction, multiplication and division
                double[] results = {
                        a + b,
                        a - b,
                        b - a,
                        a * b,
                        b != 0 ? a / b : Double.NaN,
                        a != 0 ? b / a : Double.NaN
                };

                String[] newExpressions = {
                        "(" + exprA + "+" + exprB + ")",
                        "(" + exprA + "-" + exprB + ")",
                        "(" + exprB + "-" + exprA + ")",
                        "(" + exprA + "*" + exprB + ")",
                        "(" + exprA + "/" + exprB + ")",
                        "(" + exprB + "/" + exprA + ")"
                };

                //try each possible operation
                for (int k = 0; k < results.length; k++) {

                    if (!Double.isFinite(results[k])) {
                        continue;
                    }

                    ArrayList<Double> nextNumbers = new ArrayList<>();
                    ArrayList<String> nextExpressions = new ArrayList<>();

                    //keep the numbers we haven't used yet
                    for (int m = 0; m < numbers.size(); m++) {
                        if (m != i && m != j) {
                            nextNumbers.add(numbers.get(m));
                            nextExpressions.add(expressions.get(m));
                        }
                    }

                    //add the result of combining the two numbers
                    nextNumbers.add(results[k]);
                    nextExpressions.add(newExpressions[k]);

                    //keep searching with the remaining numbers
                    String solution = solve(nextNumbers, nextExpressions);

                    if (solution != null) {
                        return solution;
                    }
                }
            }
        }

        //no solution was found
        return null;
    }
}
