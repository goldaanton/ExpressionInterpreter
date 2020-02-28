package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

public class BinOp implements AbstractExpression {

    private AbstractExpression right;
    private AbstractExpression left;
    private Token op;

    public BinOp(AbstractExpression left, Token op, AbstractExpression right) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Object solve(Context context) {
        Object leftResult = left.solve(context);
        Object rightResult = right.solve(context);

        if(op.getType() == TokenType.ADDITION) {
            return (Integer)leftResult + (Integer)rightResult;
        } else if (op.getType() == TokenType.SUBTRACTION) {
            return (Integer)leftResult - (Integer)rightResult;
        } else if (op.getType() == TokenType.DIVISION) {
            return (Integer)leftResult / (Integer)rightResult;
        } else if (op.getType() == TokenType.MULTIPLICATION) {
            return (Integer)leftResult * (Integer)rightResult;
        }
        return null;
    }
}
