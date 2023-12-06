#include <iostream>
#include <stdio.h>
#include <fstream>
#include <unistd.h>
#include <mpi.h>
#include <bits/stdc++.h>

using namespace std;

void swap(int* low, int* high ) {
  int temp;

  temp = *low;
  *low = *high;
  *high = temp;

}

int partition( int* array, int low, int high ) {
  int left, right;
  int pivot;

  left = low;
  right = high;
  pivot = array[low];

  while ( left < right ) {
    while ( array[right] > pivot )
      right--;

    while ( (left < right) && (array[left] <= pivot) )
      left++;

    if ( left < right )
     swap(&array[left], &array[right] );
  }

  array[low] = array[right];
  array[right] = pivot;

  return right;
}

int median(int* array, int low, int high ) {
    int middle = (low+high)/2, upper = low, lower = high, temp;

	if ( array[low] > array[middle] )
		swap( &array[middle], &array[low] );
	if ( array[low] > array[high] )
		swap( &array[low], &array[high] );
	if ( array[middle] > array[high] )
		swap( &array[high], &array[middle] );

	swap(&array[middle], &array[low]);

	return partition(array, low, high);	
}

void quickSort( int* array, int low, int high ) {
  int pivotPosition;
  
	  if (low < high) {
		  pivotPosition = median(array, low, high);
		  quickSort(array, low, pivotPosition-1);
		  quickSort(array, pivotPosition+1, high );
	  }

}

int escolhaPivo(int* vetor, int comeco, int fim) {
    int calcMedio = (comeco + fim) / 2;
    int medio = vetor[calcMedio];
    int pivo = (vetor[comeco] + medio + vetor[fim]) / 3;
    return pivo;
}

void printArq(int* vetor, int n) {
    
    int id;
    MPI_Comm_rank(MPI_COMM_WORLD, &id);

	string texto = "/saidaDoProcessador_" + to_string(id) + ".txt";
	string path =  get_current_dir_name() + texto;

	ofstream fw(path, ofstream::out);

	fw << "Olá sou o processador " << id << " e estes são os valores do meu vetor: " << endl;

	for (int i = 0; i < n; i++)
		fw << vetor[i] << " \n"[i + 1 == n];
}

void mergeVetores(int* vetor1, int n1, int* vetor2, int n2, int* vetorResultante, int n) {
    int i;
    for (i = 0; i < n1; i++) vetorResultante[i] = vetor1[i];
    for (int j = 0; j < n2; j++) vetorResultante[i++] = vetor2[j];
}

