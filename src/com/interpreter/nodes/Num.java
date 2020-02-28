package com.interpreter.nodes;

import com.interpreter.token.Token;

public class Num extends AST {

    private Token token;

    public Num(Token token) {
        this.token = token;
    }

    public Token getNumToken() {
        return token;
    }
}
