package com.interpreter.solvers;


import com.interpreter.nodes.*;

public class Interpreter {

    private Parser parser;
    private Context context;

    public Interpreter(Parser parser) {
        this.parser = parser;
        this.context = new Context();
    }

    public void interpret() {
        AbstractExpression tree = parser.parse();
        tree.solve(context);
    }

    public Context getContext() {
        return context;
    }
}
