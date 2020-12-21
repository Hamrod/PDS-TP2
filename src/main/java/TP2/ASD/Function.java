package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

abstract public class Function {

	abstract public String pp();

	abstract public Llvm.IR toIR() throws TypeException;
}
