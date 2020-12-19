package TP2.ASD;

import TP2.Llvm.*;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

public class Program {
    public static SymbolTable symbolTable;
    List<Instruction> instructions;

    public Program(List<Instruction> i) {
        this.instructions = i;
    }

    // Pretty-printer
    public String pp() {
        String s  = "{\n";
        for (Instruction instruction : instructions) {
            if (!(instruction instanceof EndOfBlock)) {
                s += '\t' + instruction.pp() + '\n';
            }
        }
        return s + "}";
    }

    // IR generation
    public IR toIR() throws TypeException {
        IR ir = new IR(Llvm.empty(), Llvm.empty());
        for (Instruction instruction : instructions) {
            ir.append(instruction.toIR());
        }
        return ir;
    }
  }