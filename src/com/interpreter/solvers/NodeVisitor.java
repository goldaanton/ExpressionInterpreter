package com.interpreter.solvers;

import com.interpreter.nodes.*;

public abstract class NodeVisitor {

    public abstract String visitBinOp(BinOp node);
    public abstract String visitNum(Num node);
    public abstract String visitUnaryOp(UnaryOp node);
    public abstract String visitCompound(Compound node);
    public abstract String visitNoOp(NoOp node);
    public abstract String visitAssign(Assign node);
    public abstract String visitVar(Var node);


    public String genericVisit(AST node) {
        System.out.println("There is no such node type");
        return null;
    }

    public String visit(AST node) {
        if(node instanceof BinOp)
            return visitBinOp((BinOp)node);
        else if (node instanceof Num)
            return visitNum((Num) node);
        else if (node instanceof UnaryOp)
            return visitUnaryOp((UnaryOp) node);
        else if (node instanceof Compound)
            return visitCompound((Compound) node);
        else if (node instanceof NoOp)
            return visitNoOp((NoOp) node);
        else if (node instanceof Assign)
            return visitAssign((Assign) node);
        else if (node instanceof Var)
            return visitVar((Var) node);
        else
            return genericVisit(node);
    }
}
