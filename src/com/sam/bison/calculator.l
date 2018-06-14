%{
    int yywrap();
    #define YYSTYPE double
    void yyerror(char*);
    #include "calculator.tab.h"
%}
%%
[a-z]   {
            yylval = *yytext - 'a';
            return VARIABLE;
        }
0x\.?[a-f0-9]+|0x[a-f0-9]+\.[a-f0-9]* {
            yylval=atof(yytext);
            return HEXADECIMAL;
        }
\.?[0-9]+|[0-9]+\.[0-9]*    {
            yylval = atof(yytext);
            return INTEGER;
        }
[-+()=/*&|~!^@\n]  {return *yytext;}
sin {
    return SIN;
    }
cos {
    return COS;
    }
tan {
    return TAN;
    }
[ \t]    ;
.    yyerror("无效的输入字符");
%%
int yywrap() {
    return 1;
}