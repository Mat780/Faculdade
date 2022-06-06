/*
  avaliaLex.c
  Faz chamadas sucessivas ao lex() para reconhecer todas as palavras validas pelo automato
  Faça:
     gcc avaliaLex.c lex.c -o avaliaLex

*/
#include "lex.h"
#include <stdlib.h>
FILE *fin;

int main(int argc, char **argv){
  int t;

  if(argc<2){
    printf("\nUse: lex <file>\n");
    return 1;
  }
  fin=fopen(argv[1], "r");
  if(!fin){
    printf("\nProblema na abertura do arquivo %s\n", argv[1]);
    return 1;   
  }
  do{
    t = lex();
    if(t==ERRO_LEXICO){
      printf("\nNa linha %3d encontrado um simbolo invalido: \t(codigo %d): \t[%s]", lines, t, lexema);
    }
    else
      printf("\nNa linha %3d encontrado token: %15s \t(codigo %d): \t[%s]", lines, terminalName[t], t, lexema);
  }while (t!=FIM);
  puts("");
  fclose(fin);
  return 0;
}

