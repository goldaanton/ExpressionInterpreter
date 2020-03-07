package com.interpreter.exceptions;

public class RedefinitionVariableException extends RuntimeException {

    private String name;

    public RedefinitionVariableException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RedefinitionVariableException{" +
                "name='" + name + '\'' +
                '}';
    }
}
