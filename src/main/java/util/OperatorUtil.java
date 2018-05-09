package util;

import base.Operator;

public class OperatorUtil {

    public static boolean hasGreaterPrecedence(char operator1, char operator2) {
        int precedence1 = getPrecedence(operator1);
        int precedence2 = getPrecedence(operator2);
        return precedence1 > precedence2;
    }

    public static boolean hasEqualPrecedence(char operator1, char operator2) {
        int precedence1 = getPrecedence(operator1);
        int precedence2 = getPrecedence(operator2);
        return precedence1 == precedence2;
    }

    private static int getPrecedence(char operator) {
        for(Operator cur : Operator.values()) {
            char curSymbol = cur.getSymbol();
            if (curSymbol == operator) {
                return cur.getPrecedence();
            }
        }
        return 0;
    }

    public static Operator.Associativity getAssociativity(char operator) {
        for(Operator cur : Operator.values()) {
            char curSymbol = cur.getSymbol();
            if (curSymbol == operator) {
                return cur.getAssociativity();
            }
        }
        return Operator.Associativity.LEFT;
    }
}
