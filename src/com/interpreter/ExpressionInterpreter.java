package com.interpreter;

import java.util.Scanner;

public class ExpressionInterpreter {
    public static void main(String[] args) {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String expression = scanner.nextLine();
            Lexer lexer = new Lexer(expression);
            Parser parser = new Parser(lexer);
            Interpreter interpreter = new Interpreter(parser);
            int res = interpreter.interpret();
            System.out.println(res);
        }
    }
}
