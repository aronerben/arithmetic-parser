package processor;

import base.Symbols;
import base.Token;
import base.TokenType;
import exception.ExceptionCollection;

import java.util.*;

public class Lexer {

    //TODO(functions)
    public static List<Token> tokenize(String expression) {
        //normalize expression (remove whitespace)
        expression = expression.replaceAll("\\s+","");
        List<Token> tokens = new ArrayList<>();
        boolean unaryNeg = false;
        for(int i = 0; i < expression.length();) {
            char curChar = expression.charAt(i);
            //check if operator
            if(Symbols.PARTS.contains(curChar)) {
                //implicit multiplication (before parenthesis)
                if(curChar == '(' && i != 0) {
                    char lastChar = expression.charAt(i-1);
                    if(lastChar == ')' || Symbols.UNARY_OPERATORS.contains(lastChar) || Character.isDigit(lastChar)) {
                        tokens.add(new Token<>('*', TokenType.OPERATOR));
                    }
                }
                //unary operator -, only if next character is digit
                if(curChar == '-' && i != expression.length()-1 && Character.isDigit(expression.charAt(i+1))) {
                    if(i == 0) {
                        unaryNeg = true;
                    } else {
                        char lastChar = expression.charAt(i - 1);
                        if (Symbols.PARTS_NO_UNARY.contains(lastChar) && lastChar != ')') {
                            unaryNeg = true;
                        }
                    }
                }
                //unary operator -, only if last character is digit
                if(curChar == '!' && i != 0) {
                    char lastChar = expression.charAt(i-1);
                    if(!Character.isDigit(lastChar)) {
                        throw new ExceptionCollection.TokenizeException(curChar, i+1);
                    }
                }
                if(!unaryNeg) {
                    tokens.add(new Token<>(curChar, TokenType.OPERATOR));
                }
                //implicit multiplication (after parenthesis)
                if(curChar == ')' && i != expression.length()-1) {
                    char nextChar = expression.charAt(i+1);
                    if(nextChar == '(' || Character.isDigit(nextChar)) {
                        tokens.add(new Token<>('*', TokenType.OPERATOR));
                    }
                }
                i++;
                //check if number
            } else if(Character.isDigit(curChar)) {
                int j;
                //find end of number
                for(j = i; j < expression.length(); j++) {
                    char curDigitChar = expression.charAt(j);
                    if(!Character.isDigit(curDigitChar) && !(curDigitChar == '.')) {
                        //exit loop without incrementing j
                        break;
                    }
                }
                //check invalid point
                if(expression.charAt(j-1) == '.') {
                    throw new ExceptionCollection.TokenizeException(curChar, i+1);
                }
                tokens.add(new Token<>(Double.parseDouble((unaryNeg ? "-" : "") + expression.substring(i, j)), TokenType.NUMBER));
                //update token index to skip after entire read number
                i = j;
                unaryNeg = false;
            } else {
                throw new ExceptionCollection.TokenizeException(curChar, i+1);
            }
        }
        return tokens;
    }
}
