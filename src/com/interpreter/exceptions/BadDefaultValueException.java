package com.interpreter.exceptions;

public class BadDefaultValueException extends RuntimeException {

    private String name;

    public BadDefaultValueException(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BadDefaultValueException{" +
                "name='" + name + '\'' +
                '}';
    }
}
