package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.TokenType;

import java.util.Optional;

public class VarDeclarationExpression extends DeclarationExpression{

    private VarExpression variable;
    private TokenType tokenType;

    public VarDeclarationExpression(VarExpression variable, TokenType tokenType) {
        this.variable = variable;
        this.tokenType = tokenType;
    }

    @Override
    public Optional<?> solve(Context context) {
        context.getGlobalScope().put(variable.getVarToken().getValue(String.class).orElseThrow(RuntimeException::new), TokenType.getDefaultValue(tokenType));

        return Optional.empty();
    }
}
