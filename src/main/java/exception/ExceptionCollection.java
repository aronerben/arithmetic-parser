package exception;

public class ExceptionCollection {

    public static class TokenizeException extends RuntimeException {
        public TokenizeException(String s) {
            super(s);
        }
    }

    public static class TokenException extends RuntimeException {
        public TokenException(String error) {
            super(error);
        }
    }
}
