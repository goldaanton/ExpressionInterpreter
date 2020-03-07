package com.interpreter.exceptions;

public class NoSuchOperatorException extends RuntimeException{

    private String name;

    public NoSuchOperatorException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NoSuchOperatorException{" +
                "name='" + name + '\'' +
                '}';
    }
}
