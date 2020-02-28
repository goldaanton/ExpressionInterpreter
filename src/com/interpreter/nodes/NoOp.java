package com.interpreter.nodes;

import com.interpreter.solvers.Context;

public class NoOp implements AbstractExpression {
    @Override
    public Object solve(Context context) {
        return null;
    }
}
