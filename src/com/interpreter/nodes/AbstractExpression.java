package com.interpreter.nodes;

import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;

import java.util.Optional;

public interface AbstractExpression {
    void analyzeNode(SymbolTable symbolTable);
    Optional<?> solve(Context context);
}
