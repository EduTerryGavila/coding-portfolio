/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_MINIC_TAB_H_INCLUDED
# define YY_YY_MINIC_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif
/* "%code requires" blocks.  */
#line 55 "minic.y"

	#include "listaCodigo.h"

#line 53 "minic.tab.h"

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    VAR = 258,                     /* VAR  */
    CONST = 259,                   /* CONST  */
    IF = 260,                      /* IF  */
    WHILE = 261,                   /* WHILE  */
    PRINT = 262,                   /* PRINT  */
    READ = 263,                    /* READ  */
    LPAREN = 264,                  /* LPAREN  */
    RPAREN = 265,                  /* RPAREN  */
    PUNTOCOMA = 266,               /* PUNTOCOMA  */
    COMA = 267,                    /* COMA  */
    ASSIGNOP = 268,                /* ASSIGNOP  */
    LCORCH = 269,                  /* LCORCH  */
    RCORCH = 270,                  /* RCORCH  */
    DO = 271,                      /* DO  */
    FOR = 272,                     /* FOR  */
    INZ = 273,                     /* INZ  */
    TO = 274,                      /* TO  */
    EQOP = 275,                    /* EQOP  */
    NEQOP = 276,                   /* NEQOP  */
    LT = 277,                      /* LT  */
    LTEQ = 278,                    /* LTEQ  */
    GT = 279,                      /* GT  */
    GTEQ = 280,                    /* GTEQ  */
    COND = 281,                    /* COND  */
    INTLITERAL = 282,              /* INTLITERAL  */
    ID = 283,                      /* ID  */
    STRING = 284,                  /* STRING  */
    OTHERELSE = 285,               /* OTHERELSE  */
    ELSE = 286,                    /* ELSE  */
    PLUSOP = 287,                  /* PLUSOP  */
    MINUSOP = 288,                 /* MINUSOP  */
    MULTOP = 289,                  /* MULTOP  */
    DIVOP = 290,                   /* DIVOP  */
    UMINUS = 291                   /* UMINUS  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
union YYSTYPE
{
#line 58 "minic.y"

	char* cadena;
	ListaC codigo;

#line 111 "minic.tab.h"

};
typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif

/* Location type.  */
#if ! defined YYLTYPE && ! defined YYLTYPE_IS_DECLARED
typedef struct YYLTYPE YYLTYPE;
struct YYLTYPE
{
  int first_line;
  int first_column;
  int last_line;
  int last_column;
};
# define YYLTYPE_IS_DECLARED 1
# define YYLTYPE_IS_TRIVIAL 1
#endif


extern YYSTYPE yylval;
extern YYLTYPE yylloc;

int yyparse (void);


#endif /* !YY_YY_MINIC_TAB_H_INCLUDED  */
