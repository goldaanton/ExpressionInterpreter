package com.interpreter.nodes;

import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

import java.util.Optional;

public class TypeExpression implements AbstractExpression {

    private Token token;

    public TypeExpression(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public Optional<?> solve(Context context) {


        return Optional.empty();
    }
}
