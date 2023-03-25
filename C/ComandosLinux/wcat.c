#include <stdio.h>

// Importação da biblioteca stdlib para o uso da função exit()
#include <stdlib.h>

int main(int argc, char const *argv[]) {
    // Declaração da variável "caractere", que será responsavel por armazenar cada caractere do texto lido
    char caractere;
    
    // Caso não haja parametros ele irá ler da entrada padrão
    if(argc == 1){
        do {
            caractere = fgetc(stdin);
            printf("%c", caractere);
        } while(caractere != '\n');
    }

    // Usarei "contador" como index do vetor argv, e também na comparação com o argc para saber
    // quando acaba os parametros da linha de comando  
    int contador = 1;

    while(contador < argc) {
        // Abertura do arquivo passado como parametro em modo de leitura
        FILE *arquivo = fopen(argv[contador] , "r");

        // Verificando se o arquivo conseguiu ser aberto ou não,
        // Caso não consiga ser aberto é impresso no terminal qual arquivo não conseguiu ser aberto
        // E por fim finaliza o programa com status 1 indicando que houve algum erro.
        if(arquivo == NULL) {
            printf("wcat: não é possível abrir o arquivo <%s>\n", argv[contador]);
            exit(1);

        }

        // Roda o laço até o ponteiro chegar ao final do arquivo
        // Dentro do laço ele pega caractere por caractere e imprime no terminal

        // OBS: O primeiro caractere é pego fora do while para que feof() funcione corretamente
        //      , E para não imprimir o EOF no terminal.
        caractere = fgetc(arquivo);

        while(feof(arquivo) == 0) {
            printf("%c", caractere);
            caractere = fgetc(arquivo);

        }

        // Fechamento do arquivo atual
        fclose(arquivo);

        // Após completar a leitura do arquivo, adicionamos ao contador para que
        // OU ele passe para o próximo arquivo
        // OU faça com que o while termine   
        contador++;
    }

    // Se o código funcionou corretamente até o fim ele irá sair com status 0;
    exit(0);
}
