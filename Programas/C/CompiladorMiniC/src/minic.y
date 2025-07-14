%{

	#include <stdio.h>
	#include <string.h>
	#include <stdlib.h>
	
	#include "minic.tab.h"

	#include "listaSimbolos.h"
	#include "listaCodigo.h"



	Lista tablaSimbolos;
	PosicionLista posicionLista;
	Tipo tipo;
	int contCadenas=1;
	int index_tabla_simbolos=0;
	
	void yyerror(char const *);

	extern int yylex();
	extern int yylineno;
	extern int errores_lexicos;

	int perteneceTSimb();
	
	void insertarSib();
	void insertarSibValor();

	int esConstante();

	void imprimirTSimb();
	int errores_sintacticos=0;
	int tempReg[10];

	int analisis_ok();

	int obtenerReg();

	void imprimirCodigo();

	char* concatena(char* a, char* b);
	char *concatena_int(char *a, int b);
	void liberarReg(char *reg);

	int contador_etiq = 1;
	char *nuevaEtiqueta();

	int errores_semanticos = 0;

	
%}

%code requires{
	#include "listaCodigo.h"
}
%union{
	char* cadena;
	ListaC codigo;
}





%token VAR CONST IF WHILE PRINT READ LPAREN RPAREN PUNTOCOMA COMA ASSIGNOP LCORCH RCORCH DO FOR INZ TO EQOP NEQOP LT LTEQ GT GTEQ COND
%token <cadena> INTLITERAL ID STRING

%left 	OTHERELSE
%left 	ELSE

%left	PLUSOP MINUSOP
%left	MULTOP DIVOP
%left 	UMINUS

%type 	<codigo> program expression statement statement_list print_item print_list read_list declarations identifier_list asig

%locations

%%

program: { tablaSimbolos=creaLS(); } ID LPAREN RPAREN LCORCH declarations statement_list RCORCH 
											{
												imprimirTSimb();
												
												concatenaLC($6,$7);
												liberaLC($7);

												imprimirCodigo($6);
												
												liberaLS(tablaSimbolos);
												liberaLC($6);
											}
;


/* DECLARACION DE VARIABLES */


declarations: declarations VAR { tipo=VARIABLE; } identifier_list PUNTOCOMA
											{
												$$=$1;
												concatenaLC($$,$4);
												liberaLC($4);
											}
											
| declarations CONST { tipo=CONSTANTE; } identifier_list PUNTOCOMA
											{ 
												$$=$1;
												concatenaLC($$,$4);
												liberaLC($4);
											}
|											
											{
												$$=creaLC();
											}
; 

identifier_list: asig						
											{
												$$ = $1; 
											}
| identifier_list COMA asig 				
											{	
												$$ = $1;
												concatenaLC($$, $3);
												liberaLC($3); 
											}
;

asig:ID 									
											{
												if (!perteneceTSimb($1)){
													insertarSib($1, tipo);
												} else{
													printf ("ERROR: Identidicador %s ya esta declarado\n", $1);
													errores_semanticos=1;
												}
												$$=creaLC();
											}  
| ID ASSIGNOP expression					
											{ 
												if (!perteneceTSimb($1)){
													insertarSib($1, tipo);
												} else{
													printf ("ERROR: Identidicador %s ya esta declarado\n", $1);
													errores_semanticos=1;
												}
												
												
												$$=creaLC();

												concatenaLC($$, $3);

												Operacion oper;
												oper.op = "sw";
												oper.res =  recuperaResLC($3);
												oper.arg1 = concatena("_",$1);
												oper.arg2 = NULL;
												insertaLC($$, finalLC($$), oper);
												guardaResLC($$, oper.res);
												liberarReg(oper.res);
												
												liberaLC($3);
											}
;


/* CUERPO DE PROGRAMA */


statement_list: statement_list statement 	
											{ 	
												$$ = $1;
												concatenaLC($$,$2); 
												liberaLC($2);
											}
| 											
											{
												$$=creaLC(); 
											}
;

