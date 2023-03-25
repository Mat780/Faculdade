#include <stdio.h>

// Importação da biblioteca stdlib para o uso da função exit()
#include <stdlib.h>

int main(int argc, char const *argv[]){
    
    // Caso não haja parametros o programa irá imprimir o que é esperado como parametro
    if(argc == 1) {
        printf("wzip: arquivo1 [arquivo2 ...]\n");
        exit(1);
    }

    // Definição dos ponteiros de arquivo, além da criação do arquivo file.zip
    // Que é onde sera armazenado nossa compactação
    FILE *arquivo;
    FILE *arquivoZip = fopen("file.z", "wb");

    // Usaremos o contador como indexe de argv[] e para saber se já compactamos todos os arquivos
    int contador = 1;

    // Laço que irá executar até que acabe os arquivos passados para compactação
    while(contador < argc) {
        // Abertura do arquivo e armazenamento de seu ponteiro de leiutra em "arquivo"
        arquivo = fopen(argv[contador], "r+");

        // Se "arquivo" estiver apontando para NULL significa que o arquivo não conseguiu ser aberto
        // Portanto é impresso qual arquivo não podê ser aberto 
        // e por fim finaliza o programa com status 1 indicando erro
        if(arquivo == NULL) {
            printf("wzip: não é possível abrir o arquivo <%s>\n", argv[contador]);
            exit(1);
        }

        // Pegando o primeiro caractere do arquivo
        char caractere = getc(arquivo);
        char letraAtual;
        int qtdLetraAtual;

        // Laço que executa até o final da leitura do arquivo
        while(feof(arquivo) == 0) {
            // Define a letra atual com o caractere atual e contador de letraAtual em 0
            letraAtual = caractere;
            qtdLetraAtual = 0;

            // Laço que roda até que um caractere diferente do armazenado em "letraAtual" seja pego
            // Enquanto ele roda vai acrescentando a "qtdLetraAtual" para contar quantas vezes a letra foi repetida
            while(caractere == letraAtual) {
                qtdLetraAtual++;
                caractere = getc(arquivo);
            }

            // Condicional para descartar \n e EOF da compactação
            if(letraAtual != '\n' && letraAtual != EOF) {

                fwrite(&qtdLetraAtual, 1, sizeof(int), arquivoZip);
                fwrite(&letraAtual, 1, sizeof(char), arquivoZip);

                printf("%d%c", qtdLetraAtual, letraAtual);

            }
            
        }

        // Fecha o arquivo de leitura atual
        fclose(arquivo);

        // Adiciona-se ao contador para ele passar para o proximo arquivo
        // Ou encerrar o laço
        contador++;
    }

    // A fim de separar o print do wzip do proximo comando utilizo este "\n"
    printf("\n");

    // Caso o código corra bem, ele por fim saíra com status 0, indicando que ocorreu tudo bem
    exit(0);
}
