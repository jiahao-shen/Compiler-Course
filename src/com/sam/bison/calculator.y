%token   HEXADECIMAL INTEGER VARIABLE SIN COS TAN
%left    '+' '-'
%left    '*' '/'
%left    '&'
%left    '|'
%left    '^'
%right   '@''~'
%left    '!'

%{

    #include <stdio.h>   
    #include <math.h>
    #define YYSTYPE double
    #define pi 3.1415926 
    void yyerror(char*);
    int yylex(void);

    double sym[26];
%}

%%
program:
    program statement '\n'
    |
    ;
statement:
     expr    {printf("%lf\n", $1);}
     |VARIABLE '=' expr    {sym[(int)$1] = $3;}
     ;
expr:
    INTEGER
    |HEXADECIMAL
    |VARIABLE{$$ = sym[(int)$1];}
    |expr '+' expr    {$$ = $1 + $3;}
    |expr '-' expr    {$$ = $1 - $3;}
    |expr '*' expr    {$$ = $1 * $3;}
    |expr '/' expr    {$$ = $1 / $3;}
    |expr '&' expr    {$$ = (int)$1 & (int)$3;}
    |expr '|' expr    {$$ = (int)$1 | (int)$3;}
    |'~' expr         {$$ = ~(int)$2;}
    |'@' expr         {$$ = sqrt($2);}
    |expr '@' expr    {$$ = $1*sqrt($3);}
    |expr '!'         {int i=1,s=1;for(;i<=$2;i++)s*=i;$$=s;}
    |expr '^' expr    {$$=pow($1,$3);}
    |'('expr')'       {$$ = $2;}
    |SIN'('expr')'       {$$ = sin($3*pi/180.0);}
    |COS'('expr')'       {$$ = cos($3*pi/180.0);}
    |TAN'('expr')'       {$$ = tan($3*pi/180.0);}
    ;

%%

void yyerror(char* s) {
    fprintf(stderr, "%s\n", s);
}

int main(void) {
    yyparse();
    return 0;
}