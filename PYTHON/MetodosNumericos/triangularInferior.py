def pegarMatrixExtendida():
    matrix = []

    print("Digite o valor do N: ", end= "")
    n = int(input())

    for linha in range(n):

        vetorLinha = []

        for coluna in range(n+1):

            if(coluna == n):
                print("Digite o resultado da linha %d: " %(linha), end="")
            
            else:
                print("Digite o valor da matrix na posição [%d][%d] " %(linha, coluna) , end="")

            vetorLinha.append(int(input()))
        
        matrix.append(vetorLinha)

    return matrix

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

def main():
  # matrix = [[1, 0, 0, 5], [2, 1, 0, 10], [3, 4, 1, 30]] # Vai dar [5, 0, 15]
  matrix = pegarMatrixExtendida()

  vetorResultante = triangularInferior(matrix)

  for valor in vetorResultante:
    print(valor)

main()

# Matheus Felipe Alves Durães