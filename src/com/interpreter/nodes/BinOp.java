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
    public String solve(Context context) {
        String result = null;

        String leftResult = left.solve(context);
        String rightResult = right.solve(context);

        if(op.getType() == TokenType.ADDITION) {
            result = String.valueOf(Integer.parseInt(leftResult) + Integer.parseInt(rightResult));
        } else if (getOp().getType() == TokenType.SUBTRACTION) {
            return String.valueOf(Integer.parseInt(leftResult) - Integer.parseInt(rightResult));
        } else if (getOp().getType() == TokenType.DIVISION) {
            return String.valueOf(Integer.parseInt(leftResult) / Integer.parseInt(rightResult));
        } else if (getOp().getType() == TokenType.MULTIPLICATION) {
            return String.valueOf(Integer.parseInt(leftResult) * Integer.parseInt(rightResult));
        }

        return result;
    }

    public Token getOp() {
        return this.op;
    }

    public AbstractExpression getLeft() {
        return this.left;
    }

    public AbstractExpression getRight() {
        return this.right;
    }
}
