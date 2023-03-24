from time import sleep as espere

class NO:
	def __init__(self, posicaoRobo, sala1, sala2):
		self.posicaoRobo = posicaoRobo
		self.sala1 = sala1
		self.sala2 = sala2
		self.noEsq = None
		self.noLimpo = None
		self.noDir = None

def expande(noAtual):
	noAtual.noEsq = NO("Esquerda", noAtual.sala1, noAtual.sala2)
	noAtual.noDir = NO("Direita", noAtual.sala1, noAtual.sala2)
    
	if(noAtual.posicaoRobo == "Esquerda"):
		noLimpo = NO(noAtual.posicaoRobo, "Limpo", noAtual.sala2)
    
	else:
		noLimpo = NO(noAtual.posicaoRobo, noAtual.sala1, "Limpo")
	
	noAtual.noLimpo = noLimpo

def buscaEmLargura(noRaiz): # Vetor no formato de fila
	noAtual = noRaiz
	contadorDeNo = 1
	contadorDeIteracoes = 0
	fronteira = []

	if(noAtual.sala1 == "Limpo" and noAtual.sala2 == "Limpo"):
		print("O próprio nó raiz já é nosso estado objetivo")

	while(noAtual.sala1 != "Limpo" or noAtual.sala2 != "Limpo"):
		expande(noAtual)
		contadorDeNo += 3
		contadorDeIteracoes += 1

		fronteira.append(noAtual.noEsq)
		fronteira.append(noAtual.noLimpo)
		fronteira.append(noAtual.noDir)

		print("Estado atual: [%s] [%s] [%s] ->" %(noAtual.posicaoRobo, noAtual.sala1, noAtual.sala2), end=" ")

		noAtual = fronteira.pop(0)

		print("Proximo estado: [%s] [%s] [%s]" %(noAtual.posicaoRobo, noAtual.sala1, noAtual.sala2))
	
	print("\nQuantidade de nos criados: %d" %(contadorDeNo))
	print("Quantas interações foram necessárias para chegar ao estado final: %d" %(contadorDeIteracoes))
	print("Quantidade de nos na fronteira que sobraram: %d" %(len(fronteira)))

def buscaEmProfundidade(noRaiz):
	noAtual = noRaiz
	contadorDeNo = 1
	contadorDeIteracoes = 0
	fronteira = []

	if(noAtual.sala1 == "Limpo" and noAtual.sala2 == "Limpo"):
		print("O próprio nó raiz já é nosso estado objetivo")

	while( (noAtual.sala1 != "Limpo" or noAtual.sala2 != "Limpo") and contadorDeIteracoes < 50):
		expande(noAtual)
		contadorDeNo += 3
		contadorDeIteracoes += 1

		fronteira.append(noAtual.noEsq)
		fronteira.append(noAtual.noLimpo)
		fronteira.append(noAtual.noDir)

		print("Estado atual: [%s] [%s] [%s] ->" %(noAtual.posicaoRobo, noAtual.sala1, noAtual.sala2), end=" ")

		noAtual = fronteira.pop(-1)

		print("Proximo estado: [%s] [%s] [%s]" %(noAtual.posicaoRobo, noAtual.sala1, noAtual.sala2))
	
	if(contadorDeIteracoes == 50):
		print("\nO mesmo estado está sendo repetido muitas vezes, assim podemos concluir que ele entrou em um loop infinito")
	print("\nQuantidade de nos criados: %d" %(contadorDeNo))
	print("Quantas interações foram necessárias para chegar ao estado final: %d" %(contadorDeIteracoes))
	print("Quantidade de nos na fronteira que sobraram: %d" %(len(fronteira)))

