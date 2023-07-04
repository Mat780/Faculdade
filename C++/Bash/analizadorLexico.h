/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include <fstream>
#include <ctype.h>
#include <string.h>
#include "comando.h"

class Scanner {
    private: 
        string texto; // Armazena o texto de entrada.
        int pos; // Posição atual.
        int tamanhoTexto; // Armazena o tamanho do texto.
    
    public:
        // Construtor.
        Scanner();

        // Método que substitui o texto anterior e reinicia o parser.
        void novoComando(string);
    
        // Método que retorna o próximo token baseado no texto atual do parser.
        Token* nextToken();     

};