statement: ID ASSIGNOP expression PUNTOCOMA
											{	
												if(!perteneceTSimb($1)){
													errores_semanticos=1;
													printf("ERROR: ID %s no esta declarado\n",$1);
												}

												else if (esConstante($1)){
													printf("ERROR: ID %s es constante\n", $1);
													errores_semanticos=1;
												}

												$$=creaLC();
												concatenaLC($$,$3);
												Operacion oper;
												oper.op = "sw";
												oper.res =  recuperaResLC($3);
												oper.arg1 = concatena("_",$1);
												oper.arg2 = NULL;
												insertaLC($$, finalLC($$), oper);
												guardaResLC($$, oper.res);
												liberarReg(oper.res);
												
												liberaLC($3);

											}   
| LCORCH  statement_list  RCORCH 			
											{ 
												$$ = $2;
											}   
| IF LPAREN expression RPAREN statement ELSE statement 
											{
												$$=creaLC();
												concatenaLC($$,$3);

												// Generar etiqueta
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();


												Operacion oper;
												oper.op = "beqz";
												oper.res =  recuperaResLC($3);
												oper.arg1 = etiqueta1;
												oper.arg2 = NULL;
												insertaLC($$, finalLC($$), oper);
												guardaResLC($$, oper.res);
												
												liberarReg(recuperaResLC($3));
												liberaLC($3);

												concatenaLC($$,$5);
												liberaLC($5);

												Operacion oper2;
												oper2.op = "b";
												oper2.res =  etiqueta2;
												oper2.arg1 = NULL;
												oper2.arg2 = NULL;
												insertaLC($$, finalLC($$), oper2);
												guardaResLC($$, oper2.res);

												Operacion oper3;
												oper3.op = "etiq";
												oper3.res =  etiqueta1;
												oper3.arg1 = NULL;
												oper3.arg2 = NULL;
												insertaLC($$, finalLC($$), oper3);
												guardaResLC($$, oper3.res);

												concatenaLC($$,$7);
												liberaLC($7);

												Operacion oper4;
												oper4.op = "etiq";
												oper4.res =  etiqueta2;
												oper4.arg1 = NULL;
												oper4.arg2 = NULL;
												insertaLC($$, finalLC($$), oper4);
												guardaResLC($$, oper4.res);	
											}  
| IF LPAREN expression RPAREN statement %prec OTHERELSE 
											{ 
												$$=creaLC();
												concatenaLC($$,$3);

												// Generar etiqueta
												char* etiqueta = nuevaEtiqueta();
												

												Operacion oper;
												oper.op = "beqz";
												oper.res =  recuperaResLC($3);
												oper.arg1 = etiqueta;
												oper.arg2 = NULL;
												insertaLC($$, finalLC($$), oper);
												guardaResLC($$, oper.res);
												
												liberarReg(recuperaResLC($3));
												liberaLC($3);

												concatenaLC($$,$5);
												liberaLC($5);

												Operacion oper2;
												oper2.op = "etiq";
												oper2.res =  etiqueta;
												oper2.arg1 = NULL;
												oper2.arg2 = NULL;
												insertaLC($$, finalLC($$), oper2);
												guardaResLC($$, oper2.res);	
											}  
| WHILE LPAREN expression RPAREN statement 
											{
												$$=creaLC();

												// Generar etiqueta
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC($$, finalLC($$), oper1);
												guardaResLC($$, oper1.res);
												
												concatenaLC($$,$3);

												Operacion oper2;
												oper2.op = "beqz";
												oper2.res =  recuperaResLC($3);
												oper2.arg1 = etiqueta2;
												oper2.arg2 = NULL;
												insertaLC($$, finalLC($$), oper2);
												guardaResLC($$, oper2.res);
												
												concatenaLC($$,$5);

												Operacion oper3;
												oper3.op = "b";
												oper3.res =  etiqueta1;
												oper3.arg1 = NULL;
												oper3.arg2 = NULL;
												insertaLC($$, finalLC($$), oper3);
												guardaResLC($$, oper3.res);

												Operacion oper4;
												oper4.op = "etiq";
												oper4.res =  etiqueta2;
												oper4.arg1 = NULL;
												oper4.arg2 = NULL;
												insertaLC($$, finalLC($$), oper4);
												guardaResLC($$, oper4.res);
												
												liberarReg(recuperaResLC($3));
												liberaLC($3);
												liberaLC($5);
											}
