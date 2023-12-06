# Aluno: Matheus Felipe Alves Durães

import random
import sys

# Função para fazer o MDC
def mdc(a, b):
    while(b != 0):
        c = a % b
        a = b
        b = c

    return a

# Função de Euclides extendida
def euclidesExtendido(a, b): 
    s = 1
    t = 0

    x = 0
    y = 1

    while(b != 0):
        c = a % b
        q = a // b

        i = s - q * x
        j = t - q * y

        a = b
        b = c

        s = x
        t = y
        x = i
        y = j

    return a, s, t
    
# Função para calcularo o inverso do inteiro "a"
def calcularInverso(a, n):
    m, s, t = euclidesExtendido(n, a)

    if m == 1:
        if t >= 0:
            return t 
        
        else:
            return n + t 
    else:
        return -1

# Função usada pelas tarefas "Iniciar" ou "Preparar" de Fábio
def fabioIniciarOuPreparar(r, n, vetorR):
    vetorAux = []

    if (len(vetorR) == 0):
        menorJaVisto = 1
    
    else:
        menorJaVisto = min(vetorR)

    while(mdc(r, n) != 1):
        if (n == len(vetorAux)):
            print("E")
            return -1
        
        while(r in vetorAux and menorJaVisto != n):
            r = random.randint(menorJaVisto, n)

        vetorAux.append(r)

        if (menorJaVisto + 1 == r):
            menorJaVisto = r

    return r

