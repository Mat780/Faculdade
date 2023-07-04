from math import pow as potencia

def f1(x):
    resultado = x**3 - (9 * x) + 3
    return resultado

def MaisOuMenos(x):
    if (x > 0):
        return '+'
    else:
        return ''

def secante(x0, x1, taxaAproximacao):
    diferenca = abs(x1 - x0)
    resultado = 0
    k = 1

    aproximacaoAtingida = diferenca < taxaAproximacao
    resultadoFx0 = f1(x0)

    while(aproximacaoAtingida == False):

        resultadoFx1 = f1(x1)

        # Calculo da forma
        x2 = (resultadoFx1 * (x1 - x0)) / (resultadoFx1 - resultadoFx0)
        x2 = x1 - x2

        diferenca = abs(x2 - x1)
        aproximacaoAtingida = diferenca < taxaAproximacao

        # Print da tabela
        print("K: %2d" %(k), end= " | ")

        caract = MaisOuMenos(resultadoFx1)
        print("f(Xk): %s%.6f" %(caract, resultadoFx1), end=" | ")

        caract = MaisOuMenos(resultadoFx0)
        print("f(Xk-1): %s%.6f" %(caract, resultadoFx0), end=" | ")

        print("Xk+1: %.6f" %(x2), end=" | ")
        
        print("abs(Xk+1 - Xk): %.6f" %(diferenca))

        # Atualização do Xk-1 e Xk
        x0 = x1
        x1 = x2

        k += 1

        resultadoFx0 = resultadoFx1



    resultado = x2
    return resultado

def main():
    x0 = 0
    x1 = 1
    taxaAproximacao = potencia(10, -4)
    print(taxaAproximacao)

    resultado = secante(x0, x1, taxaAproximacao)
    print("Secante: %.6f" %(resultado))

main()

# Matheus Felipe Alves Durães