from flask import request
from flask_restx import Namespace, Resource, fields
from google.protobuf.timestamp_pb2 import Timestamp
#from dateutil import parser
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
eventoListDto = api.model("EventoList", {
    "eventos": fields.List(fields.Nested(eventoDto))
})
usersListDto = api.model('UsersListDto', {
    'usersIds': fields.List(fields.Integer, required=True, description='Lista de IDs de usuarios')
})

#######################################################
# Definición de endpoints para el swagger
#######################################################
@api.route("/new")
class EventoInsert(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR")
    @api.doc(id="createEvent") # Esto define el operationId
    @api.expect(eventoDto)
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
class Evento(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE","COORDINADOR")
    @api.doc(id="deleteEventById") # Esto define el operationId
    @api.response(200, "Success", model=eventoDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def delete(self, id):
        """Eliminar evento"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.deleteEvento(payload)
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
        
@api.route("/<int:id>")
class GetEvento(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="getEventoById") # Esto define el operationId
    @api.response(200, "Success", model=eventoDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener Donacion"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.getEventoById(payload)
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.NOT_FOUND:
                return  {"error": str(e.details())}, 404
            else:
                return {"error": str(e.details())}, 500
            
@api.route("/")
class EventoList(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="listEventos") # Esto define el operationId
    @api.response(200, "Success", model=eventoListDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self):
        """Obtener todos los eventos"""
        try:
            result = cliente.getAllEventos()
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            else:
                return {"error": str(e)}, 500
            
@api.route("/<int:id>/users")
class AddUsersToEvento(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="insertUsersToEvento")
    @api.response(200, "Success")
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    @api.expect(usersListDto)
    def post(self, id):
        """Agregar usuarios a un evento"""
        try:
            
            data = api.payload  
            payload = {
                "id": id,
                "usersIds": [{"id": user_id} for user_id in data.get("usersIds", [])]
            }

            result = cliente.insertUsersToEvento(payload)
            return json.loads(result), 200

        except grpc.RpcError as e:
            if e.code() == grpc.StatusCode.NOT_FOUND:
                return {"error": str(e.details())}, 404
            elif e.code() == grpc.StatusCode.INVALID_ARGUMENT:
                return {"error": str(e.details())}, 400
            elif e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return {"error": str(e.details())}, 401
            else:
                return {"error": str(e.details())}, 500