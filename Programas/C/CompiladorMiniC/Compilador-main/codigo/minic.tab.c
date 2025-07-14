/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Identify Bison output, and Bison version.  */
#define YYBISON 30802

/* Bison version string.  */
#define YYBISON_VERSION "3.8.2"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* First part of user prologue.  */
#line 1 "minic.y"


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

	

#line 125 "minic.tab.c"

# ifndef YY_CAST
#  ifdef __cplusplus
#   define YY_CAST(Type, Val) static_cast<Type> (Val)
#   define YY_REINTERPRET_CAST(Type, Val) reinterpret_cast<Type> (Val)
#  else
#   define YY_CAST(Type, Val) ((Type) (Val))
#   define YY_REINTERPRET_CAST(Type, Val) ((Type) (Val))
#  endif
# endif
# ifndef YY_NULLPTR
#  if defined __cplusplus
#   if 201103L <= __cplusplus
#    define YY_NULLPTR nullptr
#   else
#    define YY_NULLPTR 0
#   endif
#  else
#   define YY_NULLPTR ((void*)0)
#  endif
# endif

#include "minic.tab.h"
/* Symbol kind.  */
enum yysymbol_kind_t
{
  YYSYMBOL_YYEMPTY = -2,
  YYSYMBOL_YYEOF = 0,                      /* "end of file"  */
  YYSYMBOL_YYerror = 1,                    /* error  */
  YYSYMBOL_YYUNDEF = 2,                    /* "invalid token"  */
  YYSYMBOL_VAR = 3,                        /* VAR  */
  YYSYMBOL_CONST = 4,                      /* CONST  */
  YYSYMBOL_IF = 5,                         /* IF  */
  YYSYMBOL_WHILE = 6,                      /* WHILE  */
  YYSYMBOL_PRINT = 7,                      /* PRINT  */
  YYSYMBOL_READ = 8,                       /* READ  */
  YYSYMBOL_LPAREN = 9,                     /* LPAREN  */
  YYSYMBOL_RPAREN = 10,                    /* RPAREN  */
  YYSYMBOL_PUNTOCOMA = 11,                 /* PUNTOCOMA  */
  YYSYMBOL_COMA = 12,                      /* COMA  */
  YYSYMBOL_ASSIGNOP = 13,                  /* ASSIGNOP  */
  YYSYMBOL_LCORCH = 14,                    /* LCORCH  */
  YYSYMBOL_RCORCH = 15,                    /* RCORCH  */
  YYSYMBOL_DO = 16,                        /* DO  */
  YYSYMBOL_FOR = 17,                       /* FOR  */
  YYSYMBOL_INZ = 18,                       /* INZ  */
  YYSYMBOL_TO = 19,                        /* TO  */
  YYSYMBOL_EQOP = 20,                      /* EQOP  */
  YYSYMBOL_NEQOP = 21,                     /* NEQOP  */
  YYSYMBOL_LT = 22,                        /* LT  */
  YYSYMBOL_LTEQ = 23,                      /* LTEQ  */
  YYSYMBOL_GT = 24,                        /* GT  */
  YYSYMBOL_GTEQ = 25,                      /* GTEQ  */
  YYSYMBOL_COND = 26,                      /* COND  */
  YYSYMBOL_INTLITERAL = 27,                /* INTLITERAL  */
  YYSYMBOL_ID = 28,                        /* ID  */
  YYSYMBOL_STRING = 29,                    /* STRING  */
  YYSYMBOL_OTHERELSE = 30,                 /* OTHERELSE  */
  YYSYMBOL_ELSE = 31,                      /* ELSE  */
  YYSYMBOL_PLUSOP = 32,                    /* PLUSOP  */
  YYSYMBOL_MINUSOP = 33,                   /* MINUSOP  */
  YYSYMBOL_MULTOP = 34,                    /* MULTOP  */
  YYSYMBOL_DIVOP = 35,                     /* DIVOP  */
  YYSYMBOL_UMINUS = 36,                    /* UMINUS  */
  YYSYMBOL_YYACCEPT = 37,                  /* $accept  */
  YYSYMBOL_program = 38,                   /* program  */
  YYSYMBOL_39_1 = 39,                      /* $@1  */
  YYSYMBOL_declarations = 40,              /* declarations  */
  YYSYMBOL_41_2 = 41,                      /* $@2  */
  YYSYMBOL_42_3 = 42,                      /* $@3  */
  YYSYMBOL_identifier_list = 43,           /* identifier_list  */
  YYSYMBOL_asig = 44,                      /* asig  */
  YYSYMBOL_statement_list = 45,            /* statement_list  */
  YYSYMBOL_statement = 46,                 /* statement  */
  YYSYMBOL_print_list = 47,                /* print_list  */
  YYSYMBOL_print_item = 48,                /* print_item  */
  YYSYMBOL_read_list = 49,                 /* read_list  */
  YYSYMBOL_expression = 50                 /* expression  */
};
typedef enum yysymbol_kind_t yysymbol_kind_t;




#ifdef short
# undef short
#endif

/* On compilers that do not define __PTRDIFF_MAX__ etc., make sure
   <limits.h> and (if available) <stdint.h> are included
   so that the code can choose integer types of a good width.  */

#ifndef __PTRDIFF_MAX__
# include <limits.h> /* INFRINGES ON USER NAME SPACE */
# if defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stdint.h> /* INFRINGES ON USER NAME SPACE */
#  define YY_STDINT_H
# endif
#endif

/* Narrow types that promote to a signed type and that can represent a
   signed or unsigned integer of at least N bits.  In tables they can
   save space and decrease cache pressure.  Promoting to a signed type
   helps avoid bugs in integer arithmetic.  */

#ifdef __INT_LEAST8_MAX__
typedef __INT_LEAST8_TYPE__ yytype_int8;
#elif defined YY_STDINT_H
typedef int_least8_t yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef __INT_LEAST16_MAX__
typedef __INT_LEAST16_TYPE__ yytype_int16;
#elif defined YY_STDINT_H
typedef int_least16_t yytype_int16;
#else
typedef short yytype_int16;
#endif

/* Work around bug in HP-UX 11.23, which defines these macros
   incorrectly for preprocessor constants.  This workaround can likely
   be removed in 2023, as HPE has promised support for HP-UX 11.23
   (aka HP-UX 11i v2) only through the end of 2022; see Table 2 of
   <https://h20195.www2.hpe.com/V2/getpdf.aspx/4AA4-7673ENW.pdf>.  */
#ifdef __hpux
# undef UINT_LEAST8_MAX
# undef UINT_LEAST16_MAX
# define UINT_LEAST8_MAX 255
# define UINT_LEAST16_MAX 65535
#endif

#if defined __UINT_LEAST8_MAX__ && __UINT_LEAST8_MAX__ <= __INT_MAX__
typedef __UINT_LEAST8_TYPE__ yytype_uint8;
#elif (!defined __UINT_LEAST8_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST8_MAX <= INT_MAX)
typedef uint_least8_t yytype_uint8;
#elif !defined __UINT_LEAST8_MAX__ && UCHAR_MAX <= INT_MAX
typedef unsigned char yytype_uint8;
#else
typedef short yytype_uint8;
#endif

