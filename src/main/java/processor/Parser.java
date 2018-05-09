package processor;

import base.Operator;
import base.Token;
import exception.ExceptionCollection;
import util.OperatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
    public static void main(String[] args) {
        List<Token> tokens = Lexer.tokenize("(2*(2-2)^3*1+5%2)");
        tokens = Lexer.tokenize("2(3)4");
        System.out.println(tokens);
        System.out.println(transformToPostFix(tokens));
    }

    //shunting-yard algorithm to transform into postfix notation (RPN), equivalent to AST
    public static List<Token> transformToPostFix(List<Token> tokens) {
        //operator stack and output queue
        Stack<Token> operators = new Stack<>();
        List<Token> tokensPostFix = new ArrayList<>();
        int tokenCounter = 0;

        for (Token curToken : tokens) {
            tokenCounter++;
            switch (curToken.getType()) {
                case OPERATOR:
                    //special case, left parenthesis => push to stack
                    if (curToken.getValue().equals('(')) {
                        operators.push(curToken);
                        //special case, right parenthesis => pop every operator until matching parenthesis is found
                    } else if (curToken.getValue().equals(')')) {
                        if(operators.empty()) {
                            throw new ExceptionCollection.ParserException("Mismatched parentheses at position: " + tokenCounter);
                        }
                        while (!operators.empty() && !operators.peek().getValue().equals('(')) {
                            if (!operators.empty()) {
                                tokensPostFix.add(operators.pop());
                            } else {
                                //operator stack is empty without left parenthesis found
                                throw new ExceptionCollection.ParserException("Mismatched parentheses at position: " + tokenCounter);
                            }
                        }
                        //pop left parenthesis
                        operators.pop();
                    } else {
                        //clean up operator stack according to precedence and associativity
                        while (!operators.empty() && !operators.peek().getValue().equals('(')
                                && (OperatorUtil.hasGreaterPrecedence((char) operators.peek().getValue(), (char) curToken.getValue())
                                || (OperatorUtil.hasEqualPrecedence((char) operators.peek().getValue(), (char) curToken.getValue())
                                && (OperatorUtil.getAssociativity((char) operators.peek().getValue()) == Operator.Associativity.LEFT)))) {
                            tokensPostFix.add(operators.pop());
                        }
                        operators.push(curToken);
                    }
                    break;
                case NUMBER:
                    tokensPostFix.add(curToken);
                    break;
            }
        }
        //empty operator stack onto queue
        while(!operators.empty()) {
            if(operators.peek().getValue().equals('(')
                    || operators.peek().getValue().equals(')')) {
                //leftover parenthesis found
                throw new ExceptionCollection.ParserException("Mismatched parentheses found.");
            }
            tokensPostFix.add(operators.pop());
        }
        return tokensPostFix;
    }
}
