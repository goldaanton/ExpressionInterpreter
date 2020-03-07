package com.interpreter.exceptions;

public class InappropriateTypeException extends RuntimeException {

    private String name;

    public InappropriateTypeException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "InappropriateTypeException{" +
                "name='" + name + '\'' +
                '}';
    }
}
