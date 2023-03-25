#include <stdio.h>

// Importação da biblioteca stdlib para o uso da função:
// exit()
#include <stdlib.h>

int main(int argc, char const *argv[]){

    // Caso não seja passado nenhum parametro
    // Será impresso os parametros que o programa espera receber
    // E sair com status 1 indicando que ouve erro    
    if(argc == 1) {
        printf("wunzip: arquivo1 [arquivo2 ...]\n");
        exit(1);
    }

    // Definição dos ponteiros dos arquivos
    // Juntamente com a criação e abertura do arquivo descompactado chamado de "Unzip.txt"
    FILE* arquivoBinario;
    FILE* arquivoUnzip = fopen("Unzip.txt", "w+");

    // Inicialização do contador que será usado como indexe de argv[]
    // E como parametro controle do laço a seguir
    int contador = 1;

    // O laço irá executar até que todos os arquivos sejam descompactados
    while(contador < argc) {
        // Abertura do arquivo compactado
        arquivoBinario = fopen(argv[contador], "rb");

        // Caso o arquivo compactado não consiga ser aberto a variavel "arquivoBinario" apontará para NULL
        // Com isso será impresso o nome do arquivo que não conseguiu ser aberto
        // E o programa sairá com status 1, indicando que ocorreu um erro
        if(arquivoBinario == NULL) {
            printf("wunzip: não é possível abrir o arquivo <%s>\n", argv[contador]);
            exit(1);
        }

        // Definição das variaveis:
        // "qtdCaracteres" que simboliza a quantidade de caracteres a serem escritos
        // "caractere" que representa justamente o caractere que será escrito
        int qtdCaracteres;
        char caractere;

        // Variavel para controalar o while e receber o resultado de fread
        // Caso fread consiga ler o(s) byte(s) especificados ele retornará um valor maior que 0
        // Caso contrário ele irá retornar 0
        // Com essa informação fiz com que o while rode até fread não conseguir mais ler
        int oByteFoiLido;

        // Leitura do primeiro caractere e da primeira quantidade de caracteres
        oByteFoiLido = fread(&qtdCaracteres, sizeof(int), 1, arquivoBinario);
        oByteFoiLido = fread(&caractere, sizeof(char), 1, arquivoBinario);

        // Laço que executa até o fim do arquivoBinario
        while(oByteFoiLido != 0) {

            // Laço interno para a impressão do caractere descompactado
            // Pela quantidade especificada também na descompactação
            for(int i = 0; i < qtdCaracteres; i++) {
                printf("%c", caractere);
                fputc(caractere, arquivoUnzip);
            }
            
            // Por fim a leitura do próximo caractere e de sua quantidade
            oByteFoiLido = fread(&qtdCaracteres, sizeof(int), 1, arquivoBinario);
            oByteFoiLido = fread(&caractere, sizeof(char), 1, arquivoBinario);
        }

        // Fecha o arquivo binario atual
        fclose(arquivoBinario);

        // Adiciona ao contador assim passando para o proximo arquivo ou encerrando o while
        contador++;
    }

    // A fim de separar o print do wunzip do proximo comando utilizo este "\n"
    printf("\n");

    exit(0);
}
