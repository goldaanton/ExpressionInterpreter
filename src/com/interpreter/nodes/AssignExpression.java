package com.interpreter.nodes;

import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;

import java.util.Optional;

public class AssignExpression implements AbstractExpression {

    private VarExpression left;
    private AbstractExpression right;

    public AssignExpression(VarExpression left, AbstractExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        String varName = left.getVarToken().getValue(String.class).orElseThrow(RuntimeException::new);
        symbolTable.fetchSymbol(varName);
        right.analyzeNode(symbolTable);
    }

    @Override
    public Optional<?> solve(Context context) {
        String varName = left.getVarToken().getValue(String.class).orElseThrow(RuntimeException::new);
        Optional<?> varValue = right.solve(context);
        context.getGlobalScope().put(varName, varValue);
        return Optional.empty();
    }
}
