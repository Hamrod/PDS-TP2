package TP2.ASD.Expr;

import TP2.ASD.Int;
import TP2.ASD.Type;
import TP2.Llvm;
import TP2.TypeException;
import TP2.Utils;

public class VarExpression extends Expression {

    Type type;
    String ident;

    public VarExpression(Type type, String ident) {
        this.ident = ident;
        this.type = type;
    }

    @Override
    public String pp() {
        return "";
    }

    @Override
    public RetExpression toIR() throws TypeException {

        String result = Utils.newtmp();

        Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty()).appendCode(new Llvm.Load(type.toLlvmType(), ident, result));

        return new RetExpression(ir, type, result);
    }
}
