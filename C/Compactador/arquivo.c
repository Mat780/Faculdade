#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "arvHuff.h"
#include "hash.h"
#include "arquivo.h"

/* Funcoes utilizadas para a compactacao */

// Funcao responsavel por criar um arquivo em binario
FILE* arquivo_criar(char* nomeArquivo) {
	FILE *arquivoBinario;

	arquivoBinario = fopen(nomeArquivo, "wb+");

	return arquivoBinario;
}

// Funcao incubida de converter o K e o alfabeto para binario
void arquivo_converterToBinario(unsigned int inteiro, FILE *arquivoBinario) {

	int i, c = 0, binario;

	char primeiroByte[9];
	char segundoByte[9];
	primeiroByte[8] = '\0';
	segundoByte[8] = '\0';

	for (i = 15; i >= 0; i--) {

		binario = inteiro >> i;

		if (i == 7) {
			c = 0;
		}

		if (binario & 1) {

			if (i < 8) { // Primeiro Byte
				primeiroByte[c] = '1';

			} else { // Segundo Byte
				segundoByte[c] = '1';
			}

		} else {

			if (i < 8) { // Primeiro Byte
				primeiroByte[c] = '0';

			} else { // Segundo Byte
				segundoByte[c] = '0';
			}

		}
		c++;
	}

	// Escrevendo os dois bytes no arquivo binario
	fputs(primeiroByte, arquivoBinario);
	fputs(segundoByte, arquivoBinario);
	
}

// Funcao encarregada de escrever um byte e, se estiver incompleto, preenche com zeros
void arquivo_putByte(char* vetor, FILE* arquivoBinario) {
	int tamanho = 0;
	
	while (vetor[tamanho] != '\0') tamanho++;

	while (tamanho != 8) {
		vetor[tamanho] = '0';
		tamanho++;
	}

	fputs(vetor, arquivoBinario);
	
}

/* Funcao responsavel por tentar criar um byte por meio de um codigo Huffman (str)
e a passagem de um vetor (que sera utilizado ate o final do codigo para a codificacao/compactacao.
Se o vetor estiver cheio (iteravel tem valor 8) ele e enviado para a funcao arquivo_putByte() */
void arquivo_putString(FILE* arquivoBinario, String* str, char* vetor, int* i) {
	char auxChar;
	
	for (; str != NULL; str = hash_stringGetProx(str)) {
		if (*i == 8) {
			arquivo_putByte(vetor, arquivoBinario);
			*i = 0;
			vetor[0] = '\0';
		}

		auxChar = hash_stringGetNumero(str);

		// Adiciona-se 48 por estarmos transformando de INT para CHAR
		vetor[*i] = auxChar + 48;
		
		vetor[*i + 1] = '\0';
		(*i)++;

	}
}

// Funcao incubida de compactar o texto. Ela apresenta chamada de outras funcoes para executar suas aplicacoes
void arquivo_compactar(FILE* arquivoBinario, FILE* arquivoTexto, HashTable* ht, ArvHuff* arv) {
	int i, tamanho = 0;
	
	for (HashTable* aux = ht; hash_getProx(aux) != NULL; aux = hash_getProx(aux)) tamanho++;

	// Sera chamado arquivo_converterToBinario para escrever o K
	arquivo_converterToBinario(tamanho, arquivoBinario);

	// Sera chamado arquivo_converterToBinario para escrever todo o alfabeto (por isso o laco)
	for (HashTable* aux = ht; hash_getProx(aux) != NULL; aux = hash_getProx(aux)) {
		arquivo_converterToBinario(hash_getLetraASCII(aux), arquivoBinario);
	}

	String* strAux = NULL;
	
	// Inicializacao do vetor que sera responsavel por 1 byte por vez
	char vetor[9];
	vetor[0] = '\0';
	i = 0;

	// Sera feito o percurso de pre-ordem 
	for (HashTable* aux = ht; hash_getProx(aux) != NULL; aux = hash_getProx(aux)) {
		String* str = hash_getString(aux);
		String* strAuxB = str;
		
		// Analisa se algum bit e diferente por meio de uma comparacao para escrever o percurso
		if (strAux != NULL) { 
			while (hash_stringGetNumero(strAux) == hash_stringGetNumero(strAuxB)) {
				strAux = hash_stringGetProx(strAux);
				strAuxB = hash_stringGetProx(strAuxB);
			}
		}

		arquivo_putString(arquivoBinario, strAuxB, vetor, &i);
		strAux = str;
	}

	// Finalizando a pre ordem, colocamos o 1 a mais para representar o fim da pre ordem
	if (i == 8) {
		arquivo_putByte(vetor, arquivoBinario);
		i = 1;
		vetor[0] = '1';
		vetor[1] = '\0';
	} else {
		vetor[i++] = '1';
	}

	// Essa parte ira ver a letra do alfabeto do texto que queremos compactar e colocar o seu percurso
	HashTable* aux;
	char c;
	while (fscanf(arquivoTexto, "%c", &c) != EOF) {
		for (aux = ht; hash_getLetraASCII(aux) != c; aux = hash_getProx(aux));
		String* str = hash_getString(aux);
		arquivo_putString(arquivoBinario, str, vetor, &i);
	}

	// Por fim pegamos e inserimos artificialmente o nosso HEOF para marcarmos o final do arquivo
	for (aux = ht; hash_getLetraASCII(aux) != 256; aux = hash_getProx(aux));
	String* str = hash_getString(aux);
	arquivo_putString(arquivoBinario, str, vetor, &i);
	arquivo_putByte(vetor, arquivoBinario);

	// Liberacao de toda memoria alocada
	hash_free(ht);
	arvHuff_free(arv);
}

