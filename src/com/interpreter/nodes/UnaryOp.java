package com.interpreter.nodes;

import com.interpreter.token.Token;

public class UnaryOp extends AST {

    private Token op;
    private AST expr;

    public UnaryOp(Token op, AST expr) {
        this.op = op;
        this.expr = expr;
    }

    public AST getExpr() {
        return this.expr;
    }

    public Token getOp() {
        return this.op;
    }
}
