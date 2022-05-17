#include <stdio.h>
#include <stdlib.h>
#include "arv.h"
#include "pilhaFloat.h"

// Estrutura do nó para ser usado na árvore que armazenará a expressão
struct arv{
	char info;
	union {
		char op;
		int num;
	} val;
	struct arv* pai;
	struct arv* esq;
	struct arv* dir;
};

// Estrutura da pilha de árvores, que armazenará os "(" para contabiliza-los
struct pilhaArv{
	char lado;
	struct arv* no;
	struct pilhaArv* prox_no;
};

// Função para criar um nó vazio
Arv* arv_criavazia(void){
	return NULL;
}

// Função para criar um nó da árvore
Arv* arv_cria(char c, Arv* sae, Arv* sad){
	Arv* p = (Arv*)malloc(sizeof(struct arv));
	arv_setInfo(p, c);
	arv_setEsq(p, sae);
	arv_setDir(p, sad);
	arv_setPai(p, arv_criavazia());
	if(arv_vazia(sae) == 0) arv_setPai(sae, p);
	if(arv_vazia(sad) == 0) arv_setPai(sad, p);
	return p;
}

// Função para liberar a árvore inteira, ou seja destrui-la
void arv_libera(Arv* a){
	if(!arv_vazia(a)){
		arv_libera(a->esq);
		arv_libera(a->dir);
		if (a->pai != NULL) {
			if(a->pai->esq == a) a->pai->esq = NULL;
			else if(a->pai->dir == a) a->pai->dir = NULL;
		}
		free(a);
	}
}

// Função de verificação se o nó é vazio ou não
int arv_vazia(Arv* a){
	return a == NULL;
}

// Função de pertence vasculha a árvore em busca de uma informação "c", retorna true caso ache "c"
// Professor não cheguei a usar essa função nem mesmo entendi o por que de ela estar aqui
int arv_pertence(Arv* a, char c){
	if(arv_vazia(a)){
		return 0;
	} else {
		return a->info == c || arv_pertence(arv_getEsq(a), c) || arv_pertence(arv_getDir(a), c);
	}
}

// Função de imprimir as informação da árvore em um esquema de pré-ordem
void arv_imprime(Arv* a){
	if (!arv_vazia(a)){
		printf("%c ", arv_getInfo(a));
		arv_imprime(arv_getEsq(a));
		arv_imprime(arv_getDir(a));
	}
}

