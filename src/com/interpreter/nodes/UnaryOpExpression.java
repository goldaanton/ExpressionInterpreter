package com.interpreter.nodes;

import com.interpreter.exceptions.NoSuchOperatorException;
import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;
import com.interpreter.token.Token;
import java.util.Optional;

public class UnaryOpExpression implements AbstractExpression {

    private Token op;
    private AbstractExpression expr;

    public UnaryOpExpression(Token op, AbstractExpression expr) {
        this.op = op;
        this.expr = expr;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        expr.analyzeNode(symbolTable);
    }

    @Override
    public Optional<?> solve(Context context) {
        int opValue = ((Optional<Integer>)expr.solve(context))
                .orElseThrow(RuntimeException::new);

        switch (op.getType()) {
            case ADDITION:
                return Optional.of(opValue);
            case SUBTRACTION:
                return Optional.of(-1 * opValue);
            default:
                throw new NoSuchOperatorException("UnaryOpExpression");
        }
    }
}
