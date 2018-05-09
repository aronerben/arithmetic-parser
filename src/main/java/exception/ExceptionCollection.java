package exception;

public class ExceptionCollection {

    public static class TokenizeException extends RuntimeException {
        public TokenizeException(String error) {
            super(error);
        }
    }

    public static class TokenException extends RuntimeException {
        public TokenException(String error) {
            super(error);
        }
    }

    public static class ParserException extends RuntimeException {
        public ParserException(String error) {
            super(error);
        }
    }
}
