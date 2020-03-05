package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import com.interpreter.token.TokenType;
import java.util.Optional;

public class UnaryOpExpression implements AbstractExpression {

    private Token op;
    private AbstractExpression expr;

    public UnaryOpExpression(Token op, AbstractExpression expr) {
        this.op = op;
        this.expr = expr;
    }

    @Override
    public Optional<?> solve(Context context) {
        TokenType opType = op.getType();

        int opValue = ((Optional<Integer>)expr.solve(context))
                .orElseThrow(RuntimeException::new);

        if(opType == TokenType.ADDITION)
            return Optional.of(opValue);
        else if (opType == TokenType.SUBTRACTION)
            return Optional.of(-1 * opValue);
        return Optional.empty();
    }
}
