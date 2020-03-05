package com.interpreter.solvers;

import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Lexer {

    private String expression;
    private int pos;
    private char currentChar;

    private final char none = '\0';

    private static Map<String, Token> RESERVED_KEYWORDS;

    static {
        HashMap<String, Token> temporaryMap = new HashMap<>();

        Token beginToken = new Token(TokenType.BEGIN, "BEGIN");
        Token endToken = new Token(TokenType.END, "END");

        temporaryMap.put(beginToken.getValue(String.class).orElseThrow(RuntimeException::new), beginToken);
        temporaryMap.put(endToken.getValue(String.class).orElseThrow(RuntimeException::new), endToken);

        RESERVED_KEYWORDS = Collections.unmodifiableMap(temporaryMap);
    }

    public Lexer(String expression) {
        this.expression = expression;
        this.pos = 0;
        this.currentChar = expression.charAt(pos);
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
        StringBuilder integerString = new StringBuilder();
        while(currentChar != none && Character.isDigit(currentChar)) {
            integerString.append(currentChar);
            advance();
        }
        return Integer.parseInt(integerString.toString());
    }

    private Token id() {
        StringBuilder result = new StringBuilder();

        while(currentChar != none && Character.isAlphabetic(currentChar)) {
            result.append(currentChar);
            advance();
        }

        return RESERVED_KEYWORDS.getOrDefault(result.toString(), new Token(TokenType.ID, result.toString()));
    }

    public Token getNextToken() {
        if (Character.isWhitespace(currentChar))
            skipWhiteSpaces();
        if (Character.isDigit(currentChar))
            return new Token(TokenType.INTEGER, getInteger());
        if (Character.isAlphabetic(currentChar)) {
            return id();
        }
        Token token = new Token(TokenType.getTokenTypeByAbbreviation(currentChar));
        advance();
        return token;
    }

//    private char peek() {
//        int peekPos = ++pos;
//        if(peekPos >= expression.length())
//            return none;
//        else
//            return expression.charAt(peekPos);
//    }
}
