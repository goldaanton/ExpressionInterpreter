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
    public Object solve(Context context) {
        TokenType opType = op.getType();

        if(opType == TokenType.ADDITION)
            return expr.solve(context);
        else if (opType == TokenType.SUBTRACTION)
            return (-1 * (Integer)expr.solve(context));
        return null;
    }
}
