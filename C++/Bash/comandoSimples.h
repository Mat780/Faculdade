/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include <iostream>

using namespace std;

struct ComandoSimples {
    // Espaço disponivel para argumentos pré-alocados
    int numeroDeArgumentosDisponivel;

    // Numero de argumentos
    int numeroDeArgumentos;

    // Vetor de argumentos;
    char ** argumentos;

    // Construtor
    ComandoSimples(const char* comandoEscrito, int tamanhoComando);

    // Inserção de argumentos no comando simples
    void inserirArgumento(const char * argumento, int tamanhoArgumento);

};