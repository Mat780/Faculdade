from flask import jsonify


class pantanalException(Exception):
    def __init__(self, mensagem, codigo=404):
        self.messagem = mensagem
        self.codigo = codigo

    def get(self):
        data = jsonify({'message': self.messagem, 'status': self.codigo})
        data.status_code = self.codigo
        return data

    pass
