from math import sqrt as raiz_quadrada
from math import pow as potencia

def f1_(h):
    resultado = potencia(h, 3) - 25
    return resultado

def bissecao(xMin, xMax, casasDeAproximacao):
    verificacao = abs(xMax - xMin) / 2
    iteracao = 1
    casasDeAproximacaoRound = int(casasDeAproximacao + 2)

    casasDeAproximacao *= -1
    casasDeAproximacaoVerificacao = potencia(10, casasDeAproximacao)

    while(verificacao > casasDeAproximacaoVerificacao):
        h = (xMin + xMax) / 2
        h = round(h, casasDeAproximacaoRound)

        resultadoF1_ = f1_(h)
        verificacao = abs(xMax - xMin) / 2
        print("i: %d | xMin: %.4f | xMax: %.4f | f1_: %.4f | verifica: %.4f" % (iteracao, xMin, xMax, resultadoF1_, verificacao))

        if(resultadoF1_ > 0):
            xMax = h

        else:
            xMin = h        

        iteracao += 1 

    resultado = round((xMin + xMax) / 2, casasDeAproximacaoRound)
    print("i: %d | xMin: %.4f | xMax: %.4f | resultado: %.4f" % (iteracao, xMin, xMax, resultado))

    return resultado

def main():
    # xMin = 2 xMax = 3 casasDeAproximacao = potencia(10, -2) resultado = 2.925
    xMin = 2
    xMax = 3
    casasDeAproximacao = 2

    resultado = bissecao(xMin, xMax, casasDeAproximacao)
    print(resultado)

main()

# Matheus Felipe Alves Durães