#if defined __UINT_LEAST16_MAX__ && __UINT_LEAST16_MAX__ <= __INT_MAX__
typedef __UINT_LEAST16_TYPE__ yytype_uint16;
#elif (!defined __UINT_LEAST16_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST16_MAX <= INT_MAX)
typedef uint_least16_t yytype_uint16;
#elif !defined __UINT_LEAST16_MAX__ && USHRT_MAX <= INT_MAX
typedef unsigned short yytype_uint16;
#else
typedef int yytype_uint16;
#endif

#ifndef YYPTRDIFF_T
# if defined __PTRDIFF_TYPE__ && defined __PTRDIFF_MAX__
#  define YYPTRDIFF_T __PTRDIFF_TYPE__
#  define YYPTRDIFF_MAXIMUM __PTRDIFF_MAX__
# elif defined PTRDIFF_MAX
#  ifndef ptrdiff_t
#   include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  endif
#  define YYPTRDIFF_T ptrdiff_t
#  define YYPTRDIFF_MAXIMUM PTRDIFF_MAX
# else
#  define YYPTRDIFF_T long
#  define YYPTRDIFF_MAXIMUM LONG_MAX
# endif
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned
# endif
#endif

#define YYSIZE_MAXIMUM                                  \
  YY_CAST (YYPTRDIFF_T,                                 \
           (YYPTRDIFF_MAXIMUM < YY_CAST (YYSIZE_T, -1)  \
            ? YYPTRDIFF_MAXIMUM                         \
            : YY_CAST (YYSIZE_T, -1)))

#define YYSIZEOF(X) YY_CAST (YYPTRDIFF_T, sizeof (X))


/* Stored state numbers (used for stacks). */
typedef yytype_int8 yy_state_t;

/* State numbers in computations.  */
typedef int yy_state_fast_t;

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif


#ifndef YY_ATTRIBUTE_PURE
# if defined __GNUC__ && 2 < __GNUC__ + (96 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_PURE __attribute__ ((__pure__))
# else
#  define YY_ATTRIBUTE_PURE
# endif
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# if defined __GNUC__ && 2 < __GNUC__ + (7 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_UNUSED __attribute__ ((__unused__))
# else
#  define YY_ATTRIBUTE_UNUSED
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YY_USE(E) ((void) (E))
#else
# define YY_USE(E) /* empty */
#endif

/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
#if defined __GNUC__ && ! defined __ICC && 406 <= __GNUC__ * 100 + __GNUC_MINOR__
# if __GNUC__ * 100 + __GNUC_MINOR__ < 407
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")
# else
#  define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                           \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")              \
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# endif
# define YY_IGNORE_MAYBE_UNINITIALIZED_END      \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

#if defined __cplusplus && defined __GNUC__ && ! defined __ICC && 6 <= __GNUC__
# define YY_IGNORE_USELESS_CAST_BEGIN                          \
    _Pragma ("GCC diagnostic push")                            \
    _Pragma ("GCC diagnostic ignored \"-Wuseless-cast\"")
# define YY_IGNORE_USELESS_CAST_END            \
    _Pragma ("GCC diagnostic pop")
#endif
#ifndef YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_END
#endif


#define YY_ASSERT(E) ((void) (0 && (E)))

#if !defined yyoverflow

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* !defined yyoverflow */