def buscaEmProfundidadeSemRepetirEstadoAnterior(noRaiz):
	noAtual = noRaiz
	contadorDeNo = 1
	contadorDeIteracoes = 0
	fronteira = []
	noRepetidos = [noAtual]
	repetido = True

	if(noAtual.sala1 == "Limpo" and noAtual.sala2 == "Limpo"):
		print("O próprio nó raiz já é nosso estado objetivo")

	while((noAtual.sala1 != "Limpo" or noAtual.sala2 != "Limpo") and contadorDeIteracoes < 10):
		expande(noAtual)
		contadorDeNo += 3
		contadorDeIteracoes += 1

		fronteira.append(noAtual.noDir)
		fronteira.append(noAtual.noLimpo)
		fronteira.append(noAtual.noEsq)


		print("Estado atual: [%s] [%s] [%s] ->" %(noAtual.posicaoRobo, noAtual.sala1, noAtual.sala2), end=" ")

		noAtual = fronteira.pop(-1)

		while(repetido):
			infoAtual = pegarInfoNo(noAtual)
			repetido = False

			for i in range(0, len(noRepetidos)):
				infoRepetido = pegarInfoNo(noRepetidos[i])

				if(infoAtual[0] == infoRepetido[0] and infoAtual[1] == infoRepetido[1] and infoAtual[2] == infoRepetido[2]):
					repetido = True
					break
			
			if(repetido):
				noAtual = fronteira.pop(-1)

			else:
				noRepetidos.append(noAtual)

		repetido = True

		print("Proximo estado: [%s] [%s] [%s]" %(noAtual.posicaoRobo, noAtual.sala1, noAtual.sala2))
	
	print("\nQuantidade de nos criados: %d" %(contadorDeNo))
	print("Quantas interações foram necessárias para chegar ao estado final: %d" %(contadorDeIteracoes))
	print("Quantidade de nos na fronteira que sobraram: %d" %(len(fronteira)))

def pegarInfoNo(no):
	vetor = []
	vetor.append(no.posicaoRobo)
	vetor.append(no.sala1)
	vetor.append(no.sala2)

	return vetor

def main():
	noRaiz = NO("Esquerda", "Sujo", "Sujo")
	opcao = -1
	quantidadeSeparacao = 50

	print("-" * quantidadeSeparacao)
	print("Observação: Estado atual é o nó atual, e o proximo estado é o no retirado da fronteira")
	print("Não necessariamente o proximo estado tem como seu pai o estado atual")
	print('O nó raiz padrão é: "Esquerda", "Sujo", "Sujo" ')

	while(opcao != 4):
		print("-" * quantidadeSeparacao)
		print("Escolha uma das opções a seguir")
		print("Opção [0] : Busca em largura")
		print("Opção [1] : Busca em profundidade (Puro)")
		print("Opção [2] : Busca em profundidade sem repetição de nó")
		print("Opção [3] : Alteração do nó raiz")
		print("Opção [4] : Sair")
		print("Sua escolha: ", end="")
		opcao = int(input())
		print("-" * quantidadeSeparacao)

		match(opcao):
			case 0:
				buscaEmLargura(noRaiz)

			case 1:
				buscaEmProfundidade(noRaiz)
			
			case 2:
				buscaEmProfundidadeSemRepetirEstadoAnterior(noRaiz)
			
			case 3:
				direcao = ""
				sala1 = ""
				sala2 = ""
				opcaoInterna = -1
				while(opcaoInterna != 0 and opcaoInterna != 1):
					print("-" * quantidadeSeparacao)
					print("Escolha o lado que o robo irá começar")
					print("[0] Esquerda")
					print("[1] Direita")
					print("Sua escolha: ", end="")
					opcaoInterna = int(input())

					if(opcaoInterna == 0):
						direcao = "Esquerda"
					
					elif (opcaoInterna == 1):
						direcao = "Direita"
					
					else:
						print("Opção inválida tente novamente")
				
				opcaoInterna = -1
				while(opcaoInterna != 0 and opcaoInterna != 1):
					print("-" * quantidadeSeparacao)
					print("Escolha se a sala 1 estará limpa ou suja")
					print("[0] Suja")
					print("[1] Limpa")
					print("Sua escolha: ", end="")
					opcaoInterna = int(input())

					if(opcaoInterna == 0):
						sala1 = "Suja"
					
					elif (opcaoInterna == 1):
						sala1 = "Limpo"
					
					else:
						print("Opção inválida tente novamente")
				
				opcaoInterna = -1
				while(opcaoInterna != 0 and opcaoInterna != 1):
					print("-" * quantidadeSeparacao)
					print("Escolha se a sala 2 estará limpa ou suja")
					print("[0] Suja")
					print("[1] Limpa")
					print("Sua escolha: ", end="")
					opcaoInterna = int(input())

					if(opcaoInterna == 0):
						sala2 = "Suja"
					
					elif (opcaoInterna == 1):
						sala2 = "Limpo"
					
					else:
						print("Opção inválida tente novamente")
				
				noRaiz = NO(direcao, sala1, sala2)
				print("No raiz criado com sucesso com base em suas escolhas")

			case 4:
				print("Encerrando o programa...")
				espere(1)

			case _:
				print("Opção inválida tente novamente")

main()