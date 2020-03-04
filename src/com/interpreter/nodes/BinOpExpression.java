package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

import java.util.Optional;

public class BinOpExpression implements AbstractExpression {

    private AbstractExpression right;
    private AbstractExpression left;
    private Token op;

    public BinOpExpression(AbstractExpression left, Token op, AbstractExpression right) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public Optional<?> solve(Context context) {
        int leftValue = ((Optional<Integer>)left.solve(context))
                .orElseThrow(RuntimeException::new);
        int rightValue = ((Optional<Integer>)right.solve(context))
                .orElseThrow(RuntimeException::new);

        if(op.getType() == TokenType.ADDITION) {
            return Optional.of(leftValue + rightValue);
        } else if (op.getType() == TokenType.SUBTRACTION) {
            return Optional.of(leftValue - rightValue);
        } else if (op.getType() == TokenType.DIVISION) {
            return Optional.of(leftValue / rightValue);
        } else if (op.getType() == TokenType.MULTIPLICATION) {
            return Optional.of(leftValue * rightValue);
        }
        throw new RuntimeException();
    }
}
