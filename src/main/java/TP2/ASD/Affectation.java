package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.SymbolTable;
import TP2.TypeException;

public class Affectation {
    SymbolTable.Symbol var;
    Expression expr;

    public Affectation(SymbolTable.Symbol var, Expression expr) {
        this.var = var;
        this.expr = expr;
    }

    // Pretty-printer
    public String pp() {
        return  " := " + expr.pp();
    }

    /*
    // IR generation
    public Expression.RetExpression toIR() throws TypeException {



    }*/
}

