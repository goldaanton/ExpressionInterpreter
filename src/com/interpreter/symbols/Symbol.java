package com.interpreter.symbols;

import java.util.Optional;

public abstract class Symbol {
    protected String name;
    protected Symbol type;

    public Symbol(String name) {
        this.name = name;
        this.type = null;
    }

    public Symbol(String name, Symbol type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Optional<Symbol> getType() {
        return Optional.ofNullable(type);
    }
}
