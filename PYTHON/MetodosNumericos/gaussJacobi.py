from numpy import copy

def jacobi(matrix, x0, parada):
    n = len(matrix)
    ePossivel = True
    soma = 0

    xKMenos1 = x0.copy()
    xK = x0.copy()

    #* Vê se é possivel executar o metodo
    for i in range(n):
        diagonal = abs(matrix[i][i])
        soma = 0
        for j in range(n):
            if (i != j):
                soma += abs(matrix[i][j])
        
        if(soma >= diagonal): # Soma deve ser sempre menor que a diagonal para ser possivel usar Jacobi
            ePossivel = False
            break
    
    if(ePossivel == False):
        print("Metodo de Jacobi é impossivel nessa matriz\n")
        exit(1)
    

    while True:
        for i in range(n):
            divisor = matrix[i][i]
            soma = 0

            for j in range(n + 1):
                if (j == n): #* A extensão que seria o vetor "b", sempre deve ser somado
                    soma += matrix[i][j]

                elif (i != j): #* Sempre que i == j, desconsiderar o valor
                    soma -= matrix[i][j] * xKMenos1[j]
            
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
        
        erroRelativo = maiorParteCima / maiorParteBaixo
        
        xKMenos1 = xK.copy()

        #* Caso o erro relativo seja menor que a parada então o laço termina
        if parada > erroRelativo:
            break

    return xKMenos1
    

def main():
    matrix = [[10, 2, 1, 7], [1, 5, 1, -8], [2, 3, 10, 6]] # ~= [1, -2, 1]
    x0 = [0.7, -1.6, 0.6]; parada = 10**(-2)

    vetorResultante = jacobi(matrix, x0, parada)

    print(vetorResultante)

main()

# Matheus Felipe Alves Durães