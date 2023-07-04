/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include "comandoSimples.h"
#include <string.h>

/* Construtor */
ComandoSimples::ComandoSimples(const char *comandoEscrito, int tamanhoComando) {
    numeroDeArgumentos = 1;
    numeroDeArgumentosDisponivel = 4;
    argumentos = new char*[5];

    char * comandoCopiado = (char*) malloc(tamanhoComando);
    strncpy(comandoCopiado, comandoEscrito, tamanhoComando);
    
    argumentos[0] = comandoCopiado;

    for (int i = 1; numeroDeArgumentosDisponivel >= i; i++) {
        argumentos[i] = NULL;
    }

}

/* Metodo para inserção de argumento no comando simples */
void ComandoSimples::inserirArgumento(const char * argumento, int tamanhoArgumento) {
    if(numeroDeArgumentos == numeroDeArgumentosDisponivel - 1) {
        
        int novoNumeroDeArgumentosDisponivel = numeroDeArgumentosDisponivel * 2;
        char ** novosArgumentos = new char*[novoNumeroDeArgumentosDisponivel + 1];

        for (int i = 0; numeroDeArgumentosDisponivel > i; i++) {
            novosArgumentos[i] = argumentos[i];
        }

        for (int i = numeroDeArgumentos; novoNumeroDeArgumentosDisponivel >= i; i++) {
            novosArgumentos[i] = NULL;
        }

        numeroDeArgumentosDisponivel = novoNumeroDeArgumentosDisponivel;
        argumentos = novosArgumentos;
    }
    
    this->argumentos[numeroDeArgumentos] = (char*) malloc(tamanhoArgumento);
    strncpy(argumentos[numeroDeArgumentos], argumento, tamanhoArgumento);
    
    numeroDeArgumentos++;
    this->argumentos[numeroDeArgumentos] = NULL;

}
