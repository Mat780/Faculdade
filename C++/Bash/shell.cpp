/*
Alunos: Giovanna Rodrigues Mendes e Matheus Felipe Alves Durães
RGAs: 2021.1904.032-7 e 2021.1904.008-4
Docente: Ronaldo Alves Ferreira
Disciplina: Sistemaas Operacionais -P01-2023-1
*/

// Cabeçalho das bibliotecas que necessitaremos.
#include <iostream>
#include <fstream>
#include <sstream>

#include <sys/types.h>
#include <sys/wait.h>
#include <csignal>

#include <string.h>
#include <string>
#include <regex.h>
#include <dirent.h>
#include <unistd.h>
#include <fcntl.h>
#include <vector>

#include "analizadorSintatico.h"

// Definindo o tamanho máximo do Path.
#define MAX_PATH 260

// Utilizando isto para evitar std::, por exemplo e deixar o código maior.
using namespace std;

// Variáveis globais para a utilização do comando history.
int quantidadeDeComandos = 0;

// Usamos ios::out nesse caso para abrir o arquivo pela primeira vez
// Caso ele já exista ele irá apagar o conteudo anterior para escrever a execução atual.
ofstream arquivoDeHistorico(".history", ios::out);


/* Função responsável por tratar sinais provindas do teclado, como:
    - Crtl+C: não deve parar a execução do terminal;
    - Crtl+D: deve encerrar a nossa shell. 
    
Vale ressaltar que, quando o sinal for SIGQUIT, além do terminal fechar,
fechamos o .history e excluímos-o, para caso iniciar a shell novamente 
não ter resquícios de outras execuções e gastar memória sem necessidade. */
void tratadorDeSinal(int sinal) {

    if (sinal == SIGINT) {
        ;
    }

    else if (sinal == SIGQUIT) {
        arquivoDeHistorico.close();
        remove(".history");
        exit(0);

    }

    else if (sinal == SIGCHLD) {
        wait(NULL);
    }

}

/* Função responsável por adicionar o comando chamado ao .history. */
void adicionarAoHistory(string comando) {

    // Se não tiver aberto o arquivo, abrimos-o gravando ao final do aquivo (ios::app).
    if (!arquivoDeHistorico.is_open()) {
        arquivoDeHistorico.open(".history", ios::app);
    }

    // Inserindo no arquivo o comando juntamente com a sua numeração de ordem executado.
    arquivoDeHistorico << "[" << quantidadeDeComandos << "] " << comando << endl;
    quantidadeDeComandos++;
}


/* Função responsável por executar os comandos internos da shell implementados abaixo. 
Vale lembrar que, não estão presentes todas as implementações dos comandos. */
bool executarComandosInternos(ComandoSimples* comando) {

    /* Iniciando a variável de controle para ver se é ou não um comando interno.
    Se for interno, entrará em um dos corpos das condições abaixo e o valor da
    variável será true. Caso contrário, será false. */
    bool comandoInternoExecutado = false;

    /* Caso o comando seja cd, altera o path do processo atual. 
    Se encontrar .., retorna um diretório e se um nome, avança um diretório
    dependendo da ordem inserida no terminal. Também imprime na tela caso
    este não exista. */
    if (strcmp(comando->argumentos[0], "cd") == 0) {
        
        if (comando->argumentos[1] == NULL) {
		    chdir(getenv("HOME")); 
	    }

        else {
            string argsCD = comando->argumentos[1];
            int tamanhoArgsCD = argsCD.length();
            string auxiliar = "";

            for(int i = 0; i < tamanhoArgsCD; i++) {

                if (argsCD[i] != '/' && isspace(argsCD[i]) == false) {
                    auxiliar += argsCD[i];

                } else if (isspace(argsCD[i]) == false) {

                    char * vetorChar = (char *) malloc(auxiliar.length() + 1);
                    strncpy(vetorChar, auxiliar.c_str(), auxiliar.length());

                    if (chdir(vetorChar) != 0) {
                        cout << "tecii: cd: " << argsCD << ": Arquivo ou diretório inexistente" << endl;
                        exit(1);
                    }
                    
                    free(vetorChar);
                    auxiliar = "";
                }
            
            }

            if (auxiliar.empty() == false) {
                char * vetorChar = (char*) malloc(auxiliar.length() + 1);
                strncpy(vetorChar, auxiliar.c_str(), auxiliar.length());
                chdir(vetorChar);

            }

        }
	    
	    comandoInternoExecutado = true;
    }

    /* Caso seja um history, é passado para um vector os últimos comandos
    seja 50 ou menos, dependendo do valor de comandos inseridos. Ao final, 
    são impressos na tela. */
    else if (strcmp(comando->argumentos[0], "history") == 0) {
        arquivoDeHistorico.close();

        int quantidadeAtual = 50;

        ifstream arquivo(".history");
        vector<string> comandos;
        string linha;

        if (quantidadeDeComandos < 50) {
            
            for (int i = 0; i < quantidadeDeComandos; i++) {
                getline(arquivo, linha);
                comandos.push_back(linha);
            }

            quantidadeAtual = quantidadeDeComandos;
        }
        else {

            for (int i = 0; i < (quantidadeDeComandos - 50); i++) {
                getline(arquivo, linha);
            }

            for (int i = 0; i < 50; i++) {
                getline(arquivo, linha);
                comandos.push_back(linha);
            }
        }

        for (int i = 0; i < quantidadeAtual; i++) {
           cout << comandos[i] << endl;
        }

        comandoInternoExecutado = true;
    }

    /* Caso seja um echo, imprime a frase passada ou caso esteja com '$',
    ele tentará buscar uma variavel de ambiente com esse nome, retirando o '$'. */
    else if (strcmp(comando->argumentos[0], "echo") == 0) {

        for (int i = 1; i < comando->numeroDeArgumentos; i++) {

            if (strchr(comando->argumentos[i], '$') != NULL) {

                string str = string(comando->argumentos[i]);
                string new_str = "";

                for (char c: str){
                    if (c != '$' and c != '"') {
                        new_str += c;
                    }
                }

                char * varEnv = getenv(new_str.c_str());

                if (varEnv != NULL) {
                    cout << new_str + ": " << varEnv << endl;

                } else {
                    cout << "Variavel de ambiente não encontrada" << endl;
                }


            } else {
                cout << comando->argumentos[i] << endl;

            }

        }
    
        cout << endl;
        comandoInternoExecutado = true;
    }

    /* Caso o comando seja pwd, imprime na tela o valor do path atualmente. */
    else if (strcmp(comando->argumentos[0], "pwd") == 0) {
        char * path = (char*) malloc(MAX_PATH);
        getcwd(path, MAX_PATH);
        cout << path << endl;
        free(path);

        comandoInternoExecutado = true;
    }

    /* Caso seja set, printa o valor das variáveis do qual o set é responsável
    (global e local). */
    else if (strcmp(comando->argumentos[0], "set") == 0) {

        if (comando->argumentos[1] == NULL) {
            for (char** env = environ; *env != nullptr; ++env) {
                cout << *env << "\n";
            }
        }

        comandoInternoExecutado = true;
    }

    /* E por último, se for um exit, encerra a execução da shell fechando e 
    excluindo o arquivo .history. */
    else if (strcmp(comando->argumentos[0], "exit") == 0) {
        arquivoDeHistorico.close();
        remove(".history");
        exit(0);

    }

    return comandoInternoExecutado;
}

