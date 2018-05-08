package processor;

import base.Symbols;
import base.Token;
import base.TokenType;
import exception.ExceptionCollection;

import java.util.*;

public class Lexer {

    public static List<Token> tokenize(String expression) {
        //normalize expression (remove whitespace)
        expression = expression.replaceAll("\\s+","");
        List<Token> tokens = new ArrayList<>();
        for(int i = 0; i < expression.length();) {
            char curChar = expression.charAt(i);
            //check if operator
            if(Symbols.OPERATORS.contains(curChar)) {
                tokens.add(new Token<>(curChar, TokenType.OPERATOR));
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
                    throw new ExceptionCollection.TokenizeException("Invalid token found: " + curChar + "     Found at position: " + i);
                }
                tokens.add(new Token<>(Double.parseDouble(expression.substring(i, j)), TokenType.NUMBER));
                //update token index to skip after entire read number
                i = j;
            } else {
                throw new ExceptionCollection.TokenizeException("Invalid token found: " + curChar + "     Found at position: " + i);
            }
        }
        return tokens;
    }
}
