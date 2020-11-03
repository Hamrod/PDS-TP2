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


// TODO : other rules

program returns [TP2.ASD.Program out]
    : a=affectation EOF { $out = new TP2.ASD.Program($a.out); }
    | f=function EOF
    ;

function
    : FUNC type IDENT LP RP RB expression LB
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

affectation returns [TP2.ASD.Affectation out]
    : v=IDENT AFFECT r=expression { $out = new TP2.ASD.Affectation($v.getText(), $r.out); }
    ;

factor returns [TP2.ASD.Expr.Expression out]
    : p=primary { $out = $p.out; }
    | IDENT
    ;

primary returns [TP2.ASD.Expr.Expression out]
    : INTEGER { $out = new TP2.ASD.Expr.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
