package com.interpreter.nodes;

import com.interpreter.nodes.AST;

import java.util.ArrayList;

public class Compound extends AST {

    private ArrayList<AST> children;

    public Compound() {
        children = new ArrayList<AST>();
    }

    public ArrayList<AST> getChildren() {
        return children;
    }
}
