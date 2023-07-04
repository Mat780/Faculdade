from sympy import *
import copy

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

def pegarVetorResultadoDaMatrix(matrix, elevacao, xK):
    n = len(matrix)
    vetorResultante = []

    for i in range(n):
        soma = 0
        for j in range(n + 1):
            if (j == n): soma += matrix[i][j]

            else       : soma += (matrix[i][j] * xK[j])**elevacao[i][j]
        
        vetorResultante.append(soma)
    
    return vetorResultante

def transformarMatrixComXK(matrixOriginal, elevacao, xK, extensao):
    n = len(matrixOriginal)
    matrix = copy.deepcopy(matrixOriginal)

    for i in range(n):
        for j in range(n + 1):
            if (j == n): 
              if(extensao[i] != 0): matrix[i][j] = extensao[i] * -1
              else: matrix[i][j] = 0

            else       : matrix[i][j] = (matrixOriginal[i][j] * xK[j])**elevacao[i][j]
    
    return matrix


def newton(fx, fxElevado, fxString, x0, xNs, parada):
    n = len(fxString)
    jx = []
    jxElevado = []

    xK0 = copy.deepcopy(x0)
    xK1 = copy.deepcopy(x0)

    for i in range(n):
        aux = []
        auxElevado = []

        for j in range(xNs): # Linha
            xN = symbols('x' + str(j + 1))

            temp = diff(fxString[i][j], xN)
            temp = str(temp)

            indexX = temp.find('x')

            if (indexX != -1):
                aux.append(int (temp[:indexX - 1]))

                if(len(temp) >= indexX+4):
                    auxElevado.append(int(temp[indexX + 4:]))
                
                else: 
                    auxElevado.append(1)

            else:
                aux.append(1)
                auxElevado.append(0)

        aux.append(-1)
        jx.append(aux)
        jxElevado.append(auxElevado)
    
    print(jx)
    print(jxElevado)
    
    #* Fazer fx(xK)
    #* Fazer jx(xK)
    #* Resultado de (fx(xK) * -1) para extensão de jx(xK)
    #* S = Gauss(jx_Extendida)
    #* Por fim xK + 1 = xK + S
    #* Parada: max(|| Xk+1 - Xk ||) < parada

    cont = 0

    while(True):
        vetorResultanteFx = pegarVetorResultadoDaMatrix(fx, fxElevado, xK0)
        vetorParada = []

        for i in range(len(xK0)):
            vetorParada.append(abs(xK0[i]))
        
        if max(vetorParada) < parada: break

        if cont == 0:
          jx = transformarMatrixComXK(jx, jxElevado, xK0, vetorResultanteFx)
          jxKN = transformarMatrixComXK(jx, jxElevado, xK0, vetorResultanteFx)
        else:
          jxKN = transformarMatrixComXK(jx, jxElevado, xK0, vetorResultanteFx)

        print("jxKN:", jxKN)

        gauss(jxKN, 4, True)
        
        s = triangularSuperior(jxKN, 4)

        vetorParada = [] 
        for i in range(len(xK0)):
            xK1[i] = round(xK0[i] + s[i], 4)
            vetorParada.append(abs(xK1[i] - xK0[i]))

        calculoParada = max(vetorParada)

        # print("Xk0", xK0)
        # print("Xk1", xK1)
        # print("s", s)
        # print('-' * 30)

        xK0 = copy.deepcopy(xK1)

        if calculoParada < parada: break
        cont += 1

    
    return xK0


def main():
    fxString = [["x1", "x2"], ["x1**2", "x2**2"]]
    x0 = [1, 5]; xNs = 2
    fx = [[1, 1, -3], [1, 1, -9]]
    fxElevado = [[1, 1], [2, 2]]

    xK = newton(fx, fxElevado, fxString, x0, xNs, 10**(-4))

    print(xK)


main()

# Matheus Felipe Alves Durães