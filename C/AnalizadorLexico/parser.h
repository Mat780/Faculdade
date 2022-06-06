#ifndef __PARSER_H
#define __PARSER_H
/*
 parser.h
 Autor: Edna A. Hoshino
 Em: junho de 2013
 Descreve a gramatica e as transicoes do automato com pilha que, respectivamente, gera e aceita um subconjunto dos programas da linguagem C.
*/
void match(int t);
char *parser();
void S();
void S_();
void Function();
void Type();
void Function_();
void B();
void C();
void E();
void T();
void F();
void E_();
void T_();
void F_CASE();
void CASE_();
void C_();
void EXP_CASE();
void QUEBRAR();

#endif
