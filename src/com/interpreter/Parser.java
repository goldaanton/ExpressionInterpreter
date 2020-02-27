package com.interpreter;

import static java.lang.System.exit;

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

    private int factor() {
        Token token = currentToken;
        if (token.getType() == TokenType.INTEGER) {
            eat(TokenType.INTEGER);
            return Integer.parseInt(token.getValue());
        } else if (token.getType() == TokenType.LPARENTHESIS) {
            eat(TokenType.LPARENTHESIS);
            int result = expr();
            eat(TokenType.RPARENTHESIS);
            return result;
        } else {
            error();
            return 0;
        }

        /*Token token = currentToken;
        if(token.getType() == TokenType.INTEGER) {
            eat(TokenType.INTEGER);
            return new Num(token);
        } else if (token.getType() == TokenType.LPARENTHESIS) {
            eat(TokenType.LPARENTHESIS);
            AST node = expr();
            eat(TokenType.RPARENTHESIS);
            return node;
        }
        return null;*/
    }

    private int term() {
        int result = factor();

        while (currentToken.getType() == TokenType.DIVISION || currentToken.getType() == TokenType.MULTIPLICATION) {
            Token token = currentToken;
            if (token.getType() == TokenType.MULTIPLICATION) {
                eat(TokenType.MULTIPLICATION);
                result *= factor();
            } else if (token.getType() == TokenType.DIVISION) {
                eat(TokenType.DIVISION);
                result /= factor();
            }
        }

        return result;

        /*AST node = factor();

        while(currentToken.getType() == TokenType.MULTIPLICATION || currentToken.getType() == TokenType.DIVISION) {
            Token token = currentToken;
            if(token.getType() == TokenType.MULTIPLICATION) {
                eat(TokenType.MULTIPLICATION);
            } else if (token.getType() == TokenType.DIVISION) {
                eat(TokenType.DIVISION);
            }
            node = new BinOp(node, token, factor());
        }
        return node;*/
    }

    private int expr() {
        int result = term();

        while (currentToken.getType() == TokenType.ADDITION || currentToken.getType() == TokenType.SUBTRACTION) {
            Token token = currentToken;
            if (token.getType() == TokenType.ADDITION) {
                eat(TokenType.ADDITION);
                result += term();
            } else if (token.getType() == TokenType.SUBTRACTION) {
                eat(TokenType.SUBTRACTION);
                result -= term();
            }
        }
        return result;

        /*AST node = factor();

        while (currentToken.getType() == TokenType.ADDITION || currentToken.getType() == TokenType.SUBTRACTION) {
            Token token = currentToken;
            if (token.getType() == TokenType.ADDITION) {
                eat(TokenType.ADDITION);
            } else if (token.getType() == TokenType.SUBTRACTION) {
                eat(TokenType.SUBTRACTION);
            }
            node = new BinOp(node, token, factor());
        }
        return node;
    }

    private AST parse() {
        return expr();
    }*/
    }
}
