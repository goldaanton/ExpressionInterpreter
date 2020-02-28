package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

import static java.lang.System.exit;

public class Var implements AbstractExpression {

    private Token token;

    public Var(Token token) {
        this.token = token;
    }

    @Override
    public String solve(Context context) {

        String varName = token.getValue();
        String val = context.getGlobalScope().get(varName);

        if(val == "") {
            System.out.println("There is no such variable");
            exit(1);
        }
        return val;
    }

    public Token getVarToken() {
        return token;
    }
}
