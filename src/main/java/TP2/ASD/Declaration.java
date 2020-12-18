package TP2.ASD;

import TP2.Llvm;
import TP2.Llvm.*;
import TP2.SymbolTable;
import TP2.TypeException;

import java.util.List;

import static TP2.SymbolTable.*;

public class Declaration extends Instruction {

    Type type;
    List<String> idents;

    public Declaration(Type type, List<String> idents) {
        this.type = type;
        this.idents = idents;
    }

    @Override
    public String pp() {
        String s = type.pp();
        for (String ident : idents) {
            s += " " + ident + ',';
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

    @Override
    public IR toIR() throws TypeException {

        IR ir = new IR(Llvm.empty(), Llvm.empty());

        if (Program.symbolTable == null) {
            Program.symbolTable = new SymbolTable();
        } else {
            Program.symbolTable = new SymbolTable(Program.symbolTable);
        }

        idents.forEach(ident -> {
            Program.symbolTable.add(new VariableSymbol(type, ident));
            ir.appendCode(new Alloca(type.toLlvmType(), ident));
        });

        return ir;
    }
}
