from src.exception.pantanalException import pantanalException


def existeCampo(campos, response):
    for campo in campos:
        if campo not in response:
            raise pantanalException(f"O campo {campo} está faltando")
