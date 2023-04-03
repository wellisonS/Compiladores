import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class RPNCalculator {
    public static void main(String[] args) {
        String filename = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Stack<Double> stack = new Stack<>();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                for (String token : tokens) {
                    if (isOperator(token)) {
                        double operand2 = stack.pop();
                        double operand1 = stack.pop();
                        double result = evaluate(token, operand1, operand2);
                        stack.push(result);
                    } else {
                        double value = Double.parseDouble(token);
                        stack.push(value);
                    }
                }
            }
            System.out.println(stack.pop());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static double evaluate(String operator, double operand1, double operand2) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
