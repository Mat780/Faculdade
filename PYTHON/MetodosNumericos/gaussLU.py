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

    for i in range(n):
        matrixL[i][i] = 1

        for j in range(i + 1, n):
            
            zerador = round(matrix[j][i] / matrix[i][i], numeroDeCasasAproximacao)
            matrixL[j][i] = zerador

            matrix[j][i] = 0
            
            for k in range(i + 1, n):
                matrix[j][k] = round(matrix[j][k] - (zerador * matrix[i][k]), numeroDeCasasAproximacao)
    
    return [matrixL, matrix]


def pegarMatrix():
    matrix = []

    print("Digite o valor do N:")
    n = int(input())

    for c in range(n):
        vetorLinha = []
        for j in range(n+1):
            if(j == n):
                print("Digite o resultado da linha %d:" %(c))
            
            else:
                print("Digite o valor da matrix na posição [%d][%d]" %(c, j))

            vetorLinha.append(int(input()))
        
        matrix.append(vetorLinha)


    return matrix

def main():
    # matrix = [[3, 2, 1, 6], [1, 3, 1, 5], [2, 2, 3, 7]]    # Vai dar [1, 1, 1]
    # matrix = [[1, 1, 1, 1], [4, 4, 2, 2], [2, 1, -1, 0]]   # Vai dar [1, -1, 1]
    # matrix = [[0.003, 59.14, 59.17], [5.291, -6.13, 46.78]] # Vai dar [10, 1]
    # matrix = [[1, 1, 0, 3, 4], [2, 1, -1, 1, 1], [3, -1, -1, 2, -3], [-1, 2, 3, -1, 4]] # [-1, 2, 0, 1]
    matrix = [[1, 1, 0, 3, 4], [2, 1, -1, 1, 1], [3, -1, -1, 2, -3], [-1, 2, 3, -1, 4]]
    n = len(matrix)

    numeroDeCasasAproximacao = 2

    matrixL, matrixU = gauss(matrix, numeroDeCasasAproximacao)

    vetorResultante = triangularInferior(matrixL, numeroDeCasasAproximacao)

    for i in range(n):
        matrixU[i][n] = vetorResultante[i]

    vetorResultante = triangularSuperior(matrixU, numeroDeCasasAproximacao)

    print(vetorResultante)

main()

# Matheus Felipe Alves Durães