void broadcast(int root, int id, int qtdProcessadores, int* variavel) {
    if (id == root) {
        for (int i = root + 1; i < (qtdProcessadores + root); i++) 
            MPI_Send(variavel, 1, MPI_INT, i, 0, MPI_COMM_WORLD);

    } else {
        MPI_Recv(variavel, 1, MPI_INT, root, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

}

void somaPrefixa(int root, int id, int qtdProcessadores, int* numero) {
    int aux;

    if (id == root) {
        MPI_Send(numero, 1, MPI_INT, id + 1, 0, MPI_COMM_WORLD);
        
    } else if (id == (root + qtdProcessadores - 1)) {
        MPI_Recv(&aux, 1, MPI_INT, id - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        *numero += aux;

    } else {
        MPI_Recv(&aux, 1, MPI_INT, id - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        *numero += aux;
        MPI_Send(numero, 1, MPI_INT, id + 1, 0, MPI_COMM_WORLD);
    }

}

//TODO Aprender a fazer comunicadores diferentes

//* 1: Formar os vetores nos 'p' processadores com tamanho 'n/p'
//* 2: Processador root envia por broadcast o pivo para os outros p processadores
//* 3: Os processadores separam o vetor local entre 'leftSide' e 'rightSide' do pivo
//* 4: Faz-se uma soma prefixa para saber o tamanho total de leftSide e rightSide
//* 5: Faz-se um broadcast de um vetor contendo tamanho total de lS e rS
//* 6: Calcula-se em cada processador quem são os processadores em lS e rS (Levar em consideração o resto)
//*     6.1: pLs = % de lS * pConjunto , (arredondar para baixo o resultado)
//*     6.2: pRs = % de rS * pConjunto , (arredondar para baixo o resultado)
//*     6.3: Desempatar os calculos acima com o resto das % * pConjunto
//* 7: Verificar se o processador atual faz parte de lS ou rS.
//*     7.1: Calcula-se a quantidade de rodadas do for com: lS/rS (Qtd que todos os processadores em rS vão rodar)
//*     7.2: Calcula-se a rodada extra para cada processador: ((pConjunto - 1) + lS % rS >= id) 
//*     7.3: Primeiro os lS processadores enviam seus vetores 'rS' aos rS processadores
//* 8: O passo anterior invertendo lS com rS
//* 9: Atualiza-se as variaveis: pConjunto, pInicialConjunto
//*     9.1 Se lS mantêm o numero pInicialConjunto
//*     9.2 Se rS, então pInicialConjunto += pLs
//* 10: Repetir a partir do passo 2, até o pConjunto ser igual a 1.
//* 11: Ordenção do vetor local de cada p processador
//* 12: Barreira global para todos os processadores.
//* 13: Fazer o print de cada processador em arquivos separados

int main(int argc, char **argv) {
    int n;
    int id, processadores;

    n = pow(10, atoi(argv[1]));

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &id);
    MPI_Comm_size(MPI_COMM_WORLD, &processadores);

    int pConjunto = processadores;
    int pLs, pRs, pInicialConjunto = 0; 
    int tamanhoVetor = n/processadores;
    int *vetor = (int*)malloc(tamanhoVetor * sizeof(int)); 
    int pivo;
    float tamanhoConjunto = n;
    time_t start, end;

    //* 1: Formar os vetores nos 'p' processadores com tamanho 'n/p'
    srand48(time(NULL) + id);
    for (int i = 0; i < tamanhoVetor; i++) vetor[i] = lrand48() % 100;

    time(&start);

    while (pConjunto != 1) {

        //* 2: Processador root envia por broadcast o pivo para os outros p processadores
        if (id == pInicialConjunto) {
            pivo = escolhaPivo(vetor, 0, tamanhoVetor-1);
        }

        //MPI_Bcast(&pivo, 1, MPI_INT, pInicialConjunto, MPI_COMM_WORLD);
        broadcast(pInicialConjunto, id, pConjunto, &pivo);

        //* 3: Os processadores separam o vetor local entre 'leftSide' e 'rightSide' do pivo
        
        int *leftSide = (int*)malloc(tamanhoVetor * sizeof(int));
        int *rightSide = (int*)malloc(tamanhoVetor * sizeof(int));
        int tamanhoLLocal, tamanhoRLocal;
        int tamanhoL, tamanhoR;
        tamanhoL = tamanhoR = 0;

        for (int i = 0; i < tamanhoVetor; i++) {
            
            if (vetor[i] < pivo) {
                leftSide[tamanhoL] = vetor[i];
                tamanhoL++;
                
            } else {
                rightSide[tamanhoR] = vetor[i];
                tamanhoR++;
            }        
        }
        
        tamanhoLLocal = tamanhoL;
        tamanhoRLocal = tamanhoR;

        //* 4: Faz-se uma soma prefixa para saber o tamanho total de leftSide e rightSide
        // MPI_Scan(&tamanhoL, &tamanhoL, 1, MPI_INT, MPI_SUM, MPI_COMM_WORLD);
        // MPI_Scan(&tamanhoR, &tamanhoR, 1, MPI_INT, MPI_SUM, MPI_COMM_WORLD);
        somaPrefixa(pInicialConjunto, id, pConjunto, &tamanhoL);
        somaPrefixa(pInicialConjunto, id, pConjunto, &tamanhoR);

        //* 5: Faz-se um broadcast de um vetor contendo tamanho total de lS e rS
        // MPI_Bcast(&tamanhoL, 1, MPI_INT, processadoresGrupo-1, MPI_COMM_WORLD);
        // MPI_Bcast(&tamanhoR, 1, MPI_INT, processadoresGrupo-1, MPI_COMM_WORLD);
        broadcast(pInicialConjunto, id, pConjunto, &tamanhoL);
        broadcast(pInicialConjunto, id, pConjunto, &tamanhoR);
        
        //* 6: Calcula-se em cada processador quem são os processadores em lS e rS (Levar em consideração o resto)
        //*     6.1: pLs = % de lS * pConjunto , (arredondar para baixo o resultado)
        //*     6.2: pRs = % de rS * pConjunto , (arredondar para baixo o resultado)
        //*     6.3: Desempatar os calculos acima com o resto das % * pConjunto

        float porcentagemL = (tamanhoL / tamanhoConjunto);
        float porcentagemR = (tamanhoR / tamanhoConjunto);

        int porcentagemInteiraL = porcentagemL * 100;
        int porcentagemInteiraR = porcentagemR * 100;

        float restoL = porcentagemL * 100 - porcentagemInteiraL; 
        float restoR = porcentagemR * 100 - porcentagemInteiraR;

        pLs = porcentagemL * pConjunto;
        pRs = pConjunto - pLs;

        if (pLs == 0) pLs = 1;
        else if (pRs == 0) pRs = 1;
        else if ((pLs + pRs) < pConjunto) {
            if (restoL > restoR) pLs += 1;
            else pRs += 1;
        }

        if (pLs >= pConjunto) pLs = pConjunto - 1;
        if (pRs >= pConjunto) pRs = pConjunto - 1;
        
        if (id == pInicialConjunto) printf("PLs: %d e PRs: %d\n", pLs, pRs);

        //* 7: Verificar se o processador atual faz parte de lS ou rS.
        //*     7.1: Calcula-se a quantidade de rodadas do for com: lS/rS (Qtd que todos os processadores em rS vão rodar)
        //*     7.2: Calcula-se a rodada extra para cada processador: ((pConjunto - 1) + lS % rS >= id) 
        //*     7.3: Primeiro os lS processadores enviam seus vetores 'rS' aos rS processadores

		//* 8: O passo anterior invertendo lS com r

		//* 9: Atualiza-se as variaveis: pConjunto, pInicialConjunto
		//*     9.1 Se lS mantêm o numero pInicialConjunto
		//*     9.2 Se rS, então pInicialConjunto += pLs

        bool souEsquerdo = id < (pInicialConjunto + pLs);
        int rodadasFor = 0, processadorQueVouMeComunicar;
        int *vetorAuxiliar; 
        int tamanhoAuxiliar;
		bool vetAuxiliarUtilizado = false;

        // Primeiro vamos enviar os numeros de LeftSide para RightSide
        // Segundo faremos de RightSide para o LeftSide

        if (souEsquerdo) { // LeftSide

            bool processadoresAtendidos = false;
            int pRestantes = pRs;

            while(processadoresAtendidos == false) {
                for(int i = pInicialConjunto; i < (pLs + pInicialConjunto); i++) {
                    if (i == id) rodadasFor++;
                    
                    pRestantes--;

                    if (pRestantes <= 0) {
                        processadoresAtendidos = true;
                        break;
                    }
                }
            }

            processadorQueVouMeComunicar = ((id - pInicialConjunto) % (pConjunto - pLs)) + pLs + pInicialConjunto; // Oposto, ou seja do outro lado

            int pacote[2] = {tamanhoRLocal, id};

            MPI_Send(pacote, 2, MPI_INT, processadorQueVouMeComunicar, 0, MPI_COMM_WORLD);
            MPI_Send(rightSide, tamanhoRLocal, MPI_INT, processadorQueVouMeComunicar, 0, MPI_COMM_WORLD);

            //! Possivel free em rightSide

			for (int i = 0; i < rodadasFor; i++) {

				if (vetAuxiliarUtilizado) {

                    int tamanhoResultante = tamanhoAuxiliar + tamanhoLLocal;
                    int *vetorResultante = (int*)malloc(tamanhoResultante * sizeof(int));

                    mergeVetores(leftSide, tamanhoLLocal, vetorAuxiliar, tamanhoAuxiliar, vetorResultante, tamanhoResultante);

                    //! Possivel free em vetorResultante
                    leftSide = vetorResultante;
                    tamanhoLLocal = tamanhoResultante;

				}

				MPI_Recv(pacote, 2, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                tamanhoAuxiliar = pacote[0];
                vetorAuxiliar = (int*)malloc(tamanhoAuxiliar * sizeof(int));

				MPI_Recv(vetorAuxiliar, tamanhoAuxiliar, MPI_INT, pacote[1], 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

				vetAuxiliarUtilizado = true;
			}

            if (rodadasFor != 0) {
                int tamanhoResultante = tamanhoAuxiliar + tamanhoLLocal;
                int *vetorResultante = (int*)malloc(tamanhoResultante * sizeof(int));

                mergeVetores(leftSide, tamanhoLLocal, vetorAuxiliar, tamanhoAuxiliar, vetorResultante, tamanhoResultante);

                //! Possivel free em vetorResultante, leftSide e vetorAuxiliar
                vetor = vetorResultante;
                tamanhoVetor = tamanhoResultante;

            } else {
                vetor = leftSide;
                tamanhoVetor = tamanhoLLocal;
            }

			pConjunto = pLs;

        } else { // RightSide

            bool processadoresAtendidos = false;
            int pRestantes = pLs;

            while(processadoresAtendidos == false) {
                for(int i = pInicialConjunto + pLs; i < (pLs + pRs + pInicialConjunto); i++) {
                   
                    if (i == id) {
                        rodadasFor++;
                    }
                    
                    pRestantes--;

                    if (pRestantes <= 0) {
                        processadoresAtendidos = true;
                        break;
                    }
                }
            }
            
            processadorQueVouMeComunicar = ((id - pInicialConjunto) % (pConjunto - pRs)) + pInicialConjunto; // Oposto, ou seja do outro lado

            for(int i = 0; i < rodadasFor; i++) {

				if (vetAuxiliarUtilizado) {
					int tamanhoResultante = tamanhoAuxiliar + tamanhoRLocal;
                    int *vetorResultante = (int*)malloc(tamanhoResultante * sizeof(int));

                    mergeVetores(rightSide, tamanhoRLocal, vetorAuxiliar, tamanhoAuxiliar, vetorResultante, tamanhoResultante);

                    //! Possivel free em vetorResultante
                    rightSide = vetorResultante;
                    tamanhoRLocal = tamanhoResultante;
				}

                int pacote[2];

            	MPI_Recv(pacote, 2, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                tamanhoAuxiliar = pacote[0];
                vetorAuxiliar = (int*)malloc(tamanhoAuxiliar * sizeof(int));

				MPI_Recv(vetorAuxiliar, tamanhoAuxiliar, MPI_INT, pacote[1], 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

				vetAuxiliarUtilizado = true;
            }

            if (rodadasFor != 0) {
                int tamanhoResultante = tamanhoAuxiliar + tamanhoRLocal;
                int *vetorResultante = (int*)malloc(tamanhoResultante * sizeof(int));

                mergeVetores(rightSide, tamanhoRLocal, vetorAuxiliar, tamanhoAuxiliar, vetorResultante, tamanhoResultante);
                //! Possivel free em vetorResultante, rightSide e vetorAuxiliar
                vetor = vetorResultante;
                tamanhoVetor = tamanhoResultante;

            } else {
                vetor = rightSide;
                tamanhoVetor = tamanhoRLocal;
            }

            int pacote[2] = {tamanhoLLocal, id};

			MPI_Send(pacote, 2, MPI_INT, processadorQueVouMeComunicar, 0, MPI_COMM_WORLD);
			MPI_Send(leftSide, tamanhoLLocal, MPI_INT, processadorQueVouMeComunicar, 0, MPI_COMM_WORLD);

            //! Possivel free para leftSide

			pInicialConjunto += pLs;
			pConjunto = pRs;

        }

		//* 10: Repetir a partir do passo 2, até o pConjunto ser igual a 1.

    }

    //* 11: Ordenção do vetor local de cada p processador
    quickSort(vetor, 0, tamanhoVetor-1);

    time(&end);

    //* 12: Barreira global para todos os processadores.
	MPI_Barrier(MPI_COMM_WORLD);

    //* 13: Fazer o print de cada processador em arquivos separados
	printArq(vetor, tamanhoVetor);

    double tempoGasto = double(end - start);
    printf("Tempo gasto pela ordenacao, sou o processador %d processadores: %.5lf segundos\n", id, tempoGasto);

    MPI_Finalize();

    return 0;

}