# Função principal responsável por rodar o sistema
def main():

    if (len(sys.argv) != 2):
        print("Erro e necessário passar qual pessoa usará o sistema")
        print("OPCOES:")
        print("F - Fábio")
        print("P - Patricia")
        print("T - Teodoro")
        print("E - Ester (Não está 100%)")

    # Ele pega a pessoa passada por parametro, que usará o sistema
    pessoa = sys.argv[1]

    # Inicialização de váriaveis "globais" para uso do sistema
    # Para manter o controle "-1" é o meu numero de erro pré definido
    n = -1
    s = -1
    v = -1
    r = -1
    t = -1
    tOriginal = -1
    x = -1
    b = -1

    # Vetor de Rs já utilizados por Fábio, assim não gerando o mesmo R ou respondendo o mesmo R duas vezes
    vetorR = []

    # A variavel modo é utilizada para denominar a tarefa a ser executada
    modo = ""

    # Enquanto a tarefa não for T ou seja terminar, ele continua rodando
    while (modo != 'T'):

        # A entrada é pega pela entrada padrão e divida pelos espaços
        entrada = input().split(" ")

        # A tarefa é pega no primeiro indice do vetor
        modo = entrada[0]

        if (pessoa == 'F'): #* Fabio

            # A seguir tarefa identificar de Fábio
            if (modo == 'I'): #? Identificar 
                n = int(entrada[1])
                s = int(entrada[2])
                v = int(entrada[3])

                if ((pow(s, 2) * v) % n != 1 or s >= n or v >= n):
                    n = -1
                    s = -1
                    v = -1
                    print("E")

                else:
                    print("C")

            # A seguir tarefa iniciar de Fábio
            elif (modo == 'X'): #? Iniciar
                if (n == -1 or s == -1):
                    print("E")
                    continue

                r = random.randint(1, n)

                r = fabioIniciarOuPreparar(r, n, vetorR)

                x = pow(r, 2, n)
                print(f"C {x}")

            # A seguir tarefa preparar de Fábio
            elif (modo == 'P'): #? Preparar
                if (n == -1 or s == -1):
                    print("E")
                    continue

                r = int(entrada[1]) 

                if (r >= n):
                    print("E")
                    continue

                x = pow(r, 2, n)
                print(f"C {x}")

            # A seguir tarefa responder de Fábio
            elif (modo == 'R'): #? Responder
                if (n == -1 or s == -1 or r == -1 or r in vetorR):
                    print("E")
                    continue

                b = int(entrada[1])

                if (b == 0):
                    print(f"C {r}")
                    vetorR.append(r)

                elif (b == 1):
                    print(f"C {(r * s) % n}")
                    vetorR.append(r)

                else:
                    print("E")
                
                r = -1

            # A seguir caso nenhuma das tarefas padrão seja identificada, logo é um erro
            elif (modo != 'T'): #! Erro
                print("Erro modo de operacao nao identificado")
        
        elif (pessoa == 'P'): #* Patricia

            # A seguir tarefa iniciar de Patricia
            if (modo == 'I'): #? Iniciar
                n = int(entrada[1])
                v = int(entrada[2])
                t = int(entrada[3])
                tOriginal = t
                b = -1

                if (t >= 3 and t <= 50 and n > v):
                    print("C")
                else:
                    n = -1
                    v = -1
                    t = -1
                    tOriginal = -1
                    print("E")

            # A seguir tarefa receber compromisso de Patricia
            elif (modo == 'Q'): #? Receber compromisso
                if (n == -1 or v == -1 or t <= 0):
                    print("E")
                    continue
                
                #* X = Compromisso
                x = int(entrada[1])

                if (x == 1 or x > n):
                    x = -1
                    print("E")
                    continue

                b = random.randint(0, 1)

                print(f"C {b}")

            # A seguir tarefa validar resposta de Patricia
            elif (modo == 'V'): #? Validar resposta
                if (x == -1 or t <= 0 or b == -1):
                    print(f"E {t}")
                    continue
                
                #* Se B == 0, auxX == r
                #* Se B == 1, auxX == y
                auxX = int(entrada[1])

                if (b == 0):
                    if (x == pow(auxX, 2, n)):
                        t -= 1
                        b = -1
                        print(f"C {t}")

                    else:
                        b = -1
                        print(f"E {t}")
                        t = tOriginal

                elif (b == 1):
                    if (x == (v * pow(auxX, 2, n) % n)):
                        t -= 1
                        b = -1
                        print(f"C {t}")

                    else:
                        b = -1
                        print(f"E {t}")
                        t = tOriginal

                else:
                    b = -1
                    print(f"E {t}")
                    t = tOriginal

            # A seguir tarefa testa compromisso de Patricia
            elif (modo == 'C'): #? Testa compromisso

                tempX = int(entrada[1]) # Recebe X
                b = int(entrada[2]) # Recebe bit 0|1
                valor = int(entrada[3]) # Recebe valor respondido por fabio (xB)

                if (b == 0):
                    if (tempX == pow(valor, 2, n)):
                        t -= 1
                        b = -1
                        print(f"C {t}")

                    else:
                        print(f"E {t}")
                        b = -1
                        t = tOriginal
                
                elif (b == 1):
                    if (tempX == (v * pow(valor, 2, n) % n)):
                        t -= 1
                        b = -1
                        print(f"C {t}")

                    else:
                        print(f"E {t}")
                        b = -1
                        t = tOriginal
                
                else:
                    print(f"E {t}")
                    b = -1
                    t = tOriginal

            # A seguir caso nenhuma das tarefas padrão seja identificada, logo é um erro
            elif (modo != 'T'): #! Erro
                print("Erro modo de operacao nao identificado")
        
        elif (pessoa == 'T'): #* Teodoro

            # A seguir tarefa iniciar de Teodoro
            if (modo == 'I'): #? Iniciar
                p = int(entrada[1])
                q = int(entrada[2])

                if (p <= 0 or q <= 0):
                    print("E")
                    p = -1
                    q = -1
                    continue

                n = p * q

                print(f"C {n}")

            # A seguir tarefa autenticar de Teodoro
            elif (modo == 'A'): #? Autenticar
                if (n == -1):
                    print(f"E")
                    continue

                while True:
                    s = random.randint(2, n - 1)
                    v = calcularInverso(s, n)

                    if ((pow(s, 2, n) * v) % n == 1):
                        break

            # A seguir tarefa forjar de Teodoro
            #! Mesmo após a mudança nos parametros dos algoritmos ele continua sem dar o resultado correto
            #! Segui a risca os algoritmos passados e não sei o que está errado ;-----;
            elif (modo == 'F'): #? Forjar
                s = int(entrada[1])

                if (n == -1 or s > n):
                    print(f"E")
                    s = -1
                    continue

                v = calcularInverso(s, n)

                if (v >= 0):
                    print(f"C {v}")
                
                else:
                    print(f"E")

            # A seguir caso nenhuma das tarefas padrão seja identificada, logo é um erro
            elif (modo != 'T'): #! Erro
                print("Erro modo de operacao nao identificado")
        
        elif (pessoa == 'E'): #* Ester

            # A seguir tarefa iniciar de Ester
            if (modo == 'I'): #? Iniciar
                if (len(entrada) != 3):
                    print("E")
                    continue

                n = int(entrada[1])
                v = int(entrada[2])

                if (v >= n):
                    print("E")
                    continue

                print("C")

            # A seguir tarefa preparar de Ester
            #! Não entendi muito bem como fazer o preparar
            #! Percebi pelos exemplos que não está certo, mas o PDF em si não ajudou muito na explicação
            elif (modo == 'P'): #? Preparar
                if (n == -1 or v == -1):
                    print("E")
                    continue
                
                b = int(entrada[1])

                # Gerar x e xb que seriam aceitos pelo bit B dado
                # Quase o mesmo processo de Fábio

                r = random.randint(1, n)

                x = pow(r, 2, n)

                #* Se B == 0, auxX == r
                #* Se B == 1, auxX == y

                if (b == 0): 
                    print(f"C {x} {r}")
                
                elif (b == 1): 
                    print(f"C {x} {(r * s) % n}")

                else:
                    print("E")

                # b # Recebe bit 0|1
                # x # Recebe X
                # xb # Recebe valor respondido por fabio (xB)

                # if (b == 0):
                #     if (x == pow(xb, 2, n)):
                #         print("C")
                
                # elif (b == 1):
                #     if (x == (v * pow(xb, 2, n) % n)):
                #         print("C")
                
                # else:
                #     print("E")

            # A seguir tarefa sorte de Ester
            elif (modo == 'S'): #? Sorte
                x0 = int(entrada[1]) # r
                x1 = int(entrada[2]) # y

                print(f"C {(x1 // x0) % n}")

            elif (modo != 'T'): #! Erro
                print("Erro modo de operacao nao identificado")
        
        # Caso a pessoa não seja reconhecida como nenhuma das pessoas acima
        # O sistema printa a mensagem e fecha
        else:
            print("Pessoa não reconhecida")
            exit(1)

    print("C")

main()