#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL \
             && defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yy_state_t yyss_alloc;
  YYSTYPE yyvs_alloc;
  YYLTYPE yyls_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (YYSIZEOF (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (YYSIZEOF (yy_state_t) + YYSIZEOF (YYSTYPE) \
             + YYSIZEOF (YYLTYPE)) \
      + 2 * YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYPTRDIFF_T yynewbytes;                                         \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * YYSIZEOF (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / YYSIZEOF (*yyptr);                        \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, YY_CAST (YYSIZE_T, (Count)) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYPTRDIFF_T yyi;                      \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  3
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   242

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  37
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  14
/* YYNRULES -- Number of rules.  */
#define YYNRULES  44
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  105

/* YYMAXUTOK -- Last valid token kind.  */
#define YYMAXUTOK   291


/* YYTRANSLATE(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, with out-of-bounds checking.  */
#define YYTRANSLATE(YYX)                                \
  (0 <= (YYX) && (YYX) <= YYMAXUTOK                     \
   ? YY_CAST (yysymbol_kind_t, yytranslate[YYX])        \
   : YYSYMBOL_YYUNDEF)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex.  */
static const yytype_int8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36
};

#if YYDEBUG
/* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_int16 yyrline[] =
{
       0,    83,    83,    83,   101,   101,   108,   108,   115,   120,
     124,   132,   142,   173,   180,   185,   211,   215,   266,   297,
     345,   374,   452,   533,   537,   543,   544,   547,   579,   616,
     653,   693,   707,   721,   734,   747,   767,   781,   794,   807,
     820,   833,   837,   848,   865
};
#endif

/** Accessing symbol of state STATE.  */
#define YY_ACCESSING_SYMBOL(State) YY_CAST (yysymbol_kind_t, yystos[State])

#if YYDEBUG || 0
/* The user-facing name of the symbol whose (internal) number is
   YYSYMBOL.  No bounds checking.  */
static const char *yysymbol_name (yysymbol_kind_t yysymbol) YY_ATTRIBUTE_UNUSED;

/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "\"end of file\"", "error", "\"invalid token\"", "VAR", "CONST", "IF",
  "WHILE", "PRINT", "READ", "LPAREN", "RPAREN", "PUNTOCOMA", "COMA",
  "ASSIGNOP", "LCORCH", "RCORCH", "DO", "FOR", "INZ", "TO", "EQOP",
  "NEQOP", "LT", "LTEQ", "GT", "GTEQ", "COND", "INTLITERAL", "ID",
  "STRING", "OTHERELSE", "ELSE", "PLUSOP", "MINUSOP", "MULTOP", "DIVOP",
  "UMINUS", "$accept", "program", "$@1", "declarations", "$@2", "$@3",
  "identifier_list", "asig", "statement_list", "statement", "print_list",
  "print_item", "read_list", "expression", YY_NULLPTR
};

static const char *
yysymbol_name (yysymbol_kind_t yysymbol)
{
  return yytname[yysymbol];
}
#endif

#define YYPACT_NINF (-21)

#define yypact_value_is_default(Yyn) \
  ((Yyn) == YYPACT_NINF)

#define YYTABLE_NINF (-1)

#define yytable_value_is_error(Yyn) \
  0

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
static const yytype_int16 yypact[] =
{
     -21,    29,    -7,   -21,    33,    38,    35,   -21,     5,   -21,
     -21,   156,    15,    15,    41,    53,    54,    57,   -21,   -21,
     175,    19,    55,   -21,    56,    21,   -21,    27,    37,    37,
       8,    39,   171,    65,    64,    37,    37,   -21,    15,   -21,
      37,   -21,   -21,    37,    86,   102,   -21,     6,   -21,   201,
     -21,    18,   -21,    37,    37,   -10,   201,   -21,   118,   -18,
     175,    37,    37,    37,    37,    37,    37,    37,    37,    37,
      37,   175,    61,     8,    62,    46,   134,   185,   -21,   -21,
      52,   201,   201,   201,   201,   201,   201,   207,   207,   -18,
     -18,   -21,   -21,   -21,   -21,   -21,   -21,    37,    37,   175,
      70,    70,   -21,   -21,   -21
};

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
static const yytype_int8 yydefact[] =
{
       2,     0,     0,     1,     0,     0,     0,     8,    14,     4,
       6,     0,     0,     0,     0,     0,     0,     0,    14,     3,
       0,     0,     0,    13,    11,     0,     9,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     5,     0,     7,
       0,    44,    43,     0,     0,     0,    28,     0,    25,    27,
      29,     0,    16,     0,     0,     0,    12,    10,     0,    42,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    15,    41,
      18,    35,    36,    37,    38,    39,    40,    31,    32,    33,
      34,    19,    23,    26,    24,    30,    20,     0,     0,     0,
       0,     0,    17,    21,    22
};

/* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -21,   -21,   -21,   -21,   -21,   -21,    72,    59,    81,   -20,
     -21,    28,   -21,    -9
};

/* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
       0,     1,     2,     8,    12,    13,    25,    26,    11,    23,
      47,    48,    51,    49
};

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int8 yytable[] =
{
      33,    78,    61,    62,    63,    64,    65,    66,     9,    10,
      61,    62,    63,    64,    65,    66,    72,    40,    73,    44,
      45,     4,    67,    68,    69,    70,    55,    56,    74,     3,
      75,    58,    37,    38,    59,    41,    42,    46,    39,    38,
      80,    43,     5,    24,    76,    77,    40,    34,     6,     7,
      28,    91,    81,    82,    83,    84,    85,    86,    87,    88,
      89,    90,    29,    30,    41,    42,    31,    50,    35,    36,
      43,    53,    92,    94,    95,    14,    15,    16,    17,   102,
     103,   104,    54,    99,    18,    27,    20,    21,   100,   101,
      61,    62,    63,    64,    65,    66,    60,    57,    22,    32,
       0,    93,    67,    68,    69,    70,    61,    62,    63,    64,
      65,    66,    71,     0,     0,     0,     0,     0,    67,    68,
      69,    70,    61,    62,    63,    64,    65,    66,    79,     0,
       0,     0,     0,     0,    67,    68,    69,    70,    61,    62,
      63,    64,    65,    66,     0,    96,     0,     0,     0,     0,
      67,    68,    69,    70,    61,    62,    63,    64,    65,    66,
       0,    14,    15,    16,    17,     0,    67,    68,    69,    70,
      18,    19,    20,    21,     0,     0,    14,    15,    16,    17,
      14,    15,    16,    17,    22,    18,    52,    20,    21,    18,
       0,    20,    21,     0,     0,     0,     0,     0,     0,    22,
       0,     0,     0,    22,    97,    61,    62,    63,    64,    65,
      66,    98,     0,     0,     0,     0,     0,    67,    68,    69,
      70,    61,    62,    63,    64,    65,    66,    61,    62,    63,
      64,    65,    66,    67,    68,    69,    70,     0,     0,     0,
       0,    69,    70
};

static const yytype_int8 yycheck[] =
{
      20,    11,    20,    21,    22,    23,    24,    25,     3,     4,
      20,    21,    22,    23,    24,    25,    10,     9,    12,    28,
      29,    28,    32,    33,    34,    35,    35,    36,    10,     0,
      12,    40,    11,    12,    43,    27,    28,    29,    11,    12,
      60,    33,     9,    28,    53,    54,     9,    28,    10,    14,
       9,    71,    61,    62,    63,    64,    65,    66,    67,    68,
      69,    70,     9,     9,    27,    28,     9,    28,    13,    13,
      33,     6,    11,    11,    28,     5,     6,     7,     8,    99,
     100,   101,    18,    31,    14,    13,    16,    17,    97,    98,
      20,    21,    22,    23,    24,    25,    10,    38,    28,    18,
      -1,    73,    32,    33,    34,    35,    20,    21,    22,    23,
      24,    25,    10,    -1,    -1,    -1,    -1,    -1,    32,    33,
      34,    35,    20,    21,    22,    23,    24,    25,    10,    -1,
      -1,    -1,    -1,    -1,    32,    33,    34,    35,    20,    21,
      22,    23,    24,    25,    -1,    11,    -1,    -1,    -1,    -1,
      32,    33,    34,    35,    20,    21,    22,    23,    24,    25,
      -1,     5,     6,     7,     8,    -1,    32,    33,    34,    35,
      14,    15,    16,    17,    -1,    -1,     5,     6,     7,     8,
       5,     6,     7,     8,    28,    14,    15,    16,    17,    14,
      -1,    16,    17,    -1,    -1,    -1,    -1,    -1,    -1,    28,
      -1,    -1,    -1,    28,    19,    20,    21,    22,    23,    24,
      25,    26,    -1,    -1,    -1,    -1,    -1,    32,    33,    34,
      35,    20,    21,    22,    23,    24,    25,    20,    21,    22,
      23,    24,    25,    32,    33,    34,    35,    -1,    -1,    -1,
      -1,    34,    35
};

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
static const yytype_int8 yystos[] =
{
       0,    38,    39,     0,    28,     9,    10,    14,    40,     3,
       4,    45,    41,    42,     5,     6,     7,     8,    14,    15,
      16,    17,    28,    46,    28,    43,    44,    43,     9,     9,
       9,     9,    45,    46,    28,    13,    13,    11,    12,    11,
       9,    27,    28,    33,    50,    50,    29,    47,    48,    50,
      28,    49,    15,     6,    18,    50,    50,    44,    50,    50,
      10,    20,    21,    22,    23,    24,    25,    32,    33,    34,
      35,    10,    10,    12,    10,    12,    50,    50,    11,    10,
      46,    50,    50,    50,    50,    50,    50,    50,    50,    50,
      50,    46,    11,    48,    11,    28,    11,    19,    26,    31,
      50,    50,    46,    46,    46
};

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr1[] =
{
       0,    37,    39,    38,    41,    40,    42,    40,    40,    43,
      43,    44,    44,    45,    45,    46,    46,    46,    46,    46,
      46,    46,    46,    46,    46,    47,    47,    48,    48,    49,
      49,    50,    50,    50,    50,    50,    50,    50,    50,    50,
      50,    50,    50,    50,    50
};

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
static const yytype_int8 yyr2[] =
{
       0,     2,     0,     8,     0,     5,     0,     5,     0,     1,
       3,     1,     3,     2,     0,     4,     3,     7,     5,     5,
       5,     7,     7,     5,     5,     1,     3,     1,     1,     1,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     2,     1,     1
};


enum { YYENOMEM = -2 };

#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab
#define YYNOMEM         goto yyexhaustedlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                    \
  do                                                              \
    if (yychar == YYEMPTY)                                        \
      {                                                           \
        yychar = (Token);                                         \
        yylval = (Value);                                         \
        YYPOPSTACK (yylen);                                       \
        yystate = *yyssp;                                         \
        goto yybackup;                                            \
      }                                                           \
    else                                                          \
      {                                                           \
        yyerror (YY_("syntax error: cannot back up")); \
        YYERROR;                                                  \
      }                                                           \
  while (0)

/* Backward compatibility with an undocumented macro.
   Use YYerror or YYUNDEF. */
#define YYERRCODE YYUNDEF

/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

#ifndef YYLLOC_DEFAULT
# define YYLLOC_DEFAULT(Current, Rhs, N)                                \
    do                                                                  \
      if (N)                                                            \
        {                                                               \
          (Current).first_line   = YYRHSLOC (Rhs, 1).first_line;        \
          (Current).first_column = YYRHSLOC (Rhs, 1).first_column;      \
          (Current).last_line    = YYRHSLOC (Rhs, N).last_line;         \
          (Current).last_column  = YYRHSLOC (Rhs, N).last_column;       \
        }                                                               \
      else                                                              \
        {                                                               \
          (Current).first_line   = (Current).last_line   =              \
            YYRHSLOC (Rhs, 0).last_line;                                \
          (Current).first_column = (Current).last_column =              \
            YYRHSLOC (Rhs, 0).last_column;                              \
        }                                                               \
    while (0)
#endif

#define YYRHSLOC(Rhs, K) ((Rhs)[K])


/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)


/* YYLOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

# ifndef YYLOCATION_PRINT

#  if defined YY_LOCATION_PRINT

   /* Temporary convenience wrapper in case some people defined the
      undocumented and private YY_LOCATION_PRINT macros.  */
#   define YYLOCATION_PRINT(File, Loc)  YY_LOCATION_PRINT(File, *(Loc))

#  elif defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL

/* Print *YYLOCP on YYO.  Private, do not rely on its existence. */

YY_ATTRIBUTE_UNUSED
static int
yy_location_print_ (FILE *yyo, YYLTYPE const * const yylocp)
{
  int res = 0;
  int end_col = 0 != yylocp->last_column ? yylocp->last_column - 1 : 0;
  if (0 <= yylocp->first_line)
    {
      res += YYFPRINTF (yyo, "%d", yylocp->first_line);
      if (0 <= yylocp->first_column)
        res += YYFPRINTF (yyo, ".%d", yylocp->first_column);
    }
  if (0 <= yylocp->last_line)
    {
      if (yylocp->first_line < yylocp->last_line)
        {
          res += YYFPRINTF (yyo, "-%d", yylocp->last_line);
          if (0 <= end_col)
            res += YYFPRINTF (yyo, ".%d", end_col);
        }
      else if (0 <= end_col && yylocp->first_column < end_col)
        res += YYFPRINTF (yyo, "-%d", end_col);
    }
  return res;
}

#   define YYLOCATION_PRINT  yy_location_print_

    /* Temporary convenience wrapper in case some people defined the
       undocumented and private YY_LOCATION_PRINT macros.  */
#   define YY_LOCATION_PRINT(File, Loc)  YYLOCATION_PRINT(File, &(Loc))

#  else

#   define YYLOCATION_PRINT(File, Loc) ((void) 0)
    /* Temporary convenience wrapper in case some people defined the
       undocumented and private YY_LOCATION_PRINT macros.  */
#   define YY_LOCATION_PRINT  YYLOCATION_PRINT

#  endif
# endif /* !defined YYLOCATION_PRINT */


# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Kind, Value, Location); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*-----------------------------------.
| Print this symbol's value on YYO.  |
`-----------------------------------*/

static void
yy_symbol_value_print (FILE *yyo,
                       yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep, YYLTYPE const * const yylocationp)
{
  FILE *yyoutput = yyo;
  YY_USE (yyoutput);
  YY_USE (yylocationp);
  if (!yyvaluep)
    return;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/*---------------------------.
| Print this symbol on YYO.  |
`---------------------------*/

static void
yy_symbol_print (FILE *yyo,
                 yysymbol_kind_t yykind, YYSTYPE const * const yyvaluep, YYLTYPE const * const yylocationp)
{
  YYFPRINTF (yyo, "%s %s (",
             yykind < YYNTOKENS ? "token" : "nterm", yysymbol_name (yykind));

  YYLOCATION_PRINT (yyo, yylocationp);
  YYFPRINTF (yyo, ": ");
  yy_symbol_value_print (yyo, yykind, yyvaluep, yylocationp);
  YYFPRINTF (yyo, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yy_state_t *yybottom, yy_state_t *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yy_state_t *yyssp, YYSTYPE *yyvsp, YYLTYPE *yylsp,
                 int yyrule)
{
  int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %d):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       YY_ACCESSING_SYMBOL (+yyssp[yyi + 1 - yynrhs]),
                       &yyvsp[(yyi + 1) - (yynrhs)],
                       &(yylsp[(yyi + 1) - (yynrhs)]));
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, yylsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args) ((void) 0)
# define YY_SYMBOL_PRINT(Title, Kind, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif






/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg,
            yysymbol_kind_t yykind, YYSTYPE *yyvaluep, YYLTYPE *yylocationp)
{
  YY_USE (yyvaluep);
  YY_USE (yylocationp);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yykind, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YY_USE (yykind);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/* Lookahead token kind.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Location data for the lookahead symbol.  */
YYLTYPE yylloc
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL
  = { 1, 1, 1, 1 }
# endif
;
/* Number of syntax errors so far.  */
int yynerrs;




/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    yy_state_fast_t yystate = 0;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus = 0;

    /* Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* Their size.  */
    YYPTRDIFF_T yystacksize = YYINITDEPTH;

    /* The state stack: array, bottom, top.  */
    yy_state_t yyssa[YYINITDEPTH];
    yy_state_t *yyss = yyssa;
    yy_state_t *yyssp = yyss;

    /* The semantic value stack: array, bottom, top.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs = yyvsa;
    YYSTYPE *yyvsp = yyvs;

    /* The location stack: array, bottom, top.  */
    YYLTYPE yylsa[YYINITDEPTH];
    YYLTYPE *yyls = yylsa;
    YYLTYPE *yylsp = yyls;

  int yyn;
  /* The return value of yyparse.  */
  int yyresult;
  /* Lookahead symbol kind.  */
  yysymbol_kind_t yytoken = YYSYMBOL_YYEMPTY;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;
  YYLTYPE yyloc;

  /* The locations where the error started and ended.  */
  YYLTYPE yyerror_range[3];



#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N), yylsp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yychar = YYEMPTY; /* Cause a token to be read.  */

  yylsp[0] = yylloc;
  goto yysetstate;


/*------------------------------------------------------------.
| yynewstate -- push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;


/*--------------------------------------------------------------------.
| yysetstate -- set current state (the top of the stack) to yystate.  |
`--------------------------------------------------------------------*/
yysetstate:
  YYDPRINTF ((stderr, "Entering state %d\n", yystate));
  YY_ASSERT (0 <= yystate && yystate < YYNSTATES);
  YY_IGNORE_USELESS_CAST_BEGIN
  *yyssp = YY_CAST (yy_state_t, yystate);
  YY_IGNORE_USELESS_CAST_END
  YY_STACK_PRINT (yyss, yyssp);

  if (yyss + yystacksize - 1 <= yyssp)
#if !defined yyoverflow && !defined YYSTACK_RELOCATE
    YYNOMEM;
#else
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYPTRDIFF_T yysize = yyssp - yyss + 1;

# if defined yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        yy_state_t *yyss1 = yyss;
        YYSTYPE *yyvs1 = yyvs;
        YYLTYPE *yyls1 = yyls;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * YYSIZEOF (*yyssp),
                    &yyvs1, yysize * YYSIZEOF (*yyvsp),
                    &yyls1, yysize * YYSIZEOF (*yylsp),
                    &yystacksize);
        yyss = yyss1;
        yyvs = yyvs1;
        yyls = yyls1;
      }