/* Função responsável por executar um comando por vez verificando se há pipes ou não.
Se houver, é criado um pipe para cada comando e há redirecionamento de entrada e saída
dos comandos. */
void comandoExecute(vector<Comando*> vetorDeComandos) {

    // Cria um pipe para cada comando, exceto o último se houver pipe.
    vector<int[2]> pipes(vetorDeComandos.size() - 1);

    for (auto& pipe: pipes) {
        if (pipe2(pipe, O_CLOEXEC) == -1) {
            perror("Erro ao colocar a flag O_CLOEXEC no pipe");
            exit(EXIT_FAILURE);
        }
    }

    // Executar cada comando em um processo separado.
    vector<pid_t> processes;

    int tamanhoDoVetorDeComandos = vetorDeComandos.size();

    for (int i = 0; i < tamanhoDoVetorDeComandos; ++i) {
        Comando* comando = vetorDeComandos[i];

        int entradaModificada = STDIN_FILENO;
        int saidaModificada = STDOUT_FILENO;

        if (i > 0) {
            entradaModificada = pipes[i-1][0];

        }

        if (i < tamanhoDoVetorDeComandos - 1) {
            saidaModificada = pipes[i][1];

        }

        bool comandoInternoExecutado = executarComandosInternos(comando->comandoSimples);

        if (comandoInternoExecutado == false) {
            pid_t pid = fork();

            if (pid == -1) { // Erro ao fazer o fork
                perror("Erro ao fazer fork");
                exit(EXIT_FAILURE);

            } else if (pid == 0) { // Processo filho

                if (entradaModificada != STDIN_FILENO) {
                    dup2(entradaModificada, STDIN_FILENO);
                    close(entradaModificada);

                } else if (comando->arquivoEntrada.empty() == false) {
                    freopen(comando->arquivoEntrada.c_str(), "r", stdin);
                }

                if (saidaModificada != STDOUT_FILENO) {
                    dup2(saidaModificada, STDOUT_FILENO);
                    close(saidaModificada);

                } else if (comando->arquivoSaida.empty() == false) {
                    freopen(comando->arquivoSaida.c_str(), "w", stdout);
                }

                if (comando->arquivoErro.empty() == false) {
                    freopen(comando->arquivoErro.c_str(), "w", stderr);
                }

                execvp(comando->comandoSimples->argumentos[0], comando->comandoSimples->argumentos);

                cout << "Erro ao executar " << comando->comandoSimples->argumentos[0] << endl;
                exit(1);

            } else { // Processo pai
                processes.push_back(pid);
            }
        } 
    }

    // Fechar a extremidade não utilizada de cada pipe.
    for (auto& pipe : pipes) {
        close(pipe[0]);
        close(pipe[1]);
    }

    // Esperar que todos os processos terminem.
    for (auto pid : processes) {
        int status;
        waitpid(pid, &status, 0);
    }

}

/* Função principal que é responsável por inserir o nome da nossa path da shell.
Ademais, chamar o analisador léxico (dentro do sintático é chamado) e o sintático.
Posteriormente, chama as funções responsáveis por adicionar o comando digitado ao
.history e executá-lo.  */
int main(int argc, char const *argv[]) {
    signal(SIGINT, tratadorDeSinal);
    signal(SIGQUIT, tratadorDeSinal);
    signal(SIGCHLD, tratadorDeSinal);

    string myPath = "tecii$ ";

    string linhaDigitada;

    while (1) {
        cout << myPath;

        getline(cin, linhaDigitada);

        parser analizadorSintatico = parser();
        vector<Comando*> vetorDeComandos;

        vetorDeComandos = analizadorSintatico.analizeSintatica(linhaDigitada);

        if (vetorDeComandos.size() == 0) {
            cout << "Comando sintaticamente errado" << endl;
        }

        else if (vetorDeComandos[0]->comandoSimples != NULL) {
            adicionarAoHistory(linhaDigitada);
            comandoExecute(vetorDeComandos);
        }

    }

    return 0;
}