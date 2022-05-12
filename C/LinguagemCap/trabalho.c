/**************************************************
*
* Nome do Aluno: MATHEUS FELIPE ALVES DURÃES
* Trabalho 1
* Professor(a): Diego Rubert        (Sensei)
*
*/

// Importando a biblioteca basica com input e output;
#include <stdio.h>

/* Armazena informações de uma capivara */
typedef struct {
    int numero_capi;    /* Número da capivara = posição na largada */
    int ultrapass;      /* Quantidade de ultrapassagens feitas */
} capivara; // Nome da estrutura.

/*  Funcao: PrintarOrdem(int tamanho_do_vetor, capivara *vetorDeCapivaras)
    Serve para: Printar a ordem de chegada e a ordem da ordenacao.
*/
void PrintarOrdem(int tamanho_do_vetor, capivara *vetorDeCapivaras){

    /*  Um laço que se repete até o tamanho_do_vetor diminuido em 1,
        pois ao sair do laco ele imprime o ultimo print com a devida quebra de linha.
    */ 
    for(int c = 0; c < tamanho_do_vetor-1; c++){
        // Printando com um espacamento para separar os valores.
        printf("%d ", vetorDeCapivaras[c].numero_capi);
    }
    // Ultimo print para ter a quebra de linha.
    printf("%d\n", vetorDeCapivaras[tamanho_do_vetor-1].numero_capi);
}

/*
    Funcao: troca(capivara *a, capivara *b)
    Serve para: Trocar o local da estrutura A pela estrutura B
    E trocar o da B pela A.
    Em resumo: [A, B] depois [B, A].
*/
void troca(capivara *a, capivara *b){
    capivara temp = *a;
    *a = *b;
    *b = temp;
}

/* 
    Funcao: CriarVetorDasCapivaras(int tamanho_do_vetor, capivara *vetorDeCapivaras)
    Serve para: Criar os valores básicos das estruturas dentro do vetor,
    no caso colocando as capivaras 1, 2, 3, 4, 5... e por ai vai, juntamente a isso
    coloca o valor de ultrapass sendo zero.
*/
void CriarVetorDasCapivaras(int tamanho_do_vetor, capivara *vetorDeCapivaras){
    // Laco com um iteravel que define o numero da capivara, junto a definicao de zero para ultrapass.
    for(int contador = 0; contador < tamanho_do_vetor; contador++){
        vetorDeCapivaras[contador].numero_capi = contador+1;
        vetorDeCapivaras[contador].ultrapass = 0;
    }
}

/*
    Funcao: FuncaoUltrapassagem(int numero_da_ultrapassagem, int tamanho_do_vetor, capivara *vetorDeCapivaras)
    Serve para: Fazer a ultrapassagem de uma capivara, em resumo a função recebe o numero da capivara
    depois ela busca a capivara dentro do vetorDeCapivaras, quando ele acha a capivara
    ele adiciona a variavel ultrapass da estrutura da capivara +1, para representar a ultrapassagem
    E por fim ele usa a funcao troca para trocar a posicao dentro do vetorDeCapivaras.
*/
void FuncaoUltrapassagem(int numero_da_ultrapassagem, int tamanho_do_vetor, capivara *vetorDeCapivaras){
    // Definicao de variavel inteira contador.
    int contador = 0;

    // Enquanto o numero da ultrapassagem nao for o mesmo numero da capivara o while continua rodando.
    while(numero_da_ultrapassagem != vetorDeCapivaras[contador].numero_capi){
        // Dentro do while ele adiciona ao contador para ele ser usado para achar a capivara.
        contador++;
    }

    // Aumento na variavel ultrapass da capivara que esta ultrapassando.
    vetorDeCapivaras[contador].ultrapass++;

    // Por fim ele usa a funcao troca, para completar a ultrapassagem.
    troca(&vetorDeCapivaras[contador], &vetorDeCapivaras[contador-1]);
}


