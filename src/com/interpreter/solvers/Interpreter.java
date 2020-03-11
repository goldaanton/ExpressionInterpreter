package com.interpreter.solvers;

import com.interpreter.nodes.AbstractExpression;
import com.interpreter.semanticanalyzer.SemanticAnalyzer;

public class Interpreter {

    private Parser parser;
    private Context context;
    private SemanticAnalyzer semanticAnalyzer;

    public Interpreter(Parser parser) {
        this.parser = parser;
        this.context = new Context();
        this.semanticAnalyzer = new SemanticAnalyzer();
    }

    public void interpret() {
        AbstractExpression tree = parser.parse();
        semanticAnalyzer.analyze(tree);
        tree.solve(context);
    }

    public Context getContext() {
        return context;
    }
}
