package processor;

import base.Operator;
import base.Symbols;
import base.Token;
import base.TokenType;
import exception.ExceptionCollection;
import util.OperatorUtil;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class Evaluator {
    public static void main(String[] args) {
        List<Token> tokens = Lexer.tokenize("3(-2!3)");
        //tokens = Lexer.tokenize("*3(*-2!3)");
        System.out.println(tokens);
        List<Token> transformedTokens = Parser.transformToPostFix(tokens);
        System.out.println(transformedTokens);
        double result = evaluate(transformedTokens);
        System.out.println(result);
    }

    //stack-based evaluation of RPN expression
    public static double evaluate(List<Token> tokens) {
        Stack<Token> evalStack = new Stack<>();
        for(Token curToken : tokens) {
            //push number to stack
            if(curToken.getType() == TokenType.NUMBER) {
                evalStack.push(curToken);
                //operator handling
            } else {
                char operatorSymbol = (char)curToken.getValue();
                //if binary operator => 2 numbers needed, unary only needs 1 number
                if(evalStack.size() < 2 && Symbols.BI_OPERATORS.contains(operatorSymbol)
                        || evalStack.size() < 1 && Symbols.UNARY_OPERATORS.contains(operatorSymbol)) {
                    throw new ExceptionCollection.EvaluatorException("No number found to map to operator.");
                } else if(Symbols.BI_OPERATORS.contains(operatorSymbol)) {
                    //operation applies to top two elements
                    Token number1 = evalStack.pop();
                    Token number2 = evalStack.pop();
                    Operator.VarArgsFunction operation = Objects.requireNonNull(
                            OperatorUtil.getOperator((Character) curToken.getValue())).getOperation();
                    //push result back to stack
                    evalStack.push(new Token<>(operation.apply((Double)number2.getValue(), (Double)number1.getValue()), TokenType.NUMBER));
                } else if(Symbols.UNARY_OPERATORS.contains(operatorSymbol)) {
                    //operation applies to top element
                    Token number = evalStack.pop();
                    Operator.VarArgsFunction operation = Objects.requireNonNull(
                            OperatorUtil.getOperator((Character) curToken.getValue())).getOperation();
                    //push result back to stack
                    evalStack.push(new Token<>(operation.apply((Double)number.getValue()), TokenType.NUMBER));
                }
            }
        }
        //numbers left over
        if(!evalStack.empty()) {
            throw new ExceptionCollection.EvaluatorException("No operator found to map to number.");
        }
        return (double) evalStack.pop().getValue();
    }
}
