package com.interpreter.nodes;

import com.interpreter.solvers.Context;

import java.util.Optional;

public class NoOp implements AbstractExpression {
    @Override
    public Optional<?> solve(Context context) {
        return Optional.empty();
    }
}
