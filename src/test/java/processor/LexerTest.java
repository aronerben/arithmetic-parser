package processor;

import static org.junit.Assert.*;
import base.Token;
import base.TokenType;
import exception.ExceptionCollection;
import org.junit.Test;

import java.util.ArrayList;

public class LexerTest {

    @Test
    public void testTokenizeEmpty() {
        String expression = "";
        ArrayList<Token> tokens = new ArrayList<>();
        assertEquals(tokens, Lexer.tokenize(expression));
    }

    @Test
    public void testTokenizeOnlyNumbersValid() {
        String expression = "2";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>(2d, TokenType.NUMBER));
        assertTrue(tokens.equals(Lexer.tokenize(expression)));

        expression = "223 42";
        tokens = new ArrayList<>();
        tokens.add(new Token<>(22342d, TokenType.NUMBER));
        assertEquals(tokens, Lexer.tokenize(expression));
    }

    @Test
    public void testTokenizeDoubleNumbersValid() {
        String expression = "2.0";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>(2.0, TokenType.NUMBER));
        assertTrue(tokens.equals(Lexer.tokenize(expression)));

        expression = "223 42.232";
        tokens = new ArrayList<>();
        tokens.add(new Token<>(22342.232, TokenType.NUMBER));
        assertEquals(tokens, Lexer.tokenize(expression));
    }

    @Test
    public void testTokenizeOnlyOperatorsValid() {
        String expression = "*";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        assertEquals(tokens, Lexer.tokenize(expression));

        expression = "*))*+ /+";
        tokens = new ArrayList<>();
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>(')', TokenType.OPERATOR));
        tokens.add(new Token<>(')', TokenType.OPERATOR));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        tokens.add(new Token<>('/', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        assertEquals(tokens, Lexer.tokenize(expression));
    }

    @Test
    public void testTokenizeOperatorsAndNumbersValid() {
        String expression = "3*";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>(3d, TokenType.NUMBER));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        assertEquals(tokens, Lexer.tokenize(expression));

        expression = "(2- 2)";
        tokens = new ArrayList<>();
        tokens.add(new Token<>('(', TokenType.OPERATOR));
        tokens.add(new Token<>(2d, TokenType.NUMBER));
        tokens.add(new Token<>('-', TokenType.OPERATOR));
        tokens.add(new Token<>(2d, TokenType.NUMBER));
        tokens.add(new Token<>(')', TokenType.OPERATOR));
        assertEquals(tokens, Lexer.tokenize(expression));

        expression = " 232.23*(- 9.534^(123/2432.2)+(43.3* 94.4) ";
        tokens = new ArrayList<>();
        tokens.add(new Token<>(232.23, TokenType.NUMBER));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('(', TokenType.OPERATOR));
        tokens.add(new Token<>('-', TokenType.OPERATOR));
        tokens.add(new Token<>(9.534, TokenType.NUMBER));
        tokens.add(new Token<>('^', TokenType.OPERATOR));
        tokens.add(new Token<>('(', TokenType.OPERATOR));
        tokens.add(new Token<>(123d, TokenType.NUMBER));
        tokens.add(new Token<>('/', TokenType.OPERATOR));
        tokens.add(new Token<>(2432.2, TokenType.NUMBER));
        tokens.add(new Token<>(')', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        tokens.add(new Token<>('(', TokenType.OPERATOR));
        tokens.add(new Token<>(43.3, TokenType.NUMBER));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>(94.4, TokenType.NUMBER));
        tokens.add(new Token<>(')', TokenType.OPERATOR));
        assertEquals(tokens, Lexer.tokenize(expression));
    }

    @Test(expected = ExceptionCollection.TokenizeException.class)
    public void testTokenizeUnexpectedSymbol1Invalid() {
        String expression = "e";
        Lexer.tokenize(expression);
    }

    @Test(expected = ExceptionCollection.TokenizeException.class)
    public void testTokenizeUnexpectedSymbol2Invalid() {
        String expression = "-0+4.3*(2\\2)";
        Lexer.tokenize(expression);
    }

    @Test(expected = ExceptionCollection.TokenizeException.class)
    public void testTokenizeUnexpectedSymbol3Invalid() {
        String expression = ".012";
        Lexer.tokenize(expression);
    }

    @Test(expected = ExceptionCollection.TokenizeException.class)
    public void testTokenizeUnexpectedSymbol4Invalid() {
        String expression = "42.";
        Lexer.tokenize(expression);
    }

}