package com.interpreter;

import static java.lang.System.exit;

public class Lexer {

    private String expression;
    private int pos;
    private char currentChar;

    private final char none = '\0';

    Lexer(String expression) {
        this.expression = expression;
        this.pos = 0;
        this.currentChar = expression.charAt(pos);
    }

    private void error() {
        System.out.println("Invalid character");
        exit(1);
    }

    private void advance() {
        pos++;
        if(pos >= expression.length())
            currentChar = none;
        else
            currentChar = expression.charAt(pos);
    }

    private void skipWhiteSpaces() {
        while (currentChar != none && Character.isWhitespace(currentChar))
            advance();
    }

    private String getInteger() {
        String res = "";
        while(currentChar != none && Character.isDigit(currentChar)) {
            res += currentChar;
            advance();
        }
        return res;
    }

    public Token getNextToken() {
        while (currentChar != none) {
            if (Character.isWhitespace(currentChar))
                skipWhiteSpaces();

            if (Character.isDigit(currentChar))
                return new Token(TokenType.INTEGER, getInteger());

            if (currentChar == '+') {
                advance();
                return new Token(TokenType.ADDITION, "+");
            }

            if (currentChar == '-') {
                advance();
                return new Token(TokenType.SUBTRACTION, "-");
            }

            if (currentChar == '*') {
                advance();
                return new Token(TokenType.MULTIPLICATION, "*");
            }

            if (currentChar == '/') {
                advance();
                return new Token(TokenType.DIVISION, "/");
            }

            if (currentChar == '(') {
                advance();
                return new Token(TokenType.LPARENTHESIS, "(");
            }

            if (currentChar == ')') {
                advance();
                return new Token(TokenType.RPARENTHESIS, ")");
            }

            error();
        }

        return new Token(TokenType.EOF, null);
    }
}
