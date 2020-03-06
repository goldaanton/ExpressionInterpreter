package com.interpreter.exceptions;

public class NoSuchSymbolException extends RuntimeException {
    private String name;

    public NoSuchSymbolException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NoSuchSymbolException{" +
                "name='" + name + '\'' +
                '}';
    }
}
