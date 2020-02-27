package com.interpreter;

public abstract class NodeVisitor {

    public abstract String visitBinOp(BinOp node);
    public abstract String visitNum(Num node);

    public String genericVisit(AST node) {
        System.out.println("There is no such node type");
        return null;
    }

    public String visit(AST node) {
        if(node instanceof BinOp)
            return visitBinOp((BinOp)node);
        else if (node instanceof Num)
            return visitNum((Num) node);
        else
            return genericVisit(node);
    }
}
