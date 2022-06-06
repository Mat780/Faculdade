/*
 parser.c : analisador sintatico (exemplo de automato com pilha)
 Autor: Edna A. Hoshino

Gramática da linguagem aceita pelo parser:

 S  -> Function S_ 
 S_ -> Function S_ 
      | epsilon
 Function -> Type Function_
 Type -> void 
       | int 
       | float
 Function_ -> main() { B } 
            | id() { B }
 B -> C B 
      | epsilon
 C -> id = E ; 
      | while (E) C 
      | { B }
 E -> TE'
 E'-> +TE' 
      | epsilon
 T -> FT'
 T'-> *FT' // * é só um operador da linguagem
      | epsilon
 F -> (E) 
      | id 
      | num

 A saida do analisador apresenta o total de linhas processadas e uma mensagem de erro ou sucesso. 
 Atualmente, nao ha controle sobre a coluna e a linha em que o erro foi encontrado.
*/

/*------------------------------- IMPLEMENTACAO DO COMANDO CASE E BREAK -------------------------------*/

/*Grupo: Loucos, Fracos e Atormentados (LFA)*/
/*Autores: 
  
  FERNANDA DAS NEVES MERQUEADES SANTOS
  GIOVANNA RODRIGUES MENDES
  MATHEUS FELIPE ALVES DURÃES
  RONEY FELIPE DE OLIVEIRA MIRANDA
  NOTA: 10, não precisa nem corrigir!

*/

#include <stdio.h>
#include <stdlib.h>
#include "lex.h"
#include "parser.h"

/* variaveis globais */
int lookahead;
FILE *fin;

/* Exige que o próximo terminal seja t e avança o ponteiro da fita de entrada (i.e., pega o próximo terminal) */
void match(int t) {

  if (lookahead == t) {
    
    lookahead = lex();
    
  }
  else {
    
    printf("\nErro(linha=%d): token %s (cod=%d) esperado.## Encontrado \"%s\" ##\n", lines, terminalName[t], t, lexema);
    exit(1);
     
  }
}

// S  -> Function S_ 
void S() {
  
  Function();
  S_();
  
}

/* S_ -> Function S_ 
      | epsilon
*/
void S_() {

  if (lookahead == INT || lookahead == FLOAT || lookahead == VOID) {

    Function();
    S_();
    
  }

}

// Function -> Type Function_
void Function() {

  Type();
  Function_();

}

/* Type -> void 
       | int 
       | float
*/
void Type() {  

  if (lookahead == INT) {
    
    match(INT);

  }

  else if (lookahead == FLOAT) {

    match(FLOAT);

  }

  else {
    
    match(VOID);
    
  }

}

/* Function_ -> main() { B } 
            | id() { B }
*/
void Function_() {

  if (lookahead == MAIN) {

    match(MAIN);
    match(ABRE_PARENT);
    match(FECHA_PARENT);
    match(ABRE_CHAVES);
    B();
    match(FECHA_CHAVES);

  }

  else {

    match(ID);
    match(ABRE_PARENT);
    match(FECHA_PARENT);
    match(ABRE_CHAVES);
    B();
    match(FECHA_CHAVES);

  }

}

/* B -> C B 
      | epsilon
*/
void B() {
  
  if (lookahead == ID || lookahead == WHILE || lookahead == ABRE_CHAVES || lookahead == SWITCH) {

    C();
    B();

  }

}
/*
 C -> id = E ; 
      | while (E) C 
      | switch (C_) { F_CASE }
      | { B }
*/
void C() {

   if (lookahead == ID) { //id = E ;
    
    match(ID);
    match(OP_ATRIB);
    E();
    match(PONTO_VIRG);

  }

  else if (lookahead == WHILE) {

    match(WHILE);
    match(ABRE_PARENT);
    E();
    match(FECHA_PARENT);
    C();

  }

  else if (lookahead == SWITCH) {

    match(SWITCH);
    match(ABRE_PARENT);
    C_(); 
    match(FECHA_PARENT);
    
    match(ABRE_CHAVES);
    F_CASE(); 
    match(FECHA_CHAVES);

  }

  else {
    match(ABRE_CHAVES);
    B();
    match(FECHA_CHAVES);

  }

}
// E-> T E_
void E() {

  T();
  E_();

}
// T -> FT'
void T() {

  F();
  T_();

}
// E_ -> + T E_ | epsilon
void E_() {
  if (lookahead == OP_ADIT) {

    match(OP_ADIT);
    T();
    E_();

  }

}
/* T'-> *FT' 
      | epsilon
*/
void T_() {

  if (lookahead == OP_MULT) {

    match(OP_MULT);
    F();
    T_();

  }

}
/*
 F -> (E), 
      | id, 
      | num
*/
void F(){

  if (lookahead == ABRE_PARENT) {

    match(ABRE_PARENT);
    E();
    match(FECHA_PARENT);

  }

  else {

    if (lookahead==ID){
      match(ID);

    }

    else {

      match(NUM);
    }
  }

}

