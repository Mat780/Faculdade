/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include <iostream>
#include <string>
#include "comandoSimples.h"
#include "token.h"

struct Comando {
    
    // Armazena o comando simples
    ComandoSimples * comandoSimples;

    // Armazena o nome do arquivo de entrada e saida
    string arquivoEntrada;
    string arquivoSaida;
    string arquivoErro;
    
    // Variaveis de controle
    bool background;

    Comando();

};