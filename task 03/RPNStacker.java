import java.util.*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Token {
    private final TokenType type;
    private final String lexeme;

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public String toString() {
        return "Token [type=" + type + ", lexeme=" + lexeme + "]";
    }
}
public enum TokenType {
    NUM, PLUS, MINUS, MULTIPLY, DIVIDE, MODULO
}


public class Regex {
    private static final Pattern NUM_PATTERN = Pattern.compile("\\d+");
    private static final Pattern PLUS_PATTERN = Pattern.compile("\\+");
    private static final Pattern MINUS_PATTERN = Pattern.compile("-");
    private static final Pattern MULTIPLY_PATTERN = Pattern.compile("\\*");
    private static final Pattern DIVIDE_PATTERN = Pattern.compile("/");
    private static final Pattern MODULO_PATTERN = Pattern.compile("%");

    public static Token matchToken(String lexeme) {
        Matcher numMatcher = NUM_PATTERN.matcher(lexeme);
        if (numMatcher.matches()) {
            return new Token(TokenType.NUM, lexeme);
        }

        Matcher plusMatcher = PLUS_PATTERN.matcher(lexeme);
        if (plusMatcher.matches()) {
            return new Token(TokenType.PLUS, lexeme);
        }

        Matcher minusMatcher = MINUS_PATTERN.matcher(lexeme);
        if (minusMatcher.matches()) {
            return new Token(TokenType.MINUS, lexeme);
        }

        Matcher multiplyMatcher = MULTIPLY_PATTERN.matcher(lexeme);
        if (multiplyMatcher.matches()) {
            return new Token(TokenType.MULTIPLY, lexeme);
        }

        Matcher divideMatcher = DIVIDE_PATTERN.matcher(lexeme);
        if (divideMatcher.matches()) {
            return new Token(TokenType.DIVIDE, lexeme);
        }

        Matcher moduloMatcher = MODULO_PATTERN.matcher(lexeme);
        if (moduloMatcher.matches()) {
            return new Token(TokenType.MODULO, lexeme);
        }

        return null; // caso não reconheça o token, retorna null
    }
}

public class RPNStacker {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java RPNStacker <expression_file.rpn>");
            return;
        }

        String filename = args[0];
        List<Token> tokens = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lexemes = line.split("\\s+");
                for (String lexeme : lexemes) {
                    Token token = Regex.matchToken(lexeme);
                    if (token == null) {
                        System.err.println("Error: Unexpected character: " + lexeme);
                        return;
                    }
                    tokens.add(token);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return;
        }


