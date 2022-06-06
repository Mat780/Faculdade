/*
 lex2.c : analisador lexico (automato finito sem tabela de transições. As transições estão codificadas no lex())
 Autor: Edna A. Hoshino
 Em: novembro/2021
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

// Simbolos validos na entrada (alfabeto do AFD):
//  '\t', '\n', ' ', '!', '"', '&', '\'', '(', ')', '*',
// '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '[', ']', '{', '|', '}'

char *keywords[] = { /* palavras reservadas da linguagem */
  "break", "case","char", "default", "do", "double", "else", "float","for", "if", "int", "main", "return", "struct", "switch", "void", "while"
};
// keyword = 0 - 16


char *terminalName[] = { /* nome dos terminais aceitos pelo automato */
  "break", "case", "char", "default", "do", "double", "else", "float","for", "if", "int", "main", "return", "struct", "switch", "void", "while", "id", "num", "OP_ATRIB", "OP_ADIT", "OP_MULT", "OP_REL", "ABRE_PARENT", "FECHA_PARENT", "PONTO_VIRG", 
  "VIRG", "ABRE_CHAVES", "FECHA_CHAVES", "CARACTER", "STRING", "OP_BIT_A_BIT_AND", "OP_BIT_A_BIT_OR", "OP_LOGICO", "OP_CASE", "ABRE_COLC", "FECHA_COLC", "PONTO", "OP_MINUS", "OP_MINUSMINUS", "OP_PONT", "OP_PLUSPLUS", "DOT_DOT","OP_NOT","OP_DIV", "FIM"
};


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
  estado_atual=0;

  do{
    c=getc(fin);
    lexema[ilexema++]=c;
    lexema[ilexema]='\0';
    switch(estado_atual){
    case 0:
      switch(c){
      case '\n':
        lines++;
      case '\t':
      case ' ':
        ilexema--;
        break;
      case '!':
        estado_atual = 1;
        break;
      case '&':
        estado_atual = 2;
        break;
      case '(':
        return ABRE_PARENT;
      case ')':
        return FECHA_PARENT;
      case '*':
        return OP_MULT;
      case '/':
        return OP_DIV;
      case '+':
        return OP_ADIT;
      case '-':
        return OP_MINUS;
      case '.':
        return PONTO;
      case ':':
        return DOT_DOT;
      case ';':
        return PONTO_VIRG;
      case '<':
      case '>':
        estado_atual = 3;
        break;
      case '=':
        estado_atual = 4;
        break;
      case '?':
        return OP_CASE;
      case '[':
        return ABRE_COLC;
      case ']':
        return FECHA_COLC;
      case '{':
        return ABRE_CHAVES;
      case '}':
        return FECHA_CHAVES;
      case '|':
        estado_atual = 5;
        break;
      default:
        if(isdigit(c)){
          estado_atual = 6;
        }
        else if(isalpha(c)){
          estado_atual = 7;
        }
        else if(!feof(fin))
          return ERRO_LEXICO;
        break;
      }
      break;
    case 1:
      if(c=='='){
        return OP_REL;
      }
      else{
        ungetc(c,fin);
        lexema[--ilexema]='\0';
        return OP_NOT;
      }      
    case 2:
        if(c=='&'){
          return OP_LOG;
        }
        else{
          ungetc(c,fin);
          lexema[--ilexema]='\0';
          return OP_BIT_AND;
        }
    case 3:
        if(c!='='){
          ungetc(c,fin);
          lexema[--ilexema]='\0';
        }
        return OP_REL;
    case 4:
        if(c=='='){
          return OP_REL;
        }
        else{
          ungetc(c,fin);
          lexema[--ilexema]='\0';
          return OP_ATRIB;
        }
    case 5:
        if(c=='|'){
          return OP_REL;
        }
        else{
          ungetc(c,fin);
          lexema[--ilexema]='\0';
          return OP_BIT_OR;
        }
    case 6:
      if(!isdigit(c)){
        ungetc(c,fin);
        lexema[--ilexema]='\0';
        return NUM;
      }
      break;
    case 7:
      if(!isalpha(c) && !isdigit(c)){
        ungetc(c,fin);
        lexema[--ilexema]='\0';
        return (isKeyword(lexema));
      }
      break;     
    }    
  } while(!feof(fin));
  if(estado_atual==0){ /* fim de arquivo */
    lexema[0]='\0';
    return FIM;
  }
  return ERRO_LEXICO;
}
