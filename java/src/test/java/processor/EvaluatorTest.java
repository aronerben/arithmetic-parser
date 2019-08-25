package processor;

import base.Token;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EvaluatorTest {

    private static final double DELTA = 10^-6;
    private static final int ACCURACY = 5;

    @Test
    public void testEvaluate1Valid() {
        String expression = "2-2";
        double expected = 0d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluate2Valid() {
        String expression = "2.1+2";
        double expected = 4.1;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluate3Valid() {
        String expression = "2*3.3";
        double expected = 6.9d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluate4Valid() {
        String expression = "2^4";
        double expected = 16d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluate5Valid() {
        String expression = "2!-3!";
        double expected = -4d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluate6Valid() {
        String expression = "3/4+1:2.5";
        double expected = 1.15d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluate7Valid() {
        String expression = "2-2%1*4-2";
        double expected = 0d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluateParentheses1Valid() {
        String expression = "(2--5)3";
        double expected = 21d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }

    @Test
    public void testEvaluateParentheses2Valid() {
        String expression = "3!(10:(4))";
        double expected = 15d;
        assertEquals(expected, Evaluator.evaluate(Parser.transformToPostFix(Lexer.tokenize(expression)), ACCURACY), DELTA);
    }
}