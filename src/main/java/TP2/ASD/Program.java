package TP2.ASD;

import TP2.ASD.Expr.Expression;
import TP2.Llvm;
import TP2.TypeException;

public class Program {
    Affectation a;

    public Program(Affectation a) {
        this.a = a;
    }

    // Pretty-printer
    public String pp() {
      return a.pp();
    }

    // IR generation
    public Llvm.IR toIR() throws TypeException {

      return a.toIR();
    }
  }