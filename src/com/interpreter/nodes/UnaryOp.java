package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

public class UnaryOp implements AbstractExpression {

    private Token op;
    private AbstractExpression expr;

    public UnaryOp(Token op, AbstractExpression expr) {
        this.op = op;
        this.expr = expr;
    }

    @Override
    public String solve(Context context) {
        String result = null;
        TokenType opType = op.getType();

        if(opType == TokenType.ADDITION)
            result = expr.solve(context);
        else if (opType == TokenType.SUBTRACTION)
            result = String.valueOf(-1 * Integer.parseInt(expr.solve(context)));

        return result;
    }

    public AbstractExpression getExpr() {
        return this.expr;
    }

    public Token getOp() {
        return this.op;
    }
}
