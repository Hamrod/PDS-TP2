package TP2.ASD;

import TP2.Llvm;
import TP2.Llvm.*;
import TP2.TypeException;

import java.util.ArrayList;
import java.util.List;

public class Func extends Function {
	String name;
	Type type;
	List<String> arguments;
	Instruction block;

	public Func(String name, Type type, List<String> arguments, Instruction block) {
		this.name = name;
		this.type = type;
		this.arguments = arguments;
		this.block = block;
	}

	public String pp() {
		String s = "FUNC " + type.pp() + " " + name + "(";
		if (arguments.size() > 0) {
			for (String argument : arguments) {
				s += argument + ",";
			}
			s = s.substring(0, s.length() - 1);
		}
		return  s + ")\n" + block.pp();
	}

	public IR toIR() throws TypeException {
		ArrayList<Llvm.Argument> llvmArgs = new ArrayList<>();

		for (String argument : arguments) {
			llvmArgs.add(new Llvm.Argument(new Llvm.Int(), argument));
		}

		IR ir = new IR(Llvm.empty(), Llvm.empty());
		IR blockIR = block.toIR();
		ir.appendCode(new Llvm.Function(name, type.toLlvmType(), llvmArgs, blockIR.getCode()));

		Instruction lastInstruction;

		return ir;
	}
}
