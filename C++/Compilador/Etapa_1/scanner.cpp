#include "scanner.h"    
#include <ctype.h>
#include <string.h>

// Construtor que recebe uma string com o nome do arquivo 
// de entrada e preenche input com seu conteudo.

Scanner::Scanner(string arquivo) {
    pos = 0;
    linhaAtual = 1;

    ifstream inputFile(arquivo, ios::in);
    string linha;

    if (inputFile.is_open()) {

        while (getline(inputFile, linha) ) {
            this->texto.append(linha + '\n');
        }
        inputFile.close();

    } else {
        cout << "Nao foi possivel abrir o arquivo\n"; 
    }

}

int Scanner::getLinhaAtual() {
    return linhaAtual;
}

Token* Scanner::checkPalavraReservada(string str) {

    int palavraReservada;

    if (strcmp(str.c_str(), "boolean") == 0) palavraReservada = BOOLEAN;
    
    else if (strcmp(str.c_str(), "class") == 0) palavraReservada = CLASS;

    else if (str[0] == 'e') {
        if (strcmp(str.c_str(), "else") == 0) palavraReservada = ELSE;

        else if (strcmp(str.c_str(), "extends") == 0) palavraReservada = EXTENDS;

        else goto saidaPadrao;
    }
    else if (strcmp(str.c_str(), "false") == 0) palavraReservada = FALSE;
        
    else if (str[0] == 'i') {
        if (strcmp(str.c_str(), "if") == 0) palavraReservada = IF;

        else if (strcmp(str.c_str(), "int") == 0) palavraReservada = INT;

        else goto saidaPadrao;
    }
    else if (strcmp(str.c_str(), "length") == 0) palavraReservada = LENGTH;
    
    else if (strcmp(str.c_str(), "main") == 0) palavraReservada = MAIN;

    else if (strcmp(str.c_str(), "new") == 0) palavraReservada = NEW;

    else if (strcmp(str.c_str(), "public") == 0) palavraReservada = PUBLIC;

    else if (strcmp(str.c_str(), "return") == 0) palavraReservada = RETURN;

    else if (str[0] == 's' || str[0] == 'S') {
        if (strcmp(str.c_str(), "static") == 0) palavraReservada = STATIC;

        else if (strcmp(str.c_str(), "String") == 0) palavraReservada = STRING;

        else goto saidaPadrao;
    }
    else if (str[0] == 't') {
        if (strcmp(str.c_str(), "this") == 0) palavraReservada = THIS;

        else if (strcmp(str.c_str(), "true") == 0) palavraReservada = TRUE;

        else goto saidaPadrao;
    }
    else if (strcmp(str.c_str(), "void") == 0) palavraReservada = VOID;
    
    else if (strcmp(str.c_str(), "while") == 0) palavraReservada = WHILE;
    
    else goto saidaPadrao;
    
    return new Token(palavraReservada, RESERVADA);

    saidaPadrao: return new Token(ID, str);
}

