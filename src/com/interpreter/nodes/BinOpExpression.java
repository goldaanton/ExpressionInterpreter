package com.interpreter.nodes;

import com.interpreter.exceptions.NoSuchOperatorException;
import com.interpreter.semanticanalyzer.SymbolTable;
import com.interpreter.solvers.Context;
import com.interpreter.token.Token;

import java.util.Optional;

public class BinOpExpression implements AbstractExpression {

    private AbstractExpression right;
    private AbstractExpression left;
    private Token op;

    public BinOpExpression(AbstractExpression left, Token op, AbstractExpression right) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public void analyzeNode(SymbolTable symbolTable) {
        left.analyzeNode(symbolTable);
        right.analyzeNode(symbolTable);
    }

    @Override
    public Optional<?> solve(Context context) {
        int leftValue = ((Optional<Integer>) left.solve(context))
                .orElseThrow(RuntimeException::new);
        int rightValue = ((Optional<Integer>) right.solve(context))
                .orElseThrow(RuntimeException::new);

        switch (op.getType()) {
            case ADDITION:
                return Optional.of(leftValue + rightValue);
            case SUBTRACTION:
                return Optional.of(leftValue - rightValue);
            case MULTIPLICATION:
                return Optional.of(leftValue * rightValue);
            case DIVISION:
                return Optional.of(leftValue / rightValue);
            default:
                throw new NoSuchOperatorException("BinOpExpression");
        }
    }
}