# else /* defined YYSTACK_RELOCATE */
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        YYNOMEM;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yy_state_t *yyss1 = yyss;
        union yyalloc *yyptr =
          YY_CAST (union yyalloc *,
                   YYSTACK_ALLOC (YY_CAST (YYSIZE_T, YYSTACK_BYTES (yystacksize))));
        if (! yyptr)
          YYNOMEM;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
        YYSTACK_RELOCATE (yyls_alloc, yyls);
#  undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;
      yylsp = yyls + yysize - 1;

      YY_IGNORE_USELESS_CAST_BEGIN
      YYDPRINTF ((stderr, "Stack size increased to %ld\n",
                  YY_CAST (long, yystacksize)));
      YY_IGNORE_USELESS_CAST_END

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }
#endif /* !defined yyoverflow && !defined YYSTACK_RELOCATE */


  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;


/*-----------.
| yybackup.  |
`-----------*/
yybackup:
  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either empty, or end-of-input, or a valid lookahead.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token\n"));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = YYEOF;
      yytoken = YYSYMBOL_YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else if (yychar == YYerror)
    {
      /* The scanner already issued an error message, process directly
         to error recovery.  But do not keep the error token as
         lookahead, it is too special and may lead us to an endless
         loop in error recovery. */
      yychar = YYUNDEF;
      yytoken = YYSYMBOL_YYerror;
      yyerror_range[1] = yylloc;
      goto yyerrlab1;
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);
  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END
  *++yylsp = yylloc;

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];

  /* Default location. */
  YYLLOC_DEFAULT (yyloc, (yylsp - yylen), yylen);
  yyerror_range[1] = yyloc;
  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
  case 2: /* $@1: %empty  */
#line 83 "minic.y"
         { tablaSimbolos=creaLS(); }
#line 1366 "minic.tab.c"
    break;

  case 3: /* program: $@1 ID LPAREN RPAREN LCORCH declarations statement_list RCORCH  */
#line 84 "minic.y"
                                                                                        {
												imprimirTSimb();
												
												concatenaLC((yyvsp[-2].codigo),(yyvsp[-1].codigo));
												liberaLC((yyvsp[-1].codigo));

												imprimirCodigo((yyvsp[-2].codigo));
												
												liberaLS(tablaSimbolos);
												liberaLC((yyvsp[-2].codigo));
											}
#line 1382 "minic.tab.c"
    break;

  case 4: /* $@2: %empty  */
#line 101 "minic.y"
                               { tipo=VARIABLE; }
#line 1388 "minic.tab.c"
    break;

  case 5: /* declarations: declarations VAR $@2 identifier_list PUNTOCOMA  */
#line 102 "minic.y"
                                                                                        {
												(yyval.codigo)=(yyvsp[-4].codigo);
												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));
												liberaLC((yyvsp[-1].codigo));
											}
#line 1398 "minic.tab.c"
    break;

  case 6: /* $@3: %empty  */
#line 108 "minic.y"
                     { tipo=CONSTANTE; }
#line 1404 "minic.tab.c"
    break;

  case 7: /* declarations: declarations CONST $@3 identifier_list PUNTOCOMA  */
#line 109 "minic.y"
                                                                                        { 
												(yyval.codigo)=(yyvsp[-4].codigo);
												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));
												liberaLC((yyvsp[-1].codigo));
											}
#line 1414 "minic.tab.c"
    break;

  case 8: /* declarations: %empty  */
#line 115 "minic.y"
                                                                                        {
												(yyval.codigo)=creaLC();
											}
#line 1422 "minic.tab.c"
    break;

  case 9: /* identifier_list: asig  */
#line 121 "minic.y"
                                                                                        {
												(yyval.codigo) = (yyvsp[0].codigo); 
											}
#line 1430 "minic.tab.c"
    break;

  case 10: /* identifier_list: identifier_list COMA asig  */
#line 125 "minic.y"
                                                                                        {	
												(yyval.codigo) = (yyvsp[-2].codigo);
												concatenaLC((yyval.codigo), (yyvsp[0].codigo));
												liberaLC((yyvsp[0].codigo)); 
											}
#line 1440 "minic.tab.c"
    break;

  case 11: /* asig: ID  */
#line 133 "minic.y"
                                                                                        {
												if (!perteneceTSimb((yyvsp[0].cadena))){
													insertarSib((yyvsp[0].cadena), tipo);
												} else{
													printf ("ERROR: Identidicador %s ya esta declarado\n", (yyvsp[0].cadena));
													errores_semanticos=1;
												}
												(yyval.codigo)=creaLC();
											}
#line 1454 "minic.tab.c"
    break;

  case 12: /* asig: ID ASSIGNOP expression  */
#line 143 "minic.y"
                                                                                        { 
												if (!perteneceTSimb((yyvsp[-2].cadena))){
													insertarSib((yyvsp[-2].cadena), tipo);
												} else{
													printf ("ERROR: Identidicador %s ya esta declarado\n", (yyvsp[-2].cadena));
													errores_semanticos=1;
												}
												
												
												(yyval.codigo)=creaLC();

												concatenaLC((yyval.codigo), (yyvsp[0].codigo));

												Operacion oper;
												oper.op = "sw";
												oper.res =  recuperaResLC((yyvsp[0].codigo));
												oper.arg1 = concatena("_",(yyvsp[-2].cadena));
												oper.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
												guardaResLC((yyval.codigo), oper.res);
												liberarReg(oper.res);
												
												liberaLC((yyvsp[0].codigo));
											}
#line 1483 "minic.tab.c"
    break;

  case 13: /* statement_list: statement_list statement  */
#line 174 "minic.y"
                                                                                        { 	
												(yyval.codigo) = (yyvsp[-1].codigo);
												concatenaLC((yyval.codigo),(yyvsp[0].codigo)); 
												liberaLC((yyvsp[0].codigo));
											}
#line 1493 "minic.tab.c"
    break;

  case 14: /* statement_list: %empty  */
#line 180 "minic.y"
                                                                                        {
												(yyval.codigo)=creaLC(); 
											}
#line 1501 "minic.tab.c"
    break;

  case 15: /* statement: ID ASSIGNOP expression PUNTOCOMA  */
