package com.interpreter.nodes;

import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;

import java.util.List;
import java.util.Optional;

public class BlockExpression implements AbstractExpression {

    private List<DeclarationExpression> declarationList;
    private CompoundExpression compoundExpression;

    public BlockExpression(List<DeclarationExpression> declarationList, CompoundExpression compoundExpression) {
        this.declarationList = declarationList;
        this.compoundExpression = compoundExpression;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        for(DeclarationExpression expression : declarationList) {
            expression.analyzeNode(symbolTable);
        }
        compoundExpression.analyzeNode(symbolTable);
    }

    @Override
    public Optional<?> solve(Context context) {
        for (DeclarationExpression declarationExpression : declarationList) {
            declarationExpression.solve(context);
        }
        compoundExpression.solve(context);
        return Optional.empty();
    }
}
