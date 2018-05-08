package base;

import exception.ExceptionCollection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenTest {

    @Test
    public void testTokenToStringValid() {
        Token<Character> token = new Token<>('*', TokenType.OPERATOR);
        assertEquals("*", token.toString());

        Token<Double> token2 = new Token<>(12.2, TokenType.NUMBER);
        assertEquals("12.2", token2.toString());
    }

    @Test
    public void testTokenEqualsValid() {
        Token<Double> token1 = new Token<>(12.2, TokenType.NUMBER);
        Token<Double> token2 = new Token<>(12.2, TokenType.NUMBER);
        assertEquals(token1, token2);
        assertEquals(token1, token1);
    }

    @Test(expected = ExceptionCollection.TokenException.class)
    public void testTokenInvalid1() {
        Token<Double> token = new Token<>(12.2, TokenType.OPERATOR);
    }

    @Test(expected = ExceptionCollection.TokenException.class)
    public void testTokenInvalid2() {
        Token<Character> token = new Token<>('*', TokenType.NUMBER);
    }

    @Test(expected = ExceptionCollection.TokenException.class)
    public void testTokenInvalid3() {
        Token<String> token = new Token<>("a", TokenType.OPERATOR);
    }

    @Test(expected = ExceptionCollection.TokenException.class)
    public void testTokenInvalid4() {
        Token<Character> token = new Token<>('1', TokenType.OPERATOR);
    }
}