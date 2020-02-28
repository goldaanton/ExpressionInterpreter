package com.interpreter.nodes;

import com.interpreter.solvers.Context;

public class NoOp implements AbstractExpression {
    @Override
    public String solve(Context context) {
        return null;
    }
}
