package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import java.util.Optional;

public class Num implements AbstractExpression {

    private Token token;

    public Num(Token token) {
        this.token = token;
    }

    @Override
    public Optional<?> solve(Context context) {
        return token.getValue(Integer.class);
    }
}
