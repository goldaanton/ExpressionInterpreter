package com.interpreter;

public class BinOp extends AST {

    private AST right;
    private AST left;
    private Token op;
    private Token token;

    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.right = right;
        this.op = op;
        this.token = op;
    }

    Token getOp() {
        return this.op;
    }

}
