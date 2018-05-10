package util;

import base.Operator;

import java.util.Objects;

public class OperatorUtil {

    public static boolean hasGreaterPrecedence(char operator1, char operator2) {
        int precedence1 = Objects.requireNonNull(getOperator(operator1)).getPrecedence();
        int precedence2 = Objects.requireNonNull(getOperator(operator2)).getPrecedence();
        return precedence1 > precedence2;
    }

    public static boolean hasEqualPrecedence(char operator1, char operator2) {
        int precedence1 = Objects.requireNonNull(getOperator(operator1)).getPrecedence();
        int precedence2 = Objects.requireNonNull(getOperator(operator2)).getPrecedence();
        return precedence1 == precedence2;
    }

    public static Operator getOperator(char operator) {
        for(Operator cur : Operator.values()) {
            char curSymbol = cur.getSymbol();
            if (curSymbol == operator) {
                return cur;
            }
        }
        return null;
    }
}
