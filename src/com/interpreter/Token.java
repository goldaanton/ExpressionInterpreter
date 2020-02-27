package com.interpreter;

public class Token {

    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    public String getToken() {
        return String.format("Token(%s, %s)", type, value);
    }
}
