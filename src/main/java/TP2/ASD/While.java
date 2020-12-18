package TP2.ASD;

import TP2.ASD.Expr.Expression.*;
import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.Llvm.*;
import TP2.TypeException;

import java.util.List;

public class While extends Instruction {

    Expression condition;
    List<Instruction> block;

    public While(Expression condition, List<Instruction> block) {
        this.condition = condition;
        this.block = block;
    }

    @Override
    public String pp() {
        String s = "WHILE " + condition.pp() + " DO\n";
        for (Instruction instruction : block) {
            s += instruction.pp() + "\n";
        }
        return s + "DONE";
    }

    @Override
    public IR toIR() throws TypeException {

        IR ir = new IR(Llvm.empty(), Llvm.empty());

        String whileLabel = "while" + ++Label.number;
        ir.appendCode(new BrLabel(whileLabel));

        ir.appendCode(new Label(whileLabel));
        RetExpression condRet = condition.toIR();
        String doLabel = "do" + ++Label.number;
        String doneLabel = "done" + ++Label.number;
        condRet.ir.appendCode(new BrIf(condRet.result, doLabel, doneLabel));
        ir.append(condRet.ir);

        ir.appendCode(new Label(doLabel));
        block.forEach(instruction -> {
            try {
                ir.append(instruction.toIR());
            } catch (TypeException e) {
                e.printStackTrace();
            }
        });
        ir.appendCode(new BrLabel(whileLabel));

        ir.appendCode(new BrLabel(doneLabel));
        return ir;
    }
}
