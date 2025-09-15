from flask import request
from flask_restx import Namespace, Resource, fields
from google.protobuf.timestamp_pb2 import Timestamp
from dateutil import parser
import json
from config.security_config import SecurityConfig
from grpc_manager_service import ManagerServiceImpl
import grpc

api = Namespace("eventos", description="Operaciones de eventos")
cliente = ManagerServiceImpl()

#######################################################
# Definición de modelos para el swagger
#######################################################
eventoDto = api.model("Donacion", {
    "id": fields.Integer(required=False),
    "nombre": fields.String(required=True),
    "descripcion": fields.String(required=True),
    "fecha": fields.DateTime(required=True)
})
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})

#######################################################
# Definición de endpoints para el swagger
#######################################################
@api.route("/new")
class EventoInsert(Resource):
    @api.expect(eventoDto)
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR")
    @api.response(201, "Created", model=eventoDto)
    @api.response(400, "Unauthorized", model=errorDto)
    @api.response(401, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Insertar nuevo evento"""
        try:
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400

            payload = request.get_json()

            username = SecurityConfig.getUser()
            usuario = json.loads(cliente.getUserByUsername(username))
            payload["usuario"] = usuario
            result = cliente.insertOrUpdateEvento(payload)
            return json.loads(result), 201
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            elif e.code() == grpc.StatusCode.INVALID_ARGUMENT:
                return  {"error": str(e.details())}, 400
            else:
                return {"error": str(e.details())}, 500

@api.route("/delete/<int:id>")
class User(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE","COORDINADOR")
    @api.response(200, "Success", model=eventoDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def delete(self, id):
        """Eliminar usuario"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.deleteEvento(payload)
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500