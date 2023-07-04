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

def triangularSuperior(matrix):
    n = len(matrix)
    vetorResultante = []

    for lixo in range(n):
        vetorResultante.append(1)

    Xk = matrix[n-1][n] / matrix[n-1][n-1]
    vetorResultante[n-1] = Xk

    for k in range(n-2, -1, -1):
        somatoria = 0

        for j in range(k+1, n):
            somatoria = somatoria + (matrix[k][j] * vetorResultante[j])

        Xk = (matrix[k][n] - somatoria) / matrix[k][k]
        vetorResultante[k] = Xk
    
    return vetorResultante

def main():

    matrix = pegarMatrixExtendida()
    
    #* Matrix de teste: matrix = [[2, -1, 1, 5], [0, 1.5, -1.5, -4.5], [0, 0, 4, 8]]

    vetorResultante = triangularSuperior(matrix)

    for valor in vetorResultante:
        print(valor)

main()

# Matheus Felipe Alves Durães