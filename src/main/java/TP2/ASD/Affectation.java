package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.SymbolTable.*;
import TP2.TypeException;


public class Affectation extends Instruction {
    String ident;
    Expression expr;

    public Affectation(String ident, Expression expr) {
        this.ident = ident;
        this.expr = expr;

    }

    @Override
    public String pp() {
        return ident +  " := " + expr.pp() + '\n';
    }


    @Override
    public Llvm.IR toIR() throws TypeException {

        Expression.RetExpression ret = expr.toIR();

        Symbol var = Program.symbolTable.lookup(ident);

        if(var == null) {
            throw new TypeException("You must initialised variable " + ident + " before using it !");
        }

        Type identType = Program.symbolTable.getType((VariableSymbol) var);


        if(!ret.type.equals(identType)) {
            throw new TypeException("type mismatch: have " + ret.type + " and " + identType);
        }

        Llvm.Instruction store = new Llvm.Store(ret.type.toLlvmType(), ident, ret.result);

        ret.ir.appendCode(store);

        return ret.ir;
    }
}

