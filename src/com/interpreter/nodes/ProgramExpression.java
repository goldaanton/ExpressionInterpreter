package com.interpreter.nodes;

import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;

import java.util.Optional;

public class ProgramExpression implements AbstractExpression {

    private String name;
    private BlockExpression block;

    public ProgramExpression(String name, BlockExpression block) {
        this.name = name;
        this.block = block;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        block.analyzeNode(symbolTable);
    }

    @Override
    public Optional<?> solve(Context context) {
        block.solve(context);
        return Optional.empty();
    }

    public String getName() {
        return name;
    }
}
