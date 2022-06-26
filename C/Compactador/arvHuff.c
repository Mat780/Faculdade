#include <stdio.h>
#include <stdlib.h>
#include "ArvHuff.h"

#define HEOF 256

struct arvHuff {
	unsigned int letra;
	int frequencia;
    
    ArvHuff* arvPai;
	ArvHuff* arvEsq;
    ArvHuff* arvDir;
};

// Ele cria um no vazio para a arvore
ArvHuff* arvHuff_criarNo() {
	ArvHuff* arv = (ArvHuff*)malloc(sizeof(struct arvHuff));
	arvHuff_setFrequencia(arv, 1);
	arvHuff_setArvPai(arv, NULL);
	arvHuff_setArvEsq(arv, NULL);
	arvHuff_setArvDir(arv, NULL);
    
	return arv;
}

// Ele cria um noi de HEOF para a arvore
ArvHuff* arvHuff_criarHEOF() {
	ArvHuff* arv = (ArvHuff*)malloc(sizeof(struct arvHuff));
	arvHuff_setLetra(arv, HEOF);
	arvHuff_setFrequencia(arv, 0);
	arvHuff_setArvPai(arv, NULL);
	arvHuff_setArvEsq(arv, NULL);
	arvHuff_setArvDir(arv, NULL);
    
	return arv;
}

// Aqui ele cria a arvore a partir do tamanho e do vetor min-heap
ArvHuff* arvHuff_criarArv(int tamanhoHuff, ArvHuff* vetorHuff[]) {

	while (tamanhoHuff > 1) {
		ArvHuff* novo = arvHuff_criarNo();
		ArvHuff* no1 = arvHuff_extrair(&tamanhoHuff, vetorHuff);
		ArvHuff* no2 = arvHuff_extrair(&tamanhoHuff, vetorHuff);

		if (arvHuff_getFrequencia(no1) <= arvHuff_getFrequencia(no2)) {
			arvHuff_setArvEsq(novo, no1);
			arvHuff_setArvDir(novo, no2); 
		} else {
			arvHuff_setArvEsq(novo, no2);
			arvHuff_setArvDir(novo, no1); 
		}
		arvHuff_setFrequencia(novo, arvHuff_getFrequencia(arvHuff_getArvEsq(novo)) + arvHuff_getFrequencia(arvHuff_getArvDir(novo)));
		vetorHuff[tamanhoHuff++] = novo;

	}

	return arvHuff_extrair(&tamanhoHuff, vetorHuff);
}

// Ele re-cria a arvore de Huffman para descompactacao do arquivo
ArvHuff* arvHuff_criarArvDescompactacao(ArvHuff* arvHuff, char vetor[], int* i, FILE* arquivo, unsigned int alfabeto[], int* posicaoAlfabeto, int tamanhoAlfabeto) {

	if ((*i) == 8) { // PROXIMO BYTE
		for (int i = 0; i < 8; i++) {
			fscanf(arquivo, "%c", &vetor[i]);
		}
		*i = 0;
	}

	if (vetor[*i] == '0' && *posicaoAlfabeto != tamanhoAlfabeto) {
		// DESCE ESQ
		ArvHuff* filho = arvHuff_criarNo();
		arvHuff->arvEsq = filho;
		filho->arvPai = arvHuff;
		(*i)++;

		arvHuff_criarArvDescompactacao(filho, vetor, i, arquivo, alfabeto, posicaoAlfabeto, tamanhoAlfabeto);
	} 

	if ((*i) == 8) { // PROXIMO BYTE
		for (int i = 0; i < 8; i++) {
			fscanf(arquivo, "%c", &vetor[i]);
		}

		*i = 0;
	}
	
	if (vetor[*i] == '1') {
		if (arvHuff->arvEsq != NULL && arvHuff->arvDir == NULL) {
			// DESCE DIR
			ArvHuff* filho = arvHuff_criarNo();
			arvHuff->arvDir = filho;
			filho->arvPai = arvHuff;
			(*i)++;

			arvHuff_criarArvDescompactacao(filho, vetor, i, arquivo, alfabeto, posicaoAlfabeto, tamanhoAlfabeto);

		} else if (arvHuff->arvPai != NULL) {
			// FOLHA
			arvHuff->letra = alfabeto[*posicaoAlfabeto];
			(*posicaoAlfabeto)++;
		}
	}

}

