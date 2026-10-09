package csc400mod4;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class PostfixCalculator {

    public static double evalPostfix(String expression) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = expression.trim().split("\\s+");

        for (String token : tokens) {
            switch (token) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "%": {
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException(
                            "Invalid postfix expression."
                        );
                    }

                    double b = stack.pop();
                    double a = stack.pop();
                    double result;

                    switch (token) {
                        case "+":
                            result = a + b;
                            break;
                        case "-":
                            result = a - b;
                            break;
                        case "*":
                            result = a * b;
                            break;
                        case "/":
                            if (b == 0) {
                                throw new ArithmeticException(
                                    "Division by zero."
                                );
                            }
                            result = a / b;
                            break;
                        default: // Modulo
                            if (b == 0) {
                                throw new ArithmeticException(
                                    "Modulo by zero."
                                );
                            }
                            result = a % b;
                    }

                    stack.push(result);
                    break;
                }

                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException(
                "Invalid postfix expression."
            );
        }

        return stack.pop();
    }

    // Read and evaluate each expression in a text file
    public static void evalFile(String fileName) {
        try (BufferedReader reader =
                 new BufferedReader(new FileReader(fileName))) {

            String expression;
            int lineNumber = 0;

            while ((expression = reader.readLine()) != null) {
                lineNumber++;

                // Skip blank lines
                if (expression.trim().isEmpty()) {
                    continue;
                }

                System.out.println("Expression: " + expression);

                try {
                    double result = evalPostfix(expression);
                    System.out.println("Result: " + result);
                } catch (IllegalArgumentException e) {
                    System.out.println(
                        "Error on line " + lineNumber + ": "
                        + e.getMessage()
                    );
                } catch (ArithmeticException e) {
                    System.out.println(
                        "Error on line " + lineNumber + ": "
                        + e.getMessage()
                    );
                }

                System.out.println();
            }

        } catch (IOException e) {
            System.out.println(
                "Error reading file: " + e.getMessage()
            );
        }
    }

    public static void main(String[] args) {
        String fileName = args.length > 0
            ? args[0]
            : "expressions.txt";

        evalFile(fileName);
    }
}