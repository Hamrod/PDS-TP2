package TP2.ASD;

import TP2.Llvm;
import TP2.Llvm.*;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

public class Program {
    public static SymbolTable symbolTable = new SymbolTable();
    List<Function> functions;

    public Program(List<Function> functions) {
        this.functions = functions;
    }

    // Pretty-printer
    public String pp() {
        String s = "";
        for (Function function : functions) {
            s += function.pp() + "\n";
        }
        return s;
    }

    // IR generation
    public IR toIR() throws TypeException {

        IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());

        for (int i = functions.size()-1; i >= 0; i--) {
            ir.append(functions.get(i).toIR());
        }

        return ir;
    }
  }