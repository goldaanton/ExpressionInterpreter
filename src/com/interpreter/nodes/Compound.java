package com.interpreter.nodes;

import com.interpreter.solvers.Context;

import java.util.ArrayList;
import java.util.Optional;

public class Compound implements AbstractExpression {

    private ArrayList<AbstractExpression> children;

    public Compound() {
        children = new ArrayList<AbstractExpression>();
    }

    @Override
    public Optional<?> solve(Context context) {
        for(AbstractExpression child : children) {
            child.solve(context);
        }
        return null;
    }

    public ArrayList<AbstractExpression> getChildren() {
        return children;
    }
}
