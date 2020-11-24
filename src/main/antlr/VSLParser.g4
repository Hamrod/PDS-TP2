parser grammar VSLParser;

options {
  language = Java;
  tokenVocab = VSLLexer;
}

@header {
  package TP2;

  import java.util.stream.Collectors;
  import java.util.Arrays;
}

program returns [TP2.ASD.Program out]
    : b=block EOF { $out = new TP2.ASD.Program($b.out); }
    ;

instruction returns [TP2.ASD.Instruction out]
    : a=affectation { $out = $a.out; }
    ;

block returns [List<TP2.ASD.Instruction> out]
    : { $out = new ArrayList<TP2.ASD.Instruction>(); } LB declaration? (i=instruction { $out.add($i.out); })+ RB
    ;

function
    : FUNC type IDENT LP RP RB block LB
    ;

affectation returns [TP2.ASD.Affectation out]
    : v=IDENT AFFECT r=expression { $out = new TP2.ASD.Affectation($v.getText(), $r.out); }
    ;

declaration returns [TP2.ASD.Declaration out]
    : INT IDENT (V IDENT)+
    ;

type
    : INT
    | VOID
    ;

expression returns [TP2.ASD.Expr.Expression out]
    : l=factor PLUS r=expression  { $out = new TP2.ASD.Expr.AddExpression($l.out, $r.out); }
    | l=factor MINUS r=expression  { $out = new TP2.ASD.Expr.SubExpression($l.out, $r.out); }
    | l=factor TIMES r=expression  { $out = new TP2.ASD.Expr.MulExpression($l.out, $r.out); }
    | l=factor DIVIDED r=expression  { $out = new TP2.ASD.Expr.DivExpression($l.out, $r.out); }
    | f=factor { $out = $f.out; }
    ;

factor returns [TP2.ASD.Expr.Expression out]
    : p=primary { $out = $p.out; }
    | IDENT
    ;

primary returns [TP2.ASD.Expr.Expression out]
    : INTEGER { $out = new TP2.ASD.Expr.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
