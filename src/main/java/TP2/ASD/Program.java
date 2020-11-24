package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

public class Program {
    public static SymbolTable symbolTable;
    List<Instruction> instructions;

    public Program(List<Instruction> i) {
        this.instructions = i;
        symbolTable = new SymbolTable();
    }

    // Pretty-printer
    public String pp() {
        String s  = "";
        for (Instruction instruction : instructions) {
            s += instruction.pp();
        }
        return s;
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException {
        Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());
        for (Instruction instruction : instructions) {
            ir.append(instruction.toIR());
        }
        return ir;
    }
  }