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
    : e=expression EOF { $out = new TP2.ASD.Program($e.out); } // TODO : change when you extend the language
    | function EOF
    ;

function
    : FUNC type IDENT LP RP
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
    // TODO : that's all?
    ;

primary returns [TP2.ASD.Expr.Expression out]
    : INTEGER { $out = new TP2.ASD.Expr.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
