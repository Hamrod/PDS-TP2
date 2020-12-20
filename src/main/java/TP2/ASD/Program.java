package TP2.ASD;

import TP2.Llvm.*;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

public class Program {
    public static SymbolTable symbolTable;
    Instruction block;

    public Program(Instruction block) {
        this.block = block;
    }

    // Pretty-printer
    public String pp() {
        return block.pp();
    }

    // IR generation
    public IR toIR() throws TypeException {
        return block.toIR();
    }
  }