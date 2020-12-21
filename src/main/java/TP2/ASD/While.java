package TP2.ASD;

import TP2.ASD.Expr.Expression.*;
import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.Llvm.*;
import TP2.TypeException;
import TP2.Utils;

import java.util.List;

public class While extends Instruction {

    Expression condition;
    Instruction block;

    public While(Expression condition, Instruction block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String pp() {
        String s = "WHILE " + condition.pp() + "\n\tDO\n";
        s += "\t" + block.pp() + "\n";
        s += "\tDONE";
        return s;
    }

    @Override
    public IR toIR() throws TypeException {

        IR ir = new IR(Llvm.empty(), Llvm.empty());

        String whileLabel = Utils.newlab("while");
        ir.appendCode(new BrLabel(whileLabel));

        ir.appendCode(new Label(whileLabel));
        RetExpression condRet = condition.toIR();
        String doLabel = Utils.newlab("do");
        String doneLabel = Utils.newlab("done");
        condRet.ir.appendCode(new BrIf(condRet.result, doLabel, doneLabel));
        ir.append(condRet.ir);

        ir.appendCode(new Label(doLabel));
        ir.append(block.toIR());
        ir.appendCode(new BrLabel(whileLabel));

        ir.appendCode(new Label(doneLabel));
        return ir;
    }
}
