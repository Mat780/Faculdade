/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include "analizadorLexico.h"

using namespace std;

// Construtor que recebe uma string com o nome do arquivo 
// de entrada e preenche input com seu conteúdo.

/* Construtor da classe Scanner */
Scanner::Scanner() {
    pos = 0;

}

/* Metodo que reinicia o scanner com um novo comando */
void Scanner::novoComando(string textoEntrada) {
    this->texto = textoEntrada;
    tamanhoTexto = texto.length();
    pos = 0;

}

/* Método que retorna o próximo token da entrada */
Token* Scanner::nextToken() {

    Token* token;
    string lexeme = "";

    int estado = 0;
    char caractereAtual;
    bool repeticaoDeMaiorQ = false;
    bool repeticaoDeMenorQ = false;

    /* Roda até conseguir retornar um token */
    while(1) {
        caractereAtual = texto[pos];
        pos++;

        switch(estado) {
            /* Estado inicial */
            case 0: 

                if (pos > tamanhoTexto or caractereAtual == '\0') {

                    if (tamanhoTexto == 0) {
                        token = new Token(NOVA_LINHA);
                    }

                    else {
                        token = new Token(FIM_DE_ARQUIVO);

                    }

                    return token;
                }

                // Pode ser: Comando, argumento ou filename
                // Quem vai saber ao certo é o sintatico
                if (isalpha(caractereAtual)) {
                    estado = 1;
                    lexeme += caractereAtual;
                }


                // Redirecionador de saida
                else if (caractereAtual == '>') {
                    estado = 3;
                }

                // Redirecionador de entrada
                else if (caractereAtual == '<') {
                    estado = 4;
                }

                // Pipe
                else if (caractereAtual == '|') {
                    token = new Token(PIPE);
                    return token;
                }

                // Background
                else if (caractereAtual == '&') {
                    token = new Token(BACKGROUND);
                    return token; 
                }

                // Argumento, que pega da tabela ascii, desde 33 até 126
                else if (caractereAtual >= 33 and caractereAtual <= 126) {
                    estado = 2;
                    lexeme += caractereAtual;
                }

                // Linha vazia ou final do comando
                else if (caractereAtual == '\n') {
                    token = new Token(NOVA_LINHA);
                    return token;
                }
            
                break;

            /* Estado se for um TEXTO = {ARGUMENTO, COMANDO, FILENAME} */
            case 1:

                if (isspace(caractereAtual) == false and caractereAtual != '\0') {
                    lexeme += caractereAtual;
                } 

                else {
                    pos--;
                    token = new Token(TEXTO, lexeme);
                    return token;
                }

                break;
            
            /* Estado se for um ARGUMENTO, argumento que eu digo é quando começa com "-" */
            case 2:

                if (isspace(caractereAtual) == false and caractereAtual != '\0') {
                    lexeme += caractereAtual;
                }
                
                else {
                    pos--;
                    token = new Token(ARGUMENTO, lexeme);
                    return token;
                }

                break;
            
            // Redirecionamento de saida padrão
            case 3:

                if (repeticaoDeMaiorQ == false) {
                    if (caractereAtual == '>') {
                        repeticaoDeMaiorQ = true;
                    } 

                    else {
                        token = new Token(MODIFICADOR, SAIDA_SOBRESCRITA);
                        return token;
                    }

                }

                else {
                    token = new Token(MODIFICADOR, SAIDA_ERRO);
                    return token;
                }

                break;
            
            // Redirecionamento de entrada padrão
            case 4:

                if (repeticaoDeMenorQ == true) {
                    token = new Token(MODIFICADOR, ENTRADA_VIA_TECLADO);
                    return token;
                }

                else if (caractereAtual == '<') {
                    repeticaoDeMenorQ = true;
                }

                else {
                    token = new Token(MODIFICADOR, ENTRADA_VIA_ARQUIVO);
                    return token;
                }


        }
    }

}
