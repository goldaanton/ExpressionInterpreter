package com.interpreter;

public class BinOp extends AST {

    private AST right;
    private AST left;
    private Token op;

    public BinOp(AST left, Token op, AST right) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public TokenType getOp() {
        return this.op.getType();
    }

    public AST getLeft() {
        return this.left;
    }

    public AST getRight() {
        return this.right;
    }
}
