lexer grammar VSLLexer;

options {
  language = Java;
}

@header {
  package TP2;
}

WS : (' '|'\n'|'\t') -> skip
   ;

COMMENT : '//' (~'\n')* -> skip
        ;

fragment LETTER : 'a'..'z' ;
fragment DIGIT  : '0'..'9' ;
fragment ASCII  : ~('\n'|'"');

//Keywords
FUNC : 'FUNC';
PROTO : 'PROTO';
WHILE : 'WHILE';
DO : 'DO';
DONE : 'DONE';
IF : 'IF';
THEN : 'THEN';
ELSE : 'ELSE';
FI : 'FI';
RETURN : 'RETURN';

LP : '('; //Left parenthesis
RP : ')';

LB : '{'; //Left curly-bracket
RB : '}';

V : ',';

PRINT : 'PRINT';
READ : 'READ';

//Types
INT : 'INT';
VOID : 'VOID';

//Operations
PLUS : '+';
MINUS : '-';
TIMES : '*';
DIVIDED : '/';
AFFECT : ':=';

//Other tokens (no conflict with keywords in VSL)
IDENT   : LETTER (LETTER|DIGIT)*;
TEXT    : '"' (ASCII)* '"' { setText(getText().substring(1, getText().length() - 1)); };
INTEGER : (DIGIT)+ ;
