package com.interpreter.semanticanalizer;

import com.interpreter.token.TokenType;

public abstract class Symbol {
    protected String name;
    protected Symbol type;

    public Symbol(String name) {
        this.name = name;
        this.type = null;
    }
}
