from flask_restx import fields
from src.server.instance import server

avaliacao = server.api.model('Resultado', {
    "produto": fields.String(description="Produto a ser avaliado"),
    "sentimento": fields.Float(
        description="Média dos raking  das avaliações sobre a satisfação geral",
        max=5, min=0
    ),
})
