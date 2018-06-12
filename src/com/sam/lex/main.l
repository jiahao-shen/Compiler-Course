%{
#include <math.h>
#include <stdio.h>
%}

letter      [a-zA-Z]
digit       [0-9]
newline     (\n)
char        \'([a-zA-Z]?|[0-9]?)\'
ws          [ \t]+
string      \"[^"\n]*\"
exponent    ((E|e)("+"|"-")?({digit}+))
float1      {digit}+"."{digit}+{exponent}?
float2      {digit}+{exponent}
double      ({float1}|{float2})
integer     {digit}+
identifier  ({letter}|"_")({letter}|{digit}|"_")*   

%%
"//"((.)*)\n                            printf("LineComment\n");
"/*"(([^*]|(("*"+)[^*/]))*)("*"+)"/"\n  printf("MultilineComment\n");

bool        { printf("%s\t关键字\n", yytext); } 
break       { printf("%s\t关键字\n", yytext); }
continue     { printf("%s\t关键字\n", yytext); }
class       { printf("%s\t关键字\n", yytext); } 
double      { printf("%s\t关键字\n", yytext); }
else        { printf("%s\t关键字\n", yytext); } 
char        { printf("%s\t关键字\n", yytext); }
float       { printf("%s\t关键字\n", yytext); }
extends     { printf("%s\t关键字\n", yytext); } 
for         { printf("%s\t关键字\n", yytext); } 
if          { printf("%s\t关键字\n", yytext); }
implements  { printf("%s\t关键字\n", yytext); } 
int         { printf("%s\t关键字\n", yytext); } 
interface   { printf("%s\t关键字\n", yytext); } 
newarray    { printf("%s\t关键字\n", yytext); } 
printf      { printf("%s\t关键字\n", yytext); } 
return      { printf("%s\t关键字\n", yytext); }
string      { printf("%s\t关键字\n", yytext); }
void        { printf("%s\t关键字\n", yytext); } 
while       { printf("%s\t关键字\n", yytext); }
include     { printf("%s\t关键字\n", yytext); }
main        { printf("%s\t关键字\n", yytext); }
true|false  { printf("%s\t关键字\n", yytext);} 

"+"         { printf("+\t运算符\n"); }
"-"         { printf("-\t运算符\n"); }
"*"         { printf("*\t运算符\n"); }
"/"         { printf("/\t运算符\n"); }
"<="        { printf("<=\t运算符\n"); }
"<"         { printf("<\t运算符\n"); }
">"         { printf(">\t运算符\n"); }
">="        { printf(">=\t运算符\n"); }
"=="        { printf("==\t运算符\n"); }
"!="        { printf("!=\t运算符\n"); }
"="         { printf("=\t运算符\n"); }
";"         { printf(";\t运算符\n"); }
","         { printf(",\t运算符\n"); }
"."         { printf(".\t运算符\n"); }
"("         { printf("(\t运算符\n"); }
")"         { printf(")\t运算符\n"); }
"["         { printf("[\t运算符\n"); }
"]"         { printf("]\t运算符\n"); }
"{"         { printf("{\t运算符\n"); }
"}"         { printf("}\t运算符\n"); }
"^"         { printf("^\t运算符\n"); }
"!"         { printf("!\t运算符\n"); }
"&"         { printf("&\t运算符\n"); }
"&&"        { printf("&&\t运算符\n"); }
"||"        { printf("||\t运算符\n"); }
":"         { printf(":\t运算符\n"); }
"~"         { printf("~\t运算符\n"); }
"<<"        { printf("<<\t运算符\n"); }
">>"        { printf(">>\t运算符\n"); }
"++"        { printf("++\t运算符\n"); }
"--"        { printf("--\t运算符\n"); }


"#"         { printf("#\t预处理符号\n"); }
{newline}       { printf("换行\n"); }
{integer}       { printf("%s\t整形\n", yytext); }
{double}        { printf("%s\t浮点数\n", yytext); }
{string}        { printf("%s\t字符串\n", yytext); }
{char}          { printf("%s\t字符\n", yytext); }    
{identifier}    { printf("%s\t变量\n", yytext); }
{ws}            {; }
.               { printf("%s\t错误\n", yytext); }
%%

int yywrap(void) {
    return 1;
}

int main(void) {
    yylex();
    return 0;
}