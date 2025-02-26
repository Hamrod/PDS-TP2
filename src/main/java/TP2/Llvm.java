package TP2;

import java.util.List;
import java.util.ArrayList;

// This file contains a simple LLVM IR representation
// and methods to generate its string representation

public class Llvm {
  static public class IR {
    List<Instruction> header; // IR instructions to be placed before the code (global definitions)
    List<Instruction> code;   // main code

    public List<Instruction> getCode() {
      return code;
    }

    public IR(List<Instruction> header, List<Instruction> code) {
      this.header = header;
      this.code = code;
    }

    // append an other IR
    public IR append(IR other) {
      header.addAll(other.header);
      code.addAll(other.code);
      return this;
    }

    // append a code instruction
    public IR appendCode(Instruction inst) {
      code.add(inst);
      return this;
    }

    // append a code header
    public IR appendHeader(Instruction inst) {
      header.add(inst);
      return this;
    }

    // Final string generation
    public String toString() {
      // This header describe to LLVM the target
      // and declare the external function printf
      StringBuilder r = new StringBuilder("; Target\n" +
              "target triple = \"x86_64-unknown-linux-gnu\"\n" +
              "; External declaration of the printf function\n" +
              "declare i32 @printf(i8* noalias nocapture, ...)\n" +
              "\n; Actual code begins\n\n");

      for (Instruction inst : header)
        r.append(inst);

      r.append("\n\n");

      // We create the function main


      for (Instruction inst : code)
      {
        r.append(inst);
      }

      return r.toString();
    }
  }

  // Returns a new empty list of instruction, handy
  static public List<Instruction> empty() {
    return new ArrayList<>();
  }


  // LLVM Types
  static public abstract class Type {
    public abstract String toString();
  }

  static public class Int extends Type {
    public String toString() {
      return "i32";
    }
  }

  static public class Void extends Type {
    public String toString() {
      return "void";
    }
  }

  // LLVM IR Instructions
  static public abstract class Instruction {
    public abstract String toString();
  }

  static public class Add extends Instruction {
    Type type;
    String left;
    String right;
    String lvalue;

    public Add(Type type, String left, String right, String lvalue) {
      this.type = type;
      this.left = left;
      this.right = right;
      this.lvalue = lvalue;
    }

    public String toString() {
      return lvalue + " = add " + type + " " + left + ", " + right +  "\n";
    }
  }

  static public class Return extends Instruction {
    Type type;
    String value;

    public Return(Type type, String value) {
      this.type = type;
      this.value = value;
    }

    public String toString() {
      if (type instanceof Int) {
        return "ret " + type + " " + value + "\n";
      } else {
        return "ret " + type + "\n";
      }
    }
  }

  static public class Sub extends Instruction {
    Type type;
    String left;
    String right;
    String lvalue;

    public Sub(Type type, String left, String right, String lvalue) {
      this.type = type;
      this.left = left;
      this.right = right;
      this.lvalue = lvalue;
    }

    public String toString() {
      return lvalue + " = sub " + type + " " + left + ", " + right +  "\n";
    }
  }

  static public class Mul extends Instruction {
    Type type;
    String left;
    String right;
    String lvalue;

    public Mul(Type type, String left, String right, String lvalue) {
      this.type = type;
      this.left = left;
      this.right = right;
      this.lvalue = lvalue;
    }

    public String toString() {
      return lvalue + " = mul " + type + " " + left + ", " + right +  "\n";
    }
  }


  static public class Div extends Instruction {
    Type type;
    String left;
    String right;
    String lvalue;

    public Div(Type type, String left, String right, String lvalue) {
      this.type = type;
      this.left = left;
      this.right = right;
      this.lvalue = lvalue;
    }

    public String toString() {
      return lvalue + " = div " + type + " " + left + ", " + right +  "\n";
    }
  }

  static public class Store extends Instruction {
    Type type;
    String var;
    String value;

    public Store(Type type, String var, String value) {
      this.type = type;
      this.var = var;
      this.value = value;
    }

    public String toString() { return "store " + type + " " + value + ", " + type + "* " + var + "\n"; }
  }

  static public class Alloca extends Instruction {
    Type type;
    String var;

    public Alloca(Type type, String var) {
      this.type = type;
      this.var = var;
    }

    @Override
    public String toString() {
      return "%" + var + "= alloca " + type + "\n";
    }
  }

  static public class Load extends Instruction {
    Type type;
    String var;
    String lvalue;

    public Load(Type type, String var, String lvalue) {
      this.type = type;
      this.var = var;
      this.lvalue = lvalue;
    }

    @Override
    public String toString() {
      return lvalue + " = load " + type + ", " + type + "* %" + var + "\n";
    }
  }

  static public class BrLabel extends Instruction {
    String name;

    public BrLabel(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "br label %" + name + "\n";
    }
  }

  static public class BrIf extends Instruction {
    String condName;
    String trueLabel;
    String falseLabel;

    public BrIf(String condName, String trueLabel, String falseLabel) {
      this.condName = condName;
      this.trueLabel = trueLabel;
      this.falseLabel = falseLabel;
    }

    @Override
    public String toString() {
      return "br i1 " + condName + ", label %" + trueLabel + ", label %" + falseLabel + "\n";
    }
  }

  static public class Label extends Instruction {

    String name;

    public Label(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return "\n" + name + ":\n";
    }
  }

  static public class Argument {
    Type type;
    String name;

    public Argument(Type type, String name) {
      this.type = type;
      this.name = name;
    }

    @Override
    public String toString() {
      return type + " " + name;
    }
  }

  static public class Function extends Instruction {

    String name;
    Type type;
    List<Argument> arguments;
    List<Instruction> block;

    public Function(String name, Type type, List<Argument> arguments, List<Instruction> block) {
      this.name = name;
      this.type = type;
      this.arguments = arguments;
      this.block = block;
    }

    @Override
    public String toString() {
      String s = "define " + type + " @" + name + "(";
      if (arguments.size() > 0) {
        for (Argument argument : arguments) {
          s += argument + ", ";
        }
        s = s.substring(0, s.length() - 2);
      }

      s += ") {\n";

      block.add(new Return(type, "0"));

      for (Instruction instruction : block) {
        s += "\t" + instruction;
      }

      return s + "}";
    }
  }

  static public class Call extends Instruction {

    Type type;
    String name;
    List<Argument> arguments;
    String lvalue;

    public Call(Type type, String name, List<Argument> arguments, String lvalue) {
      this.type = type;
      this.name = name;
      this.arguments = arguments;
      this.lvalue = lvalue;
    }

    @Override
    public String toString() {
      String s = lvalue + " = call " + type + " @" + name + "(";
      if (arguments.size() > 0) {
        for (Argument argument : arguments) {
          s += argument + ", ";
        }
        s = s.substring(0, s.length()-2);
      }
      return s + ")\n";
    }
  }

}