#line 186 "minic.y"
                                                                                        {	
												if(!perteneceTSimb((yyvsp[-3].cadena))){
													errores_semanticos=1;
													printf("ERROR: ID %s no esta declarado\n",(yyvsp[-3].cadena));
												}

												else if (esConstante((yyvsp[-3].cadena))){
													printf("ERROR: ID %s es constante\n", (yyvsp[-3].cadena));
													errores_semanticos=1;
												}

												(yyval.codigo)=creaLC();
												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));
												Operacion oper;
												oper.op = "sw";
												oper.res =  recuperaResLC((yyvsp[-1].codigo));
												oper.arg1 = concatena("_",(yyvsp[-3].cadena));
												oper.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
												guardaResLC((yyval.codigo), oper.res);
												liberarReg(oper.res);
												
												liberaLC((yyvsp[-1].codigo));

											}
#line 1531 "minic.tab.c"
    break;

  case 16: /* statement: LCORCH statement_list RCORCH  */
#line 212 "minic.y"
                                                                                        { 
												(yyval.codigo) = (yyvsp[-1].codigo);
											}
#line 1539 "minic.tab.c"
    break;

  case 17: /* statement: IF LPAREN expression RPAREN statement ELSE statement  */
#line 216 "minic.y"
                                                                                        {
												(yyval.codigo)=creaLC();
												concatenaLC((yyval.codigo),(yyvsp[-4].codigo));

												// Generar etiqueta
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();


												Operacion oper;
												oper.op = "beqz";
												oper.res =  recuperaResLC((yyvsp[-4].codigo));
												oper.arg1 = etiqueta1;
												oper.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
												guardaResLC((yyval.codigo), oper.res);
												
												liberarReg(recuperaResLC((yyvsp[-4].codigo)));
												liberaLC((yyvsp[-4].codigo));

												concatenaLC((yyval.codigo),(yyvsp[-2].codigo));
												liberaLC((yyvsp[-2].codigo));

												Operacion oper2;
												oper2.op = "b";
												oper2.res =  etiqueta2;
												oper2.arg1 = NULL;
												oper2.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
												guardaResLC((yyval.codigo), oper2.res);

												Operacion oper3;
												oper3.op = "etiq";
												oper3.res =  etiqueta1;
												oper3.arg1 = NULL;
												oper3.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);
												guardaResLC((yyval.codigo), oper3.res);

												concatenaLC((yyval.codigo),(yyvsp[0].codigo));
												liberaLC((yyvsp[0].codigo));

												Operacion oper4;
												oper4.op = "etiq";
												oper4.res =  etiqueta2;
												oper4.arg1 = NULL;
												oper4.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper4);
												guardaResLC((yyval.codigo), oper4.res);	
											}
#line 1594 "minic.tab.c"
    break;

  case 18: /* statement: IF LPAREN expression RPAREN statement  */
#line 267 "minic.y"
                                                                                        { 
												(yyval.codigo)=creaLC();
												concatenaLC((yyval.codigo),(yyvsp[-2].codigo));

												// Generar etiqueta
												char* etiqueta = nuevaEtiqueta();
												

												Operacion oper;
												oper.op = "beqz";
												oper.res =  recuperaResLC((yyvsp[-2].codigo));
												oper.arg1 = etiqueta;
												oper.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
												guardaResLC((yyval.codigo), oper.res);
												
												liberarReg(recuperaResLC((yyvsp[-2].codigo)));
												liberaLC((yyvsp[-2].codigo));

												concatenaLC((yyval.codigo),(yyvsp[0].codigo));
												liberaLC((yyvsp[0].codigo));

												Operacion oper2;
												oper2.op = "etiq";
												oper2.res =  etiqueta;
												oper2.arg1 = NULL;
												oper2.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
												guardaResLC((yyval.codigo), oper2.res);	
											}
#line 1629 "minic.tab.c"
    break;

  case 19: /* statement: WHILE LPAREN expression RPAREN statement  */
#line 298 "minic.y"
                                                                                        {
												(yyval.codigo)=creaLC();

												// Generar etiqueta
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper1);
												guardaResLC((yyval.codigo), oper1.res);
												
												concatenaLC((yyval.codigo),(yyvsp[-2].codigo));

												Operacion oper2;
												oper2.op = "beqz";
												oper2.res =  recuperaResLC((yyvsp[-2].codigo));
												oper2.arg1 = etiqueta2;
												oper2.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
												guardaResLC((yyval.codigo), oper2.res);
												
												concatenaLC((yyval.codigo),(yyvsp[0].codigo));

												Operacion oper3;
												oper3.op = "b";
												oper3.res =  etiqueta1;
												oper3.arg1 = NULL;
												oper3.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);
												guardaResLC((yyval.codigo), oper3.res);

												Operacion oper4;
												oper4.op = "etiq";
												oper4.res =  etiqueta2;
												oper4.arg1 = NULL;
												oper4.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper4);
												guardaResLC((yyval.codigo), oper4.res);
												
												liberarReg(recuperaResLC((yyvsp[-2].codigo)));
												liberaLC((yyvsp[-2].codigo));
												liberaLC((yyvsp[0].codigo));
											}
#line 1681 "minic.tab.c"
    break;

  case 20: /* statement: DO statement WHILE expression PUNTOCOMA  */
#line 346 "minic.y"
                                                                                        { 
												(yyval.codigo)=creaLC();
												char* etiqueta1 = nuevaEtiqueta();

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper1);
												guardaResLC((yyval.codigo), oper1.res);
												
												concatenaLC((yyval.codigo),(yyvsp[-3].codigo));

												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));
												
												Operacion oper2;
												oper2.op = "bnez";
												oper2.res =  recuperaResLC((yyvsp[-1].codigo));
												oper2.arg1 = etiqueta1;
												oper2.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
												guardaResLC((yyval.codigo), oper2.res);

												liberarReg(recuperaResLC((yyvsp[-1].codigo)));
												liberaLC((yyvsp[-3].codigo));
												liberaLC((yyvsp[-1].codigo));
											}
#line 1714 "minic.tab.c"
    break;

  case 21: /* statement: FOR ID INZ expression TO expression statement  */
#line 375 "minic.y"
                                                                                        {
												
												if(!perteneceTSimb((yyvsp[-5].cadena))){
													errores_semanticos=1;
													printf("ERROR: ID %s no esta declarado\n", (yyvsp[-5].cadena));
												}


												(yyval.codigo)=creaLC();
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();
												
												char* rg_inz = recuperaResLC((yyvsp[-3].codigo));
												char* rg_to = recuperaResLC((yyvsp[-1].codigo));

												concatenaLC((yyval.codigo),(yyvsp[-3].codigo));
												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper1);
												guardaResLC((yyval.codigo), oper1.res);

												Operacion oper2;
												oper2.op = "bgt";
												oper2.res =  rg_inz;
												oper2.arg1 = rg_to;
												oper2.arg2 = etiqueta2;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
												guardaResLC((yyval.codigo), oper2.res);


												Operacion oper3;
												oper3.op = "sw";
												oper3.res = rg_inz;
												oper3.arg1 = concatena("_",(yyvsp[-5].cadena));
												oper3.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);
												guardaResLC((yyval.codigo), oper3.res);
												
												concatenaLC((yyval.codigo),(yyvsp[0].codigo));

												Operacion oper4;
												oper4.op = "addi";
												oper4.res =  rg_inz;
												oper4.arg1 = rg_inz;
												oper4.arg2 = "1";
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper4);
												guardaResLC((yyval.codigo), oper4.res);												

												Operacion oper5;
												oper5.op = "b";
												oper5.res =  etiqueta1;
												oper5.arg1 = NULL;
												oper5.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper5);
												guardaResLC((yyval.codigo), oper5.res);
												
												Operacion oper6;
												oper6.op = "etiq";
												oper6.res =  etiqueta2;
												oper6.arg1 = NULL;
												oper6.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper6);
												guardaResLC((yyval.codigo), oper6.res);

												liberarReg(rg_inz);
												liberarReg(rg_to);

												liberaLC((yyvsp[-3].codigo));
												liberaLC((yyvsp[-1].codigo));
												liberaLC((yyvsp[0].codigo));

											}
