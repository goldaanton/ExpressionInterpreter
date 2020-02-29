package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

import java.util.Optional;

public class Assign implements AbstractExpression {

    private Var left;
    private AbstractExpression right;
    private Token op;

    public Assign(Var left, Token op, AbstractExpression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public Optional<?> solve(Context context) {
        String varName = left.getVarToken().getValue(String.class).orElseThrow(RuntimeException::new);
        Optional<?> varValue = right.solve(context);
        context.getGlobalScope().put(varName, varValue);
        return Optional.empty();
    }
}
