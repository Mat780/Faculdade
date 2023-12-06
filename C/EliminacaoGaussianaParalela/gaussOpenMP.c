#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <omp.h>

//* Funcao responsavel por printar a matriz A para debug.
void printarMatrix(double** A, long int n) {
    printf("---------------------------------------\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%lf ", A[i][j]);
        }
        printf("\n");
    }
}

int main(int argc, char const *argv[]) {
    
    //long int n = pow(10.0, atoi(argv[1])); // Implementacao final.
    long int n = pow(10.0, 3); //! Debug
    
    double maiorDaColuna; // Variavel responsavel por ver o maior valor da coluna.
    long int linha;

    int numeroLimitante = 100000; // Se reduzir o de baixo pra 1 , reduzir aq um pouco.
    int numeroLimitanteInferior = 10; // Na pior das hipotes substituir por 1.

    double** A = (double**) malloc(n * sizeof(double*));
    double*  y = (double*)  malloc(n * sizeof(double)); // Possui os valores finais depois da eliminacao gaussiana.
    double*  b = (double*)  malloc(n * sizeof(double));

    time_t t; // Timestamp para ser a semente geradora do rand().
    double tempoExecucao;

    // Inicializacao da matriz A e do vetor b.
    srand((unsigned) time(&t));
    
    #pragma omp parallel for
    for (long int i = 0; i < n; i++) {

        A[i] = (double*) malloc(n * sizeof(double));

        srand((unsigned) rand());

        for (long int j = 0; j < n; j++) {
            if (i == j) A[i][j] = rand() % numeroLimitante;
            else        A[i][j] = rand() % numeroLimitanteInferior;
        }

        int aux = rand();
        b[i] = aux > numeroLimitante ? numeroLimitante : A[i][i] + aux;
    }

    tempoExecucao = omp_get_wtime();

    // Eliminacao gaussiana.
    for (long int k = 0; k < n; k++) { // Loop externo.

        maiorDaColuna = fabs(A[k][k]);
        linha = k;
        
        // Verifica o maior valor da coluna atual e armazena em qual linha esse valor se encontra.
        #pragma omp parallel for        
        for (long int i = (k + 1); i < n; i++) {
            if (maiorDaColuna < fabs(A[i][k])) {
                maiorDaColuna = fabs(A[i][k]);
                linha = i;
            } 
        }
        

        // Caso a linha do maior valor nao seja k, ele ira trocar as linhas: K e "linha".
        if (linha != k) {
            double* auxA = A[k];
            double auxB = b[k];

            A[k]     = A[linha];
            b[k]     = b[linha];
            A[linha] = auxA;
            b[linha] = auxB;
        }

        // Caso a diagonal seja igual a zero, mesmo com as mudancas feitas acima,
        // entao isso significa que a matriz e impossivel.
        if (A[k][k] == 0) {
            printf("A coluna inteira esta zerada\n");
            exit(1);
        }

        #pragma omp parallel for        
        for (long int j = k + 1; j < n; j++) {
            if (A[k][j] != 0) A[k][j] = A[k][j] / A[k][k]; // Passo de divisao.
        }
        

        y[k] = b[k] / A[k][k]; // Valores para y que serao utilizados nas atribuicoes para x.
        A[k][k] = 1;

        #pragma omp parallel for
        for (long int i = k + 1; i < n; i++) {

            for (long int j = k + 1; j < n; j++) A[i][j] = A[i][j] - A[i][k] * A[k][j]; // Passo de eliminacao.

            b[i] = b[i] - A[i][k] * y[k];
            A[i][k] = 0;
        }
        
    }

    double* x = (double*) malloc(n * sizeof(double));
    double somador = 0.0; // Variavel usada para somar os valores da linha.

    // Esse laco serve para as atribuicoes dos valores de x no sistema linear.
    for (long int i = (n-1); i > -1; i--) {
        somador = 0.0;
        
        #pragma omp parallel for reduction(+ : somador)
        for (long int j = (i+1); j < n; j++) somador += A[i][j] * x[j];
        

        x[i] = y[i] - somador;
    }

    double tempoGasto = omp_get_wtime() - tempoExecucao;

    // printf("---------------------------------------\n");
    // for (int i = 0; i < n; i++) {
    //     printf("%.33lf\n", x[i]);
    // }
    
    printf("O tempo de execucao foi: %lf\n", tempoGasto);
    printf("Fim\n");

    return 0;
}
