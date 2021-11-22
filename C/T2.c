/**************************************************
*
* Nome do(a) estudante: Matheus Felipe Alves Durães, Gian Guilherme Carvalho Nunes
* Trabalho 2
* Professor(a): Diego Padilha Rubert
*
*/

// Importando todas as bibliotecas necessárias
#include <stdio.h> // Biblioteca básica da linguagem C
#include <stdlib.h> // Biblioteca necessária para alocar espaços na memoria para a lista duplamente encadeada
#include <string.h> // Biblioteca necessária para poder copiar a string para dentro do registro

#define MAX_OP 3 // Numero maximo para operação
#define MAX_LINE 128 // Numero maximo da linha

typedef struct instr {
    char op[MAX_OP+1];    // Operação a ser feita
    char reg1[MAX_OP+1];  // Registrador do operando 1 quando registrador
    
    int val1;             // Valor do operando 1 quando inteiro
    char reg2[MAX_OP+1];  // Registrador do operando 2 quando registrador
    
    struct instr *prev;   // Celula anterior
    struct instr *next;   // Proxima celula

} instruction; // Definicao do nome do registro

/*  Funcao insere: tem como parametro um registro novo a ser inserido e um ponteiro para a lista
    , assim inserindo na ultima posicao o novo registro da funcao */
void insere(instruction *nova, instruction *lista) { 
    
    // Inicializa 2 ponteiros para celulas de instrucao
    instruction *p, *q;

    // "p" vai ser o inserido e "q" vai ser nosso guia para o fim da lista de instrucao 
    p = lista;
    q = lista->next;

    // Aqui ele vai procurar o fim da lista, até que "q" seja igual a null, pois assim "p" sera o ultimo registro
    while (q != NULL) {
        p = q;
        q = q->next;
    }

    // Aqui define que a nova celula vai apontar para "p" e para null
    nova->prev = p;
    nova->next = q;

    // E agora "p" vai apontar para nova, o novo registro
    p->next = nova;
    
    // E retorna para a funcao de leitura
    return;
}

/* Funcao leitura_capivarica: tem como parametro o ponteiro para acessar a lista, 
    e o ponteiro do vetor que veio na linha de comando, no caso usaremos isso para acessar
    o arquivo .cap para ser lido, com isso guardaremos as informações dos comandos a serem
    reproduzidos, assim como um compilador */
void leitura_capivarica(instruction *lista, char *argv[]) {

    int control_op = 0, control_insere = 0; // Inicialização das variaveis inteiras
    char command[MAX_OP+1]; // Inicio do vetor de comando

    FILE *file; // Ponteiro do arquivo

    file = fopen(argv[1],"r"); // Abrindo o arquivo para leitura

    instruction *nova; // Registro da instrucao
    nova = (instruction *) malloc(sizeof (instruction)); // Alocamento de espaco na memoria

    // Enquanto a leitura nao chegar ao fim de arquivo, continue lendo atraves de string, de no max 3 caracteres
    while (fscanf(file, " %s", command) != EOF) {

        // Caso ele ache um #, vai ler e ignorar completamente o que vier a seguir
        if (command[0] == '#'){
			fscanf(file, "%*[^\n] ");

        } else { // Senão, é uma operação que precisa ser preenchida

            // Este primeiro if verifica se é algum tipo de operacao valida
            if(command[1] == 'o' || command[1] == 'd' || command[1] == 'u' || command[1] == 'i' || command[1] == 'm' || command[1] == 'e' || command[1] == 'l'|| command[1] == 'g' || command[1] == 'r'){

                // Se for uma operacao valida ele ira verificar se ha alguma instrucao anterior
                // a ser colocada na lista de instrucoes
                if(control_insere == 1){

                    // Caso a instrucao va somente ate o reg1/val1 
                    if(control_op == 1){
                        // Ele ira atribuir "?" para o reg2, para nao termos lixo de memoria
                        strcpy(nova->reg2, "?");
                    }

                    // Depois de tudo arrumado ele insere a instrucao dentro da lista
                    insere(nova, lista);

                    // Ele aloca uma nova instrucao para a lista de instrucoes
                    nova = (instruction *) malloc(sizeof (instruction));
                }

                strcpy(nova->op, command); // Define a operação a ser feita
                control_op = 0; // Reseta o controlador de operacoes
                control_insere = 1; // Define insercao para 1

            } else if (control_op == 0){
                // Ele pode ser tanto numero quanto letra
                // Se for numero pode ter + - , por isso temos que converte-lo
                if(command[0] == '+' || command[0] == '-' || (command[0] >= '0' && command[0] <= '9') ){
                    // Ele transforma a string em inteiro
                    nova->val1 = atoi(command);
                    // E entao ele define o reg1 como "?", para evitar lixo de memoria  
                    strcpy(nova->reg1, "?");

                } else {
                    // Se nao for numero, entao ele copia a string para o reg1
                    strcpy(nova->reg1, command);
                    // E define val1 como zero, para evitar lixo de memoria
                    nova->val1 = 0;

                }
                // Define o controlador como 1, para representar que recebeu 1 parametro
                control_op = 1;

            } else if (control_op == 1){ // Se o controlador ja tiver recebido 1 parametro, ele recebera o segundo, caso tambem nao haja uma nova operacao

                strcpy(nova->reg2, command); // O segundo parametro ele so e string por isso so copia para reg2
                control_op = 2; // E define o controlador como 2, para representar que recebeu 2 parametros, assim evitando de sobrescrever com "?"

            }
        }
    }

    // Apos terminar de ler o arquivo.cap , ele termina de inserir a ultima instrucao para a lista de instrucoes
    if(control_insere == 1){
        if(control_op == 1){
            strcpy(nova->reg2, "?");
        }

        insere(nova, lista);
    }

    // E por fim fecha o arquivo .cap
    fclose(file);

    // E retorna a main 
    return;
}