| DO statement WHILE expression PUNTOCOMA			
											{ 
												$$=creaLC();
												char* etiqueta1 = nuevaEtiqueta();

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC($$, finalLC($$), oper1);
												guardaResLC($$, oper1.res);
												
												concatenaLC($$,$2);

												concatenaLC($$,$4);
												
												Operacion oper2;
												oper2.op = "bnez";
												oper2.res =  recuperaResLC($4);
												oper2.arg1 = etiqueta1;
												oper2.arg2 = NULL;
												insertaLC($$, finalLC($$), oper2);
												guardaResLC($$, oper2.res);

												liberarReg(recuperaResLC($4));
												liberaLC($2);
												liberaLC($4);
											}
| FOR ID INZ expression TO expression statement // INCREMENTAMOS DE 1 EN 1
											{
												
												if(!perteneceTSimb($2)){
													errores_semanticos=1;
													printf("ERROR: ID %s no esta declarado\n", $2);
												}


												$$=creaLC();
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();
												
												char* rg_inz = recuperaResLC($4);
												char* rg_to = recuperaResLC($6);

												concatenaLC($$,$4);
												concatenaLC($$,$6);

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC($$, finalLC($$), oper1);
												guardaResLC($$, oper1.res);

												Operacion oper2;
												oper2.op = "bgt";
												oper2.res =  rg_inz;
												oper2.arg1 = rg_to;
												oper2.arg2 = etiqueta2;
												insertaLC($$, finalLC($$), oper2);
												guardaResLC($$, oper2.res);


												Operacion oper3;
												oper3.op = "sw";
												oper3.res = rg_inz;
												oper3.arg1 = concatena("_",$2);
												oper3.arg2 = NULL;
												insertaLC($$, finalLC($$), oper3);
												guardaResLC($$, oper3.res);
												
												concatenaLC($$,$7);

												Operacion oper4;
												oper4.op = "addi";
												oper4.res =  rg_inz;
												oper4.arg1 = rg_inz;
												oper4.arg2 = "1";
												insertaLC($$, finalLC($$), oper4);
												guardaResLC($$, oper4.res);												

												Operacion oper5;
												oper5.op = "b";
												oper5.res =  etiqueta1;
												oper5.arg1 = NULL;
												oper5.arg2 = NULL;
												insertaLC($$, finalLC($$), oper5);
												guardaResLC($$, oper5.res);
												
												Operacion oper6;
												oper6.op = "etiq";
												oper6.res =  etiqueta2;
												oper6.arg1 = NULL;
												oper6.arg2 = NULL;
												insertaLC($$, finalLC($$), oper6);
												guardaResLC($$, oper6.res);

												liberarReg(rg_inz);
												liberarReg(rg_to);

												liberaLC($4);
												liberaLC($6);
												liberaLC($7);

											}
| FOR ID INZ expression COND expression statement // El programador podrá establecer una condicion de terminación
											{
												
												if(!perteneceTSimb($2)){
													errores_semanticos=1;
													printf("ERROR: ID %s no esta declarado\n", $2);
												}


												$$=creaLC();
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();
												
												char* rg_inz = recuperaResLC($4);

												concatenaLC($$,$4);
												concatenaLC($$,$6);

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC($$, finalLC($$), oper1);
												guardaResLC($$, oper1.res);

												char* rg_cond = recuperaResLC($6);
												concatenaLC($$,$6);

												Operacion oper2;
												oper2.op = "bnez";
												oper2.res =  rg_cond;
												oper2.arg1 = etiqueta2;
												oper2.arg2 = NULL;
												insertaLC($$, finalLC($$), oper2);
												guardaResLC($$, oper2.res);


												Operacion oper3;
												oper3.op = "sw";
												oper3.res = rg_inz;
												oper3.arg1 = concatena("_",$2);
												oper3.arg2 = NULL;
												insertaLC($$, finalLC($$), oper3);
												guardaResLC($$, oper3.res);
												
												concatenaLC($$,$7);

												Operacion oper4;
												oper4.op = "addi";
												oper4.res =  rg_inz;
												oper4.arg1 = rg_inz;
												oper4.arg2 = "1";
												insertaLC($$, finalLC($$), oper4);
												guardaResLC($$, oper4.res);												

												Operacion oper5;
												oper5.op = "b";
												oper5.res =  etiqueta1;
												oper5.arg1 = NULL;
												oper5.arg2 = NULL;
												insertaLC($$, finalLC($$), oper5);
												guardaResLC($$, oper5.res);
												
												Operacion oper6;
												oper6.op = "etiq";
												oper6.res =  etiqueta2;
												oper6.arg1 = NULL;
												oper6.arg2 = NULL;
												insertaLC($$, finalLC($$), oper6);
												guardaResLC($$, oper6.res);

												liberarReg(rg_inz);
												liberarReg(rg_cond);

												liberaLC($4);
												liberaLC($6);
												liberaLC($7);

											}

