#ifndef _ARQUIVO_H
#define _ARQUIVO_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "arvHuff.h"
#include "hash.h"

// Funcao responsavel por criar um arquivo em binario
FILE* arquivo_criar(char* nomeArquivo);

// Funcao incubida de converter o K e o alfabeto para binario
void arquivo_converterToBinario(unsigned int letra, FILE* arquivo);

// Funcao encarregada de escrever um byte e, se estiver incompleto, preenche com zeros
void arquivo_putByte(char* vetor, FILE* arquivo);

/* Funcao responsavel por tentar criar um byte por meio de um codigo Huffman (str)
e a passagem de um vetor (que sera utilizado ate o final do codigo para a codificacao/compactacao.
Se o vetor estiver cheio (iteravel tem valor 8) ele e enviado para a funcao arquivo_putByte() */
void arquivo_putString(FILE* arquivoBinario, String* str, char* vetor, int* i);

// Funcao incubida de compactar o texto. Ela apresenta chamada de outras funcoes para executar suas aplicacoes
void arquivo_compactar(FILE* arquivoBinario, FILE* arquivoTexto, HashTable* ht, ArvHuff* arv);

// Funcao designada de descompactar o texto. Ela apresenta chamada de outras funcoes para executar suas aplicacoes
void arquivo_descompactar(FILE* arquivoBinario, FILE* arquivoTexto);

// Funcao responsavel por pegar dois bytes e coloca em um vetor de dois bytes
void arquivo_getTwoBytes(FILE* arquivo, char vetorTwoBytes[]);

// Funcao responsavel por pegar um byte e colocar no vetor de um byte
void arquivo_getOneByte(FILE* arquivo, char vetorOneByte[]);

// Funcao encarregada de converter um binario para um int
int arquivo_binaryToInteger(char vetor[]);

#endif