/* Funcoes utilizadas para a descompactacao */

// Funcao responsavel por pegar dois bytes e coloca em um vetor de dois bytes
void arquivo_getTwoBytes(FILE* arquivoBinario, char vetorTwoBytes[]) {
	char c;

	for (int i = 0; i < 16; i++) {
		fscanf(arquivoBinario, "%c", &c);

		if (i <= 7) {
			vetorTwoBytes[i+8] = c;

		} else if (i >= 8) {
			vetorTwoBytes[i-8] = c;

		}
	}
}

// Funcao responsavel por pegar um byte e colocar no vetor de um byte
void arquivo_getOneByte(FILE* arquivoBinario, char vetorOneByte[]) {
	for (int i = 0; i < 8; i++) {
		fscanf(arquivoBinario, "%c", &vetorOneByte[i]);
	}
}

// Funcao encarregada de converter um binario para um int
int arquivo_binaryToInteger(char vetor[]) {
	int num = 0, potencia = 1;

	for (int i = 15; i >= 0; i--) {
		num += (vetor[i] - 48) * potencia;
		potencia *= 2;
	}
	
	return num;
}

// Funcao designada de descompactar o texto. Ela apresenta chamada de outras funcoes para executar suas aplicacoes
void arquivo_descompactar(FILE* arquivoBinario, FILE* arquivoTexto) {

	// Inicializacao do vetor responsavel pela parte que contem 2 bytes cada informacao
	int i = 0;
	char vetorTwoBytes[17];
	vetorTwoBytes[16] = '\0';

	// Identifica o valor do K
	arquivo_getTwoBytes(arquivoBinario, vetorTwoBytes);
	int k = arquivo_binaryToInteger(vetorTwoBytes);

	unsigned int alfabeto[k+1];
	alfabeto[k] = '\0';

	// Converte o binario para a letra do alfabeto
	for (i = 0; i < k; i++) {
		arquivo_getTwoBytes(arquivoBinario, vetorTwoBytes);
		alfabeto[i] = arquivo_binaryToInteger(vetorTwoBytes);
	}

	/* A partir daqui, o codigo e encarregado de analisar o resto dos bits/bytes
	e fazer o processo contrario da compactacao para descompactar*/

	// Inicializa o vetor responsavel por apenas 1 byte
	char vetorOneByte[9];
	vetorOneByte[8] = '\0';

	arquivo_getOneByte(arquivoBinario, vetorOneByte);

	// Cria a raiz da arvore de Huffman
	ArvHuff* arvHuff = arvHuff_criarNo();

	int posicaoAlfabeto = 0;
	i = 0;

	// Chama a funcao que ira recriar a arvore huffman baseado no percurso de pre ordem e no alfabeto que ja esta organizado em pre ordem
	arvHuff_criarArvDescompactacao(arvHuff, vetorOneByte, &i, arquivoBinario, alfabeto, &posicaoAlfabeto, k);
	i++;


	ArvHuff* arvAux = arvHuff;
	char c;
	while(1) {

		// Caso o vetor de 1 byte tenha sido totalmente percorrido, pega-se outro byte
		if (i == 8) {
			arquivo_getOneByte(arquivoBinario, vetorOneByte);
			i = 0;
		}

		// Entao pegamos 1 bit desse byte para analize
		c = vetorOneByte[i++];

		// Caso for 0 ele desce a esquerda e se for 1 ele desce a direita
		if (c == '0') {
			arvAux = arvHuff_getArvEsq(arvAux);
		} else if(c == '1') {
			arvAux = arvHuff_getArvDir(arvAux);
		}

		// Caso ele chegue a uma folha
		if (arvHuff_getArvEsq(arvAux) == NULL && arvHuff_getArvDir(arvAux) == NULL) {
			// Entao pega-se o numero do no, ou seja o codigo ascii da letra
			unsigned int num = arvHuff_getLetra(arvAux);

			// Caso o codigo seja HEOF ele finaliza o while e por fim a descompactacao
			if (num == 256) {
				break;
			
			// Senao ele pega o codigo ascii e transforma em CHAR, em sequencia o registra no arquivo de texto descompactado
			} else {
				fputc(num, arquivoTexto);
				arvAux = arvHuff;

			}
		}
	}

	// Liberacao de toda memoria alocada
	arvHuff_free(arvHuff);

}