| PRINT LPAREN print_list RPAREN PUNTOCOMA 
											{ 
												$$ = $3;
											} 
| READ LPAREN read_list RPAREN PUNTOCOMA 	
											{ 
												$$ = $3;
											} 
;

print_list: print_item
| print_list COMA print_item {  $$=creaLC(); concatenaLC($$,$1); concatenaLC($$,$3); }
;

print_item: expression						
											{ // Por defecto $$=$1

											$$=creaLC();
											concatenaLC($$,$1);

											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "1";
											oper.arg2 = NULL;
											insertaLC($$, finalLC($$), oper);
											guardaResLC($$, oper.res);
																						
											Operacion oper2;
											oper2.op = "move";
											oper2.res =  "$a0";
											oper2.arg1 = recuperaResLC($1);
											oper2.arg2 = NULL;
											insertaLC($$, finalLC($$), oper2);
											guardaResLC($$, oper2.res);

											Operacion oper3;
											oper3.op = "syscall";
											oper3.res =  NULL;
											oper3.arg1 = NULL;
											oper3.arg2 = NULL;
											insertaLC($$, finalLC($$), oper3);

											liberarReg(recuperaResLC($1));
											liberaLC($1);
										}
| STRING								
										{
											tipo=CADENA;
											insertarSibValor($1, tipo, contCadenas);
											contCadenas++;

											
											$$=creaLC();
											
											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "4";
											oper.arg2 = NULL;
											insertaLC($$, finalLC($$), oper);
											guardaResLC($$, oper.res);

											
											Operacion oper2;
											oper2.op = "la";
											oper2.res =  "$a0";
											oper2.arg1 = concatena_int("$str",(contCadenas-1));
											oper2.arg2 = NULL;
											insertaLC($$, finalLC($$), oper2);
											guardaResLC($$, oper2.res);

											Operacion oper3;
											oper3.op = "syscall";
											oper3.res =  NULL;
											oper3.arg1 = NULL;
											oper3.arg2 = NULL;
											insertaLC($$, finalLC($$), oper3);


										}
;

read_list: ID							
										{											
											if(!perteneceTSimb($1)){
												errores_semanticos=1;
												printf("ERROR: ID %s no esta declarado\n", $1);
											}
											else if (esConstante($1)){
												errores_semanticos=1;
												printf("ERROR: ID %s es constante\n", $1);
											}

											$$=creaLC();
											
											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "5";
											oper.arg2 = NULL;
											insertaLC($$, finalLC($$), oper);
											guardaResLC($$, oper.res);

											Operacion oper2;
											oper2.op = "syscall";
											oper2.res =  NULL;
											oper2.arg1 = NULL;
											oper2.arg2 = NULL;
											insertaLC($$, finalLC($$), oper2);

											Operacion oper3;
											oper3.op = "sw";
											oper3.res =  "$v0";
											oper3.arg1 = concatena("_",$1);
											oper3.arg2 = NULL;
											insertaLC($$, finalLC($$), oper3);
											guardaResLC($$, oper3.res);
											liberarReg(oper.res);
										}
| read_list COMA ID					
										{ 	if (!perteneceTSimb($3)){
												printf("ERROR: ID %s no esta declarado\n", $3);
												errores_semanticos=1;
											}
											else if (esConstante($3)){
												printf("ERROR: ID %s es constante\n", $3);
												errores_semanticos=1;
											}

											$$=creaLC();
											concatenaLC($$,$1);
											liberaLC($1);

											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "5";
											oper.arg2 = NULL;
											insertaLC($$, finalLC($$), oper);
											guardaResLC($$, oper.res);

											Operacion oper2;
											oper2.op = "syscall";
											oper2.res =  NULL;
											oper2.arg1 = NULL;
											oper2.arg2 = NULL;
											insertaLC($$, finalLC($$), oper2);

											Operacion oper3;
											oper3.op = "sw";
											oper3.res =  "$v0";
											oper3.arg1 = concatena("_",$3);
											oper3.arg2 = NULL;
											insertaLC($$, finalLC($$), oper3);
											guardaResLC($$, oper3.res);
											liberarReg(oper.res);
										}   
