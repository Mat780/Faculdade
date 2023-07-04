#include <fstream>
#include "token.h"

class Scanner {
    private: 
        string texto; // Armazena o texto de entrada
        int pos; // Posicao atual
        int linhaAtual;
    
    public:
    // Construtor
        Scanner(string);

        int getLinhaAtual();
    
        // Metodo que retorna o proximo token da entrada
        Token* nextToken();     

        // Metodo que verifica vários possiveis tokens quando no estado 0
        Token* tokensCase0(char);

        // Metodo que verifica se o ID encontrado, 
        // é mesmo um ID ou uma palavra reservada
        Token* checkPalavraReservada(string);
    
        // Metodo para erro durante a analise lexica, 
        // essa função encerra o programa 
        void erroLexico(string);
};
