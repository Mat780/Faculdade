#include <stdio.h>
#include <stdlib.h>
#include "pilhaFloat.h"

// Estrutura de pilha de pontos flutuantes, usado para resolver a expressão
struct pilhaFloat{
	float num;
	PilhaFloat* next;
};

// Função para criar a pilha de pontos flutuantes
PilhaFloat* pilhaFloat_criarPilha(void){
	PilhaFloat* pilha = (PilhaFloat*)malloc(sizeof(struct pilhaFloat));
	pilha->next = NULL;
	return pilha;
}

// Função para empilhar o numero na pilha de pontos flutuantes
void pilhaFloat_empilha(PilhaFloat* pilha, float num){
	PilhaFloat* novaPilha = pilhaFloat_criarPilha();
	float numFloat = num;
	novaPilha->num = numFloat;

	novaPilha->next = pilha->next;
	pilha->next = novaPilha;
}

// Função para desempilhar o numero no topo da pilha de pontos flutuantes
float pilhaFloat_desempilha(PilhaFloat* pilha){
	if(pilha->next != NULL){
		PilhaFloat* auxiliar;
		auxiliar = pilha->next;
		pilha->next = pilha->next->next;
		return auxiliar->num;
	} else {
		printf("Pilha vazia\n");
		return 0;
	}
}

// Função para desempilhar o numero no topo da pilha juntamente com um operador
// Explicando melhor: Ele desempilha 2 numeros, e os junta através do operador que é dado para a função retornando o resultado da operação
float pilhaFloat_desempilhaComOp(PilhaFloat* pilha, char op){
	float n2 = pilhaFloat_desempilha(pilha);
	float n1 = pilhaFloat_desempilha(pilha);

	switch(op){
		case '+':
			return n1 + n2;
			break;
		case '-':
			return n1 - n2;
			break;
		case '*':
			return n1 * n2;
			break;
		case '/':
			return n1 / n2;
			break;
		default:
			printf("Operador invalido");
			break;		
	}

	return 0;
}

// Função para mostrar a pilha de pontos flutuantes
void pilhaFloat_mostrarPilha(PilhaFloat* pilha){
	PilhaFloat* auxiliar = pilha->next;
	printf("--------- PILHA: ---------\n");
	for(int c = 0; auxiliar != NULL; c++){
		printf("ITEM: %d, NUM: %.2f\n", c, auxiliar->num);
		auxiliar = auxiliar->next;
	}
}
