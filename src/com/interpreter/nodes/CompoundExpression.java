package com.interpreter.nodes;

import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;

import java.util.ArrayList;
import java.util.Optional;

public class CompoundExpression implements AbstractExpression {

    private ArrayList<AbstractExpression> children;

    public CompoundExpression() {
        children = new ArrayList<>();
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        for (AbstractExpression child : children) {
            child.analyzeNode(symbolTable);
        }
    }

    @Override
    public Optional<?> solve(Context context) {
        for (AbstractExpression child : children) {
            child.solve(context);
        }
        return Optional.empty();
    }

    public ArrayList<AbstractExpression> getChildren() {
        return children;
    }
}
