from flask import request
from flask_restx import Namespace, Resource, fields
import json
from grpcManagerService import ManagerServiceImpl

api = Namespace("user", description="Operaciones de usuario")
cliente = ManagerServiceImpl()

#######################################################
# Definición de modelos para el swagger
#######################################################
rolDto = api.model("RolDto", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
userDto = api.model("UserDto", {
    "id": fields.Integer(required=False),
    "username": fields.String(required=True),
    "password": fields.String(required=True),
    "email": fields.String(required=True),
    "nombre": fields.String(required=True),
    "apellido": fields.String(required=True),
    "telefono": fields.String(required=False),
    "activo": fields.String(required=True),
    "rol": fields.Nested(rolDto, required=True)
})

@api.route("/")
class UserList(Resource):
    @api.marshal_with(userDto, as_list=True, mask=False) #Response
    def get(self):
        """Obtener todos los usuarios"""
        result = cliente.getAllUser()
        return json.loads(result), 200
    
    @api.expect(userDto) #Request
    @api.marshal_with(userDto, mask=False) #Response
    def post(self):
        """Crear un nuevo usuario"""
        result = cliente.insertOrUpdateUser(request.json)
        return json.loads(result), 201

@api.route("/<int:id>")
class User(Resource):
    @api.expect(userDto) #Request
    @api.marshal_with(userDto, mask=False) #Response
    def put(self, id):
        """Actualizar un usuario"""
        request.json["id"] = id
        result = cliente.insertOrUpdateUser(request.json)
        return json.loads(result), 200
