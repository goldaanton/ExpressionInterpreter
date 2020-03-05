package com.interpreter;

import com.interpreter.solvers.Interpreter;
import com.interpreter.solvers.Lexer;
import com.interpreter.solvers.Parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExpressionInterpreter {
    public static void main(String[] args) {
        StringBuilder expression = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("test\\file.txt"));
            String line = null;
            while((line = reader.readLine()) != null)
                expression.append(line);
            reader.close();
        } catch (IOException ex) {

        }
//        System.out.println(expression.toString());

        Scanner scanner = new Scanner(System.in);
        Lexer lexer = new Lexer(expression.toString());
        Parser parser = new Parser(lexer);
//        parser.parse();
        Interpreter interpreter = new Interpreter(parser);
        interpreter.interpret();
        interpreter.getContext().getGlobalScope().forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
