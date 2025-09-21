from flask import request
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig
from grpc_manager_service import ManagerServiceImpl
import grpc

api = Namespace("donaciones", description="Operaciones de donaciones")
cliente = ManagerServiceImpl()

#######################################################
# Definición de modelos para el swagger
#######################################################
categoriaDto = api.model("Categoria", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
donacionDto = api.model("Donacion", {
    "id": fields.Integer(required=False),
    "categoria": fields.Nested(categoriaDto, required=True),
    "descripcion": fields.String(required=True),
    "cantidad": fields.Integer(required=True),
    "eliminado": fields.Boolean(required=False)
})
donacionListDto = api.model("DonacionList", {
    "donaciones": fields.List(fields.Nested(donacionDto))
})
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})

#######################################################
# Definición de endpoints para el swagger
#######################################################
@api.route("/new")
class DonacionInsert(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="createDonation") # Esto define el operationId
    @api.expect(donacionDto)
    @api.response(201, "Created", model=donacionDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        print(request.get_json())
        """Insertar una nueva donacion"""
        try:
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400

            payload = request.get_json()
            username = SecurityConfig.getUser()
            usuario = json.loads(cliente.getUserByUsername(username))
            payload["usuario"] = usuario
            result = cliente.insertOrUpdateDonacion(payload)
            return json.loads(result), 201
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            elif e.code() == grpc.StatusCode.INVALID_ARGUMENT:
                return  {"error": str(e.details())}, 400
            else:
                return {"error": str(e.details())}, 500
           
@api.route("/<int:id>")
class Donacion(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="getDonationById") # Esto define el operationId
    @api.response(200, "Success", model=donacionDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener Donacion"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.getDonacionById(payload)
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.NOT_FOUND:
                return  {"error": str(e.details())}, 404
            else:
                return {"error": str(e.details())}, 500
        
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="updateDonationById") # Esto define el operationId
    @api.expect(donacionDto) #Request
    @api.response(200, "Success", model=donacionDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def put(self, id):
        """Actualizar un donacion"""
        try:
            #print("REQUEST JSON:", request.get_json())  # <--- imprime el body
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 401
            payload = request.get_json()
            payload["id"] = id
            username = SecurityConfig.getUser()
            usuario = json.loads(cliente.getUserByUsername(username))
            payload["usuario"] = usuario
            result = cliente.insertOrUpdateDonacion(payload)
            return json.loads(result), 200
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
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="deleteDonationById") # Esto define el operationId
    @api.response(200, "Success", model=donacionDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def delete(self, id):
        """Eliminar donacion"""
        try:
            payload = {"id": id}  # solo necesitas el id
            username = SecurityConfig.getUser()
            usuario = json.loads(cliente.getUserByUsername(username))
            payload["usuario"] = usuario
            print(payload)
            result = cliente.deleteDonacion(payload)
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            elif e.code() == grpc.StatusCode.NOT_FOUND:
                return  {"error": str(e.details())}, 404
            else:
                return {"error": str(e.details())}, 500


@api.route("/")
class DonacionList(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="listDonations") # Esto define el operationId
    @api.response(200, "Success", model=donacionListDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self):
        """Obtener todos las donaciones"""
        try:
            result = cliente.getAllDonaciones()
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            else:
                return {"error": str(e)}, 500