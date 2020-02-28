package com.interpreter.solvers;

import java.util.HashMap;

public class Context {

    private HashMap<String, String> globalScope;

    public Context() {
        globalScope = new HashMap<>();
    }

    public HashMap<String, String> getGlobalScope() {
        return globalScope;
    }
}
