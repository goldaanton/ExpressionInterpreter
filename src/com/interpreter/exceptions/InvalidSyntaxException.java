package com.interpreter.exceptions;

public class InvalidSyntaxException extends RuntimeException{

    private String name;

    public InvalidSyntaxException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InvalidSyntaxException{" +
                "name='" + name + '\'' +
                '}';
    }
}
