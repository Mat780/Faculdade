/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include <iostream>
#include <vector>
#include "analizadorLexico.h"

class parser {
    public: 
        // O analizador lexico
        Scanner analizadorLexico;

        // Token para as comparações
        Token* token;

        // Comandos atuais, antes de serem colocados no vetor
        Comando* comandoAtual;
        ComandoSimples* comandoSimplesAtual;

        vector<Comando*> vetorDeComandos;

        // Variaveis de controle
        bool entradaViaTeclado;
        bool temErroSintatico;

        // Construtor
        parser();

        // Metodo para inserir o comando no vetor
        void inserirComandoNoVetor(Comando* comando);

        // A própria analise sintatica
        vector<Comando*> analizeSintatica(string linhaDeComando);

        // Metodos para saber o token esperado corresponde ao token atual
        bool match(int tokenEsperado);
        bool match_Atr(int tokenEsperado);

        // Produções
        void LINHA_DE_COMANDO();
        void COMANDO_E_ARGUMENTOS();
        void PIPES();
        void MODIFICADORES_ENTRADA_SAIDA();
        void EXE_BACKGROUND();

};