/* F_CASE ->   case (C_): CASE_ */ 
/*           | case C_: CASE_   */
/*           | default: C       */
/*           | Epsilon          */
/* Producao incubida por analisar o case, tanto o termo quanto a condicao, ou default*/
void F_CASE() {
  
  if (lookahead == CASE) {

    match(CASE);
    
    /* Se possui um abre parenteses inicial na condicao de case */
    if(lookahead == ABRE_PARENT) {

      match(ABRE_PARENT);
      C_();
      match(FECHA_PARENT);

    /* Senao pegue somente a condicional para o case */
    } else {

      C_();

    }

    match(DOT_DOT);
    CASE_();

  } 

  else if (lookahead == DEFAULT) {

    match(DEFAULT);
    match(DOT_DOT);
    C();

    QUEBRAR();

  }

}

/* CASE_ ->   F_CASE            */
/*          | QUEBRAR F_CASE    */
/*          | C QUEBRAR F_CASE  */
/* Producao responsavel por ler o corpo do case, ela não analisa o termo CASE responsavel pela chamada de 
F_case() e nem a condicao de tal. */
void CASE_() {

  /*Se um novo termo CASE for encontrado, a producao F_case() deve ser conjurada para que a analise seja feita. */
  if (lookahead == CASE) {

    F_CASE();

  } 
  
  /* Se ele encontrar um BREAK entao chamara a producao QUEBRAR() em sequencia F_CASE() */
  else if (lookahead == BREAK) {

    QUEBRAR();
    F_CASE();

  } 
  
  /* Se nenhuma das condicoes for atendida entao havera um corpo,
    depois de verificar o corpo chamara a producao QUEBRAR() em sequencia F_CASE() */
  else {

    C();

    QUEBRAR();

    F_CASE();

  }

}

/* C_ ->  id EXP_CASE       */
/*      | num EXP_CASE      */
/* Producao responsavel por verificar se o que foi lido e uma variavel ou um valor real.*/
void C_() {

  /* Se lookahead for igual a uma variavel */
  if (lookahead == ID) {

      match(ID);
      EXP_CASE();

  }
  /* Se lookahead for igual a um valor real. */
  else {

    match(NUM);
    EXP_CASE();

  }

}

/* EXP_CASE ->   +C_       */      
/*             | *C_       */
/*             | -C_       */
/*             | /C_       */
/*             | Epsilon   */
/* Producao responsavel por verificar os operando */
void EXP_CASE() {

  if (lookahead == OP_ADIT) {

    match(OP_ADIT);
    C_();

  } 
  
  else if (lookahead == OP_MULT) {

    match(OP_MULT);
    C_();

  } 
  
  else if (lookahead == OP_MINUS) {

    match(OP_MINUS);
    C_();

  } 
  
  else if (lookahead == OP_DIV) {

    match(OP_DIV);
    C_();

  }
  
}

/* QUEBRAR -> break;    */
/*           | Epsilon  */
/* Producao responsavel por verificar todos os breaks compilados*/
void QUEBRAR() {

  if(lookahead == BREAK) {

    match(BREAK);
    match(PONTO_VIRG);

  }

}    


/*******************************************************************************************
 parser(): 
 - efetua o processamento do automato com pilha AP
 - devolve uma mensagem para indicar se a "palavra" (programa) estah sintaticamente correta.
********************************************************************************************/
char *parser () {

   lookahead = lex(); // inicializa lookahead com o primeiro terminal da fita de entrada (arquivo)

   S(); // chama a variável inicial da gramática.

   if(lookahead == FIM)
      return("Programa sintaticamente correto!");
   else
      return("Fim de arquivo esperado");

}

int main(int argc, char**argv) {

  if(argc < 2) {

    printf("\nUse: compile <filename>\n");
    return 1;

  }

  else {

    printf("\nAnalisando lexica e sintaticamente o programa: %s", argv[1]);
    fin=fopen(argv[1], "r");

    if(!fin) {

      printf("\nProblema na abertura do programa %s\n", argv[1]);
      return 1;

    }
    // chama o parser para processar o arquivo de entrada
    printf("\nTotal de linhas processadas: %d\nResultado: %s\n", lines, parser());
    fclose(fin);
    return 0;

  }

}

