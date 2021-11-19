/**************************************************
*
* Nome do(a) estudante: Giovanna Rodrigues Mendes e Roney Felipe de Oliveira Miranda
* Trabalho 2
* Professor(a): Diego Padilha Rubert
*
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_OP 3
#define MAX_LINE 128

typedef struct instr {
    char op[MAX_OP+1];    /* Operação */
    char reg1[MAX_OP+1];  /* Registrador do operando 1, se registrador */
    
    int val1;             /* Valor do operando 1, se inteiro */
    char reg2[MAX_OP+1];  /* Registrador do operando 2, se houver */
    
    struct instr *prev;   /* Anterior */
    struct instr *next;   /* Próximo */
} instruction;


void imprime_lista(instruction *lista) {

    instruction *p;

    for (p = lista->next; p != NULL; p = p->next) {
        printf("\nOperacao: ");
        
        printf("%s\n", p->op);
            
        printf("Operadores: \n");
        printf("Operador 1: ");
        printf("%s", p->reg1);

        printf("\nOperador 2: ");
        printf("%s", p->reg2);

        printf("\n--------------FIM DA LINHA----------------");
    }
}


void busca_insere_lista(instruction *nova, instruction *lista) { 
    
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

    int cont = 0, aux = 0, aux2 = 0;
    char caracter;

    FILE *f; // Ponteiro para andar pelo arquivo

    f = fopen(argv[1],"r"); //Comando para abrir o arquivo 

    while (fscanf(f, "%c", &caracter) != EOF) {

        /*Criação da célula*/
        
        instruction *nova;
        nova = (instruction *) malloc(sizeof (instruction));

        int pulei_linha = 1;

        //Ler cada linha
        while (caracter != '\n') {

            if (caracter == 35) {

                while(caracter != '\n') {
                    fscanf(f, "%c", &caracter);
                }
                pulei_linha = 0;
            }
            
            else if (caracter != 32) {
                
                if(cont < 3) {
                    nova->op[cont] = caracter;
                    cont++;
                }

                else if (aux == 1) {
                    nova->reg1[aux2] = caracter;
                    nova->reg1[aux2+1] = '\0';
                    nova->reg2[aux2] = '\0';
                    aux2++;
                }
                else if (aux == 2) {
                    nova->reg2[aux2] = caracter;
                    nova->reg2[aux2+1] = '\0';
                    aux2++;
                }
            }
            //Os espaços em branco caem aqui
            else if ((caracter == 32) && (cont > 2)) {
                aux++;
                aux2 = 0;
            }

            if (pulei_linha) {
                fscanf(f, "%c", &caracter);
            }
        }

        pulei_linha = 1;

        if (aux != 0) {
            busca_insere_lista(nova, lista);
        }

        cont = 0, aux = 0, aux2 = 0;

    }

    imprime_lista(lista);

    fclose(f);

}

int main (int argc, char *argv[]) {
    
    //Declarando a lista duplamente encadeada com cabeça
    instruction *lista;
    lista = (instruction *) malloc(sizeof (instruction));
    lista->prev = NULL;
    lista->next = NULL;

    ler_aquivo(lista, argv);


}
