package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

public class Print extends Instruction {

	@Override
	public String pp() {
		return "PRINT";
	}

	@Override
	public Llvm.IR toIR() throws TypeException {
		return new Llvm.IR(Llvm.empty(), Llvm.empty());
	}
}
