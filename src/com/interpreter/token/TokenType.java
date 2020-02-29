package com.interpreter.token;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum TokenType {
    INTEGER('d'),
    ADDITION('+'),
    SUBTRACTION('-'),
    DIVISION('/'),
    MULTIPLICATION('*'),
    LPARENTHESIS('('),
    RPARENTHESIS(')'),
    EOF('\0'),
    BEGIN('b'),
    END('e'),
    DOT('.'),
    ID('i'),
    ASSIGN('='),
    SEMI(';');

    private static final Map<Character, TokenType> TOKEN_ABBREVIATION = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(TokenType::getTokenTypeAbbreviation, tokenType -> tokenType)));

    public static TokenType getTokenTypeByAbbreviation(char abbreviation) {
        return TOKEN_ABBREVIATION.getOrDefault(abbreviation, EOF);
    }

    public char getTokenTypeAbbreviation() {
        return tokenTypeAbbreviation;
    }

    private char tokenTypeAbbreviation;

    TokenType(char tokenTypeAbbreviation) {
        this.tokenTypeAbbreviation = tokenTypeAbbreviation;
    }
}
