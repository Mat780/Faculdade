def triangularSuperior(matrix, numeroDeCasasAproximacao):
    n = len(matrix)
    vetorResultante = []

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

def gauss(matrix, numeroDeCasasAproximacao, usarGaussParcial = False):
    n = len(matrix)

    for i in range(n):

        if(usarGaussParcial):
            gaussParcial(matrix, i)

        for j in range(i + 1, n):
            if (matrix[i][i] == 0):
                gaussParcial(matrix, i)
            
            zerador = round(matrix[j][i] / matrix[i][i], numeroDeCasasAproximacao)
            matrix[j][i] = 0
            
            for k in range(i + 1, n + 1):
                matrix[j][k] = round(matrix[j][k] - (zerador * matrix[i][k]), numeroDeCasasAproximacao)

def gaussParcial(matrix, coluna):
    n = len(matrix)
    maior = abs(matrix[coluna][coluna])
    linhaMaior = coluna

    for i in range(coluna + 1, n):
        valorAtual = abs(matrix[i][coluna])

        if(maior < valorAtual):
            maior = valorAtual
            linhaMaior = i

    matrix[coluna], matrix[linhaMaior] = matrix[linhaMaior], matrix[coluna]

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
    matrix = [[3, 2, 1, 6], [1, 3, 1, 5], [2, 2, 3, 7]]    # Vai dar [1, 1, 1]
    # matrix = [[1, 1, 1, 1], [4, 4, 2, 2], [2, 1, -1, 0]]   # Vai dar [1, -1, 1]
    # matrix = [[0.003, 59.14, 59.17], [5.291, -6.13, 46.78]] # Vai dar [10, 1]
    # matrix = [[0.003, 59.14, 59.17], [5.291, -6.13, 46.78]]
    matrix = [[1, 1, -3], [2, 10, -17]] # [-1.625, -1.375]

    numeroDeCasasAproximacao = 4

    gauss(matrix, numeroDeCasasAproximacao, usarGaussParcial = True)
    print(matrix)
    
    vetorResultante = triangularSuperior(matrix, numeroDeCasasAproximacao)
    print(vetorResultante)

main()

# Matheus Felipe Alves Durães