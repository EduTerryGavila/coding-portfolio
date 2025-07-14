%{

#include "minic.tab.h"

void error_entero();
void error_id();
void error_lexico();

int errores_lexicos=0;

%}

%option yylineno

digito              [0-9]
letra               [a-zA-Z]
entero              {digito}+
%%

[ \n\r\t]+                           ;

"//"(.*)[\n]                        ;
\/\*([^\*]|[\*]+[^\*\/])*[\*]+\/    ;

var                                 return VAR;
const                               return CONST;
if                                  return IF;
else                                return ELSE;
while                               return WHILE;
do                                  return DO;
print                               return PRINT;
read                                return READ;
for                                 return FOR;
inz                                 return INZ;
to                                  return TO;
cond                                return COND;


({letra}|_)({letra}|{digito}|_)*    { if(yyleng>16){ error_id(); } yylval.cadena=strdup(yytext); return ID;}
{entero}                            { if(atoll(yytext)>2147483648){ error_entero();} yylval.cadena=yytext; return INTLITERAL; }

\"([^"\\]|\\.)*\"                   { yylval.cadena=strdup(yytext); return STRING; }

"("                                 return LPAREN;		
")"                                 return RPAREN;
"{"                                 return LCORCH;
"}"                                 return RCORCH;
";"                                 return PUNTOCOMA;
","                                 return COMA;
"="                                 return ASSIGNOP;
"+"                                 return PLUSOP;
"-"                                 return MINUSOP;
"*"                                 return MULTOP;
"/"                                 return DIVOP;
"=="                                return EQOP;
"!="                                return NEQOP;
"<"                                 return LT;
"<="                                return LTEQ;
">"                                 return GT;
">="                                return GTEQ;





[^0-9a-zA-Z(){};,=+\-\*\/_"\\\s]+     error_lexico();
%%


void error_id(){
  errores_lexicos++;
  printf("Error: identificador de longitud superior a 16 en la linea %d: %s\n", yylineno, yytext);
}

void error_entero(){
  errores_lexicos++;
  printf("Error: entero superior al máximo establecido en la linea %d: %s\n",yylineno, yytext);
}

void error_lexico(){
  errores_lexicos++;
  printf("Error: carácteres inválidos en la linea %d: %s\n",yylineno, yytext);
}


