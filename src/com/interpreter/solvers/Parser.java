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

    private AbstractExpression variable() {
        /*
         *      variable : ID
         */

        AbstractExpression node = new VarExpression(currentToken);
        eat(TokenType.ID);
        return node;
    }

    private AbstractExpression factor() {

        /*
         *       factor : PLUS  factor | MINUS factor | INTEGER
         *              | LPAREN expr RPAREN | variable
         */

        Token token = currentToken;

        if(token.getType() == TokenType.ADDITION) {
            eat(TokenType.ADDITION);
            return new UnaryOpExpression(token, factor());
        } else if (token.getType() == TokenType.SUBTRACTION) {
            eat(TokenType.SUBTRACTION);
            return new UnaryOpExpression(token, factor());
        } else if (token.getType() == TokenType.INTEGER) {
            eat(TokenType.INTEGER);
            return new NumExpression(token);
        } else if (token.getType() == TokenType.L_PARENTHESIS) {
            eat(TokenType.L_PARENTHESIS);
            AbstractExpression node = expr();
            eat(TokenType.R_PARENTHESIS);
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
            node = new BinOpExpression(node, token, factor());
        }
        return node;
    }

    private AbstractExpression expr() {

        /*
         *       expr   : term ((PLUS | MINUS) term)*
         */

        AbstractExpression node = term();

        while (currentToken.getType() == TokenType.ADDITION || currentToken.getType() == TokenType.SUBTRACTION) {
            Token token = currentToken;
            if (token.getType() == TokenType.ADDITION) {
                eat(TokenType.ADDITION);
            } else if (token.getType() == TokenType.SUBTRACTION) {
                eat(TokenType.SUBTRACTION);
            }
            node = new BinOpExpression(node, token, term());
        }
        return node;
    }


    private AbstractExpression assignmentStatement() {
        /*
         *      assignment_statement : variable ASSIGN expr
         */

        AbstractExpression left = variable();
        Token token = currentToken;
        eat(TokenType.ASSIGN);
        AbstractExpression right = expr();
        return  new AssignExpression((VarExpression)left, right);
    }

    private AbstractExpression empty() {
        /*
         *      An empty production
         */

        return new NoOpExpression();
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

    private ArrayList<AbstractExpression> statementList() {
        /*
         *      statement_list : statement | statement SEMI statement_list
         */

        AbstractExpression node = statement();

        ArrayList<AbstractExpression> result = new ArrayList<>();
        result.add(node);

        while (currentToken.getType() == TokenType.SEMI) {
            eat(TokenType.SEMI);
            result.add(statement());
        }

        if(currentToken.getType() == TokenType.ID)
            error();

        return result;
    }

    private AbstractExpression compoundStatement() {
        /*
         *      compoundStatement : BEGIN statementList END
         */


        eat(TokenType.BEGIN);
        ArrayList<AbstractExpression> nodes = statementList();
        eat(TokenType.END);

        CompoundExpression root = new CompoundExpression();
        for(AbstractExpression node : nodes) {
            root.getChildren().add(node);
        }
        return root;
    }

    private AbstractExpression program() {
        /*
         *      program : compoundStatement DOT
         */

        AbstractExpression node = compoundStatement();
        eat(TokenType.DOT);
        return node;
    }

    public AbstractExpression parse() {
        AbstractExpression root = program();
        if (currentToken.getType() != TokenType.EOF)
            error();
        return root;
    }
}
