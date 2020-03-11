package com.interpreter.nodes;

import com.interpreter.exceptions.RedefinitionVariableException;
import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;
import com.interpreter.symbols.Symbol;
import com.interpreter.symbols.VarSymbol;
import com.interpreter.token.TokenType;

import java.util.Optional;

public class VarDeclarationExpression extends DeclarationExpression {

    private VarExpression variable;
    private TokenType tokenType;

    public VarDeclarationExpression(VarExpression variable, TokenType tokenType) {
        this.variable = variable;
        this.tokenType = tokenType;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        String varName = variable.getVarToken().getValue(String.class).orElseThrow(RuntimeException::new);
        TokenType varType = tokenType;

        if (symbolTable.containSymbol(varName))
            throw new RedefinitionVariableException(varName);

        Symbol varTypeSymbol = symbolTable.fetchSymbol(varType.name());
        symbolTable.defineSymbol(new VarSymbol(varName, varTypeSymbol));
    }

    @Override
    public Optional<?> solve(Context context) {
        context.getGlobalScope().put(variable.getVarToken().getValue(String.class)
                .orElseThrow(RuntimeException::new), TokenType.getDefaultValue(tokenType));

        return Optional.empty();
    }
}
