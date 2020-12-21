package TP2;

import TP2.ASD.Program;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Utils {
  private static int tmp = 0;
  private static int lab = 0;
  private static int glob = 0;
  private static int var = 0;
  private final static Pattern re = Pattern.compile("\\\\n");

  // return "  " × level, useful for code indentation
  static public String indent(int level) {
    StringBuilder r = new StringBuilder();
    while(level-- > 0)
      r.append("  ");
    return r.toString();
  }

  // generate a new unique local identifier (starting with %)
  public static String newtmp() {
    tmp++;
    return "%tmp" + tmp;
  }

  // generate a new unique label starting with str
  public static String newlab(String str) {
    lab++;
    return str + lab;
  }

  // generate a new unique global identifier (starting with @tmp) *)
  public static String newglob(String str) {
    glob++;
    return str + glob;
  }

  public static String newVar(String str) {
    return str + var;
  }

  public static void addBlock() {
    var++;
    Program.symbolTable = new SymbolTable(Program.symbolTable);
  }

  public static void removeBlock() {
    var--;
    Program.symbolTable.toParent();
  }

  // transform escaped newlines ('\' 'n') into newline form suitable for LLVM
  // and append the NUL character (end of string)
  // return a pair: the new String, and its size (according to LLVM)
  public static LLVMStringConstant stringTransform(String str) {
    Matcher m = re.matcher(str);
    StringBuffer res = new StringBuffer();
    int count = 0;

    while(m.find()) {
      m.appendReplacement(res, "\\\\0A");
      count++;
    }

    m.appendTail(res).append("\\00");

    // + 1 for \00
    // - 1 by \n because each ('\' '\n') is transformed into one char
    return new LLVMStringConstant(res.toString(), 1 + str.length() - count);
  }

  // Return type of stringTransform
  public static class LLVMStringConstant {
    String str;
    int length;
    LLVMStringConstant(String str, int length) {
      this.str = str;
      this.length = length;
    }
  }
}
