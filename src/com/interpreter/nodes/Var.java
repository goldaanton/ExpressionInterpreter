package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import java.util.Optional;

public class Var implements AbstractExpression {

    private Token token;

    public Var(Token token) {
        this.token = token;
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
