package com.interpreter.solvers;

import com.interpreter.nodes.*;
import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

import java.util.ArrayList;

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

    private AST factor() {

        /*
         *       factor : PLUS  factor | MINUS factor | INTEGER
         *              | LPAREN expr RPAREN | variable
         */

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
            return variable();
        }
    }

    private AST term() {

        /*
         *       term : factor ((MUL | DIV) factor)*
         */

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

        /*
         *       expr   : term ((PLUS | MINUS) term)*
         *       term   : factor ((MUL | DIV) factor)*
         *       factor : INTEGER | LPARENTHESIS expr RPARENTHESIS
         */

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

    private AST program() {
        AST node = compoundStatement();
        eat(TokenType.DOT);
        return node;
    }

    private AST compoundStatement() {
        eat(TokenType.BEGIN);
        ArrayList<AST> nodes = statementList();
        eat(TokenType.END);

        Compound root = new Compound();
        for(AST node : nodes) {
            root.getChildren().add(node);
        }
        return root;
    }

    private ArrayList<AST> statementList() {
        /*
         *      statement_list : statement | statement SEMI statement_list
         */

        AST node = statement();

        ArrayList<AST> result = new ArrayList<AST>();
        result.add(node);

        while (currentToken.getType() == TokenType.SEMI) {
            eat(TokenType.SEMI);
            result.add(statement());
        }

        if(currentToken.getType() == TokenType.ID)
            error();

        return result;
    }

    private AST statement() {
        /*
         *      statement : compound_statement | assignment_statement | empty
         */

        if(currentToken.getType() == TokenType.BEGIN)
            return compoundStatement();
        else if (currentToken.getType() == TokenType.ID)
            return assignmentStatement();
        else
            return empty();
    }

    private AST assignmentStatement() {
        /*
         *      assignment_statement : variable ASSIGN expr
         */

        AST left = variable();
        Token token = currentToken;
        eat(TokenType.ASSIGN);
        AST right = expr();
        return  new Assign((Var)left, token, right);
    }

    private AST variable() {
        /*
         *      variable : ID
         */

        AST node = new Var(currentToken);
        eat(TokenType.ID);
        return node;
    }

    private AST empty() {
        /*
         *      An empty production
         */

        return new NoOp();
    }

    public AST parse() {
        AST node = program();
        if (currentToken.getType() != TokenType.EOF)
            error();
        return node;
    }
}
