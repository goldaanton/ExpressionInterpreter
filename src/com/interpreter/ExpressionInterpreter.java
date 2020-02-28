package com.interpreter;

import com.interpreter.solvers.Interpreter;
import com.interpreter.solvers.Lexer;
import com.interpreter.solvers.Parser;

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
            System.out.println(interpreter.getContext().getGlobalScope().size());
            interpreter.getContext().getGlobalScope().forEach((key, value) -> System.out.println("key = " + key + " ; val " + value));
            System.out.println(res);
        }
    }
}
