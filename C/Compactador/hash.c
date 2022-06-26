#include <stdio.h>
#include <stdlib.h>
#include "arvHuff.h"
#include "hash.h"

// Definicao das estruturas
struct string { 
	int numero; 
	String* prox; 
	String* ant; 
};

struct hashTable { 
	String* string; 
	unsigned int letraASCII; 
	HashTable* prox;
};

// Criacao de uma string iniciando com um numero
String* hash_stringCriar(int numero) {
	String* str = (String*)malloc(sizeof(struct string));
	str->numero = numero;
	str->ant = NULL;
	str->prox = NULL;
	return str;
}

// Criacao de uma string vazia
String* hash_stringCriarVazio() {
	String* str = (String*)malloc(sizeof(struct string));
	str->ant = NULL;
	str->prox = NULL;
	return str;
}

// Concatenar uma string com outra
void hash_stringAdd(String* strTopo, String* strNova) {
	String* strTemp = strTopo;

	while (strTemp->prox != NULL) {
		strTemp = strTemp->prox;
	}

	strTemp->prox = strNova;
	strNova->ant = strTemp;
}

// Remove o ultimo carectere da string 
void hash_stringRemove(String* str) {
	
	String *strAux = str, *novoTopo = str;

	while (strAux->prox != NULL) {
		novoTopo = strAux;
		strAux = strAux->prox;
	}

	novoTopo->prox = NULL;
	free(strAux);

}

// Copia uma string inteira e devolve o ponteiro da nova string
String* hash_stringCopia(String* str) {
	String* novaBase = hash_stringCriar(str->numero); 
	String *tempTopo, *tempMeio;
	str = str->prox;
	
	if (str != NULL) {
		tempTopo = hash_stringCriar(str->numero);
		novaBase->prox = tempTopo;
		tempTopo->ant = novaBase;
		str = str->prox;

		while (str != NULL) {
			tempMeio = tempTopo;
			tempTopo = hash_stringCriar(str->numero);
			tempMeio->prox = tempTopo;
			tempTopo->ant = tempMeio;
			str = str->prox;
		}
	}

	return novaBase;

}

// Pega o ponteiro da proxima string
String* hash_stringGetProx(String* str) {
	return str->prox;
}

// Pega o caractere da string atual
int hash_stringGetNumero(String* str) {
	return str->numero;
}

// Imprime toda a string tendo como base a que for passada como parametro
void hash_stringPrint(String* str) { // DEBUG
	
	while (str != NULL) {
		printf("%d", str->numero);
		str = str->prox;
	}
	printf("\n");

}

// Criacao da tabela hash
HashTable* hash_criar() {
	HashTable* ht = (HashTable*)malloc(sizeof(struct hashTable));
	ht->string = NULL;
	ht->letraASCII = -1;
	ht->prox = NULL;
}

// Ele armazena o ponteiro da proxima tabela, na tabela atual
void hash_setProx(HashTable* atual, HashTable* posterior) {
	atual->prox = posterior;
}

// Ele retorna o ponteiro da proxima tabela
HashTable* hash_getProx(HashTable* atual) {
	return atual->prox;
}

// Ele retorna o codigo ASCII da letra armazenada na tabela atual
unsigned int hash_getLetraASCII(HashTable* atual) {
	return atual->letraASCII;
}

// Ele retorna a string da tabela atual
String* hash_getString(HashTable* atual) {
	return atual->string;
}

// Ele monta toda a tabela hash baseado na pre ordem da arvore Huffman
void hash_preOrdem(ArvHuff* arv, HashTable* hash, String* str, int direcao) {

	if (arv != NULL) {

		if (direcao == 0 || direcao == 1) {
			if(str == NULL) {
				str = hash_stringCriar(direcao);
			} else {
				String* nova = hash_stringCriar(direcao);
				hash_stringAdd(str, nova);
			}
		}

		hash_preOrdem(arvHuff_getArvEsq(arv), hash, str, 0);
		hash_preOrdem(arvHuff_getArvDir(arv), hash, str, 1);

		if (arvHuff_getArvEsq(arv) == NULL && arvHuff_getArvDir(arv) == NULL) {
			HashTable* hashAux = hash;
			while (hashAux->prox != NULL) {
				hashAux = hashAux->prox;
			}

			
			hashAux->letraASCII = arvHuff_getLetra(arv);
			hashAux->string = hash_stringCopia(str->prox);
			hashAux->prox = hash_criar();

		}

		hash_stringRemove(str);
	}

}

// Libera da memoria toda a hashTable e suas strings
void hash_free(HashTable* ht) {
	String *aux, *str;
	str = ht->string;

	HashTable* temp;
	while (ht != NULL) {
		
		while (str != NULL) {
			aux = str;
			str = str->prox;
			free(aux);
		}

		temp = ht;
		ht = ht->prox;
		free(temp);
	}
}