package com.interpreter.nodes;

import com.interpreter.solvers.Context;

public interface AbstractExpression {
    Object solve(Context context);
}
