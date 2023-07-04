from numpy import copy

def seidel(matrix, x0, parada):
    n = len(matrix)
    ePossivel = True

    xKMenos1 = x0.copy()
    xK = x0.copy()

    #* Vê se é possivel executar o metodo
    for i in range(n):
        diagonal = abs(matrix[i][i])
        soma = 0
        for j in range(n):
            if (i != j):
                soma += abs(matrix[i][j])
        
        if(soma >= diagonal): # Soma deve ser sempre menor que a diagonal para ser possivel usar Seidel
            ePossivel = False
            break
    
    if(ePossivel == False):
        print("Metodo de Seidel é impossivel nessa matrix\n")
        exit(1)
    
    #* Critério de Sassenfeld
    vetorSassenfeld = [1] * n
    for i in range(n):
        divisor = abs(matrix[i][i])
        soma = 0
        for j in range(n):
            if (i != j): #* Sempre que i == j, desconsiderar o valor
                soma += abs(matrix[i][j]) * vetorSassenfeld[j]
        
        vetorSassenfeld[i] = soma / divisor
    
    if max(vetorSassenfeld) >= 1: 
        print("Metodo de Seidel é impossivel nessa matriz")
        exit(1)
        

    while True:
        for i in range(n):
            divisor = matrix[i][i]
            soma = 0

            for j in range(n + 1):
                if (j == n): #* A extensão que seria o vetor "b", sempre deve ser somado
                    soma += matrix[i][j]

                elif (i != j): #* Sempre que i == j, desconsiderar o valor
                    soma -= matrix[i][j] * xK[j]
            
            xK[i] = (1 / divisor) * soma
    

        # Parte de cima da fração
        maiorParteCima = abs(xK[0] - xKMenos1[0])

        # Parte de baixo da fração
        maiorParteBaixo = abs(xK[0])

        for i in range(1, n):
            valorAtualCima = abs(xK[i] - xKMenos1[i])
            valorAtualBaixo = abs(xK[i])

            if valorAtualCima > maiorParteCima:
                maiorParteCima = valorAtualCima
            
            if valorAtualBaixo > maiorParteBaixo:
                maiorParteBaixo = valorAtualBaixo
        
        print("%.4f / %.4f" %(maiorParteCima, maiorParteBaixo))
        erroRelativo = maiorParteCima / maiorParteBaixo
        
        print("Xk-1:", xKMenos1)
        print("Xk:", xK)

        xKMenos1 = xK.copy()

        print("\nErro relativo: %.4f" %(erroRelativo))

        #* Caso o erro relativo seja menor que a parada então o laço termina
        if parada > erroRelativo:
            break

    return xKMenos1
    

def main():
    matrix = [[10, 2, 1, 7], [1, 5, 1, -8], [2, 3, 10, 6]] # ~= [1, -2, 1]
    x0 = [0.7, -1.6, 0.6]; parada = 10**(-2)

    vetorResultante = seidel(matrix, x0, parada)

    print(vetorResultante)

main()

# Matheus Felipe Alves Durães