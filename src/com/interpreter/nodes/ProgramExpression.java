package com.interpreter.nodes;

import com.interpreter.solvers.Context;

import java.util.ArrayList;
import java.util.Optional;

public class ProgramExpression implements AbstractExpression {

    private String name;
    private BlockExpression block;

    public ProgramExpression(String name, BlockExpression block) {
        this.name = name;
        this.block = block;
    }

    @Override
    public Optional<?> solve(Context context) {
        block.solve(context);
        return Optional.empty();
    }
}
