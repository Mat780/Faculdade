#include <iostream>
#include <string.h>

using namespace std;

//* Funcao que troca o valor na posicao i, com o valor da posicao j
void troca(int* vetor, int i, int j) {

    int aux = vetor[i];
    vetor[i] = vetor[j];
    vetor[j] = aux;

}

//* Funcao de Debug para saber se o rotor e condizente com os mostrados pelo PDF
void printaMatriz(int vetor[5][256], int n) {
    
    for (int i = 0; i < n; i++) {
        cout << "---------------------------------------------------------------" << endl;
        for (int j = 0; j < 256; j++) {
            printf("%3d ", vetor[i][j]);
            if (j % 16 == 15) cout << endl;
        } 
        cout << "---------------------------------------------------------------" << endl;
    }
}

//* Funcao que inicia o rotor para a decifragem, pegando o valor na posicao [i][j] e armazena em aux, assim usa esse valor para acessar a posicao [i][aux]
//* E dentro da posicao [i][aux] no rotor modificado armazena-se j, assim invertendo toda a matriz de rotores
void iniciarRotorParaDecripto(int n, int (&vetorRotores)[5][256], int (&vetorRotoresModificado)[5][256]) {
    int aux;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < 256; j++) {
           aux = vetorRotores[i][j];
           vetorRotoresModificado[i][aux] = j;
        } 
    }
}

//* Funcao que permuta os rotores com a matriz de trocas
void permutaVetorRotoresETrocas(int n, int (&vetorRotores)[5][256], int (&vetorTrocas)[5][256]) {
    for (int i = 0; i < n; i++) {
        int y = 0;
        for (int j = 0; j < 256; j++) {
            y = (y + vetorRotores[i][j] + vetorTrocas[i][j]) % 256;
            troca(vetorRotores[i], j, y);
        }
    }
}

