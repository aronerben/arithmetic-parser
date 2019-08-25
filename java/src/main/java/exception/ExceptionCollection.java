package exception;

import base.TokenType;

public class ExceptionCollection {

    public static class TokenizeException extends RuntimeException {
        public TokenizeException(char curChar, int position) {
            super("Invalid token found: " + curChar + "     Found at position: " + position);
        }
    }

    public static class TokenException extends RuntimeException {
        public <T> TokenException(T value, TokenType type) {
            super("Invalid token created, value: " + value
                    + ", given value type: " + value.getClass() + ", required type: " + type);
        }
    }

    public static class ParserException extends RuntimeException {
        public ParserException(String error) {
            super(error);
        }
    }

    public static class EvaluatorException extends RuntimeException {
        public EvaluatorException(String error) {
            super("Expression could not be evaluated. " + error);
        }
    }
}
