package processor;

import base.Token;
import base.TokenType;
import exception.ExceptionCollection;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testTransformToPostFixEmpty() {
        String expression = "";
        ArrayList<Token> tokens = new ArrayList<>();
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));
    }

    @Test
    public void testTransformToPostFixOnlyOperatorsValid() {
        String expression = "+";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));

        expression = "*+ -^*";
        tokens = new ArrayList<>();
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        tokens.add(new Token<>('^', TokenType.OPERATOR));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('-', TokenType.OPERATOR));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));

        expression = "*(+(-*))";
        tokens = new ArrayList<>();
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('-', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));
    }

    @Test
    public void testTransformToPostFixOnlyNumbersValid() {
        String expression = "23";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>(23d, TokenType.NUMBER));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));

        expression = "312 31 23";
        tokens = new ArrayList<>();
        tokens.add(new Token<>(3123123d, TokenType.NUMBER));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));
    }

    @Test
    public void testTransformToPostFixValid() {
        String expression = "2!(3!)4!";
        ArrayList<Token> tokens = new ArrayList<>();
        tokens.add(new Token<>(2d, TokenType.NUMBER));
        tokens.add(new Token<>('!', TokenType.OPERATOR));
        tokens.add(new Token<>(3d, TokenType.NUMBER));
        tokens.add(new Token<>('!', TokenType.OPERATOR));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>(4d, TokenType.NUMBER));
        tokens.add(new Token<>('!', TokenType.OPERATOR));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));

        expression = "4-+*/*-4";
        tokens = new ArrayList<>();
        tokens.add(new Token<>(4d, TokenType.NUMBER));
        tokens.add(new Token<>('-', TokenType.OPERATOR));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('/', TokenType.OPERATOR));
        tokens.add(new Token<>(-4d, TokenType.NUMBER));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));

        expression = "3 + (4) 2 / ( 1 - 5 ) ^ 2 ^ 3";
        tokens = new ArrayList<>();
        tokens.add(new Token<>(3d, TokenType.NUMBER));
        tokens.add(new Token<>(4d, TokenType.NUMBER));
        tokens.add(new Token<>(2d, TokenType.NUMBER));
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>(1d, TokenType.NUMBER));
        tokens.add(new Token<>(5d, TokenType.NUMBER));
        tokens.add(new Token<>('-', TokenType.OPERATOR));
        tokens.add(new Token<>(2d, TokenType.NUMBER));
        tokens.add(new Token<>(3d, TokenType.NUMBER));
        tokens.add(new Token<>('^', TokenType.OPERATOR));
        tokens.add(new Token<>('^', TokenType.OPERATOR));
        tokens.add(new Token<>('/', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
        assertEquals(tokens, Parser.transformToPostFix(Lexer.tokenize(expression)));
    }

    @Test(expected = ExceptionCollection.ParserException.class)
    public void testTransformToPostFixUnbalancedParentheses1Invalid() {
        String expression = ")";
        Parser.transformToPostFix(Lexer.tokenize(expression));
    }

    @Test(expected = ExceptionCollection.ParserException.class)
    public void testTransformToPostFixUnbalancedParentheses2Invalid() {
        String expression = "(";
        Parser.transformToPostFix(Lexer.tokenize(expression));
    }

    @Test(expected = ExceptionCollection.ParserException.class)
    public void testTransformToPostFixUnbalancedParentheses3Invalid() {
        String expression = ")(";
        Parser.transformToPostFix(Lexer.tokenize(expression));
    }

    @Test(expected = ExceptionCollection.ParserException.class)
    public void testTransformToPostFixUnbalancedParentheses4Invalid() {
        String expression = "4-(3*4)-2)";
        Parser.transformToPostFix(Lexer.tokenize(expression));
    }

    @Test(expected = ExceptionCollection.ParserException.class)
    public void testTransformToPostFixUnbalancedParentheses5Invalid() {
        String expression = "(---2^2()";
        Parser.transformToPostFix(Lexer.tokenize(expression));
    }

    @Test(expected = ExceptionCollection.ParserException.class)
    public void testTransformToPostFixUnbalancedParentheses6Invalid() {
        String expression = "-(2^(2(2))";
        Parser.transformToPostFix(Lexer.tokenize(expression));
    }
}