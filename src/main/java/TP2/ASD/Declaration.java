package TP2.ASD;

import TP2.Llvm;
import TP2.TypeException;

import java.util.List;

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
            s += " " + ident + ",";
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

    @Override
    public Llvm.IR toIR() throws TypeException {
        return null;
    }
}
