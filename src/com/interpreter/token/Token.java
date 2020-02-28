package com.interpreter.token;

public class Token {

    private TokenType type;
    private Object value;

    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }
}
