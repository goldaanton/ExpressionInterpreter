package com.interpreter.nodes;

import com.interpreter.token.Token;

public class Assign extends AST {

    private Var left;
    private AST right;
    private Token op;

    public Assign(Var left, Token op, AST right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public Var getLeft() {
        return this.left;
    }

    public AST getRight() {
        return right;
    }
}
