package TP2.ASD;

import TP2.Llvm;
import TP2.Llvm.*;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.ArrayList;
import java.util.List;

public class Block extends Instruction {

    List<Instruction> instructionList;

    public Block(List<Instruction> instructionList) {
        this.instructionList = instructionList;
    }

    @Override
    public String pp() {
        if (instructionList.size() == 1) {
            return '\t' + instructionList.get(0).pp();
        } else {
            String s = "{\n";
            for (Instruction instruction : instructionList) {
                s += "\t" + instruction.pp() + "\n";
            }
            return s + "}";
        }
    }

    @Override
    public IR toIR() throws TypeException {
        IR ir = new IR(Llvm.empty(), Llvm.empty());

        if (Program.symbolTable == null) {
            Program.symbolTable = new SymbolTable();
        } else {
            Program.symbolTable = new SymbolTable(Program.symbolTable);
        }

        for (Instruction instruction : instructionList) {
            ir.append(instruction.toIR());
        }

        TP2.ASD.Program.symbolTable.toParent();

        return ir;
    }
}
