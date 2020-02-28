package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

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
    public String solve(Context context) {
        String varName = left.getVarToken().getValue();
        String varValue = right.solve(context);
        context.getGlobalScope().put(varName, varValue);
        return "";
    }

    public Var getLeft() {
        return this.left;
    }

    public AbstractExpression getRight() {
        return right;
    }
}
