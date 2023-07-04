/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

#include "analizadorSintatico.h"
#include <string.h>

/* Construtor do parser */
parser::parser() {
    this->temErroSintatico = false;
    this->entradaViaTeclado = false;

    this->analizadorLexico = Scanner();

    this->token = NULL;
    this->comandoAtual = NULL;
    this->comandoSimplesAtual = NULL;

};

/* Metodo para inserção de comandos no vetor */
void parser::inserirComandoNoVetor(Comando* comando) {
    vetorDeComandos.push_back(comando);
};

/* Metodo da analize sintática do comando passado 
    Se correto passa o vetor de comandos com todos os comandos prontos para execução
    Se recebe uma linha vazia, ele retorna o vetor com um comando vazio que é ignorado
    Se os comandos passados estiverem errados ele retorna um vetor vazio
*/
vector<Comando*> parser::analizeSintatica(string linhaDeComando) {

    vector<Comando*> auxiliar;

    int tamanhoDoVetorDeComandos = vetorDeComandos.size();

    /* Liberação, se necessária, dos comandos anteriores */
    if (tamanhoDoVetorDeComandos >= 1) {
        for (int i = 0; i < tamanhoDoVetorDeComandos; i++) {
            free(vetorDeComandos[i]->comandoSimples->argumentos);
            free(vetorDeComandos[i]->comandoSimples);
            free(vetorDeComandos[i]);
        }
    }

    vetorDeComandos = auxiliar;
    temErroSintatico = false;

    /* Ajuste e chamada do analizador lexico */
    analizadorLexico.novoComando(linhaDeComando);
    token = analizadorLexico.nextToken();

    /* Inicio da analize sintática */
    LINHA_DE_COMANDO();

    if (temErroSintatico) {
        vector<Comando*> vetorVazio;
        return vetorVazio;
    }

    else {
        return vetorDeComandos;
    }

};

/* Metodo que verifica se o token esperado é o mesmo que o token atual */
bool parser::match(int tokenEsperado) {
    bool resposta = tokenEsperado == token->name;

    return resposta;
};

/* Metodo que verifica se o atributo do token esperado é o mesmo que o atributo do token atual */
bool parser::match_Atr(int tokenEsperado) {
    bool resposta = tokenEsperado == token->atributo;

    return resposta;
};

void parser::LINHA_DE_COMANDO() {

    if (match(NOVA_LINHA)) {
        inserirComandoNoVetor(new Comando());
        return;
    }

    else {

        COMANDO_E_ARGUMENTOS();

        inserirComandoNoVetor(comandoAtual);

        MODIFICADORES_ENTRADA_SAIDA();
        EXE_BACKGROUND();
    }

};

/* Metodo responsavel por verificar os comandos e argumentos, juntamente dos pipes */
void parser::COMANDO_E_ARGUMENTOS() {

    // Se possuir erro sintatico, encerre a analize
    if (temErroSintatico) {
        return;
    }

    int tamanhoDoVetor = token->lexeme.length() + 1;

    // Pega o comando
    if (match(TEXTO)) {
        comandoSimplesAtual = new ComandoSimples(token->lexeme.c_str(), tamanhoDoVetor);
        token = analizadorLexico.nextToken();

    } else {
        temErroSintatico = true;
        return;
    }

    // Pega todos os parametros
    while (match(TEXTO) || match(ARGUMENTO)) {
        tamanhoDoVetor = token->lexeme.length() + 1;
        comandoSimplesAtual->inserirArgumento(token->lexeme.c_str(), tamanhoDoVetor);
        token = analizadorLexico.nextToken();

    }

    // Caso já exista um comando, ele será adicionado ao vetor para dar vaga a um novo comando
    if (comandoAtual != NULL) {
        inserirComandoNoVetor(comandoAtual);
    }

    // Cria o novo comando e coloca ele como atual
    Comando* comando = new Comando();
    comandoAtual = comando;

    comandoAtual->comandoSimples = comandoSimplesAtual;

    /* Verifica se há pipes ou não */
    PIPES();

};

/* Metodo responsavel por verificar se há pipes entre os comandos ou não */
void parser::PIPES() {

    /* Caso haja pipes ele colocara o nome */
    if (match(PIPE)) {

        token = analizadorLexico.nextToken();

        COMANDO_E_ARGUMENTOS();

    } else {
        ;
    }

};

/* Metodo que verifica se há modificadores de entrada e saida */
void parser::MODIFICADORES_ENTRADA_SAIDA() {

    // Se possuir erro sintatico, encerre a analize
    if (temErroSintatico) {
        return;
    }

    if (match(MODIFICADOR)) {

        /* Puxa o token a frente do modificador para saber o nome do arquivo
            que redirecionaremos ou a entrada ou a saida.
        */
        Token* tokenAfrente = analizadorLexico.nextToken(); 
        int tamanhoVetor = tokenAfrente->lexeme.length() + 1;
        char vetorChar[tamanhoVetor];

        // Caso o token a frente não seja um texto significa que houve um erro
        if(tokenAfrente->name != TEXTO) {
            temErroSintatico = true;
            return;
        }

        strncpy(vetorChar, tokenAfrente->lexeme.c_str(), tamanhoVetor);

        if (match_Atr(ENTRADA_VIA_ARQUIVO)) {
            vetorDeComandos[0]->arquivoEntrada = vetorChar;
        } 

        //! Ele reconhece a entrada via teclado do comando passado, porém não pega a entrada via teclado.
        else if (match_Atr(ENTRADA_VIA_TECLADO)) {
            //TODO Mudança no lexico e na shell, para caso seja via teclado pegar o input desejado via teclado
            // Coloca o ponto de parada provisoriamente no arquivo de entrada, depois iremos pegar o texto
            // E varrer em busca da linha contendo apenas o ponto de parada para usar de entrada 
            vetorDeComandos[0]->arquivoEntrada = vetorChar;
            entradaViaTeclado = true;

        }

        else {
            int quantidadeDeComandos = vetorDeComandos.size();

            if (match_Atr(SAIDA_SOBRESCRITA)) {
                vetorDeComandos[quantidadeDeComandos - 1]->arquivoSaida = vetorChar;
            }

            else if (match_Atr(SAIDA_ERRO)) {

                int tamanhoVetorComandos = vetorDeComandos.size();
                for (int i = 0; i < tamanhoVetorComandos; i++) {
                    vetorDeComandos[i]->arquivoErro = vetorChar;

                }

            }

        }

        token = analizadorLexico.nextToken();

        MODIFICADORES_ENTRADA_SAIDA();

    } else {
        ;
    }

};

//! Reconhece o "&" , porém não foi implementado parar rodar em background.
/* Metodo que reconhece se a execução será em background ou não */
void parser::EXE_BACKGROUND() {

    // Se possuir erro sintatico, encerre a analize
    if (temErroSintatico) {
        return;
    }

    if (match(BACKGROUND)) {

        int tamanhoVetor = vetorDeComandos.size();

        for (int i = 0; tamanhoVetor > i; i++) {
            vetorDeComandos[i]->background = true;
        }

    } else {
        ;
    }

};