package com.interpreter;

public class Num extends AST {

    private Token token;

    public Num(Token token) {
        this.token = token;
    }

    public String getValue() {
        return token.getValue();
    }
}
