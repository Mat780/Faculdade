import copy

def f1(x):
    return x**2 - 4

def f1_(x):
    return 2 * x

def newton(x0, parada):

    xK0 = copy.deepcopy(x0)
    xK1 = copy.deepcopy(x0)

    while True:
        resultadoF1 = f1(xK0)
        resultadoF1_ = f1_(xK0)

        xK1 = xK0 - (resultadoF1 / resultadoF1_)

        if abs(xK1 - xK0) < parada: break

        xK0 = xK1

    print(xK1)

def main():
    x0 = 3

    newton(3, 10**(-3))

main()

# Matheus Felipe Alves Durães