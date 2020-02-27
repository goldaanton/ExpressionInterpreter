package com.interpreter;

public class Num extends AST {

    private Token token;
    private  String value;

    public Num(Token token) {
        this.token = token;
        this.value = token.getValue();
    }

}
