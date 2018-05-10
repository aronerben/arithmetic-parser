package base;

import exception.ExceptionCollection;

import java.util.Objects;

public class Token<T> {
    private T value;
    private TokenType type;

    public Token(T value, TokenType type) {
        this.value = value;
        this.type = type;
        checkTypeCongruency();
    }

    private void checkTypeCongruency() {
        //check for: number & operator type congruency + invalid operator
        if ((this.type == TokenType.NUMBER && !(this.value instanceof Double))
                || (this.type == TokenType.OPERATOR && !(this.value instanceof Character))
                || !Symbols.PARTS.contains(this.value) && this.value instanceof Character) {
                throw new ExceptionCollection.TokenException(this.value, this.type);
        }
    }

    public TokenType getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token<?> token = (Token<?>) o;
        return Objects.equals(value, token.value) &&
                type == token.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}