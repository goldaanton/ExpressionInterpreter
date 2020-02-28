package com.interpreter.nodes;

import com.interpreter.token.Token;

public class Var extends AST {

    private Token token;

    public Var(Token token) {
        this.token = token;
    }

    public Token getVarToken() {
        return token;
    }
}