;

expression: expression PLUSOP expression	
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "add";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2); 

										}
| expression MINUSOP expression
										{
											
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "sub";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}
| expression MULTOP expression
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "mul";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}
| expression DIVOP expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "div";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}
| expression EQOP expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "xor";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);

											Operacion oper2;
											oper2.op = "sltiu";
											oper2.res = recuperaResLC($1);
											oper2.arg1 = recuperaResLC($1);
											oper2.arg2 = "1";
											insertaLC($$,finalLC($$),oper2);
										}
| expression NEQOP expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "xor";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);

										}	
| expression LT expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "slt";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}
| expression LTEQ expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "sle";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}	
| expression GT expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "sgt";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}			
| expression GTEQ expression			
										{
											$$ = $1;
											concatenaLC($$,$3);
											Operacion oper;
											oper.op = "sge";
											oper.res = recuperaResLC($1);
											oper.arg1 = recuperaResLC($1);
											oper.arg2 = recuperaResLC($3);
											insertaLC($$,finalLC($$),oper);
											liberaLC($3);
											liberarReg(oper.arg2);
										}															
| LPAREN expression RPAREN 				
										{ 
											$$ = $2;
										}
| MINUSOP expression %prec UMINUS 		
										{ 
											$$ = $2;
											Operacion oper;
											oper.op = "neg";
											
											oper.res = recuperaResLC($2);
											oper.arg1 = recuperaResLC($2);
											oper.arg2 = NULL;
											insertaLC($$,finalLC($$),oper);
										}
| ID 									
										{
											if(!perteneceTSimb($1)){
												printf("ERROR: ID %s no esta declarado\n", $1);
												errores_semanticos=1;
											}

											$$=creaLC();
											int rg = obtenerReg();
											Operacion oper;
											oper.op = "lw";
											oper.res =  concatena_int("$t",rg);
											oper.arg1 = concatena("_",$1);
											oper.arg2 = NULL;
											insertaLC($$, finalLC($$), oper);
											guardaResLC($$, oper.res);
										}
| INTLITERAL							
										{
											$$=creaLC();
											int rg = obtenerReg();
											Operacion oper;
											oper.op = "li";
											oper.res =  concatena_int("$t",rg);
											oper.arg1 = strdup($1);
											oper.arg2 = NULL;
											insertaLC($$, finalLC($$), oper);
											guardaResLC($$, oper.res);
										}
;

%%


/**
*	Inserta un valor en la tabla de simbolos
*/
void insertarSibValor(char *nombre, Tipo tipo, int valor){
	Simbolo simb;
	simb.nombre = nombre;
	simb.tipo = tipo;
	simb.valor = valor;


	// Hacemos la inserción ordenada de manera que aparezca primero cadenas y luego constantes/variables

	if(tipo==CADENA){

		PosicionLista pos = inicioLS(tablaSimbolos);
		for(int j=0;j<contCadenas-1;j++){ // Queremos insertar nuevas cadenas despues de la última cadena (para que esten ordenada)
			pos = siguienteLS(tablaSimbolos, pos);
		}
		insertaLS(tablaSimbolos, pos, simb);

	}else{
		insertaLS(tablaSimbolos, finalLS(tablaSimbolos), simb); // Insertamos al final de la tabla de simbolos para agrupar constantes/variables
	}
	index_tabla_simbolos++;
}

/**
*	Inserta un valor en la tabla de simbolos (con un valor por defecto) [método sobrecargado]
*/
void insertarSib(char *nombre, Tipo tipo){
	insertarSibValor(nombre, tipo, -1);
}

