package TP2.ASD;

import TP2.ASD.Expr.Expression.*;
import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.Llvm.*;
import TP2.TypeException;

public class Return extends Instruction {

    Expression expr;

    public Return(Expression expr) {
        this.expr = expr;
    }

    @Override
    public String pp() {
        return "RETURN " + expr.pp();
    }

    @Override
    public Llvm.IR toIR() throws TypeException {
        RetExpression ret = expr.toIR();
        ret.ir.appendCode(new Llvm.Return(ret.type.toLlvmType(), ret.result));
        return ret.ir;
    }
}
