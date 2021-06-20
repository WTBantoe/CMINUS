grammar CMINUS;

/*High-level Definitions*/
program
    : extDefList
    ;

extDefList
    : extDef extDefList
    | EOF
    ;

extDef
    : specifier extDecList SEMI
    | specifier SEMI
    | specifier funDec compSt
    ;

extDecList
    : varDec
    | varDec COMMA extDecList
    ;

/* Specifiers */
specifier
    : TYPE
    | structSpecifier
    ;

structSpecifier
    : STRUCT optTag LC defList RC
    | STRUCT tag
    ;

optTag
    : ID
    |
    ;

tag
    : ID
    ;

/* Declarators */
varDec
    : ID
    | varDec LB INT RB
    ;

funDec
    : ID LP varList RP
    | ID LP RP
    ;

varList
    : paramDec COMMA varList
    | paramDec
    ;

paramDec
    : specifier varDec
    ;

/* Statements */
compSt
    : LC defList stmtList RC
    ;

stmtList
    : stmt stmtList
    |
    ;

stmt
    : exp SEMI
    | compSt
    | RETURN exp SEMI
    | IF LP exp RP stmt
    | IF LP exp RP stmt ELSE stmt
    | WHILE LP exp RP stmt
    ;

/*  Local Definitions */
defList
    : def defList
    |
    ;

def
    : specifier decList SEMI
    ;

decList
    : dec
    | dec COMMA decList
    ;

dec
    : varDec
    | varDec ASSIGNOP exp
    ;

/* Expressions */
exp
    : LP exp RP
    | ID LP args RP
    | ID LP RP
    | exp LB exp RB
    | exp DOT ID

    | <assoc=right> (MINUS | NOT) exp

    | exp (STAR | DIV) exp

    | exp (PLUS | MINUS) exp

    | exp RELOP exp

    | exp AND exp

    | exp OR exp

    | <assoc=right> exp ASSIGNOP exp

    | ID
    | INT
    | FLOAT
    ;

args
    : exp COMMA args
    | exp
    ;


SEMI:           ';';
COMMA:          ',';
ASSIGNOP:       '=';
RELOP:          '>'|'<'|'>='|'<='|'=='|'!=';
PLUS:           '+';
MINUS:          '-';
STAR:           '*';
DIV:            '/';
AND:            '&&';
OR:             '||';
DOT:            '.';
NOT:            '!';
TYPE:           'int'|'float';
LP:             '(';
RP:             ')';
LB:             '[';
RB:             ']';
LC:             '{';
RC:             '}';
STRUCT:         'struct';
RETURN:         'return';
IF:             'if';
ELSE:           'else';
WHILE:          'while';

WHITESPACE:     [ \t\n\r]+ ->skip;

fragment DECNOTZERO:        [1-9];
fragment DECDIGIT:          [0-9];
fragment DEC:               '0' | DECNOTZERO DECDIGIT*;
fragment OCTPRE:            '0';
fragment OCTNOTZERO:        [1-7];
fragment OCTDIGIT:          [0-7];
fragment OCT:               OCTPRE ('0'|OCTNOTZERO OCTDIGIT*);
fragment HEXPRE:            '0'[xX];
fragment HEXNOTZERO:        [1-9a-fA-F];
fragment HEXDIGIT:          [0-9a-fA-F];
fragment HEX:               HEXPRE ('0'|HEXNOTZERO HEXDIGIT*);
fragment EXPSUF:            [Ee][+-]?DECDIGIT+;
fragment LETTER:            [a-zA-Z_];
fragment ALPHABET:          [a-zA-Z_0-9];
fragment BLOCKCOMSTART:     '/*';
fragment BLOCKCOMCON:       ~'*'|'*'~'/';
fragment BLOCKCOMEND:       '*/';

INT:            DEC | OCT | HEX;
FLOAT:          DECDIGIT+ '.' DECDIGIT+ | DECDIGIT* '.' DECDIGIT+ EXPSUF | DECDIGIT+ '.' EXPSUF;
ID:             LETTER ALPHABET*;
LINECOMMENT:    '//' ~[\r\n]* ->skip;
BLOCKCOMMENT:   BLOCKCOMSTART BLOCKCOMCON* BLOCKCOMEND ->skip;

