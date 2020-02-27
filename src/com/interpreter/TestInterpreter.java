package com.interpreter;

import java.util.Scanner;

public class TestInterpreter {
    public static void main(String[] args) {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String expression = scanner.nextLine();
            Lexer lexer = new Lexer(expression);
            Interpreter interpreter = new Interpreter(lexer);
            int res = interpreter.expr();
            System.out.println(res);
        }
    }
}
