/**************************************************
*
* Nome dos estudantes:
* 	Giovanna Rodrigues Mendes (2021.1904.032-7) e 
* 	Matheus Felipe Alves Duraes (2021.1904.008-4)
*
* Trabalho 2
* Professor(a): Marco Aurelio Stefanes
*
*/

#include <stdio.h>
#include <stdlib.h>
#include "arvHuff.h"
#include "hash.h"
#include "arquivo.h"

int main(int argc, char *argv[]) {

	/* Aqui esta a parte do codigo que e responsavel por verificar uma compactacao */
	if (argc == 4 && *argv[1] == 'c') {
		struct arvHuff* vetorHuff[257];
		int tamanhoHuff = 0;
		char c;

		FILE* arquivoTexto;
		arquivoTexto = fopen(argv[2], "r");
		
		// Pega a frequencia das letras percorrendo todo o arquivo de texto
		while (fscanf(arquivoTexto, "%c", &c) != EOF) {
			for (int i = 0; i <= tamanhoHuff; i++) {
				
				/* Caso o i ja tenha percorrido o vetor e chegue ao final 
				*  ele adiciona a letra atual na ultima posicao do vetor
				*  e encerra o for.
				*/
				if (i == tamanhoHuff) {
					vetorHuff[tamanhoHuff] = arvHuff_criarNo();
					arvHuff_setLetra(vetorHuff[tamanhoHuff++], c);
					i = tamanhoHuff + 1;

				/* Caso ele ache a letra atual dentro do vetor entao ele
				*  aumenta a frequencia daquela letra.
				*/
				} else if (arvHuff_getLetra(vetorHuff[i]) == c) {
					arvHuff_addFrequencia(vetorHuff[i]);
					i = tamanhoHuff + 1;
				}

			}
		}
		
		// Fecha o arquivo de texto
		fclose(arquivoTexto);
		
		// Ele adiciona ao vetor o HEOF no final
		vetorHuff[tamanhoHuff++] = arvHuff_criarHEOF();

		// Aqui ele monta o min-heap e ja faz o heapsort
		heapsort(tamanhoHuff, vetorHuff);
		
		// Aqui criamos a arvore Huffman com o tamanho do vetor juntamente ao proprio vetor
		struct arvHuff* arvoreHuff = arvHuff_criarArv(tamanhoHuff, vetorHuff);

		// Entao criamos uma tabela Hash e uma String vazia
		HashTable* ht = hash_criar();
		String* str = hash_stringCriarVazio();

		/* A tabela hash agora sera preenchida com as informacoes da arvore de Huffman 
		*  fazendo com que a tabela se alinhe a pre ordem da arvore de Huffman, tendo
		*  tanto o percurso necessario quanto a letra que o percurso gera
		*/
		hash_preOrdem(arvoreHuff, ht, str, -1);

		// Ele cria o arquivo binario (compactado) para podermos escrever
		FILE* arquivoBinario = arquivo_criar(argv[3]);

		/* Entao ele abre novamente o arquivo de texto para podermos colocar as informacoes 
		*  necessarias dentro do arquivo compactado
		*/
		arquivoTexto = fopen(argv[2], "r");

		// Por fim ele chama uma funcao que fara a compactacao do arquivo de texto
		arquivo_compactar(arquivoBinario, arquivoTexto, ht, arvoreHuff);

		// Fecha os dois arquivos
		fclose(arquivoBinario);
		fclose(arquivoTexto);

		printf("O arquivo %s foi compactado para o arquivo %s\n", argv[2], argv[3]);

	/* Aqui esta a parte do codigo que e responsavel por verificar uma descompactacao */
	} else if (argc == 4 && *argv[1] == 'd') {	

		// Abre o arquivo binario para a leitura
		FILE* arquivoBinario = fopen(argv[2], "rb");

		// Abre o arquivo de texto para escrita ao descompactar
		FILE* arquivoTexto = fopen(argv[3], "w+");
		
		// Chama a funcao de descompactacao
		arquivo_descompactar(arquivoBinario, arquivoTexto);

		// Fecha os dois arquivos
		fclose(arquivoBinario);
		fclose(arquivoTexto);

		printf("O arquivo %s foi descompactado para o arquivo %s\n", argv[2], argv[3]);

	// Caso os argumentos sejam insuficientes ou estejam em excesso
	} else if (argc != 4) {
		printf("ERRO: Numero de argumentos invalido");

	// Qualquer outra coisa sera tratado como uma operacao invalida nao reconhecida
	} else {
		printf("ERRO: Tipo de operacao nao reconhecida pelo sistema");
	}
	
}
