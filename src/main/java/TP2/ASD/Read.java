package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

public class Read extends Instruction {

	@Override
	public String pp() {
		return "READ";
	}

	@Override
	public Llvm.IR toIR() throws TypeException {
		return new Llvm.IR(Llvm.empty(), Llvm.empty());
	}
}
