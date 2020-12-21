package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.Llvm.*;
import TP2.TypeException;
import TP2.Utils;

import java.util.ArrayList;
import java.util.List;

public class If extends Instruction {

    Expression condition;
    Instruction thenBlock;
    Instruction elseBlock;

    public If(Expression condition, Instruction thenBlock, Instruction elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public If(Expression condition, Instruction thenBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = null;
    }

    @Override
    public String pp() {
        String s = "IF " + condition.pp() + "\n\tTHEN\n";
        s += "\t" + thenBlock.pp() + "\n";
        if (elseBlock != null) {
            s += "\tELSE\n";
            s += "\t" + elseBlock.pp() + "\n";
        }
        s += "\tFI";
        return s;
    }

    @Override
    public Llvm.IR toIR() throws TypeException {
        Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());

        String thenLabel = Utils.newlab("then");
        String elseLabel = "";
        String fiLabel = "";

        Expression.RetExpression condRet = condition.toIR();
        if (elseBlock != null) {
            elseLabel = Utils.newlab("else");
            condRet.ir.appendCode(new BrIf(condRet.result, thenLabel, elseLabel));
            fiLabel = Utils.newlab("fi");
        } else {
            fiLabel = Utils.newlab("fi");
            condRet.ir.appendCode(new BrIf(condRet.result, thenLabel, fiLabel));
        }
        ir.append(condRet.ir);

        ir.appendCode(new Label(thenLabel));
        ir.append(thenBlock.toIR());
        ir.appendCode(new BrLabel(fiLabel));

        if (elseBlock != null) {
            ir.appendCode(new Label(elseLabel));
            ir.append(elseBlock.toIR());
            ir.appendCode(new BrLabel(fiLabel));
        }

        ir.appendCode(new Label(fiLabel));

        return ir;
    }
}
