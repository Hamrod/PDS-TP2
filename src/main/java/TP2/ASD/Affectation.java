package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.TypeException;


public class Affectation extends Instruction {
    String var;
    Expression expr;

    public Affectation(String var, Expression expr) {
        this.var = var;
        this.expr = expr;

    }

    @Override
    public String pp() {
        return var +  " := " + expr.pp() + '\n';
    }


    @Override
    public Llvm.IR toIR() throws TypeException {
        Expression.RetExpression ret = expr.toIR();

        Llvm.Instruction store = new Llvm.Store(ret.type.toLlvmType(), var, ret.result);

        ret.ir.appendCode(store);

        return ret.ir;
    }
}