// Metodo que retorna o proximo token da entrada 
Token* Scanner::nextToken() {
    Token* token;
    string lexeme = "";

    int estado = 0;
    char caractereAtual;

    while(1) {
        caractereAtual = texto[pos];
        // cout << "While: " << caractereAtual << "\n";
        switch(estado) {
            case 0:
                pos++;

                if (caractereAtual == '\0') {
                    token = new Token(FIM_DE_ARQUIVO);
                    return token;

                } else if (isalpha(caractereAtual)) {
                    estado = 1;
                    lexeme += caractereAtual;

                } else if (isdigit(caractereAtual)) {
                    estado = 2;

                } else if (caractereAtual == '&') {
                    estado = 3;

                } else if (caractereAtual == '/') {
                    estado = 4;

                } else if (caractereAtual == '=') {
                    estado = 5;

                } else if (caractereAtual == '!') {
                    estado = 6;

                } else if (caractereAtual == '\n') {
                    linhaAtual++;
                }

                token = tokensCase0(caractereAtual);

                if(token != NULL) return token;
                
                break;

            case 1: { // Veio de 0 por ser uma letra 
                
                bool e_Variavel = (isalnum(caractereAtual) || caractereAtual == '_');

                if(e_Variavel) {
                    lexeme += caractereAtual;

                } else if (caractereAtual == '.' && (lexeme.compare("System") == 0)) {
                    lexeme += caractereAtual;
                    estado = 7;
                
                } else {
                    return checkPalavraReservada(lexeme);
                }

                pos++;
                break;

            }

            case 2: { // Veio de 0, por ser numero

                bool e_Numero = isdigit(caractereAtual);

                if(e_Numero == false) {
                    token = new Token(NUMERO, INTEIRO);
                    return token;
                }

                pos++;
                break;
            }

            case 3: { // Veio de 0, por ser '&'
                
                bool e_E_Logico = caractereAtual == '&';

                if(e_E_Logico == false) {
                    erroLexico("Falha encontrar &&");
                }

                pos++;

                token = new Token(OPERADOR, E_LOGICO);
                return token;
            }

            case 4: { // Veio de 0, por ser '/'

                bool e_Comentario = caractereAtual == '/';
                bool e_ComentarioGrande = caractereAtual == '*';

                if(e_Comentario) {

                    while(caractereAtual != '\n') {
                        pos++;
                        caractereAtual = texto[pos];
                    }
                    
                    linhaAtual++;
                    estado = 0;
                    
                } else if (e_ComentarioGrande) {
                    bool acheiAsterisco = false;
                    bool acheiBarraDepois = false;

                    while(acheiAsterisco == false || acheiBarraDepois == false) {
                        pos++;
                        caractereAtual = texto[pos];

                        if(caractereAtual == '*') {
                            acheiAsterisco = true;

                        } else if (acheiAsterisco && caractereAtual == '/') {
                            acheiBarraDepois = true;

                        } else {
                            acheiAsterisco = false;
                            acheiBarraDepois = false;
                        }
                    }

                } else {
                    token = new Token(OPERADOR, DIVISAO);

                    return token;

                }

                pos++;
                break;
            }

            case 5: { // Veio de 0, por ser '='

                bool e_Igualdade = caractereAtual == '=';

                if(e_Igualdade) {
                    token = new Token(OPERADOR, IGUALDADE);

                } else {
                    token = new Token(OPERADOR, ATRIBUICAO);
                    pos--;

                }

                pos++;
                return token;
            }

            case 6: { // Veio de 0, por ser '!'

                bool e_Diferente = caractereAtual == '=';

                if(e_Diferente) {
                    token = new Token(OPERADOR, DIFERENTE);

                } else {
                    token = new Token(OPERADOR, NEGACAO);
                    pos--;
                }

                pos++;
                return token;
            }

            case 7: {// Veio de 1 por ser System. 

                bool e_Letra = (isalpha(caractereAtual));
                bool e_SystemOut = (lexeme.compare("System.out") == 0);

                if(e_Letra) { // Entrara aqui ate achar .
                    lexeme += caractereAtual;

                } else if(caractereAtual == '.' && e_SystemOut) {
                    lexeme += caractereAtual;
                    estado = 8;
                    
                } else {
                    erroLexico("Erro ao analisar a palavra reservada System.out.println");
                    
                }

                pos++;
                break;
            }

            case 8:{ // Veio de 7 por ser System.out.

                bool e_Letra = isalpha(caractereAtual);
                bool e_SystemOutPrintln = strcmp(lexeme.c_str(), "System.out.println") == 0;
                e_SystemOutPrintln = e_SystemOutPrintln && (isspace(caractereAtual) == false || caractereAtual != '\n');

                if (e_SystemOutPrintln) {
                    token = new Token(SYSTEM_OUT_PRINTLN);
                    return token;

                } else if(e_Letra) { 
                    lexeme += caractereAtual;

                } else {
                    cout << lexeme << endl;
                    erroLexico("Erro ao analisar a palavra reservada System.out.println");
                }

                pos++;
                break;
            }


        }
    }

}

Token* Scanner::tokensCase0(char caractereAtual) {
    Token* token = NULL;

    switch (caractereAtual) {
        case '<': token = new Token(OPERADOR, MENOR_Q); break;
        case '>': token = new Token(OPERADOR, MAIOR_Q); break;
        case '+': token = new Token(OPERADOR, ADICAO); break;
        case '-': token = new Token(OPERADOR, SUBTRACAO); break;
        case '*': token = new Token(OPERADOR, MULTIPLICACAO); break;
        case '(': token = new Token(SEPARADOR, ABRE_PARENTESES); break;
        case ')': token = new Token(SEPARADOR, FECHA_PARENTESES); break;
        case '[': token = new Token(SEPARADOR, ABRE_COLCHETE); break;
        case ']': token = new Token(SEPARADOR, FECHA_COLCHETE); break;
        case '{': token = new Token(SEPARADOR, ABRE_CHAVES); break;
        case '}': token = new Token(SEPARADOR, FECHA_CHAVES); break;
        case ';': token = new Token(SEPARADOR, PONTO_VIRGULA); break;
        case '.': token = new Token(SEPARADOR, PONTO_FINAL); break;
        case ',': token = new Token(SEPARADOR, VIRGULA); break;

    }

    return token;
}

void Scanner::erroLexico(string msg) {
    cout << "Linha "<< linhaAtual << ": " << msg << endl;
    
    exit(EXIT_FAILURE);
}
