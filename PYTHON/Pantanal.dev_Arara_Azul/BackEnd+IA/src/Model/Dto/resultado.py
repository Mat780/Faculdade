from flask import jsonify


class Resultado:

    def __init__(self, produto, sentimento,avaliacoes):
        self.produto = produto
        self.sentimento = sentimento
        self.avaliacoes = avaliacoes

    def json(self):
        return jsonify({
            "produto": self.produto,
            "sentimento": self.sentimento,
            "avaliacoes" : self.avaliacoes
        })
