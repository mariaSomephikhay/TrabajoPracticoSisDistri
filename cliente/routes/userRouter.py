from flask import request
from flask_cors import cross_origin
from flask_restx import Namespace, Resource, fields
import json
from grpcManagerService import ManagerServiceImpl

api = Namespace("user", description="Operaciones de usuario")
cliente = ManagerServiceImpl()

#######################################################
# Definición de modelos para el swagger
#######################################################
rolDto = api.model("Rol", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
userDto = api.model("Usuario", {
    "id": fields.Integer(required=False),
    "username": fields.String(required=True),
    "password": fields.String(required=True),
    "email": fields.String(required=True),
    "nombre": fields.String(required=True),
    "apellido": fields.String(required=True),
    "telefono": fields.String(required=False),
    "activo": fields.Boolean(required=True),
    "rol": fields.Nested(rolDto, required=True)
})
userListDto = api.model("UsuarioList", {
    "usuarios": fields.List(fields.Nested(userDto))
})

@cross_origin()
@api.route("/")
class UserList(Resource):
    @api.marshal_with(userListDto, mask=False) #Response
    def get(self):
        """Obtener todos los usuarios"""
        try:
            result = cliente.getAllUser()
            result = cliente.getAllUser()
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
        
    @api.expect(userDto) #Request
    @api.marshal_with(userDto, mask=False) #Response
    def post(self):
        """Crear un nuevo usuario"""
        try:
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400
            payload = request.get_json()
            result = cliente.insertOrUpdateUser(payload)
            return json.loads(result), 201
        except Exception as e:
            return {"error": str(e)}, 500

@cross_origin()
@api.route("/<int:id>")
class User(Resource):
    @api.expect(userDto) #Request
    @api.marshal_with(userDto, mask=False) #Response
    def put(self, id):
        """Actualizar un usuario"""
        try:
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400
            payload = request.get_json()
            payload["id"] = id
            result = cliente.insertOrUpdateUser(payload)
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
