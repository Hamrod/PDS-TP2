package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

public class Affectation {
    String var;
    Expression expr;

    public Affectation(String var, Expression expr) {
        this.var = var;
        this.expr = expr;
    }

    // Pretty-printer
    public String pp() {
        return var +  " := " + expr.pp();
    }


    // IR generation
    public Llvm.IR toIR() throws TypeException {
        Expression.RetExpression ret = expr.toIR();
        Type varType = Program.symbolTable.getType((SymbolTable.VariableSymbol) Program.symbolTable.lookup(var));


        /*if(!ret.type.equals(varType)) {
            throw new TypeException("type mismatch: have " + varType + " and " + ret.type);
        }*/

        Llvm.Instruction store = new Llvm.Store(ret.type.toLlvmType(), var, ret.result);

        ret.ir.appendCode(store);

        return ret.ir;
    }
}

