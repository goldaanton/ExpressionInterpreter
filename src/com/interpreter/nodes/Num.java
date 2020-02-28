package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

public class Num implements AbstractExpression {

    private Token token;

    public Num(Token token) {
        this.token = token;
    }

    @Override
    public Object solve(Context context) {
        return token.getValue();
    }
}
