/*
 lex.c : analisador lexico (automato finito)
 Autor: Edna A. Hoshino
 Em: junho/2013
*/
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "lex.h"

/* variaveis globais */
char lexema[81]; /* armazena a palavra processada pelo automato */
int ilexema;    /* indice usado para atualizar lexema */
int lines=1, currlines=0;       /* contador de linhas processadas */
char c;          /* armazena caracter lido da entrada */

char symbols[] = { /* alfabeto de entrada em ordem crescente de codigo ascii */
  '\t', '\n', ' ', '!', '"', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '[', ']', '{', '|', '}'
};
char delta[][nSymbols+3] = { /* tabela de transicao do AFD */
/*   '\t','\n','','!','”','&',''','(',')','*','+','v','-','.','/',':',';','<','=','>','?','[',']','{','|','}',letra,dig,invalido*/
  /* 0*/ { 0,  0, 0,13,15,22,15, 5, 6,  3,  2, 8,30,29,35,34, 7, 4, 1, 4,26,27,28, 9,23,10,11,12, 15},
  /* 1*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,14,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 2*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, 33,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /* 3*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 4*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,14,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 5*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 6*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 7*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 8*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1}, 
  /* 9*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*10*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*11*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,11,11, -1},
  /*12*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,12, -1},
  /*13*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,14,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*14*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*15*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*16*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,16, -1},
  /*17*/ {-1, -1,-1,18,18,18,19,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18,18},
  /*18*/ {-1, -1,-1,-1,-1,-1,19,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*19*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*20*/ {-1, -1,20,20,21,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20, 20},
  /*21*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*22*/ {-1, -1,-1,-1,-1,24,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*23*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,25,-1,-1,-1, -1},
  /*24*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*25*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*26*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*27*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*28*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*29*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*30*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,31,-1,-1,-1,-1,-1,-1,32,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*31*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*32*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*33*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*34*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
  /*34*/ {-1, -1,-1,-1,-1,-1,-1,-1,-1, -1, -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1},
};

char *keywords[] = { /* palavras reservadas da linguagem */
  "break", "char", "default", "do", "double", "else", "float","for", "if", "int", "main", "return", "struct", "switch", "void", "while"
};

int action[] = { /* ação associada a cada estado do automato */
  ERRO_LEXICO, OP_ATRIB, OP_ADIT, OP_MULT, OP_REL, ABRE_PARENT, FECHA_PARENT, PONTO_VIRG, VIRG, ABRE_CHAVES, FECHA_CHAVES, ID, NUM, OP_NOT, OP_REL, ERRO_LEXICO, 
  NUM, ERRO_LEXICO, ERRO_LEXICO, CARACTER, ERRO_LEXICO, STRING, OP_BIT_AND, OP_BIT_OR, OP_LOG, OP_LOG, OP_CASE, ABRE_COLC, FECHA_COLC, PONTO, OP_MINUS, OP_MINUSMINUS, OP_PONT, OP_PLUSPLUS, DOT_DOT, OP_DIV
};

char *terminalName[] = { /* nome dos terminais aceitos pelo automato */
  "break", "char", "default", "do", "double", "else", "float","for", "if", "int", "main", "return", "struct", "switch", "void", "while", "id", "num", "OP_ATRIB", "OP_ADIT", "OP_MULT", "OP_REL", "ABRE_PARENT", "FECHA_PARENT", "PONTO_VIRG", 
  "VIRG", "ABRE_CHAVES", "FECHA_CHAVES", "CARACTER", "STRING", "OP_BIT_A_BIT_AND", "OP_BIT_A_BIT_OR", "OP_LOGICO", "OP_CASE", "ABRE_COLC", "FECHA_COLC", "PONTO", "OP_MINUS", "OP_MINUSMINUS", "OP_PONT", "OP_PLUSPLUS", "DOT_DOT","OP_NOT","OP_DIV", "FIM"
};

/*******************************************************
 nextSymbol(): 
 - avança o ponteiro da fita de entrada do AFD
 - devolve o simbolo lido na entrada
*******************************************************/
char nextSymbol (FILE* fin)
{
  int i, f, m;

  c=getc(fin);
  if(feof(fin)){
    return FIM;
  }
  
  if (c!='\t' && c!=' ' && c!='\n'){
    lexema[ilexema++]=c;
  }
  else{
    if(c=='\n')
      lines++;
  }
  
  if(isdigit(c))
    return DIGITO;
  else if(isalpha(c))
    return LETRA;
  else{
    i=0; f=nSymbols;
    while(i<=f){
       m=(i+f)/2;
       if(c==symbols[m])
	 break;
       else{
	 if(c<symbols[m])
	   f=m-1;
	 else
	   i=m+1;
       }
    }
    if (i<=f)
      return m;
    else
      return INVALIDO;
  }
}

/********************************************************************************
 isKeyword(): 
 - verifica se s eh uma palavra reservada
 - devolve o codigo da keyword s ou o codigo ID p/ indicar que s eh uma variavel.
*********************************************************************************/
char isKeyword (char *s)
{
  int i, f, m;
  int x;

  i=0; f=nKeywords-1;
  while(i<=f){
    m=(i+f)/2;
    if((x=strcmp(s,keywords[m]))>0){
      i=m+1;
    }
    else if(x<0){
      f=m-1;
    }
    else{
      return m;
    }
  }
  return ID;
}

/********************************************************************************
 lex(): 
 - realiza o processamento do AFD considerando o arquivo fin como fita de entrada
 - devolve codigo da palavra aceita pelo AFD (ou codigo de erro)
*********************************************************************************/
int lex()
{
  int estado_atual, prox_estado, next, t;

  ilexema =0;
  next = nextSymbol(fin);
  estado_atual=0;

  while((next != FIM) && (prox_estado=delta[estado_atual][next]) != -1){
    estado_atual=prox_estado;
    next=nextSymbol(fin);
  }

  if(next!=FIM){
    if(c!='\t' && c!='\n' && c!=' '){
      ungetc(c, fin);
      ilexema--;
    }
    if(c=='\n')
      currlines=lines-1;
    else
      currlines=lines;
  }
  else if(estado_atual==0){ /* next==FIM e nenhum simbolo processado */
    return FIM;
  }
  lexema[ilexema]='\0';
  ilexema=0;
  t=action[estado_atual];
  if(t==ID){
    return (isKeyword(lexema));
  }
  return t;
}
