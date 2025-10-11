from decouple import config
import requests
from flask import request
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig

PRODUCER_URL  = config('PRODUCER_URL', cast=str)

api = Namespace("transferencias", description="Operaciones de transferencias")

#######################################################
# Definición de modelos para el swagger
#######################################################
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})

donacionTransferenciaDto =  api.model("DonacionTransferencia", {
        "categoria": fields.String(required=True),
        "descripcion": fields.String(required=True),
        "cantidad": fields.Integer(required=False)
})
transferenciaDto = api.model("Transferencia", {
    "id_solicitud": fields.Integer(required=True),
    "id_organizacion_donante": fields.Integer(required=True),
    "donaciones": fields.List(fields.Nested(donacionTransferenciaDto))
})

#######################################################
# Definición de endpoints-kafka para el swagger
#######################################################

# Kafka - Endpoints
@api.route("/donacion/<int:id_solicitante>/new")
class Transferencia(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="newTransfer") # Esto define el operationId
    @api.expect(transferenciaDto) #Request
    @api.response(200, "Success")
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self, id_solicitante):
        """Enviar transferencia de donaciones a organizacion"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/donation/transfer/{id_solicitante}/new"
            requests.post(url, json=data)

            return 200
        
        except Exception as e:
            return {"error": str(e)}, 500