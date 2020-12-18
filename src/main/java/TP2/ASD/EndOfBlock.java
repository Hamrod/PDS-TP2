package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

public class EndOfBlock extends Instruction {


    @Override
    public String pp() {
        return "";
    }

    @Override
    public Llvm.IR toIR() throws TypeException {

        TP2.ASD.Program.symbolTable.toParent();

        return new Llvm.IR(Llvm.empty(), Llvm.empty());
    }
}
