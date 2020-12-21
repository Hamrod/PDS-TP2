package TP2.ASD;

import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.ArrayList;
import java.util.List;

public class Proto extends Function {

	String name;
	Type type;
	List<String> args;

	public Proto(String name, Type type, List<String> args) {
		this.name = name;
		this.type = type;
		this.args = args;
	}

	public String pp() {
		String s = "PROTO " + type.pp() + " " + name + "(";
		if (args.size() > 0) {
			for (String argument : args) {
				s += argument + ",";
			}
			s = s.substring(0, s.length() - 1);
		}
		return  s + ")";
	}

	public Llvm.IR toIR() throws TypeException {
		List<SymbolTable.VariableSymbol> a = new ArrayList<>();
		for (String arg : args) {
			a.add(new SymbolTable.VariableSymbol(new Int(), arg));
		}
		Program.symbolTable.add(new SymbolTable.FunctionSymbol(type, name, a, false));
		return new Llvm.IR(Llvm.empty(), Llvm.empty());
	}
}
