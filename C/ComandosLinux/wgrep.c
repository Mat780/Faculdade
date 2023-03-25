#include <stdio.h>

// Importação da biblioteca stdlib para o uso das funções:
// exit() e malloc()
#include <stdlib.h>

// Importação da biblioteca string para o uso da função:
// strstr()
#include <string.h>

int main(int argc, char const *argv[]) {

    // Definição do buffer e seu tamanho para a função de getline()
    size_t tamanhoBuffer = 10;
    char *buffer = (char*)malloc(sizeof(char) * tamanhoBuffer);

    // Definição do ponteiro que futuramente nos dirá se existe uma subpalavra na linha lida
    // Atráves do retorno da função strstr()
    char *existeSubPalavra = NULL;

    // Caso não haja argumentos suficientes para a execução
    // Ele ira imprimir o que ele espera receber ao ser chamado e depois sai com stauts 1 indicando erro
    if (argc < 2) {
        printf("wgrep: termo_de_busca [arquivo ...]\n");
        exit(1);

    } 
    // Caso haja exatemente 2 argumentos, ou seja somente 1 parametro
    // Portanto ele irá o usar o parametro passado como termo de busca
    // E irá ler da entrada padrão.
    else if (argc == 2) {
        
        // Variavel para pegar o retorno de getline e usar como parametro de parada do while
        // Motivo de variavel: Para evitar o warning de não utilização do retorno de getline
        //                     que geraria outro warning se não usassemos a variavel com a informação armazenada
        int quantidadeCaracteresLidos;

        // While só para quando ele recebe da entrada padrão apenas o '\n'
        do {
            quantidadeCaracteresLidos = getline(&buffer, &tamanhoBuffer, stdin);
            existeSubPalavra = strstr(buffer, argv[1]);

            if(existeSubPalavra != NULL) {
                printf("%s", buffer);
            }

        } while(quantidadeCaracteresLidos != 0 && (buffer[0] != '\n')); 

    }
    
    // Inicializando o contador para ser nosso indexe de argv[]
    // Começando na posição 2 para pegar somente os arquivos
    int contador = 2;

    // Executa o laço até que acabe os arquivos passados por parametro
    while(contador < argc) {

        // Abre o arquivo guardando seu ponteiro de leitura em "arquivo"
        FILE *arquivo = fopen(argv[contador], "r");

        // Se o arquivo estiver apontando para NULL significa que ele não conseguiu ser aberto
        // Portanto imprime o nome do arquivo que não conseguiu ser aberto
        // E o programa sai com status 1 indicando erro
        if (arquivo == NULL) {
            printf("wgrep: não é possível abrir o arquivo <%s>\n", argv[contador]);
            exit(1);
        }    

        // Laço de repetição até encontrar o final do arquivo
        // Dentro dele é executado a verificação se a linha armazenada no buffer possui a subpalavra
        // do termo de pesquisa, caso haja a subpalavra, a linha inteira será escrita
        while( (getline(&buffer, &tamanhoBuffer, arquivo)) != EOF) {
            existeSubPalavra = strstr(buffer, argv[1]);
            if(existeSubPalavra != NULL) {
                printf("%s", buffer);
            }

            existeSubPalavra = NULL;
            buffer = NULL;

        }

        // Fecha-se o arquivo atual 
        fclose(arquivo);


        // Aumenta-se o contador para que ele passe para o proximo arquivo
        // OU que ele finalize o laço
        contador++;
    }

    // A fim de separar a saída final do wgrep e o proximo comando utilizo esse "\n"
    printf("\n");

    // Após a conclusão do laço tem-se que tudo foi feito corretamente e portanto o programa sai com status 0
    exit(0);
}
