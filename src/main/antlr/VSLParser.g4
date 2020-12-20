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
    | w=whileDo { $out = $w.out; }
    | i=ifThen { $out = $i.out; }
    | r=ret { $out = $r.out; }
    ;

ret returns [TP2.ASD.Instruction out]
    : RETURN e=expression { $out = new TP2.ASD.Return($e.out); }
    ;

block returns [TP2.ASD.Instruction out] locals [List<TP2.ASD.Instruction> list]
    : { $list = new ArrayList<TP2.ASD.Instruction>(); } LC (d=declaration { $list.add($d.out); })? (i=instruction { $list.add($i.out); })+ RC {$out = new TP2.ASD.Block($list);}
    | i=instruction {$out = $i.out;}
    ;

function
    : FUNC type IDENT LP RP block
    ;

whileDo returns [TP2.ASD.Instruction out]
    : WHILE cond=expression DO b=block DONE { $out = new TP2.ASD.While($cond.out, $b.out); }
    ;

ifThen returns [TP2.ASD.Instruction out]
    : IF cond=expression THEN b=block ELSE b2=block FI { $out = new TP2.ASD.If($cond.out, $b.out, $b2.out); }
    | IF cond=expression THEN b=block FI { $out = new TP2.ASD.If($cond.out, $b.out); }
    ;

affectation returns [TP2.ASD.Affectation out]
    : v=IDENT AFFECT r=expression { $out = new TP2.ASD.Affectation($v.getText(), $r.out); }
    ;

declaration returns [TP2.ASD.Declaration out] locals [ArrayList<String> idents]
    : { $idents = new ArrayList<String>(); } INT v=IDENT { $idents.add($v.getText()); } (V v=IDENT { $idents.add($v.getText()); })* { $out = new TP2.ASD.Declaration(new TP2.ASD.Int(), $idents); }
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
    | i=IDENT { $out = new TP2.ASD.Expr.VarExpression(new TP2.ASD.Int(), $i.getText()); }
    ;

primary returns [TP2.ASD.Expr.Expression out]
    : INTEGER { $out = new TP2.ASD.Expr.IntegerExpression($INTEGER.int); }
    // TODO : that's all?
    ;
