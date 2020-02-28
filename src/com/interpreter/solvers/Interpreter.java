package com.interpreter.solvers;


import com.interpreter.nodes.*;
import com.interpreter.token.TokenType;

import java.util.HashMap;

import static java.lang.System.exit;

public class Interpreter extends NodeVisitor {

    private Parser parser;
    private HashMap<String, String> globalScope;

    public Interpreter(Parser parser) {
        this.parser = parser;
        this.globalScope = new HashMap<>();
    }

    @Override
    public String visitBinOp(BinOp node) {

        String result = null;

        String left = visit(node.getLeft());
        String right = visit(node.getRight());

        if(node.getOp().getType() == TokenType.ADDITION) {
            result = String.valueOf(Integer.parseInt(left) + Integer.parseInt(right));
        } else if (node.getOp().getType() == TokenType.SUBTRACTION) {
            return String.valueOf(Integer.parseInt(left) - Integer.parseInt(right));
        } else if (node.getOp().getType() == TokenType.DIVISION) {
            return String.valueOf(Integer.parseInt(left) / Integer.parseInt(right));
        } else if (node.getOp().getType() == TokenType.MULTIPLICATION) {
            return String.valueOf(Integer.parseInt(left) * Integer.parseInt(right));
        }

        return result;
    }

    @Override
    public String visitNum(Num node) {
        return node.getNumToken().getValue();
    }

    @Override
    public String visitUnaryOp(UnaryOp node) {

        String result = null;
        TokenType op = node.getOp().getType();

        if(op == TokenType.ADDITION)
            result = visit(node.getExpr());
        else if (op == TokenType.SUBTRACTION)
            result = String.valueOf(-1 * Integer.parseInt(visit(node.getExpr())));

        return result;
    }

    @Override
    public String visitVar(Var node) {
        String varName = node.getVarToken().getValue();
        String val =globalScope.get(varName);

        if(val == "") {
            System.out.println("There is no such variable");
            exit(1);
        }
        return val;
    }

    @Override
    public String visitAssign(Assign node) {
        String varName = node.getLeft().getVarToken().getValue();
        String varValue = visit(node.getRight());
        globalScope.put(varName, varValue);
        return "";
    }

    @Override
    public String visitNoOp(NoOp node) {
        return null;
    }

    @Override
    public String visitCompound(Compound node) {
        for(AST child : node.getChildren()) {
            visit(child);
        }
        return "";
    }

    public int interpret() {
        AST tree = parser.parse();
        visit(tree);
        return -1;
    }

    public HashMap<String, String> getGlobalScope() {
        return globalScope;
    }
}
