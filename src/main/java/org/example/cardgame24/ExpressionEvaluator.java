package org.example.cardgame24;

import java.util.Stack;

public class ExpressionEvaluator {

    public double evaluate(String expression) {

        //stacks to store numbers and operators
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        //remove spaces from the expression
        expression = expression.replaceAll("\\s+", "");

        //keeps track of whether we need a number or operator
        boolean expectNumber = true;

        //go through each character in the expression
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            //if the character is a number
            if (Character.isDigit(c)) {
                if (!expectNumber) {
                    throw new IllegalArgumentException("Invalid expression");
                }

                int start = i;

                //read the full number (example: 12 instead of 1 and 2)
                while (i < expression.length()
                        && Character.isDigit(expression.charAt(i))) {
                    i++;
                }

                numbers.push(Double.parseDouble(
                        expression.substring(start, i)));

                i--;
                expectNumber = false;
            }

            //if we find an opening parenthesis
            else if (c == '(') {
                if (!expectNumber) {
                    throw new IllegalArgumentException("Invalid expression");
                }

                operators.push(c);
            }

            //if we find a closing parenthesis
            else if (c == ')') {
                if (expectNumber) {
                    throw new IllegalArgumentException("Invalid expression");
                }

                //calculate everything inside the parentheses
                while (!operators.isEmpty()
                        && operators.peek() != '(') {
                    calculate(numbers, operators);
                }

                if (operators.isEmpty()) {
                    throw new IllegalArgumentException("Invalid parentheses");
                }

                operators.pop();
                expectNumber = false;
            }

            //if we find an arithmetic operator
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (expectNumber) {
                    throw new IllegalArgumentException("Invalid expression");
                }

                //calculate higher priority operations first
                while (!operators.isEmpty()
                        && operators.peek() != '('
                        && precedence(operators.peek()) >= precedence(c)) {
                    calculate(numbers, operators);
                }

                operators.push(c);
                expectNumber = true;
            }

            //reject any unsupported characters
            else {
                throw new IllegalArgumentException("Invalid character");
            }
        }

        //make sure the expression does not end with an operator
        if (expectNumber) {
            throw new IllegalArgumentException("Incomplete expression");
        }

        //calculate any remaining operations
        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new IllegalArgumentException("Invalid parentheses");
            }

            calculate(numbers, operators);
        }

        //there should only be one final answer
        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }

        double result = numbers.pop();

        //make sure the result is a valid number
        if (!Double.isFinite(result)) {
            throw new IllegalArgumentException("Invalid calculation");
        }

        return result;
    }

    //determines which operators have higher priority
    private int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        }

        if (operator == '*' || operator == '/') {
            return 2;
        }

        return 0;
    }

    //performs a calculation using two numbers and an operator
    private void calculate(Stack<Double> numbers,
                           Stack<Character> operators) {

        //make sure there are enough numbers to calculate
        if (numbers.size() < 2) {
            throw new IllegalArgumentException("Invalid expression");
        }

        double b = numbers.pop();
        double a = numbers.pop();
        char operator = operators.pop();

        //perform the correct arithmetic operation
        switch (operator) {
            case '+' -> numbers.push(a + b);
            case '-' -> numbers.push(a - b);
            case '*' -> numbers.push(a * b);
            case '/' -> {
                if (b == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero");
                }

                numbers.push(a / b);
            }
            default -> throw new IllegalArgumentException("Invalid operator");
        }
    }
}
