#include "scanner.h"
#include <iomanip>

int main(int argc, char* argv[]) {
    // Verifica se foi executado corretamente
    // Essa main espera receber o nome do arquivo a ser
    // executado na linha de comando.
    
    //* Se tem a quantidade parâmetros necessários para iniciar a compilação
    if (argc != 2) {
        cout << "Uso: ./compiler nome_arquivo.mj\n";
        return 1;
    }

    Scanner* scanner = new Scanner(argv[1]);
    
    Token* t;

    // Os prints abaixo formam a tabela para melhor visualização dos tokens obtidos
    int numeroDeEspacos = 18;

    for (int i = 0; i < 63; i++) cout << "-";
    cout << endl;

    cout << "| " << left << setw(numeroDeEspacos) << "Token Name" << " | ";
    cout << left << setw(numeroDeEspacos) << "Token Attribute" << " | ";
    cout << left << setw(numeroDeEspacos) << "Token Lexeme" << "|" << endl;

    for (int i = 0; i < 63; i++) cout << "-";
    cout << endl;

    do {
        t = scanner->nextToken();
 
        cout << "| " << left << setw(numeroDeEspacos) << enumToString(t->name) << " | ";
        cout << left << setw(numeroDeEspacos) << enumToString(t->attribute) << " | ";
        cout << left << setw(numeroDeEspacos) << t->lexeme << "|" << endl;

    } while (t->name != FIM_DE_ARQUIVO);

    for (int i = 0; i < 63; i++) cout << "-";
    cout << endl;

    delete scanner;
    
    return 0;
}