package com.interpreter.token;

import java.util.Optional;

public class Token {

    private TokenType type;
    private Object value;

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Token(TokenType type) {
        this.type = type;
        this.value = Optional.empty();
    }

    public TokenType getType() {
        return this.type;
    }

    public <T> Optional<T> getValue(Class<T> expectedType) {
        if (expectedType.isInstance(value))
            return Optional.ofNullable(expectedType.cast(value));
        else
            // TODO throw exception providing type checking has failed
            throw new RuntimeException();
    }
}
