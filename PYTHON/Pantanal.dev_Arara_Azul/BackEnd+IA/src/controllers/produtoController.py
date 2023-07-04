import random

from flask_restx import Resource, fields
from src.server.instance import server
from src.validade.validadeJson import existeCampo
from src.exception.pantanalException import *
from src.Model.Dto.resultado import Resultado
from src.Model.schemaDocumentation.resultado import avaliacao

app, api, cache = server.app, server.api, server.cache


@api.route('/produtos')
class Produto(Resource):

    @api.doc(model=[avaliacao])
    @api.expect(
        server.api.model('Produto', {
            "produto": fields.String(description="Produto a ser avaliado", required="true"), }))
    def post(self):
        response = api.payload
        try:
            existeCampo(['produto'], response)
        except pantanalException as e:
            return e.get()
        return Resultado().json()
