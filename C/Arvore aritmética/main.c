#include <stdio.h>
#include <stdlib.h>
#include "arv.h"
#include "pilhaFloat.h"

/*----------------------------------------------------------------

	@Author: Matheus Felipe Alves Durães
	@RGA: 2021.1904.008-4

	@Professor: Marco Aurelio 

*/

int main(void){
	// Inicialização de váriaveis necessárias
	int acumulador_Num = -1;
	char charAtt, isBuggy = 'N';
	// Criação da pilha de árvore
	PilhaArv* pilha_arv = pilhaArv_criarPilha();
	// Esta variável serve única e exclusivamente para armazenar o ultimo nó desempilhado
	// Para que quando a pilha for totalmente desempilhada ainda tenhamos a raiz da árvore
	Arv* arvDesempilhada = NULL;

	// Temos o primeiro scanf com espaçamento para evitar lixo de memoria e inicializar o while
	// Tentei por do while anteriormente porém ele não consegui finalizar o while por causa do espaço
	// Por isso retornei ao formato do while "rustico"
	scanf(" %c", &charAtt);
	// While irá rodar enquanto a expressão não acabar e enquanto ele não detectar algum bug/erro na expressão digitada
	while(charAtt != '\n' && isBuggy != 'Y'){
		// Primeiramente ele consulta o topo da pilha de árvores, já que ele é muito usado durante o while
		Arv* isArvOn = pilhaArv_consultaTopo(pilha_arv);

		// Então vamos para um switch do caractere atual pego pelo scanf
		switch(charAtt){
			case '(': ;
				// Cria um novo nó com a informação de ser um "("
				Arv* novaArv = arv_cria('(', arv_criavazia(), arv_criavazia());
				// Verificamos o lado do nó do topo da pilha
				char lado = pilhaArv_getLadoTopo(pilha_arv);

				// Caso a pilha esteja vazia retornará "N" que significa NULL para o nosso contexto
				if(lado != 'N'){
					// Como a pilha não está vazia, ela possui um respectivo lado, ou "E" de esquerdo ou "D" de direito, o lado representará onde o novo nó será atribuido
					if(lado == 'E'){
						arv_setEsq(isArvOn, novaArv);
						// Aqui setamos o lado para ser o meio, já que agora se espera um operador	
						pilhaArv_setLadoTopo(pilha_arv, 'M');
					}
					else if (lado == 'D' && arv_vazia( arv_getDir( isArvOn ) ) ) arv_setDir(isArvOn, novaArv);
					else isBuggy = 'Y';
				}

				// Por fim empilharemos o novo nó na pilha
				pilhaArv_empilha(novaArv, pilha_arv);
				break;
			case ')':
				// Aqui verificamos a pilha está vazia ou não
				// Caso ela não esteja vazia, então segue o processo para desempilhar
				if(isArvOn != NULL){

					// Verificamos aqui se é necessário criar um nó de numero
					// Sabemos disso atráves do acumulador de numeros o "acumulador_Num", se for diferente de zero significa que ele pegou numeros durante a leitura
					if(acumulador_Num != -1){
						// Criamos o nó e atribuimos o número a ele
						Arv* noNum = arv_cria('N', arv_criavazia(), arv_criavazia());
						arv_setNum(noNum, acumulador_Num);
						// Como estamos desempilhando já se pressupoe que o número está a direita do operador, por isso colocamos ele a direita
						if(arv_vazia( arv_getDir( isArvOn ) ) ) {
							arv_setDir(isArvOn, noNum);
						} else {
							isBuggy = 'Y';
						}
						// Resetamos o acumulador para que ele possa pegar outro numero na leitura
						acumulador_Num = -1;
					}

					if(arv_getDir(isArvOn) == NULL)	{
						printf("ERRO: Operacao nao finalizada\n");
						isBuggy = 'Y';
					}
					// Por fim desempilhamos o topo da pilha
					arvDesempilhada = pilhaArv_desempilha(pilha_arv);

				// Caso ela esteja vazia, então a expressão está errada, e o programa irá parar
				} else {
					isBuggy = 'Y';
					printf("ERRO: Primeiro deve-se abrir um parenteses, para poder fecha-lo\n");
				}
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				// Aqui verificamos se a pilha está vazia ou não
				// Caso ela não esteja vazia...
				if(isArvOn != NULL){
					
						// Vemos se a variavel "acumulador_Num" pegou algum numero durante a leitura
						if(acumulador_Num != -1) {
							// Caso tenha pego criamos o nó para o numero e adicionamos o numero ao nó 
							Arv* noNum = arv_cria('N', arv_criavazia(), arv_criavazia());
							arv_setNum(noNum, acumulador_Num);
							// Como estamos pegando um operador, já se pressupoe que o numero está a esquerda, por isso atribuimos a esquerda.
							if (arv_vazia( arv_getEsq( isArvOn ) )) arv_setEsq(isArvOn, noNum);
							else {
								isBuggy = 'Y';
								printf("ERRO: Tentando alocar um no de numero em um local ja alocado\n");
							}
							// Resetamos o acumulador para que ele possa pegar outro numero na leitura
							acumulador_Num = -1;
						}
						
						if(arv_getEsq(isArvOn) == NULL)	{
							printf("ERRO: Operador sem operandos\n");
							isBuggy = 'Y';
						}
							
						// Então setamos o operador do nó no topo da pilha
						arv_setOp(isArvOn, charAtt);
						// E informamos que esse nó é um operador
						arv_setInfo(isArvOn, 'O');

						// Mudamos o lado do topo para direita, o que significa que o proximo nó a ser adicionado, será adicionado á direita
						pilhaArv_setLadoTopo(pilha_arv, 'D');

					

				// Caso ela esteja vazia, então ocorreu um erro
				} else {
					printf("ERRO: Uma operacao deve ser precedida de um numero, que deve ser precedida de um '('\n");
					isBuggy = 'Y';
				}
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9': ;
				// Aqui pegamos os numeros que vierem durante a leitura
				// Primeiro verificamos se o topo da pilha está vazio
				// Caso não esteja...
				if(isArvOn != NULL){
					// Vamos verificamos se o "acumulador_Num" é diferente de zero
					// Se sim significa que ele já está armazenando algum numero
					if(acumulador_Num != -1){
						// Então "empurraremos" esse numero para uma casa decimal a frente multiplicando-o por 10
						acumulador_Num *= 10;
						// Em seguida converteremos o proximo numero de char para int e descontaremos a tabela ascii do valor convertido
						// Como "0" na tabela ascii é representado por 48, então diminuiremos 48 do resultado da conversão
						acumulador_Num += (int)charAtt - 48;

					// Caso o acumulador esteja zerado, significa que o numero atual é o começo de um novo numero
					} else {
						// Por isso faremos a conversão de char para int, descontando a tabela ascii do valor convertido
						//Como "0" na tabela ascii é representado por 48, então diminuiremos 48 do resultado da conversão
						acumulador_Num = (int)charAtt - 48;
					}
				
				// Caso ela esteja vazia, então a expressão está incorreta e o programa fechará com a mensagem de erro
				} else {
					printf("ERRO: Um '(' deve preceder o primeiro numero de uma operacao\n");
					isBuggy = 'Y';
				}
				break;
			case ' ':
				// Este caso é exclusivamente para "excluir" os espaços dentro da expressão
				break;
			// Por fim qualquer caractere diferente dos citados acima é considerado um erro
			// O operador '/' ou seja divisão foi desconsiderado, pelo motivo que foi conversado em sala sobre simplificar o trabalho, não contendo numeros com virgula, ou seja numeros float
			// Por este motivo ele nem mesmo foi incluido como operador
			default:
				printf("ERRO: Caracter invalido\n");
				printf("Este caractere '%c' nao e aceito\n", charAtt);
				isBuggy = 'Y';
				break;
		}
		// Ao final do switch, pegamos o próximo caracter da expressão
		// Caso ele receba aqui o '/n' o while finalizará 
		scanf("%c", &charAtt);
	}

	if(pilhaArv_consultaTopo(pilha_arv) != NULL) isBuggy = 'Y';
	
	// Verificamos se houve algum bug/erro durante a execução do programa
	// Caso a resposta seja "N" de não, então tudo ocorreu bem e podemos resolver a expressão
	if(isBuggy == 'N'){
		// Aqui criamos a pilha de inteiros para resolução da expressão
		PilhaFloat* pilha = pilhaFloat_criarPilha();
		
		if(arvDesempilhada != NULL) arv_resolve(arvDesempilhada, pilha);

		float resultado = pilhaFloat_desempilha(pilha);
		printf("RESPOSTA: %.2f\n", resultado);

	// Caso algo tenha dado errado então iremos printar uma mensagem de erro
	} else {
		printf("---------------------------- ERRO ------------------------------------\n");
		printf("A expressao apresentou algum erro tente novamente com uma expressao valida\n");
		printf("Lembre-se todas as operacoes devem estar em torno de um '(' e um ')'\n");
		printf("E dentro dos parenteses podem haver outros parenteses, ou uma operacao entre numeros\n");
		printf("Denotada desta forma: ( NUMERO OPERADOR NUMERO )\n");
	}

	printf("END OF PROGRAM\n");
}