#line 1796 "minic.tab.c"
    break;

  case 22: /* statement: FOR ID INZ expression COND expression statement  */
#line 453 "minic.y"
                                                                                        {
												
												if(!perteneceTSimb((yyvsp[-5].cadena))){
													errores_semanticos=1;
													printf("ERROR: ID %s no esta declarado\n", (yyvsp[-5].cadena));
												}


												(yyval.codigo)=creaLC();
												char* etiqueta1 = nuevaEtiqueta();
												char* etiqueta2 = nuevaEtiqueta();
												
												char* rg_inz = recuperaResLC((yyvsp[-3].codigo));

												concatenaLC((yyval.codigo),(yyvsp[-3].codigo));
												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));

												Operacion oper1;
												oper1.op = "etiq";
												oper1.res =  etiqueta1;
												oper1.arg1 = NULL;
												oper1.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper1);
												guardaResLC((yyval.codigo), oper1.res);

												char* rg_cond = recuperaResLC((yyvsp[-1].codigo));
												concatenaLC((yyval.codigo),(yyvsp[-1].codigo));

												Operacion oper2;
												oper2.op = "bnez";
												oper2.res =  rg_cond;
												oper2.arg1 = etiqueta2;
												oper2.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
												guardaResLC((yyval.codigo), oper2.res);


												Operacion oper3;
												oper3.op = "sw";
												oper3.res = rg_inz;
												oper3.arg1 = concatena("_",(yyvsp[-5].cadena));
												oper3.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);
												guardaResLC((yyval.codigo), oper3.res);
												
												concatenaLC((yyval.codigo),(yyvsp[0].codigo));

												Operacion oper4;
												oper4.op = "addi";
												oper4.res =  rg_inz;
												oper4.arg1 = rg_inz;
												oper4.arg2 = "1";
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper4);
												guardaResLC((yyval.codigo), oper4.res);												

												Operacion oper5;
												oper5.op = "b";
												oper5.res =  etiqueta1;
												oper5.arg1 = NULL;
												oper5.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper5);
												guardaResLC((yyval.codigo), oper5.res);
												
												Operacion oper6;
												oper6.op = "etiq";
												oper6.res =  etiqueta2;
												oper6.arg1 = NULL;
												oper6.arg2 = NULL;
												insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper6);
												guardaResLC((yyval.codigo), oper6.res);

												liberarReg(rg_inz);
												liberarReg(rg_cond);

												liberaLC((yyvsp[-3].codigo));
												liberaLC((yyvsp[-1].codigo));
												liberaLC((yyvsp[0].codigo));

											}
#line 1880 "minic.tab.c"
    break;

  case 23: /* statement: PRINT LPAREN print_list RPAREN PUNTOCOMA  */
#line 534 "minic.y"
                                                                                        { 
												(yyval.codigo) = (yyvsp[-2].codigo);
											}
#line 1888 "minic.tab.c"
    break;

  case 24: /* statement: READ LPAREN read_list RPAREN PUNTOCOMA  */
#line 538 "minic.y"
                                                                                        { 
												(yyval.codigo) = (yyvsp[-2].codigo);
											}
#line 1896 "minic.tab.c"
    break;

  case 26: /* print_list: print_list COMA print_item  */
#line 544 "minic.y"
                             {  (yyval.codigo)=creaLC(); concatenaLC((yyval.codigo),(yyvsp[-2].codigo)); concatenaLC((yyval.codigo),(yyvsp[0].codigo)); }
#line 1902 "minic.tab.c"
    break;

  case 27: /* print_item: expression  */
#line 548 "minic.y"
                                                                                        { // Por defecto $$=$1

											(yyval.codigo)=creaLC();
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));

											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "1";
											oper.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
											guardaResLC((yyval.codigo), oper.res);
																						
											Operacion oper2;
											oper2.op = "move";
											oper2.res =  "$a0";
											oper2.arg1 = recuperaResLC((yyvsp[0].codigo));
											oper2.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
											guardaResLC((yyval.codigo), oper2.res);

											Operacion oper3;
											oper3.op = "syscall";
											oper3.res =  NULL;
											oper3.arg1 = NULL;
											oper3.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);

											liberarReg(recuperaResLC((yyvsp[0].codigo)));
											liberaLC((yyvsp[0].codigo));
										}
#line 1938 "minic.tab.c"
    break;

  case 28: /* print_item: STRING  */
#line 580 "minic.y"
                                                                                {
											tipo=CADENA;
											insertarSibValor((yyvsp[0].cadena), tipo, contCadenas);
											contCadenas++;

											
											(yyval.codigo)=creaLC();
											
											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "4";
											oper.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
											guardaResLC((yyval.codigo), oper.res);

											
											Operacion oper2;
											oper2.op = "la";
											oper2.res =  "$a0";
											oper2.arg1 = concatena_int("$str",(contCadenas-1));
											oper2.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);
											guardaResLC((yyval.codigo), oper2.res);

											Operacion oper3;
											oper3.op = "syscall";
											oper3.res =  NULL;
											oper3.arg1 = NULL;
											oper3.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);


										}
#line 1977 "minic.tab.c"
    break;

  case 29: /* read_list: ID  */
#line 617 "minic.y"
                                                                                {											
											if(!perteneceTSimb((yyvsp[0].cadena))){
												errores_semanticos=1;
												printf("ERROR: ID %s no esta declarado\n", (yyvsp[0].cadena));
											}
											else if (esConstante((yyvsp[0].cadena))){
												errores_semanticos=1;
												printf("ERROR: ID %s es constante\n", (yyvsp[0].cadena));
											}

											(yyval.codigo)=creaLC();
											
											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "5";
											oper.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
											guardaResLC((yyval.codigo), oper.res);

											Operacion oper2;
											oper2.op = "syscall";
											oper2.res =  NULL;
											oper2.arg1 = NULL;
											oper2.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);

											Operacion oper3;
											oper3.op = "sw";
											oper3.res =  "$v0";
											oper3.arg1 = concatena("_",(yyvsp[0].cadena));
											oper3.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);
											guardaResLC((yyval.codigo), oper3.res);
											liberarReg(oper.res);
										}
#line 2018 "minic.tab.c"
    break;

  case 30: /* read_list: read_list COMA ID  */
