/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

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

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     RPARENTHESIS = 258,
     LPARENTHESIS = 259,
     EOLN = 260,
     MULTIPLY = 261,
     DIVIDE = 262,
     ADD = 263,
     SUBTRACT = 264,
     EXPONENT = 265,
     COS = 266,
     SIN = 267,
     TAN = 268,
     SEC = 269,
     CSC = 270,
     COT = 271,
     E = 272,
     ARCSIN = 273,
     ARCCOS = 274,
     ARCTAN = 275,
     ARCSEC = 276,
     ARCCSC = 277,
     ARCCOT = 278,
     LN = 279,
     LOG = 280,
     SQRT = 281,
     EQUALS = 282,
     VARIABLE = 283,
     NUMBER = 284
   };
#endif
/* Tokens.  */
#define RPARENTHESIS 258
#define LPARENTHESIS 259
#define EOLN 260
#define MULTIPLY 261
#define DIVIDE 262
#define ADD 263
#define SUBTRACT 264
#define EXPONENT 265
#define COS 266
#define SIN 267
#define TAN 268
#define SEC 269
#define CSC 270
#define COT 271
#define E 272
#define ARCSIN 273
#define ARCCOS 274
#define ARCTAN 275
#define ARCSEC 276
#define ARCCSC 277
#define ARCCOT 278
#define LN 279
#define LOG 280
#define SQRT 281
#define EQUALS 282
#define VARIABLE 283
#define NUMBER 284




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 20 "main.y"
{
	struct calculator *fun;
	double du;
	char *str;
}
/* Line 1529 of yacc.c.  */
#line 113 "y.tab.h"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE yylval;

