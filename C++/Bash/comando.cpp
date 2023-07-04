/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include "comando.h"

// Construtor
Comando::Comando() {
    comandoSimples = NULL;
    arquivoEntrada = "";
    arquivoSaida = "";
    arquivoErro = "";
    background = false;
}


