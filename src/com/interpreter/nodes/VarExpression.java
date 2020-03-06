package com.interpreter.nodes;

import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

import java.util.Optional;

public class VarExpression implements AbstractExpression {

    private Token token;

    public VarExpression(Token token) {
        this.token = token;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        String varName = token.getValue(String.class).orElseThrow(RuntimeException::new);
        symbolTable.fetchSymbol(varName);
    }

    @Override
    public Optional<?> solve(Context context) {
        String varName = token.getValue(String.class).orElseThrow(RuntimeException::new);
        return Optional.of(context.getGlobalScope().get(varName)).orElseThrow(RuntimeException::new);
    }

    public Token getVarToken() {
        return token;
    }
}
