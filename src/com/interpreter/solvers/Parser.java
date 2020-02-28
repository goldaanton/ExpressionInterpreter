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

    private AbstractExpression factor() {

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
            AbstractExpression node = expr();
            eat(TokenType.RPARENTHESIS);
            return node;
        } else {
            return variable();
        }
    }

    private AbstractExpression term() {

        /*
         *       term : factor ((MUL | DIV) factor)*
         */

        AbstractExpression node = factor();

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

    private AbstractExpression expr() {

        /*
         *       expr   : term ((PLUS | MINUS) term)*
         *       term   : factor ((MUL | DIV) factor)*
         *       factor : INTEGER | LPARENTHESIS expr RPARENTHESIS
         */

        AbstractExpression node = term();

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

    private AbstractExpression program() {
        AbstractExpression node = compoundStatement();
        eat(TokenType.DOT);
        return node;
    }

    private AbstractExpression compoundStatement() {
        eat(TokenType.BEGIN);
        ArrayList<AbstractExpression> nodes = statementList();
        eat(TokenType.END);

        Compound root = new Compound();
        for(AbstractExpression node : nodes) {
            root.getChildren().add(node);
        }
        return root;
    }

    private ArrayList<AbstractExpression> statementList() {
        /*
         *      statement_list : statement | statement SEMI statement_list
         */

        AbstractExpression node = statement();

        ArrayList<AbstractExpression> result = new ArrayList<AbstractExpression>();
        result.add(node);

        while (currentToken.getType() == TokenType.SEMI) {
            eat(TokenType.SEMI);
            result.add(statement());
        }

        if(currentToken.getType() == TokenType.ID)
            error();

        return result;
    }

    private AbstractExpression statement() {
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

    private AbstractExpression assignmentStatement() {
        /*
         *      assignment_statement : variable ASSIGN expr
         */

        AbstractExpression left = variable();
        Token token = currentToken;
        eat(TokenType.ASSIGN);
        AbstractExpression right = expr();
        return  new Assign((Var)left, token, right);
    }

    private AbstractExpression variable() {
        /*
         *      variable : ID
         */

        AbstractExpression node = new Var(currentToken);
        eat(TokenType.ID);
        return node;
    }

    private AbstractExpression empty() {
        /*
         *      An empty production
         */

        return new NoOp();
    }

    public AbstractExpression parse() {
        AbstractExpression node = program();
        if (currentToken.getType() != TokenType.EOF)
            error();
        return node;
    }
}
