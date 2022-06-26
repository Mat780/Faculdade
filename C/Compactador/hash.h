#ifndef _HASH_H
#define _HASH_H

#include <stdio.h>
#include <stdlib.h>
#include "arvHuff.h"

// Definicao das estruturas
typedef struct string String;
typedef struct hashTable HashTable;

// Criacao de uma string iniciando com um numero
String* hash_stringCriar(int numero);

// Criacao de uma string vazia
String* hash_stringCriarVazio() ;

// Concatenar uma string com outra
void hash_stringAdd(String* strTopo, String* strNova);

// Remove o ultimo carectere da string 
void hash_stringRemove(String* strTopo);

// Copia uma string inteira e devolve o ponteiro da nova string
String* hash_stringCopia(String* str);

// Pega o ponteiro da proxima string
String* hash_stringGetProx(String* str);

// Pega o caractere da string atual
int hash_stringGetNumero(String* str);

// Imprime toda a string tendo como base a que for passada como parametro
void hash_stringPrint(String* str);

// Criacao da tabela hash
HashTable* hash_criar();

// Ele armazena o ponteiro da proxima tabela, na tabela atual
void hash_setProx(HashTable* atual, HashTable* posterior);

// Ele retorna o ponteiro da proxima tabela
HashTable* hash_getProx(HashTable* atual);

// Ele retorna o codigo ASCII da letra armazenada na tabela atual
unsigned int hash_getLetraASCII(HashTable* atual);

// Ele retorna a string da tabela atual
String* hash_getString(HashTable* atual);

// Ele monta toda a tabela hash baseado na pre ordem da arvore Huffman
void hash_preOrdem(ArvHuff* arv, HashTable* hash, String* str, int direcao);

// Libera da memoria toda a hashTable e suas strings
void hash_free(HashTable* ht);

#endif