#line 654 "minic.y"
                                                                                { 	if (!perteneceTSimb((yyvsp[0].cadena))){
												printf("ERROR: ID %s no esta declarado\n", (yyvsp[0].cadena));
												errores_semanticos=1;
											}
											else if (esConstante((yyvsp[0].cadena))){
												printf("ERROR: ID %s es constante\n", (yyvsp[0].cadena));
												errores_semanticos=1;
											}

											(yyval.codigo)=creaLC();
											concatenaLC((yyval.codigo),(yyvsp[-2].codigo));
											liberaLC((yyvsp[-2].codigo));

											Operacion oper;
											oper.op = "li";
											oper.res =  "$v0";
											oper.arg1 = "5";
											oper.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
											guardaResLC((yyval.codigo), oper.res);

											Operacion oper2;
											oper2.op = "syscall";
											oper2.res =  NULL;
											oper2.arg1 = NULL;
											oper2.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper2);

											Operacion oper3;
											oper3.op = "sw";
											oper3.res =  "$v0";
											oper3.arg1 = concatena("_",(yyvsp[0].cadena));
											oper3.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper3);
											guardaResLC((yyval.codigo), oper3.res);
											liberarReg(oper.res);
										}
#line 2060 "minic.tab.c"
    break;

  case 31: /* expression: expression PLUSOP expression  */
#line 694 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "add";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2); 

										}
#line 2078 "minic.tab.c"
    break;

  case 32: /* expression: expression MINUSOP expression  */
#line 708 "minic.y"
                                                                                {
											
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "sub";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2096 "minic.tab.c"
    break;

  case 33: /* expression: expression MULTOP expression  */
#line 722 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "mul";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2113 "minic.tab.c"
    break;

  case 34: /* expression: expression DIVOP expression  */
#line 735 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "div";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2130 "minic.tab.c"
    break;

  case 35: /* expression: expression EQOP expression  */
#line 748 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "xor";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);

											Operacion oper2;
											oper2.op = "sltiu";
											oper2.res = recuperaResLC((yyvsp[-2].codigo));
											oper2.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper2.arg2 = "1";
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper2);
										}
#line 2154 "minic.tab.c"
    break;

  case 36: /* expression: expression NEQOP expression  */
#line 768 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "xor";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);

										}
#line 2172 "minic.tab.c"
    break;

  case 37: /* expression: expression LT expression  */
#line 782 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "slt";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2189 "minic.tab.c"
    break;

  case 38: /* expression: expression LTEQ expression  */
#line 795 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "sle";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2206 "minic.tab.c"
    break;

  case 39: /* expression: expression GT expression  */
#line 808 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "sgt";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2223 "minic.tab.c"
    break;

  case 40: /* expression: expression GTEQ expression  */
#line 821 "minic.y"
                                                                                {
											(yyval.codigo) = (yyvsp[-2].codigo);
											concatenaLC((yyval.codigo),(yyvsp[0].codigo));
											Operacion oper;
											oper.op = "sge";
											oper.res = recuperaResLC((yyvsp[-2].codigo));
											oper.arg1 = recuperaResLC((yyvsp[-2].codigo));
											oper.arg2 = recuperaResLC((yyvsp[0].codigo));
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
											liberaLC((yyvsp[0].codigo));
											liberarReg(oper.arg2);
										}
#line 2240 "minic.tab.c"
    break;

  case 41: /* expression: LPAREN expression RPAREN  */
#line 834 "minic.y"
                                                                                { 
											(yyval.codigo) = (yyvsp[-1].codigo);
										}
#line 2248 "minic.tab.c"
    break;

  case 42: /* expression: MINUSOP expression  */
#line 838 "minic.y"
                                                                                { 
											(yyval.codigo) = (yyvsp[0].codigo);
											Operacion oper;
											oper.op = "neg";
											
											oper.res = recuperaResLC((yyvsp[0].codigo));
											oper.arg1 = recuperaResLC((yyvsp[0].codigo));
											oper.arg2 = NULL;
											insertaLC((yyval.codigo),finalLC((yyval.codigo)),oper);
										}
#line 2263 "minic.tab.c"
    break;

  case 43: /* expression: ID  */
#line 849 "minic.y"
                                                                                {
											if(!perteneceTSimb((yyvsp[0].cadena))){
												printf("ERROR: ID %s no esta declarado\n", (yyvsp[0].cadena));
												errores_semanticos=1;
											}

											(yyval.codigo)=creaLC();
											int rg = obtenerReg();
											Operacion oper;
											oper.op = "lw";
											oper.res =  concatena_int("$t",rg);
											oper.arg1 = concatena("_",(yyvsp[0].cadena));
											oper.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
											guardaResLC((yyval.codigo), oper.res);
										}
#line 2284 "minic.tab.c"
    break;

  case 44: /* expression: INTLITERAL  */
#line 866 "minic.y"
                                                                                {
											(yyval.codigo)=creaLC();
											int rg = obtenerReg();
											Operacion oper;
											oper.op = "li";
											oper.res =  concatena_int("$t",rg);
											oper.arg1 = strdup((yyvsp[0].cadena));
											oper.arg2 = NULL;
											insertaLC((yyval.codigo), finalLC((yyval.codigo)), oper);
											guardaResLC((yyval.codigo), oper.res);
										}
#line 2300 "minic.tab.c"
    break;


#line 2304 "minic.tab.c"

      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", YY_CAST (yysymbol_kind_t, yyr1[yyn]), &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;

  *++yyvsp = yyval;
  *++yylsp = yyloc;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */
  {
    const int yylhs = yyr1[yyn] - YYNTOKENS;
    const int yyi = yypgoto[yylhs] + *yyssp;
    yystate = (0 <= yyi && yyi <= YYLAST && yycheck[yyi] == *yyssp
               ? yytable[yyi]
               : yydefgoto[yylhs]);
  }

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYSYMBOL_YYEMPTY : YYTRANSLATE (yychar);
  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
      yyerror (YY_("syntax error"));
    }

  yyerror_range[1] = yylloc;
  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval, &yylloc);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:
  /* Pacify compilers when the user code never invokes YYERROR and the
     label yyerrorlab therefore never appears in user code.  */
  if (0)
    YYERROR;
  ++yynerrs;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  /* Pop stack until we find a state that shifts the error token.  */
  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYSYMBOL_YYerror;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYSYMBOL_YYerror)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;

      yyerror_range[1] = *yylsp;
      yydestruct ("Error: popping",
                  YY_ACCESSING_SYMBOL (yystate), yyvsp, yylsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  yyerror_range[2] = yylloc;
  ++yylsp;
  YYLLOC_DEFAULT (*yylsp, yyerror_range, 2);

  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", YY_ACCESSING_SYMBOL (yyn), yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturnlab;


/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturnlab;


/*-----------------------------------------------------------.
| yyexhaustedlab -- YYNOMEM (memory exhaustion) comes here.  |
`-----------------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  goto yyreturnlab;


/*----------------------------------------------------------.
| yyreturnlab -- parsing is finished, clean up and return.  |
`----------------------------------------------------------*/
yyreturnlab:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval, &yylloc);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  YY_ACCESSING_SYMBOL (+*yyssp), yyvsp, yylsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif

  return yyresult;
}

#line 879 "minic.y"



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
