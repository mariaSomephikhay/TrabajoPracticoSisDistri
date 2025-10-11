from decouple import config
import requests
from flask import request
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig

PRODUCER_URL  = config('PRODUCER_URL', cast=str)

api = Namespace("solicitudes", description="Operaciones de solicitudes de donaciones")

#######################################################
# Definición de modelos para el swagger
#######################################################
errorDto = api.model("Solicitud.Error", {
    "error": fields.String(required=True)
})
categoriaDto = api.model("Solicitud.Categoria", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
DonacionCortaDto =  api.model("Solicitud.DonacionCortaDto", {
        "id": fields.Integer(required=True),
        "descripcion": fields.String(required=True),
        "categoria": fields.Nested(categoriaDto, required=True)
})
OrganizacionDto =  api.model("Solicitud.OrganizacionDto", {
        "id": fields.Integer(required=True),
        "nombre": fields.String(required=True),
        "externa": fields.Boolean(required=False)
})
SolicitudDonacionDto = api.model("Transferencia", {
    "id_solicitud_donacion": fields.Integer(required=True),
    "id_organizacion_solicitante": fields.Integer(required=True),
    "donacion": fields.Nested(DonacionCortaDto)
})

#######################################################
# Definición de endpoints-kafka para el swagger
#######################################################

# Kafka - Endpoints
@api.route("/donacion/new")
class Transferencia(Resource):
    @api.doc(security='Bearer Auth')
    #@SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="newRequest") # Esto define el operationId
    @api.expect(SolicitudDonacionDto) #Request
    @api.response(201, "Created", model=SolicitudDonacionDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Enviar solicitud de donaciones a kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/donation/request/new"
            response = requests.post(url, json=data)

            return response.json(), response.status_code
        
        except Exception as e:
            return {"error": str(e)}, 500