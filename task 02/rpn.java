import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RPNCalculator {
    public static void main(String[] args) {
        String filename = args[0];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<Token> tokens = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                for (String part : parts) {
                    Token token = scan(part);
                    if (token == null) {
                        System.err.println("Error: Unexpected character: " + part);
                        return;
                    }
                    tokens.add(token);
                }
            }
            double result = evaluate(tokens);
            System.out.println(result);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static Token scan(String input) {
        try {
            double value = Double.parseDouble(input);
            return new Token(TokenType.NUM, input);
        } catch (NumberFormatException e) {
            switch (input) {
                case "+":
                    return new Token(TokenType.PLUS, input);
                case "-":
                    return new Token(TokenType.MINUS, input);
                case "*":
                    return new Token(TokenType.MULTIPLY, input);
                case "/":
                    return new Token(TokenType.DIVIDE, input);
                default:
                    return null;
            }
        }
    }

    private static double evaluate(List<Token> tokens) {
        Stack<Double> stack = new Stack<>();
        for (Token token : tokens) {
            if (token.type == TokenType.NUM) {
                stack.push(Double.parseDouble(token.lexeme));
            } else {
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = evaluate(token.type, operand1, operand2);
                stack.push(result);
            }
        }
        return stack.pop();
    }

    private static double evaluate(TokenType operator, double operand1, double operand2) {
        switch (operator) {
            case PLUS:
                return operand1 + operand2;
            case MINUS:
                return operand1 - operand2;
            case MULTIPLY:
                return operand1 * operand2;
            case DIVIDE:
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}

enum TokenType {
    NUM,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE
}

class Token {
    final TokenType type;
    final String lexeme;

    Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        return "Token [type=" + type + ", lexeme=" + lexeme + "]";
    }
}