// Ele extrai um numero do vetor e reduz seu tamanho
ArvHuff* arvHuff_extrair(int* tamanhoHuff, ArvHuff* vetorHuff[]) {
	*tamanhoHuff = *tamanhoHuff - 1;
	ArvHuff* retornado = vetorHuff[*tamanhoHuff];
	vetorHuff[*tamanhoHuff] = NULL;
	
	return retornado;
}

// Usado para colocar a letra no nó
void arvHuff_setLetra(ArvHuff* arv, unsigned int letra) {
	arv->letra = letra;
}

// Usado para colocar a frequencia no nó
void arvHuff_setFrequencia(ArvHuff* arv, int frequencia) {
	arv->frequencia = frequencia;
}

// Usado para aumentar a frequencia
void arvHuff_addFrequencia(ArvHuff* arv) {
	arv->frequencia += 1;
}

// Usado para colocar o pai do no
void arvHuff_setArvPai(ArvHuff* arvFilho, ArvHuff* arvPai) {
	arvFilho->arvPai = arvPai;
}

// Usado para colocar o filho esquerdo do no
void arvHuff_setArvEsq(ArvHuff* arvPai, ArvHuff* arvFilhoEsq) {
	arvPai->arvEsq = arvFilhoEsq;
	if (arvFilhoEsq != NULL) arvHuff_setArvPai(arvFilhoEsq, arvPai);
}

// Usado para colocar o filho direito do no
void arvHuff_setArvDir(ArvHuff* arvPai, ArvHuff* arvFilhoDir) {
	arvPai->arvDir = arvFilhoDir;
    if (arvFilhoDir != NULL) arvHuff_setArvPai(arvFilhoDir, arvPai);
}

// Usado para pegar o pai do no
ArvHuff* arvHuff_getArvPai(ArvHuff* arvFilho) {
	return arvFilho->arvPai;
}

// Usado para pegar o filho esquerdo do no
ArvHuff* arvHuff_getArvEsq(ArvHuff* arvPai) {
	return arvPai->arvEsq;
}

// Usado para pegar o filho direito do no
ArvHuff* arvHuff_getArvDir(ArvHuff* arvPai) {
	return arvPai->arvDir;
}

// Usado para pegar a letra do no
unsigned int arvHuff_getLetra(ArvHuff* arv) {
	return arv->letra;
}

// Usado para pegar a frequencia do no
int arvHuff_getFrequencia(ArvHuff* arv) {
	return arv->frequencia;
}

// Libera toda a arvore de Huffman, atraves de um percurso de pos ordem
void arvHuff_free(ArvHuff* arv) {

	if(arv != NULL) {
		arvHuff_free(arv->arvEsq);
		arvHuff_free(arv->arvDir);
		free(arv);
	}
}

/* As seguintes funcoes sao para construir min-heap */

// Troca dois nos de lugar
void troca(ArvHuff *a, ArvHuff *b) {
    ArvHuff auxiliar;
    
    auxiliar = *a;
    *a = *b;
    *b = auxiliar;
}

// Funcao usada para descer no min-heap
void desceMinHeap(int n, ArvHuff* H[], int i) {
    int e = (2*i + 1), d = (2*i + 2), menor;

    if (e < n && arvHuff_getFrequencia(H[e]) < arvHuff_getFrequencia(H[i])) menor = e;
    else menor = i;

    if (d < n && arvHuff_getFrequencia(H[d]) < arvHuff_getFrequencia(H[menor])) menor = d;
    if (menor != i) {
        troca(H[i], H[menor]);
        desceMinHeap(n, H, menor);
    }
}

// Funcao construtora do min-heap
void constroiMinHeap(int n, ArvHuff* H[]) {
    for (int i = n/2 - 1; i >= 0; i--) {
        desceMinHeap(n, H, i);
    }
}

// Por fim a funcao que constroi o min-heap e o ordena 
void heapsort(int n, ArvHuff* H[]) {
	constroiMinHeap(n, H);

	for (int i = n - 1; i > 0; i--) {
		troca(H[0], H[i]);
		n--;
		desceMinHeap(n, H, 0);
	}
}
