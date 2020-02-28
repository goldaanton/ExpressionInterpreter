package com.interpreter.nodes;

import com.interpreter.solvers.Context;

public interface AbstractExpression {
    String solve(Context context);
}
