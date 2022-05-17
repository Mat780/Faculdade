#ifndef _ARV_H
#define _ARV_H
#include "pilhaFloat.h"

// Estrutura do nó para ser usado na árvore que armazenará a expressão
typedef struct arv Arv;

// Estrutura da pilha de árvores, que armazenará os "(" para contabiliza-los
typedef struct pilhaArv PilhaArv;

// Função para criar um nó vazio
Arv* arv_criavazia(void);

// Função para criar um nó da árvore
Arv* arv_cria(char c, Arv* e, Arv* d);

// Função para liberar a árvore inteira, ou seja destrui-la
void arv_libera(Arv* a);

// Função de verificação se o nó é vazio ou não
int arv_vazia(Arv* a);

// Função de pertence vasculha a árvore em busca de uma informação "c", retorna true caso ache "c"
// Professor não cheguei a usar essa função nem mesmo entendi o por que de ela estar aqui
int arv_pertence(Arv* a, char c);

// Função de imprimir as informação da árvore em um esquema de pré-ordem
void arv_imprime(Arv* a);

// Função para set da info do nó
void arv_setInfo(Arv* a, char c);
// Função para retornar a info de um nó
char arv_getInfo(Arv* a);

// Função para set do operador do nó
void arv_setOp(Arv* a, char c);
// Função para retornar o operador do nó
char arv_getOp(Arv* a);

// Função para set do numero do nó
void arv_setNum(Arv* a, int n);
// Função para retornar o numero do nó
int arv_getNum(Arv* a);

// Função para set do pai do nó
void arv_setPai(Arv* a, Arv* pai);
// Função para set o nó esquerdo "esq" em seu pai "a"
// Ao mesmo tempo que chama a função para set que "esq" tem seu pai como "a"
void arv_setEsq(Arv* a, Arv* esq);
// Função para set o nó direito "dir" em seu pai "a"
// Ao mesmo tempo que chama a função para set que "dir" tem seu pai como "a"
void arv_setDir(Arv* a, Arv* dir);

// Função que retorna o nó esquedo do nó "a"
Arv* arv_getPai(Arv* a);
// Função que retorna o nó direito do nó "a"
Arv* arv_getEsq(Arv* a);
// Função que retorna o nó pai do nó "a"
Arv* arv_getDir(Arv* a);

// Função que cria a pilha de árvores a ser usada para construção da árvore
PilhaArv* pilhaArv_criarPilha(void);
// Função que empilha um nó na pilha de árvore
void pilhaArv_empilha(Arv* a, PilhaArv* pilha);
// Função que consulta o topo da pilha, se ela estiver vazia retorna NULL
Arv* pilhaArv_consultaTopo(PilhaArv* pilha);
// Função que desempilha um nó da pilha de árvores
Arv* pilhaArv_desempilha(PilhaArv* pilha);

// Função que faz set do lado do nó do topo
void pilhaArv_setLadoTopo(PilhaArv* pilha, char c);
// Função que retorna o lado do nó do topo, caso a pilha esteja vazia retorna "N" que significa NULL
char pilhaArv_getLadoTopo(PilhaArv* pilha);

// Função que faz o percuso de pré-ordem da árvore
void arv_preordem(Arv* a);
// Função que faz o percuso de em-ordem da árvore
void arv_emordem(Arv* a);
// Função que faz o percuso de pós-ordem da árvore
void arv_posordem(Arv* a);
// Função que faz o percuso de pós-ordem da árvore e resolve a expressão no processo
void arv_resolve(Arv* a, PilhaFloat* pilha);

#endif