/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include <iostream>

using namespace std;

// Nomes para os tokens
enum Names {
    INDEFINIDO,                 // 0
    
    TEXTO,                      // 1
    ARGUMENTO,                  // 2

    PIPE,                       // 3
    
    MODIFICADOR,                // 4
    ENTRADA_VIA_ARQUIVO,        // 5
    ENTRADA_VIA_TECLADO,        // 6
    SAIDA_SOBRESCRITA,          // 7
    SAIDA_ERRO,                 // 8

    BACKGROUND,                 // 9

    NOVA_LINHA,                 // 10

    FIM_DE_ARQUIVO,             // 11

};

class Token {
    public: 
        // Enum que representa o nome
        int name;

        // Enum que representa o atributo
        int atributo;

        // Texto original
        string lexeme;
    
        /* Abaixo todos os construtores */

        Token(int name) { 
            this->name = name;
        }

        Token(int name, int atributo) {
            this->name = name;
            this->atributo = atributo;
        }

        Token(int name, string l) {
            this->name = name;
            this->lexeme = l;
        }
        
};