void mov(int copy, int *paste){ // Funcao mov: Copia o valor do 1º operando para o 2º operando
    *paste = copy;
    return; // Retorna a funcao de execucao
} 

void add(int *acc, int adicionar){ // Funcao add: Adiciona o valor do 1º operando ao valor no registrador acc e armazena o resultado de volta em acc
    *acc += adicionar;
    return; // Retorna a funcao de execucao
}

void sub(int *acc, int subtrair){ // Funcao sub: Subtrai do valor no registrador acc o valor do 1º operando e armazena o resultado de volta em acc.
    *acc -= subtrair;
    return; // Retorna a funcao de execucao
}

void mul(int *acc, int multiplica){ // Funcao mul: Multiplica o valor do 1º operando pelo valor no registrador acc e armazena o resultado de volta em acc.
    *acc *= multiplica;
    return; // Retorna a funcao de execucao
}

void dive(int *acc, int divisao){ // Funcao dive: Divide o valor no registrador acc pelo valor do 1º operando e armazena o resultado de volta em acc.
    *acc /= divisao;
    return; // Retorna a funcao de execucao
}

void mod(int *acc, int mod){ // Funcao mod: Calcula o resto da divisão do valor no registrador acc pelo valor do 1º operando e armazena o resultado de volta em acc.
    *acc %= mod;
    return; // Retorna a funcao de execucao
}

void jmp(instruction **lista, int movimentos, int *pc){ // Funcao jmp: Avança no programa a quantidade de instruções definida no 1º operando (se positivo) ou retrocede (se negativo), atualizando indiretamente o registrador pc. O 1º operando nunca será 0.
    
    // Verifica se movimentos e positivo ou negativo
    if(movimentos > 0){
        // Enquanto movimentos nao chegar a 0
        while(movimentos != 0){
            // Faz a lista avancar e ao mesmo tempo registra o avanco aumentando pc
            *lista = (*lista)->next;
            (*pc)++;

            // Diminui a quantidade de movimentos
            movimentos--;
            
        }
        
    } else {
        // Enquanto movimentos nao chegar a 0
        while(movimentos != 0){
            // Faz a lista voltar e ao mesmo tempo registra a sua volta diminuindo pc
            *lista = (*lista)->prev;
            (*pc)--;

            // Como movimentos e negativo, ele aumenta ate que chegue a 0
            movimentos++;
                
        }

    }

    return; // Retorna a funcao que foi chamada
    // Pois jmp e chamado em outra funcoes
}

void jeq(instruction **lista, int *acc, int movimentos, int *pc){ // Funcao jeq: Como jmp, mas apenas se o valor no registrador acc é igual a 0.
    // Se acc for igual a 0, executa o jmp
    if(*acc == 0){
        jmp(lista, movimentos, pc); 

    } else {
        // Senao ele avanca a lista juntamente ao pc
        *lista = (*lista)->next;
        (*pc)++;
    }

    return; // Retorna a funcao de execucao
}

void jlt(instruction **lista, int *acc, int movimentos, int *pc){ //Como jmp, mas apenas se o valor no registrador acc é menor que 0.
    // Se acc for menor que 0, executa o jmp
    if(*acc < 0){
        jmp(lista, movimentos, pc);
    } else {
        // Senao ele avanca a lista juntamente ao pc
        *lista = (*lista)->next;
        (*pc)++;
    }

    return; // Retorna a funcao de execucao
}

