package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.Llvm.*;
import TP2.TypeException;

import java.util.List;

public class If extends Instruction {

    Expression condition;
    List<Instruction> thenBlock;
    List<Instruction> elseBlock;

    public If(Expression condition, List<Instruction> thenBlock, List<Instruction> elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String pp() {
        String s = "IF " + condition.pp() + "\n\tTHEN\n\t{\n";
        for (Instruction instruction : thenBlock) {
            if (!(instruction instanceof EndOfBlock)) {
                s += "\t\t" + instruction.pp() + "\n";
            }
        }
        s += "\t}\n\tELSE\n{\n";
        for (Instruction instruction : elseBlock) {
            if (!(instruction instanceof EndOfBlock)) {
                s += "\t\t" + instruction.pp() + "\n";
            }
        }
        s += "\t}\n\tFI";
        return s;
    }

    @Override
    public Llvm.IR toIR() throws TypeException {
        Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());

        String thenLabel = "then" + ++Label.number;
        String elseLabel = "else" + ++Label.number;
        String fiLabel = "if" + ++Label.number;

        Expression.RetExpression condRet = condition.toIR();
        condRet.ir.appendCode(new BrIf(condRet.result, thenLabel, elseLabel));
        ir.append(condRet.ir);

        ir.appendCode(new Label(thenLabel));
        thenBlock.forEach(instruction -> {
            try {
                ir.append(instruction.toIR());
            } catch (TypeException e) {
                e.printStackTrace();
            }
        });
        ir.appendCode(new BrLabel(fiLabel));

        ir.appendCode(new Label(elseLabel));
        elseBlock.forEach(instruction -> {
            try {
                ir.append(instruction.toIR());
            } catch (TypeException e) {
                e.printStackTrace();
            }
        });
        ir.appendCode(new BrLabel(fiLabel));

        ir.appendCode(new Label(fiLabel));

        return ir;
    }
}
