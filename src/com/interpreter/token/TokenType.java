package com.interpreter.token;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum TokenType {

    ADDITION('+'),
    SUBTRACTION('-'),
    DIVISION('/'),
    MULTIPLICATION('*'),

    L_PARENTHESIS('('),
    R_PARENTHESIS(')'),

    INTEGER('d'),

    BEGIN('b'),
    END('e'),

    ID('i'),

    ASSIGN('='),

    SEMI(';'),
    DOT('.'),

    EOF('\0');

    private char tokenTypeAbbreviation;

    private static final Map<Character, TokenType> TOKEN_ABBREVIATION = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(TokenType::getTokenTypeAbbreviation, tokenType -> tokenType)));

    public static TokenType getTokenTypeByAbbreviation(char abbreviation) {
        return TOKEN_ABBREVIATION.getOrDefault(abbreviation, EOF);
    }

    public char getTokenTypeAbbreviation() {
        return tokenTypeAbbreviation;
    }

    TokenType(char tokenTypeAbbreviation) {
        this.tokenTypeAbbreviation = tokenTypeAbbreviation;
    }
}
