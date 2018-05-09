package base;

import util.MathUtil;

import static base.Operator.Associativity.LEFT;
import static base.Operator.Associativity.RIGHT;

public enum Operator {
    ADD(1, LEFT, '+', a -> a[0]+a[1]),
    SUB(1, LEFT, '-', a -> a[0]-a[1]),
    MUL(2, LEFT, '*', a -> a[0]*a[1]),
    DIV1(2, LEFT, '/', a -> a[0]/a[1]),
    DIV2(2, LEFT, ':', a -> a[0]/a[1]),
    MOD(2, LEFT, '%', a -> a[0]%a[1]),
    POW(3, RIGHT, '^', a -> Math.pow(a[0], a[1])),
    PARLEFT(4, null, '(', null),
    PARRIGHT(4, null, ')', null),
    FAC(5, null, '!', a -> MathUtil.factorial(a[0].intValue()));

    private int precedence;
    private Associativity associativity;
    private char symbol;
    private VarArgsFunction operation;

    Operator(int precedence, Associativity associativity, char symbol, VarArgsFunction operation) {
        this.operation = operation;
        this.precedence = precedence;
        this.associativity = associativity;
        this.symbol = symbol;
    }

    public int getPrecedence() {
        return precedence;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public char getSymbol() {
        return symbol;
    }

    public VarArgsFunction getOperation() {
        return operation;
    }

    public enum Associativity {
        LEFT,
        RIGHT
    }

    @FunctionalInterface
    interface VarArgsFunction {
        Double apply(Double... args);
    }
}
