/**************************************************
*
* Nome do(a) estudante: Matheus Felipe Alves Durães, Gian Guilherme Carvalho Nunes
* Trabalho 2
* Professor(a): Diego Padilha Rubert
*
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_OP 3 // Numero maximo para operação
#define MAX_LINE 128 // Numero maximo da linha

typedef struct instr {
    char op[MAX_OP+1];    // Operação a ser feita
    char reg1[MAX_OP+1];  // Registrador do operando 1 quando registrador
    
    int val1;             // Valor do operando 1 quando inteiro
    char reg2[MAX_OP+1];  // Registrador do operando 2 quando registrador
    
    struct instr *prev;   // Celula anterior
    struct instr *next;   // Proxima celula

} instruction;

void insere(instruction *nova, instruction *lista) { 
    
    instruction *p, *q;

    p = lista;
    q = lista->next;

    while (q != NULL) {
        p = q;
        q = q->next;
    }

    nova->prev = p;
    nova->next = q;
    p->next = nova;
    
    if (q != NULL) {
        q->prev = nova;
    }
}

void ler_aquivo(instruction *lista, char *argv[]) {

    int control_op = 0, inteiro, control_insere = 0;
    char command[MAX_OP+1];

    FILE *file; // Ponteiro do arquivo

    file = fopen(argv[1],"r"); // Abrindo o arquivo para leitura

    instruction *nova;
    nova = (instruction *) malloc(sizeof (instruction));

    while (fscanf(file, " %s", command) != EOF) {

        // Caso ele ache um #, vai ler e ignorar completamente o que vier a seguir
        if (command[0] == '#'){
			fscanf(file, "%*[^\n] ");

        } else { // Senão, é uma operação que precisa ser preenchida

            if(command[1] == 'o' || command[1] == 'd' || command[1] == 'u' || command[1] == 'i' || command[1] == 'm' || command[1] == 'e' || command[1] == 'l'|| command[1] == 'g' || command[1] == 'r'){

                if(control_insere == 1){

                    if(control_op == 1){
                        strcpy(nova->reg2, "?");
                    }

                    insere(nova, lista);

                    nova = (instruction *) malloc(sizeof (instruction));

                    control_insere = 0;
                }

                strcpy(nova->op, command); // Define a operação a ser feita
                control_op = 0;
                control_insere = 1;

            } else if (control_op == 0){
                // Ele pode ser tanto numero quanto letra
                // Se for numero pode ter + - , a gente precisa converter isso
                if(command[0] == '+' || command[0] == '-' || (command[0] >= '0' && command[0] <= '9') ){
                    inteiro = atoi(command);
                    nova->val1 = inteiro;
                    strcpy(nova->reg1, "?");

                } else {
                    strcpy(nova->reg1, command);
                    nova->val1 = 0;

                }
                control_op = 1;

            } else if (control_op == 1){
                // Segundo registrador
                strcpy(nova->reg2, command);
                control_op = 2;

            }
        }
    }

    if(control_insere == 1){
        if(control_op == 1){
            strcpy(nova->reg2, "?");
        }

        insere(nova, lista);
    }

    fclose(file);
}

void mov(int copy, int *paste){ // Copia o valor do 1º operando para o 2º operando
    *paste = copy;
} 

void add(int *acc, int adicionar){ //Adiciona o valor do 1º operando ao valor no registrador acc e armazena o resultado de volta em acc
    *acc += adicionar;
}

void sub(int *acc, int subtrair){ //Subtrai do valor no registrador acc o valor do 1º operando e armazena o resultado de volta em acc.
    *acc -= subtrair;
    
}

void mul(int *acc, int multiplica){ //Multiplica o valor do 1º operando pelo valor no registrador acc e armazena o resultado de volta em acc.
    *acc *= multiplica;
}

void dive(int *acc, int divisao){ //Divide o valor no registrador acc pelo valor do 1º operando e armazena o resultado de volta em acc.
    *acc /= divisao;

}

void mod(int *acc, int mod){ // Calcula o resto da divisão do valor no registrador acc pelo valor do 1º operando e armazena o resultado de volta em acc.
    *acc %= mod;

}

void jmp(instruction **lista, int movimentos, int *pc){ //Avança no programa a quantidade de instruções definida no 1º operando (se positivo) ou retrocede (se negativo), atualizando indiretamente o registrador pc. O 1º operando nunca será 0.
    
    if(movimentos > 0){
        while(movimentos != 0){
            *lista = (*lista)->next;
            (*pc)++;
            movimentos--;
            //printf("Movimentos: %d\n PC: %d\n", movimentos, *pc);

        }
        
    } else {
        while(movimentos != 0){
            *lista = (*lista)->prev;
            (*pc)--;
            movimentos++;
                
        }

    }
    
}

void jeq(instruction **lista, int *acc, int movimentos, int *pc){ // Como jmp, mas apenas se o valor no registrador acc é igual a 0 .
    if(*acc == 0){
        jmp(lista, movimentos, pc);
    } else {
        *lista = (*lista)->next;
        (*pc)++;
    }
}

void jlt(instruction **lista, int *acc, int movimentos, int *pc){ //Como jmp, mas apenas se o valor no registrador acc é menor que 0.
    if(*acc < 0){
        jmp(lista, movimentos, pc);
    } else {
        *lista = (*lista)->next;
        (*pc)++;
    }
}

void jgt(instruction **lista, int *acc, int movimentos, int *pc){ //Como jmp, mas apenas se o valor no registrador acc é maior que 0.
    if(*acc > 0){
        jmp(lista, movimentos, pc);
    } else {
        *lista = (*lista)->next;
        (*pc)++;
    }
} 

void prt(int valor){ //Exibe na tela em uma linha o valor do 1º operando.
    printf("%d\n", valor);
}

int *qual_reg(char letra, int *acc, int *data, int *ext, int *pc){

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

void executar(instruction *lista){

    int acc, data, ext, pc;
    int *pont_reg1, *pont_reg2;
    acc = data = ext = pc = 0;

    while(lista != NULL){
        
        if(lista->op[0] == 'm'){

            if(lista->op[2] == 'v'){
                pont_reg2 = qual_reg(lista->reg2[0], &acc, &data, &ext, &pc);

                if(lista->reg1[0] == '?'){
                    mov(lista->val1, pont_reg2);

                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    mov(*pont_reg1, pont_reg2);
                }

            } else if(lista->op[2] == 'd'){
                
                if(lista->reg1[0] == '?'){
                    mod(&acc, lista->val1);
                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    mod(&acc, *pont_reg1);

                }

            } else {
                if(lista->reg1[0] == '?'){
                    mul(&acc, lista->val1);
                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    mul(&acc, *pont_reg1);

                }
            }

            lista = lista->next;
            pc++;

        } else if(lista->op[0] == 'j'){

            if(lista->op[1] == 'm'){
                jmp(&lista, lista->val1, &pc);

            } else if (lista->op[1] == 'e'){
                jeq(&lista, &acc, lista->val1, &pc);

            } else if (lista->op[1] == 'l'){
                jlt(&lista, &acc, lista->val1, &pc);
                
            } else {
                jgt(&lista, &acc, lista->val1, &pc);
                
            }

        } else {

            if(lista->op[0] == 'a'){
                if(lista->reg1[0] == '?'){
                    add(&acc, lista->val1);
                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    add(&acc, *pont_reg1);

                }
            
            } else if(lista->op[0] == 's'){
                if(lista->reg1[0] == '?'){
                    sub(&acc, lista->val1);
                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    sub(&acc, *pont_reg1);

                }

            } else if(lista->op[0] == 'd'){
                if(lista->reg1[0] == '?'){
                    dive(&acc, lista->val1);
                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    dive(&acc, *pont_reg1);

                }

            } else {
                if(lista->reg1[0] == '?'){
                    prt(lista->val1);
                } else {
                    pont_reg1 = qual_reg(lista->reg1[0], &acc, &data, &ext, &pc);
                    prt(*pont_reg1);

                }
            }

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
 
    ler_aquivo(lista, argv);
    executar(lista->next);

}
