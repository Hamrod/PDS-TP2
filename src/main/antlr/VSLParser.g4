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

program returns [TP2.ASD.Program out] locals [List<TP2.ASD.Function> list]
    : { $list = new ArrayList<TP2.ASD.Function>(); } (f=functions { $list.add($f.out); })+ EOF { $out = new TP2.ASD.Program($list); }
    ;

functions returns [TP2.ASD.Function out]
    : f=func { $out = $f.out; }
    | p=proto { $out = $p.out; }
    ;

func returns [TP2.ASD.Func out] locals [List<String> args]
    : { $args = new ArrayList<String>(); } FUNC INT n=IDENT LP (a=IDENT { $args.add($a.getText()); })? (V a=IDENT { $args.add($a.getText()); })* RP b=block {$out = new TP2.ASD.Func($n.getText(), new TP2.ASD.Int(), $args, $b.out);}
    | { $args = new ArrayList<String>(); } FUNC VOID n=IDENT LP (a=IDENT { $args.add($a.getText()); })? (V a=IDENT { $args.add($a.getText()); })* RP b=block {$out = new TP2.ASD.Func($n.getText(), new TP2.ASD.Void(), $args, $b.out);}
    ;

proto returns [TP2.ASD.Function out] locals [List<String> args]
    : { $args = new ArrayList<String>(); } PROTO INT n=IDENT LP (a=IDENT)* RP { $out = new TP2.ASD.Proto($n.getText(), new TP2.ASD.Int(), $args);}
    | { $args = new ArrayList<String>(); } PROTO VOID n=IDENT LP (a=IDENT)* RP { $out = new TP2.ASD.Proto($n.getText(), new TP2.ASD.Void(), $args);}
    ;

instruction returns [TP2.ASD.Instruction out]
    : a=affectation { $out = $a.out; }
    | w=whileDo { $out = $w.out; }
    | i=ifThen { $out = $i.out; }
    | r=ret { $out = $r.out; }
    | READ IDENT (V IDENT)* { $out = new TP2.ASD.Read();}
    | PRINT string (V string)* { $out = new TP2.ASD.Print();}
    ;

string
    : TEXT | expression
    ;

ret returns [TP2.ASD.Instruction out]
    : RETURN e=expression { $out = new TP2.ASD.Return($e.out); }
    ;

block returns [TP2.ASD.Instruction out] locals [List<TP2.ASD.Instruction> list]
    : { $list = new ArrayList<TP2.ASD.Instruction>(); } LC (d=declaration { $list.add($d.out); })? (i=instruction { $list.add($i.out); })+ RC {$out = new TP2.ASD.Block($list);}
    | i=instruction {$out = $i.out;}
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

expression returns [TP2.ASD.Expr.Expression out]
    : l=factor PLUS r=expression  { $out = new TP2.ASD.Expr.AddExpression($l.out, $r.out); }
    | l=factor MINUS r=expression  { $out = new TP2.ASD.Expr.SubExpression($l.out, $r.out); }
    | f=factor { $out = $f.out; }
    ;

factor returns [TP2.ASD.Expr.Expression out]
    : p=primary { $out = $p.out; }
    | l=factor TIMES r=expression  { $out = new TP2.ASD.Expr.MulExpression($l.out, $r.out); }
    | l=factor DIVIDED r=expression  { $out = new TP2.ASD.Expr.DivExpression($l.out, $r.out); }
    ;

primary returns [TP2.ASD.Expr.Expression out] locals [List<TP2.ASD.Expr.Expression> args]
    : INTEGER { $out = new TP2.ASD.Expr.IntegerExpression($INTEGER.int); }
    | i=IDENT { $out = new TP2.ASD.Expr.VarExpression(new TP2.ASD.Int(), $i.getText()); }
    | i=IDENT LP {$args = new ArrayList<TP2.ASD.Expr.Expression>();} (e=expression { $args.add($e.out); })? (V e=expression { $args.add($e.out); })* RB
    ;
