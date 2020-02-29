package com.interpreter.nodes;

import com.interpreter.solvers.Context;

import java.util.Optional;

public interface AbstractExpression {
    Optional<?> solve(Context context);
}