void jgt(instruction **lista, int *acc, int movimentos, int *pc){ //Como jmp, mas apenas se o valor no registrador acc é maior que 0.
    // Se acc for maior que 0, executa o jmp
    if(*acc > 0){
        jmp(lista, movimentos, pc);
    } else {
        // Senao ele avanca a lista juntamente ao pc
        *lista = (*lista)->next;
        (*pc)++;
    }

    return; // Retorna a funcao de execucao
} 

void prt(int valor){ //Exibe na tela o valor do 1º operando.
    printf("%d\n", valor);
}

int *qual_reg(char letra, int *acc, int *data, int *ext, int *pc){ // Função para determinar qual registrador utilizar

    // Verificacao de qual ponteiro de variavel mandar
    if(letra == 'a'){
        return acc;
    } else if(letra == 'd'){
        return data;
    } else if(letra == 'e'){
        return ext;
    } else {
        return pc;
    }

}

void executar(instruction *lista){ // Funcao executar: Basicamente executa o codigo

    // Inicializacao de variaveis interias, e ponteiros para inteiros
    int acc, data, ext, pc;
    int *pont_reg1, *pont_reg2;

    // Variaveis recebem 0
    acc = data = ext = pc = 0;

    // Enquanto a lista nao acabar, execute...
    while(lista != NULL){
        
        // Se a primeira letra do operador for "m"
        if(lista->op[0] == 'm'){

            
            if(lista->op[2] == 'v'){ // Se a terceira letra do operador for "v"
                // Pega o ponteiro da variavel, escrita no registrador 2
                pont_reg2 = qual_reg(lista->reg2[0], &acc, &data, &ext, &pc);

                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    // Executa a funcao com o val1, ja que nao ha reg1
                    // Junto a variavel de reg2
                    mov(lista->val1, pont_reg2);

                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    // Executa a funcao com a variavel do reg1
                    // Junto a variavel de reg2
                    mov(*pont_reg1, pont_reg2);
                }

            } else if(lista->op[2] == 'd'){ // Se a terceira letra do operador for "d"
                
                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    // Passa o ponteiro da variavel acc, junto ao valor de val1
                    mod(&acc, lista->val1);
                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    // Passa o ponteiro da variavel acc, junto ao ponteiro da variavel classificada por reg1
                    mod(&acc, *pont_reg1);

                }

            } else {
                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    // Passa o ponteiro da variavel acc, junto ao valor de val1
                    mul(&acc, lista->val1);
                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    // Passa o ponteiro da variavel acc, junto ao ponteiro da variavel classificada por reg1
                    mul(&acc, *pont_reg1);

                }
            }

            // Avanca a lista e aumenta o valor de pc
            lista = lista->next;
            pc++;

        } else if(lista->op[0] == 'j'){ // Se a primeira letra do operador for "j"

            if(lista->op[1] == 'm'){ // Se a segunda letra do operador for "m"
                // Executa a funcao de pular
                jmp(&lista, lista->val1, &pc);

            } else if (lista->op[1] == 'e'){ // Se a segunda letra do operador for "e"
                jeq(&lista, &acc, lista->val1, &pc);

            } else if (lista->op[1] == 'l'){ // Se a segunda letra do operador for "l"
                jlt(&lista, &acc, lista->val1, &pc);
                
            } else { // Senao for nenhum deles entao e a funcao jgt
                jgt(&lista, &acc, lista->val1, &pc);
                
            }

        } else {

            if(lista->op[0] == 'a'){ // Se a primeira letra do operador for "a"
                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    add(&acc, lista->val1);
                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    add(&acc, *pont_reg1);

                }
            
            } else if(lista->op[0] == 's'){ // Se a primeira letra do operador for "s"
                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    sub(&acc, lista->val1);
                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    sub(&acc, *pont_reg1);

                }

            } else if(lista->op[0] == 'd'){ // Se a primeira letra do operador for "d"
                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    dive(&acc, lista->val1);
                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    dive(&acc, *pont_reg1);

                }

            } else {
                // Se reg1 for igual a "?" significa que nao ha registro
                if(lista->reg1[0] == '?'){
                    prt(lista->val1);
                } else {
                    // Pega o ponteiro da variavel, baseado no reg1
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    prt(*pont_reg1);

                }
            }

            // Avanca a lista e aumenta o valor de pc
            lista = lista->next;
            pc++;
        } 

    }

}

int main (int argc, char *argv[]) {
    
    //Declarando a lista duplamente encadeada com cabeça
    instruction *lista;
    lista = (instruction *) malloc(sizeof (instruction));
    lista->prev = NULL;
    lista->next = NULL;
 
    // Primeiro vamos ler o arquivo de texto passado via linha de comando,
    // Nesta mesma função ele irá alocar as operações dentro da lista duplamente encadeada;
    leitura_capivarica(lista, argv);

    // Após a leitura e alocação para a lista iremos executar as operações que foram setadas
    executar(lista->next);

}
