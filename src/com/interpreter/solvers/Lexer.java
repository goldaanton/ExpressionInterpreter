package com.interpreter.solvers;

import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.exit;

public class Lexer {

    private String expression;
    private int pos;
    private char currentChar;

    private final char none = '\0';

    private static Map<String, Token> RESERVED_KEYWORDS;

    static {
        RESERVED_KEYWORDS = new HashMap<String, Token>();

        Token beginToken = new Token(TokenType.BEGIN, "BEGIN");
        Token endToken = new Token(TokenType.END, "END");

        RESERVED_KEYWORDS.put((String)beginToken.getValue(), beginToken);
        RESERVED_KEYWORDS.put((String)endToken.getValue(), endToken);
    }

    public Lexer(String expression) {
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

    private int getInteger() {
        StringBuilder integer = new StringBuilder();
        while(currentChar != none && Character.isDigit(currentChar)) {
            integer.append(currentChar);
            advance();
        }
        return Integer.parseInt(integer.toString());
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
            if(Character.isAlphabetic(currentChar)) {
                return id();
            }
            if(currentChar == '=') {
                advance();
                return new Token(TokenType.ASSIGN, "=");
            }
            if(currentChar == ';') {
                advance();
                return new Token(TokenType.SEMI, ";");
            }
            if(currentChar == '.') {
                advance();
                return new Token(TokenType.DOT, ".");
            }

            error();
        }
        return new Token(TokenType.EOF, null);
    }

//    private char peek() {
//        int peekPos = ++pos;
//        if(peekPos >= expression.length())
//            return none;
//        else
//            return expression.charAt(peekPos);
//    }

    private Token id() {
        StringBuilder result = new StringBuilder();

        while(currentChar != none && Character.isAlphabetic(currentChar)) {
            result.append(currentChar);
            advance();
        }

        return RESERVED_KEYWORDS.getOrDefault(result.toString(), new Token(TokenType.ID, result.toString()));
    }
}
