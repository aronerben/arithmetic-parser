package base;

import static base.Operator.Associativity.LEFT;
import static base.Operator.Associativity.RIGHT;

public enum Operator {
    ADD(1, LEFT, '+'),
    SUB(1, LEFT, '-'),
    MUL(2, LEFT, '*'),
    DIV1(2, LEFT, '/'),
    DIV2(2, LEFT, ':'),
    MOD(2, LEFT, '%'),
    POW(3, RIGHT, '^'),
    PARLEFT(4, null, '('),
    PARRIGHT(4, null, ')'),
    FAC(5, null, '!');

    private int precedence;
    private Associativity associativity;
    private char symbol;

    Operator(int precedence, Associativity associativity, char symbol) {
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

    public enum Associativity {
        LEFT,
        RIGHT
    }
}