/*
    Funcao: Separa(int primeiro, int ultimo, capivara *vetor)
    Serve para: Separar os numeros atraves de um pivo e parametros para a ordenacao
    no caso os parametros para o trabalho sao: 
    1. Que a ultrapassagem maior vem primeiro.
    2. Segundo que em caso de empate nas ultrapassagens a capivara com menor numero na fila de largada deve vir primeiro. 
*/
int Separa(int primeiro, int ultimo, capivara *vetor){
    /*  Definicao de variaveis inteiras: esquerda e direita
        pois uma sempre vira da esquerda e a outra da direita.
    */
    int esquerda, direita;

    // Definicao da variavel pivo com a estrutura capivara.
    capivara pivo;

    // Aqui temos os respectivos valores para cada variavel.
    pivo = vetor[primeiro];
    esquerda = primeiro - 1;
    direita = ultimo + 1;

    // Enquanto a esquerda for menor que a direita.
    while (esquerda < direita){
        // Reduza a direita em 1 , em enquanto segue o while abaixo.
        do {
            direita--;
        /* 
            Laco com 3 parametros para continuar rodando.
            1. O valor da ultrapass do vetor na posicao direita deve ser MENOR que a do pivo.
            OU
            2.1. Se o valor da ultrapass do pivo for IGUAL a do vetor na posicao direita.
            E
            2.2. O numero_capi do pivo deve ser MENOR que o numero_capi do vetor na posicao direita.
        */
        } while (vetor[direita].ultrapass < pivo.ultrapass ||
                (pivo.ultrapass == vetor[direita].ultrapass && pivo.numero_capi < vetor[direita].numero_capi) );

        // Aumente a esquerda em 1 , em enquanto segue o while abaixo.
        do {
            esquerda++;
        /*
            Laco com 3 parametros para continuar rodando.
            1. O valor da ultrapass do vetor na posicao esquerda deve ser MAIOR que a do pivo.
            OU
            2.1. Se o valor da ultrapass do pivo for IGUAL a do vetor na posicao esquerda.
            E
            2.2. O numero_capi do pivo deve ser MAIOR que o numero_capi do vetor na posicao esquerda.
        */
        } while (vetor[esquerda].ultrapass > pivo.ultrapass || 
                (pivo.ultrapass == vetor[esquerda].ultrapass && pivo.numero_capi > vetor[esquerda].numero_capi) );
        
        // Se a esquerda for menor que a direita, entao utilize a funcao troca.
        if (esquerda < direita){
            // Trocando a estrutura na posicao da esquerda com a da direita.
            troca(&vetor[esquerda], &vetor[direita]);
        }
    }
    // Por fim ele retorna o valor da direita.
    return direita;
}

/*  Funcao: QuickSort(int primeiro, int ultimo, capivara *vetorDeCapivaras)
    Serve para: Ordenar os numeros de um vetor atráves de um pivo e parametros de ordenacao
    nada muito diferente do comum, porém foi adaptado para esse trabalho a fim de conseguir usar
    as estruturas propostas ou seja os registros, juntamente aos parametros de ordenacao especificos
    do proprio exercicio, vulgo as regras de ultrapassagem.
*/
void QuickSort(int primeiro, int ultimo, capivara *vetorDeCapivaras){
    // Definicao de variavel valorSepara, para guardar o valor que separa.
    int valorSepara;

    // Se o primeiro for menor que o ultimo então...
    if (primeiro < ultimo){
        // A variavel valorSepara recebe o resultado da funcao Separa.
        valorSepara = Separa(primeiro, ultimo, vetorDeCapivaras);

        // Aqui é chamado recursivamente o Quicksort 2x para quebrar o vetor em varios pedacos.
        QuickSort(primeiro, valorSepara, vetorDeCapivaras);
        QuickSort(valorSepara+1, ultimo, vetorDeCapivaras);
    }
}


int main(void){
    // Definicao de varivaies inteiras.
    int numero_da_ultrapassagem, tamanho_do_vetor;

    // Escaneia um numero digitado no teclado e o atribui a variavel tamanho_do_vetor.
    scanf("%d", &tamanho_do_vetor);

    // Definicao de vetor do tipo estrutura capivara, com tamanho definido pela variavel tamanho_do_vetor.
    capivara vetorDeCapivaras[tamanho_do_vetor];

    // Ele chama a funcao CriarVetorDasCapivaras para criar os valores dentro das estruturas do vetorDeCapivaras.
    CriarVetorDasCapivaras(tamanho_do_vetor, vetorDeCapivaras);

    // Enquanto o numero_de_ultrapassagem nao chegar ao fim do arquivo ele continua rodando.
    while(scanf("%d", &numero_da_ultrapassagem) != EOF){
        // Chama a FuncaoUltrapassagem para realizar a devida ultrapassagem.
        FuncaoUltrapassagem(numero_da_ultrapassagem , tamanho_do_vetor, vetorDeCapivaras);
    }

    // Chamando a funcao PrintarOrdem para imprimir a ordem de chegada das capivaras.
    PrintarOrdem(tamanho_do_vetor, vetorDeCapivaras);

    // Chamando a funcao QuickSort para ordenar o vetorDeCapivaras conforme a ultrapassagem.
    QuickSort(0, tamanho_do_vetor-1, vetorDeCapivaras);

    // E por fim chamando novamente a funcao PrintarOrdem para imprimir a ordem de ultrapassagem
    // indo dos que mais ultrapassaram até os que menos ultrapassaram ou nao ultrapassaram.
    PrintarOrdem(tamanho_do_vetor, vetorDeCapivaras);

    // return 0 para encerrar o codigo.
    return 0;
}