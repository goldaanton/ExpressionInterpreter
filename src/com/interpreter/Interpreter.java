package com.interpreter;


public class Interpreter extends NodeVisitor {

    private Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    @Override
    public String visitBinOp(BinOp node) {

        String result = null;

        String left = visit(node.getLeft());
        String right = visit(node.getRight());

        if(node.getOp() == TokenType.ADDITION) {
            result = String.valueOf(Integer.parseInt(left) + Integer.parseInt(right));
        } else if (node.getOp() == TokenType.SUBTRACTION) {
            return String.valueOf(Integer.parseInt(left) - Integer.parseInt(right));
        } else if (node.getOp() == TokenType.DIVISION) {
            return String.valueOf(Integer.parseInt(left) / Integer.parseInt(right));
        } else if (node.getOp() == TokenType.MULTIPLICATION) {
            return String.valueOf(Integer.parseInt(left) * Integer.parseInt(right));
        }

        return result;
    }

    @Override
    public String visitNum(Num node) {
        return node.getValue();
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

    public int interpret() {
        AST tree = parser.parse();
        return Integer.parseInt(visit(tree));
    }
}
