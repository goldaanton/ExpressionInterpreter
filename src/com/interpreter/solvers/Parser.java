package com.interpreter.solvers;

import com.interpreter.nodes.*;
import com.interpreter.token.Token;
import com.interpreter.token.TokenType;

import javax.lang.model.element.VariableElement;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    private TypeExpression typeSpecification() {
        /*
         * type_spec : INTEGER | REAL
         */

        Token token = currentToken;
        if(currentToken.getType() == TokenType.INTEGER)
            eat(TokenType.INTEGER);
        else
            eat(TokenType.DOUBLE);
        return new TypeExpression(token);
    }

    private List<DeclarationExpression> variableDeclaration() {
        /*
         *      variable_declaration : ID (COMMA ID)* COLON type_spec
         */

        List<VarExpression> varNodes = new ArrayList<>();
        varNodes.add(new VarExpression(currentToken));
        eat(TokenType.ID);

        while(currentToken.getType() == TokenType.COMMA) {
            eat(TokenType.COMMA);
            varNodes.add(new VarExpression(currentToken));
            eat(TokenType.ID);
        }

        eat(TokenType.COLON);

        TypeExpression typeExpression = typeSpecification();

        List<DeclarationExpression> variableDeclaration = new ArrayList<>();

        for(VarExpression varExpression : varNodes) {
            variableDeclaration.add(new VarDeclarationExpression(varExpression, typeExpression.getToken().getType()));
        }

        return variableDeclaration;
    }

    private List<DeclarationExpression> declaration() {
        /*
         *      declarations : VAR (variable_declaration SEMI)+ | empty
         */

        List<DeclarationExpression>  declaration = new ArrayList<>();
        if(currentToken.getType() == TokenType.VAR) {
            eat(TokenType.VAR);

            while (currentToken.getType() == TokenType.ID) {
                List<DeclarationExpression> varDel = variableDeclaration();
                declaration.addAll(varDel);
                eat(TokenType.SEMI);
            }
        }
        return  declaration;
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
        } else if (token.getType() == TokenType.DOUBLE) {
            eat(TokenType.DOUBLE);
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

    private BlockExpression block() {
        /*
         *      block : declaration compoundStatement
         */

        List<DeclarationExpression> declarationNodes = declaration();
        CompoundExpression compoundExpression = (CompoundExpression)compoundStatement();

        return new BlockExpression(declarationNodes, compoundExpression);
    }

    private AbstractExpression program() {
        /*
         *      program : PROGRAM variable SEMI block DOT
         */

        eat(TokenType.PROGRAM);
        VarExpression varNode = (VarExpression) variable();
        String programName = (varNode).getVarToken().getValue(String.class).orElseThrow(RuntimeException::new);
        eat(TokenType.SEMI);

        BlockExpression blockExpression = block();

        ProgramExpression programExpression = new ProgramExpression(programName, blockExpression);
        eat(TokenType.DOT);

        return programExpression;
    }

    public AbstractExpression parse() {
        AbstractExpression root = program();
        if (currentToken.getType() != TokenType.EOF)
            error();
        return root;
    }
}