// Função para set da info do nó
void arv_setInfo(Arv* a, char c){
	a->info = c;
}
// Função para retornar a info de um nó
char arv_getInfo(Arv* a){
	return a->info;
}
// Função para set do operador do nó
void arv_setOp(Arv* a, char c){
	a->val.op = c;
}
// Função para retornar o operador do nó
char arv_getOp(Arv* a){
	return a->val.op;
}
// Função para set do numero do nó
void arv_setNum(Arv* a, int n){
	a->val.num = n;
}
// Função para retornar o numero do nó
int arv_getNum(Arv* a){
	return a->val.num;
}
// Função para set do pai do nó
void arv_setPai(Arv* a, Arv* pai){
	if(arv_vazia(a) == 0) a->pai = pai;
}
// Função para set o nó esquerdo "esq" em seu pai "a"
// Ao mesmo tempo que chama a função para set que "esq" tem seu pai como "a"
void arv_setEsq(Arv* a, Arv* esq){
	a->esq = esq;
	arv_setPai(esq, a);
}
// Função para set o nó direito "dir" em seu pai "a"
// Ao mesmo tempo que chama a função para set que "dir" tem seu pai como "a"
void arv_setDir(Arv* a, Arv*dir){
	a->dir = dir;
	arv_setPai(dir, a);
}
// Função que retorna o nó esquedo do nó "a"
Arv* arv_getEsq(Arv* a){
	return a->esq;
}
// Função que retorna o nó direito do nó "a"
Arv* arv_getDir(Arv* a){
	return a->dir;
}
// Função que retorna o nó pai do nó "a"
Arv* arv_getPai(Arv* a){
	return a->pai;
}
// Função que cria a pilha de árvores a ser usada para construção da árvore
PilhaArv* pilhaArv_criarPilha(void){
	PilhaArv* pilha = (PilhaArv*)malloc(sizeof(struct pilhaArv));
	pilha->lado = 'C'; // Define o lado como "C" que significa cabeça
	pilha->no = NULL;
	pilha->prox_no = NULL;
	return pilha;
}
// Função que empilha um nó na pilha de árvore
void pilhaArv_empilha(Arv* a, PilhaArv* pilha){
	PilhaArv* itemPilha = (PilhaArv*)malloc(sizeof(struct pilhaArv));
	itemPilha->lado = 'E'; // Define o lado que o próximo filho do nó armazenado vai aparecer
	itemPilha->no = a;
	itemPilha->prox_no = pilha->prox_no;
	pilha->prox_no = itemPilha;
}
// Função que consulta o topo da pilha, se ela estiver vazia retorna NULL
Arv* pilhaArv_consultaTopo(PilhaArv* pilha){
	if(pilha->prox_no != NULL) return pilha->prox_no->no;
	else return NULL;
}
// Função que desempilha um nó da pilha de árvores
Arv* pilhaArv_desempilha(PilhaArv* pilha){
	PilhaArv* itemPilha;
	Arv* arvDesempilhada;

	if(pilha->prox_no != NULL){
		itemPilha = pilha->prox_no;
		arvDesempilhada = itemPilha->no;
		pilha->prox_no = itemPilha->prox_no;
		free(itemPilha);
		return arvDesempilhada;
	} else {
		printf("PILHA VAZIA\n");
		return NULL;
	}
}
// Função que faz set do lado do nó do topo
void pilhaArv_setLadoTopo(PilhaArv* pilha, char c){
	if(pilha->prox_no != NULL) pilha->prox_no->lado = c;
	else printf("Pilha vazia, não é possível definir lado do topo");
}
// Função que retorna o lado do nó do topo, caso a pilha esteja vazia retorna "N" que significa NULL
char pilhaArv_getLadoTopo(PilhaArv* pilha){
	if(pilha->prox_no != NULL) return pilha->prox_no->lado;
	else return 'N';
}
// Função que faz o percuso de pré-ordem da árvore
void arv_preordem(Arv* a){
	if(!arv_vazia(a)){
		printf("OP: %c, NUM: %d\n", arv_getOp(a), arv_getNum(a));
		arv_preordem(arv_getEsq(a));
		arv_preordem(arv_getDir(a));
	}
}
// Função que faz o percuso de em-ordem da árvore
void arv_emordem(Arv* a){
	if(!arv_vazia(a)){
		arv_emordem(arv_getEsq(a));
		printf("OP: %c, NUM: %d\n", arv_getOp(a), arv_getNum(a));
		arv_emordem(arv_getDir(a));
	}
}
// Função que faz o percuso de pós-ordem da árvore
void arv_posordem(Arv* a){
	if(!arv_vazia(a)){
		arv_posordem(arv_getEsq(a));
		arv_posordem(arv_getDir(a));
		printf("OP: %c, NUM: %d\n", arv_getOp(a), arv_getNum(a));
	}
}
// Função que faz o percuso de pós-ordem da árvore e resolve a expressão no processo
void arv_resolve(Arv* a, PilhaFloat* pilha){
	if(!arv_vazia(a)){
		printf("OP: %c, NUM: %d\n", arv_getOp(a), arv_getNum(a));
		arv_resolve(arv_getEsq(a), pilha);
		arv_resolve(arv_getDir(a), pilha);
		// Aqui verificamos se a informação do nó é válida para processamento
		// Caso a informação não seja "(" então significa que ela é válida e possui alguma operação
		if(arv_getInfo(a) != '('){
			switch(arv_getOp(a)){
				case '+':
				case '-':
				case '*':
				case '/':
					pilhaFloat_empilha(pilha, pilhaFloat_desempilhaComOp(pilha, arv_getOp(a)));
					arv_libera(a);
					break;
				default:
					pilhaFloat_empilha(pilha, arv_getNum(a));
					break;
			}
		}
	}
}