package processor;

import base.Token;
import base.TokenType;
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

        expression = "*+ -*";
        tokens = new ArrayList<>();
        tokens.add(new Token<>('*', TokenType.OPERATOR));
        tokens.add(new Token<>('+', TokenType.OPERATOR));
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

    //TODO(write tests for added things)
}