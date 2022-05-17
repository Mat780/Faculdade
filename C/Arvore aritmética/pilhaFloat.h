#ifndef _PILHAFLOAT_H
#define _PILHAFLOAT_H

// Estrutura de pilha de pontos flutuantes, usado para resolver a expressão
typedef struct pilhaFloat PilhaFloat;

// Função para criar a pilha de pontos flutuantes
PilhaFloat* pilhaFloat_criarPilha(void);
// Função para empilhar o numero no topo da pilha de pontos flutuantes
void pilhaFloat_empilha(PilhaFloat* pilha, float num);
// Função para desempilhar o numero no topo da pilha de pontos flutuantes
float pilhaFloat_desempilha(PilhaFloat* pilha);
// Função para desempilhar o numero no topo da pilha juntamente com um operador
// Explicando melhor: Ele desempilha 2 numeros, e os junta através do operador que é dado para a função retornando o resultado da operação
float pilhaFloat_desempilhaComOp(PilhaFloat* pilha, char op);

// Função para mostrar a pilha de pontos flutuantes
void pilhaFloat_mostrarPilha(PilhaFloat* pilha);

#endif