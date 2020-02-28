package com.interpreter.solvers;

import java.util.HashMap;

public class Context {

    private HashMap<String, Object> globalScope;

    public Context() {
        globalScope = new HashMap<>();
    }

    public HashMap<String, Object> getGlobalScope() {
        return globalScope;
    }
}
