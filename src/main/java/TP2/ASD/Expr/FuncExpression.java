package TP2.ASD.Expr;

import TP2.ASD.Program;
import TP2.ASD.Type;
import TP2.Llvm;
import TP2.SymbolTable;
import TP2.TypeException;
import TP2.Utils;

import java.util.ArrayList;
import java.util.List;

public class FuncExpression extends Expression {

	Type type;
	String name;
	List<Expression> arguments;


	@Override
	public String pp() {
		String s = name + "(";
		if (arguments.size() > 0) {
			for (Expression argument : arguments) {
				s += argument.pp() + ", ";
			}
			s = s.substring(0, s.length()-2);
		}
		return s + ")";
	}

	@Override
	public RetExpression toIR() throws TypeException {

		SymbolTable.Symbol var = Program.symbolTable.lookup(name);

		if(var == null) {
			throw new TypeException("You must declare variable " + name + " before using it !");
		}

		List<Llvm.Argument> args = new ArrayList<>();
		Llvm.IR ir = new Llvm.IR(Llvm.empty(), Llvm.empty());
		RetExpression ret;

		for (Expression argument : arguments) {
			ret = argument.toIR();
			ir.append(ret.ir);
			args.add(new Llvm.Argument(ret.type.toLlvmType(), ret.result));
		}

		String result = Utils.newtmp();

		ir.appendCode(new Llvm.Call(type.toLlvmType(), name, args, result));


		return new RetExpression(ir, type, result);
	}
}
