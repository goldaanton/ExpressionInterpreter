package com.interpreter;

import static java.lang.System.exit;
import static java.lang.System.in;

public class Parser {

    private Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void error() {
        System.out.println("Invalid syntax");
        exit(1);
    }

    private void eat(TokenType type) {
        if (currentToken.getType() == type)
            currentToken = lexer.getNextToken();
        else
            error();
    }

    private AST factor() {
        Token token = currentToken;

        if(token.getType() == TokenType.ADDITION) {
            eat(TokenType.ADDITION);
            return new UnaryOp(token, factor());
        } else if (token.getType() == TokenType.SUBTRACTION) {
            eat(TokenType.SUBTRACTION);
            return new UnaryOp(token, factor());
        } else if (token.getType() == TokenType.INTEGER) {
            eat(TokenType.INTEGER);
            return new Num(token);
        } else if (token.getType() == TokenType.LPARENTHESIS) {
            eat(TokenType.LPARENTHESIS);
            AST node = expr();
            eat(TokenType.RPARENTHESIS);
            return node;
        } else {
            error();
            return null;
        }
    }

    private AST term() {
        AST node = factor();

        while (currentToken.getType() == TokenType.DIVISION || currentToken.getType() == TokenType.MULTIPLICATION) {
            Token token = currentToken;
            if (token.getType() == TokenType.MULTIPLICATION) {
                eat(TokenType.MULTIPLICATION);
            } else if (token.getType() == TokenType.DIVISION) {
                eat(TokenType.DIVISION);
            }
            node = new BinOp(node, token, factor());
        }
        return node;
    }

    private AST expr() {
        AST node = term();

        while (currentToken.getType() == TokenType.ADDITION || currentToken.getType() == TokenType.SUBTRACTION) {
            Token token = currentToken;
            if (token.getType() == TokenType.ADDITION) {
                eat(TokenType.ADDITION);
            } else if (token.getType() == TokenType.SUBTRACTION) {
                eat(TokenType.SUBTRACTION);
            }
            node = new BinOp(node, token, term());
        }
        return node;
    }

    public AST parse() {
        return expr();
    }
}
