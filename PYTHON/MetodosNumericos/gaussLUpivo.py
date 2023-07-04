def triangularSuperior(matrix, numeroDeCasasAproximacao):
    n = len(matrix)
    vetorResultante = [1] * n

    if (matrix[n-1][n] == 0):
        vetorResultante[n-1] = 0

    else:
        Xk = round(matrix[n-1][n] / matrix[n-1][n-1], numeroDeCasasAproximacao)
        vetorResultante[n-1] = Xk

    for k in range(n-2, -1, -1):
        somatoria = 0

        for j in range(k+1, n):
            somatoria = round(somatoria + (matrix[k][j] * vetorResultante[j]), numeroDeCasasAproximacao)

        Xk = round((matrix[k][n] - somatoria) / matrix[k][k], numeroDeCasasAproximacao)
        vetorResultante[k] = Xk
    
    return vetorResultante

def triangularInferior(matrix, numeroDeCasasAproximacao):
    n = len(matrix)
    vetorResultante = [1] * n

    if (matrix[n-1][n] == 0):
        vetorResultante[0] = 0
    
    else:
        Xk = round(matrix[0][n] / matrix[0][0], numeroDeCasasAproximacao)
        vetorResultante[0] = Xk

    for k in range(1, n):
        somatoria = 0

        for j in range(0, k):
            somatoria = round(somatoria + (matrix[k][j] * vetorResultante[j]), numeroDeCasasAproximacao)

        Xk = round((matrix[k][n] - somatoria) / matrix[k][k], numeroDeCasasAproximacao)
        vetorResultante[k] = Xk

    return vetorResultante

def gauss(matrix, numeroDeCasasAproximacao):
    n = len(matrix)
    matrixL = []

    for i in range(n):
        matrixL.append([0] * (n + 1))
        matrixL[i][n] = matrix[i][n]
        matrixL[i][i] = 1

    for i in range(n):

        gaussParcial(matrix, i, matrixL) # Troca pelo maior pivo da coluna

        for j in range(i + 1, n):
            zerador = round(matrix[j][i] / matrix[i][i], numeroDeCasasAproximacao)
            matrixL[j][i] = zerador

            matrix[j][i] = 0
            
            for k in range(i + 1, n):
                matrix[j][k] = round(matrix[j][k] - (zerador * matrix[i][k]), numeroDeCasasAproximacao)
    
    return [matrixL, matrix]

def gaussParcial(matrix, coluna, matrixL):
    n = len(matrix)
    maior = abs(matrix[coluna][coluna])
    linhaMaior = coluna

    for i in range(coluna + 1, n):
        valorAtual = abs(matrix[i][coluna])

        if(maior < valorAtual):
            maior = valorAtual
            linhaMaior = i

    if(coluna != linhaMaior):
        matrix[coluna], matrix[linhaMaior] = matrix[linhaMaior], matrix[coluna] # Linha
        matrixL[coluna][n], matrixL[linhaMaior][n] = matrixL[linhaMaior][n], matrixL[coluna][n] 

        for i in range(coluna): # Roda 0, 1
            matrixL[coluna][i], matrixL[linhaMaior][i] = matrixL[linhaMaior][i], matrixL[coluna][i] # Os valores da coluna

def printarMatrix(matrix):

    for i in range(len(matrix)):
        for j in range(len(matrix) + 1):
            print("|%.2f" %(matrix[i][j]), end="")
        print("|\n")

def main():
    # matrix = [[3, 2, 1, 6], [1, 3, 1, 5], [2, 2, 3, 7]]    # Vai dar [1, 1, 1]
    # matrix = [[1, 1, 1, 1], [4, 4, 2, 2], [2, 1, -1, 0]]   # Vai dar [1, -1, 1]
    # matrix = [[0.003, 59.14, 59.17], [5.291, -6.13, 46.78]] # Vai dar [10, 1]
    # matrix = [[1, 1, 0, 3, 4], [2, 1, -1, 1, 1], [3, -1, -1, 2, -3], [-1, 2, 3, -1, 4]] # [-1, 2, 0, 1]
    matrix = [[-3, 8, -2, 3, 6], [7, -1, 2, 3, 11], [-2, 3, 1, 6, 8], [1, -2, 6, 2, 7]] # [1, 1, 1, 1]
    n = len(matrix)

    numeroDeCasasAproximacao = 4

    matrixL, matrixU = gauss(matrix, numeroDeCasasAproximacao)

    vetorResultante = triangularInferior(matrixL, numeroDeCasasAproximacao)

    for i in range(n):
        matrixU[i][n] = vetorResultante[i]

    vetorResultante = triangularSuperior(matrixU, numeroDeCasasAproximacao)

    print(vetorResultante)

main()

# Matheus Felipe Alves Durães