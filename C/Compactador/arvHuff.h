#ifndef _ARVHUFF_H
#define _ARVHUFF_H

#include <stdio.h>
#include <stdlib.h>

#define HEOF 256

// Definicao da estrutura
typedef struct arvHuff ArvHuff;

// Ele cria um no vazio para a arvore
ArvHuff* arvHuff_criarNo();

// Ele cria um noi de HEOF para a arvore
ArvHuff* arvHuff_criarHEOF();

// Aqui ele cria a arvore a partir do tamanho e do vetor min-heap
ArvHuff* arvHuff_criarArv(int tamanhoHuff, ArvHuff* vetorHuff[]);

// Ele re-cria a arvore de Huffman para descompactacao do arquivo
ArvHuff* arvHuff_criarArvDescompactacao(ArvHuff* arvHuff, char vetor[], int* i, FILE* arquivo, unsigned int alfabeto[], int* posicaoAlfabeto, int tamanhoAlfabeto);

// Ele extrai um numero do vetor e reduz seu tamanho
ArvHuff* arvHuff_extrair(int* tamanhoHuff, ArvHuff* vetorHuff[]);

// Usado para colocar a letra no nó
void arvHuff_setLetra(ArvHuff* arv, unsigned int letra);

// Usado para colocar a frequencia no nó
void arvHuff_setFrequencia(ArvHuff* arv, int frequencia);

// Usado para aumentar a frequencia
void arvHuff_addFrequencia(ArvHuff* arv);

// Usado para colocar o pai do no
void arvHuff_setArvPai(ArvHuff* arvFilho, ArvHuff* arvPai);

// Usado para colocar o filho esquerdo do no
void arvHuff_setArvEsq(ArvHuff* arvPai, ArvHuff* arvFilhoEsq);

// Usado para colocar o filho direito do no
void arvHuff_setArvDir(ArvHuff* arvPai, ArvHuff* arvFilhoDir);

// Usado para pegar o pai do no
ArvHuff* arvHuff_getArvPai(ArvHuff* arvFilho);

// Usado para pegar o filho esquerdo do no
ArvHuff* arvHuff_getArvEsq(ArvHuff* arvPai);

// Usado para pegar o filho direito do no
ArvHuff* arvHuff_getArvDir(ArvHuff* arvPai);

// Usado para pegar a letra do no
unsigned int arvHuff_getLetra(ArvHuff* arv);

// Usado para pegar a frequencia do no
int arvHuff_getFrequencia(ArvHuff* arv);

// Libera toda a arvore de Huffman, atraves de um percurso de pos ordem
void arvHuff_free(ArvHuff* arv);

/* As seguintes funcoes sao para construir min-heap */

// Troca dois nos de lugar
void troca(ArvHuff *a, ArvHuff *b);

// Funcao usada para descer no min-heap
void desceMinHeap(int n, ArvHuff* H[], int i);

// Funcao construtora do min-heap
void constroiMinHeap(int n, ArvHuff* H[]);

// Por fim a funcao que constroi o min-heap e o ordena 
void heapsort(int n, ArvHuff* H[]);

#endif