#include <iostream>
#include <ctype.h> //Funcoes de caracteres
#include <string>

using namespace std;

enum Names {
    INDEFINIDO,     // 0
    ID,             // 1

    NUMERO,         // 2
    INTEIRO,        // 3

    OPERADOR,       // 4
    E_LOGICO,       // 5
    MENOR_Q,        // 6
    MAIOR_Q,        // 7
    ADICAO,         // 8
    SUBTRACAO,      // 9
    MULTIPLICACAO,  // 10
    DIVISAO,        // 11
    ATRIBUICAO,     // 12
    IGUALDADE,      // 13
    DIFERENTE,      // 14
    NEGACAO,        // 15

    SEPARADOR,      // 16
    ABRE_PARENTESES, // 17
    FECHA_PARENTESES, // 18
    ABRE_COLCHETE,    // 19
    FECHA_COLCHETE,   // 20
    ABRE_CHAVES,      // 21
    FECHA_CHAVES,     // 22
    PONTO_VIRGULA,    // 23
    PONTO_FINAL,      // 24
    VIRGULA,          // 25

    // Palavras reservadas
    BOOLEAN,            // 26
    CLASS,              // 27
    ELSE,               // 28
    EXTENDS,            // 29
    FALSE,              // 30
    IF,                 // 31
    INT,                // 32
    LENGTH,             // 33
    MAIN,               // 34
    NEW,                // 35
    PUBLIC,             // 36
    RETURN,             // 37
    STATIC,             // 38
    STRING,             // 39
    SYSTEM_OUT_PRINTLN, // 40
    THIS,               // 41
    TRUE,               // 42
    VOID,               // 43
    WHILE,              // 44
    RESERVADA,          // 45
    // Fim das palavras reservadas

    FIM_DE_ARQUIVO      // 46
};

class Token {
    public: 
        int name;
        int attribute;
        string lexeme;
    
        Token(int name) { 
            this->name = name;
            attribute = INDEFINIDO;
        }

        // Quando for um ID
        Token(int name, string l) {
            this->name = name;
            this->attribute = INDEFINIDO;
            this->lexeme = l;
        }
        
        Token(int name, int attr) {
            this->name = name;
            this->attribute = attr;
        }

};

// Metodo que recebe o name ou attribute do token,
// e retorna o seu equivalente, porém escrito em uma string
// Exemplo: enumToString(1) -> retorna ID
inline string enumToString(int num) {
    string vetorEnum[] = 
    {   "INDEFINIDO", 
        "ID",
        "NUMERO",
        "INTEIRO",
        "OPERADOR",
        "E_LOGICO",
        "MENOR_Q",
        "MAIOR_Q",
        "ADICAO",
        "SUBTRACAO",
        "MULTIPLICACAO",
        "DIVISAO",
        "ATRIBUICAO",
        "IGUALDADE",
        "DIFERENTE",
        "NEGACAO",
        "SEPARADOR",
        "ABRE_PARENTESES",
        "FECHA_PARENTESES",
        "ABRE_COLCHETE",
        "FECHA_COLCHETE",
        "ABRE_CHAVES",
        "FECHA_CHAVES",
        "PONTO_VIRGULA",
        "PONTO_FINAL",
        "VIRGULA",
        "BOOLEAN",
        "CLASS",              
        "ELSE",               
        "EXTENDS",            
        "FALSE",              
        "IF",                 
        "INT",                
        "LENGTH",             
        "MAIN",               
        "NEW",                
        "PUBLIC",             
        "RETURN",             
        "STATIC",             
        "STRING",             
        "SYSTEM_OUT_PRINTLN", 
        "THIS",               
        "TRUE",               
        "VOID",               
        "WHILE",         
        "RESERVADA",     
        "FIM_DE_ARQUIVO"
    };

    return vetorEnum[num];
}