int main(int argc, char const *argv[]) {
    
    //* Verificação inicial para ver se o algoritmo pode rodar.
    if (argc < 8) {
        cout << "Numero de parametros inválido, são necessários no minimo 7 parametros, sendo eles:" << endl;
        cout << "Modo: 'C' para criptografar e 'D' para descriptografar" << endl;
        cout << "N: Numero de rotores a serem usados de 1 a 5" << endl;
        cout << "Frases: As chaves a serem usadas por cada rotor, então caso 'N' seja igual a 2, serão 2 frases" << endl;
        cout << "K e L: Para cada rotor serão passados um par K e L, então: k1 l1 k2 l2 kn ln" << endl;
        cout << "Arquivo de entrada: O arquivo que será usado como input" << endl;
        cout << "Arquivo de saida: O arquivo que será usado como output" << endl;
        exit(1);
    }

    //* Verificação se o modo está correto.
    char modo = *argv[1];

    if (modo != 'C' && modo != 'D') {
        cout << "Modo inválido, por favor utilize somente 'C' ou 'D'" << endl;
        exit(1);
    }

    //* Conversão do parametro n, para inteiro.
    int n;
    n = atoi(argv[2]);

    //* Abertura do arquivo de leitura e abertura (ou criacao) do arquivo de saida. 
    FILE *entrada = fopen(argv[argc - 2], "rb");
    FILE *saida = fopen(argv[argc - 1], "wb");

    //* Caso o arquivo de entrada esteja nulo, então o arquivo não conseguiu ser aberto.
    if(entrada == NULL) {
        cout << "Erro: Arquivo de entrada não pode ser aberto, verifique o nome passado por parametro" << endl;
        exit(1); 
    };

    //* A seguir inicialização de variaveis necessarias para o codigo

    //* Matriz de rotores para criptografar e descriptografar
    int vetorRotoresC[5][256];
    int vetorRotoresD[5][256];

    //* Matriz de trocas para permutar os rotores
    int vetorTrocas[5][256];

    //* Vetor das chaves passadas por parametro
    string vetorChaves[5];

    //* Vetores K e L para armazenar os Ks e Ls passados por parametros
    int vetorK[5];
    int vetorL[5];

    //* Vetor movimento representa quantos simbolos foram cifrados para cada rotor 
    int vetorMovimento[5] = {0,0,0,0,0};

    //* Vetor giradas representa quantas posicoes a direita o rotor ja girou
    //* OBS: No pseudocodigo, vetorGiradas representa o "p"
    int vetorGiradas[5]   = {0,0,0,0,0};

    //* Inicialização dos vetores
    for (int i = 0; i < n; i++) {
        vetorChaves[i] = argv[3 + i];

        if (vetorChaves[i].length() >= 256) {
            cout << "Erro: Chave " << i << " passada por parametro e maior do que o permitido" << endl;
            exit(1);
        }

        vetorK[i] = atoi(argv[3 + n + (i*2)]);
        vetorL[i] = atoi(argv[3 + n + (i*2) + 1]);

        for (int j = 0; j < 256; j++) {
            vetorRotoresC[i][j] = j;
            vetorTrocas[i][j] = vetorChaves[i][j % vetorChaves[i].length()];
        } 
    }

    //* Fazendo o "embaralhamento" dos rotores com o vetor de trocas, que é feito a partir das chaves
    permutaVetorRotoresETrocas(n, vetorRotoresC, vetorTrocas);

    //* Variavel auxiliar para criptografar ou descriptografar.
    int caractereAuxiliar;

    if (modo == 'C') {
        //* Primeiro pegamos um caractere do arquivo de entrada
        caractereAuxiliar = fgetc(entrada);

        //* Apos isso entramos em um loop ate o caractereAuxiliar encontrar o EOF
        while(caractereAuxiliar != EOF) {
            //* indexRotor serve para ser nosso auxiliar ao calcular o index do rotor na hora de cifragem
            int indexRotor;

            for (int i = 0; i < n; i++) {

                //* Caso o rotor ja tenha cifrado K simbolos, o IF abaixo sera acionado
                //* Assim atualizando vetorGiradas e zerando vetorMovimento
                if (vetorMovimento[i] == vetorK[i]) {
                    vetorGiradas[i] = vetorGiradas[i] + vetorL[i];
                    vetorGiradas[i] = vetorGiradas[i] % 256;
                    vetorMovimento[i] = 0;
                }

                //* Calcula-se o indexRotor para fazer a cifra do caractereAuxiliar
                indexRotor = (caractereAuxiliar + vetorGiradas[i]) % 256;
                caractereAuxiliar = vetorRotoresC[i][indexRotor];

                //* Sempre que um caractere e cifrado o vetorMovimento e aumentado em 1
                vetorMovimento[i] += 1;

            }

            //* Apos a cifragem o caractere e escrito no arquivo de saida
            fputc(caractereAuxiliar, saida);
            caractereAuxiliar = fgetc(entrada);
        }

    } else { //* Em caso de descriptografia
        
        //* Inicia os rotores para a decifragem, pegando o vetorRotoresC e utilizando ele para iniciar o vetorRotoresD
        iniciarRotorParaDecripto(n, vetorRotoresC, vetorRotoresD);

        //* Le o arquivo de entrada e pega um caractere
        caractereAuxiliar = fgetc(entrada);

        printaMatriz(vetorRotoresD, n);

        //* Apos isso entramos em um loop ate o caractereAuxiliar encontrar o EOF
        while(caractereAuxiliar != EOF) {

            //* O laco na decifragem e ao contrario, comecando do ultimo rotor ate chegar ao primeiro rotor
            for (int i = (n-1); i > -1; i--) {

                //* Caso o rotor ja tenha cifrado K simbolos, o IF abaixo sera acionado
                //* Assim atualizando vetorGiradas e zerando vetorMovimento
                if (vetorMovimento[i] == vetorK[i]) {
                    vetorGiradas[i] = vetorGiradas[i] + vetorL[i];
                    vetorGiradas[i] = vetorGiradas[i] % 256;
                    vetorMovimento[i] = 0;
                }

                //* Usa o caractere lido como index no rotor na decifragem
                caractereAuxiliar = vetorRotoresD[i][caractereAuxiliar];

                //* E entao reduz o valor, pego no rotor, baseado no vetorGiradas
                caractereAuxiliar = caractereAuxiliar - vetorGiradas[i];
                
                //* Caso o caractereAuxiliar seja negativo, entao adiciona-se 256 ao caractereAuxiliar
                if (caractereAuxiliar < 0) {
                    caractereAuxiliar = caractereAuxiliar + 256;
                }

                //* A cada decifragem o vetorMovimento e aumentado em 1
                vetorMovimento[i] += 1;

            }

            //* Assim o simbolo decifrado e escrito na saida
            fputc(caractereAuxiliar, saida);

            //* Por fim ele le um novo caractere da entrada
            caractereAuxiliar = fgetc(entrada);

        }

    }

    return 0;
}