/**
*	Comprueba si un simbolo pertenece a la tabla de simbolos
*/
int perteneceTSimb(char *nombre){	
	PosicionLista aux = inicioLS(tablaSimbolos);

	for(int j=0;j<longitudLS(tablaSimbolos);j++){
		Simbolo simb = recuperaLS(tablaSimbolos, aux);
		if(strcmp(simb.nombre,nombre)==0){
			return 1;
		}
		aux = siguienteLS(tablaSimbolos, aux);
	}
	return 0;
}

/**
*	Comprueba si un simbolo es de tipo constante
*/
int esConstante(char *nombre){
	Simbolo simb = recuperaLS(tablaSimbolos,buscaLS(tablaSimbolos, nombre));
	return simb.tipo == CONSTANTE;
}

/**
*	Imprime la tabla de simbolos.
*/
void imprimirTSimb(){

	if(!analisis_ok){
		return; // Si hay errores no imprimir
	}
	printf("###################\n");
	printf("# Datos\n");
	printf("\t.data\n\n");

	PosicionLista aux = inicioLS(tablaSimbolos);

	for(int j=0;j<longitudLS(tablaSimbolos);j++){
		Simbolo simb = recuperaLS(tablaSimbolos, aux);

		// La impresión es diferente si es una cadena o una constante/variable.
		if(simb.tipo == CADENA){
			printf("$str%d:\n", simb.valor);
			printf("\t.asciiz %s\n", simb.nombre);	
		}else if(simb.tipo == CONSTANTE || simb.tipo == VARIABLE){
			printf("_%s:\n", simb.nombre);
			printf("\t.word 0\n");	
		}


		aux = siguienteLS(tablaSimbolos, aux);
	}
}

/**
*	Concatena dos strings
*/
char *concatena(char *a, char *b){
	char aux[strlen(a) + strlen(b)];
	sprintf(aux, "%s%s", a, b);
	return strdup(aux);
}

/**
*	Concatena un entero a un string
*/
char *concatena_int(char *a, int b){
	char aux[strlen(a) + 1];
	sprintf(aux, "%s%d", a, b);
	return strdup(aux);
}

/**
*	Obtiene un registro disponible.
*/
int obtenerReg(){
	
	for(int j=0;j<9;j++){
		if(tempReg[j]==0){
			tempReg[j]=1;

			return j;

		}
	}

	// No quedan registros libres
	printf("ERROR: No quedan registros libres\n");
	errores_semanticos=1;
	exit(1);
}

/**
*	Liberar un registro
*/
void liberarReg(char *reg){
	char index_char = reg[2];
	int index = index_char - '0';
	tempReg[index]=0;

}

/**
*	Imprime error 
*/
void yyerror(const char *msg){
	errores_sintacticos++;
	printf("Error sintactico en línea %d: %s\n", yylineno, msg);
}

/**
*	Impime lista de codigo final 
*/
void imprimirCodigo(ListaC codigo) {

	if(errores_semanticos!=0){
		return; // Si hay errores no imprimir
	}
	printf("###################\n");
	printf("# Seccion de codigo\n");
	printf("\t.text\n");
	printf("\t.globl main\n");
	printf("main:\n");

	PosicionListaC p = inicioLC(codigo);
	while (p != finalLC(codigo)) {
		Operacion oper = recuperaLC(codigo,p);

		if(strcmp(oper.op,"etiq") != 0){
			// Si no es una etiqueta, aplicar sangria y actuar como una operacion normal
			printf("\t%s",oper.op);
			if (oper.res) printf(" %s",oper.res);
			if (oper.arg1) printf(", %s",oper.arg1);
			if (oper.arg2) printf(", %s",oper.arg2);
		}else{
			// Si es una etiqueta, tratarlo como etiqueta
			printf("%s:", oper.res);
		}

		printf("\n");
		p = siguienteLC(codigo,p);
	}

	printf("# Exit\n");
	printf("\tli $v0, 10\n");
	printf("\tsyscall\n");
 }


/**
*	Obtiene una nueva etiqueta 
*/
char *nuevaEtiqueta() {
 char aux[16];
 sprintf(aux,"$l%d",contador_etiq++);
 return strdup(aux);
}

/**
*	Se asegura de que no haya ningun error en el codigo
*/
int analisis_ok(){
	return (errores_lexicos+errores_semanticos+errores_sintacticos)==0;
}
