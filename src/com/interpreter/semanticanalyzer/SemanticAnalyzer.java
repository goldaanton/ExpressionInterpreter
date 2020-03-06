package com.interpreter.semanticanalyzer;

import com.interpreter.nodes.AbstractExpression;

public class SemanticAnalyzer {

    private SymbolTable symbolTable;

    public SemanticAnalyzer() {
        this.symbolTable = new SymbolTable();
    }

    public void analyze(AbstractExpression root) {
        root.analyzeNode(symbolTable);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
