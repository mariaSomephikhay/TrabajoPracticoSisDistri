from flask import request
from flask_restx import Namespace, Resource, fields
import json
from utils.password_utils import hashPassword, verifyPassword
from config.security_config import SecurityConfig
from grpc_manager_service import ManagerServiceImpl

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
loginDto = api.model("Login", {
    "username": fields.String(required=True),
    "password": fields.String(required=True)
})
tokenDto = api.model("Token", {
    "token": fields.String(required=True),
    "expires_in": fields.Integer(required=True) # Segundos hasta expirar
})


#######################################################
# Definición de endpoints para el swagger
#######################################################
@api.route("/login")
class UserLogin(Resource):
    @api.expect(loginDto)
    @api.marshal_with(tokenDto, mask=False)
    def post(self):
        """Login de usuario y devolución de JWT"""
        try:

            payload = request.get_json()
            username = payload["username"]
            password = payload["password"]

            # Implementar servicio para obtener usuario por username
            user = cliente.getUserByUsername(username)
            if (not user) or (not verifyPassword(password, user["password"])):
                return {"error": "Credenciales inválidas"}, 401

            token = SecurityConfig.generateToken(user)

            return {"token": token, "expires_in": SecurityConfig.expMinutes * 60}, 201
        except Exception as e:
            return {"error": str(e)}, 500
             
@api.route("/register")
class UserRegister(Resource):
    @api.expect(userDto)
    @api.marshal_with(userDto, mask=False)
    def post(self):
        """Registrar un nuevo usuario"""
        try:
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400

            payload = request.get_json()
            payload["password"] = hashPassword(payload["password"])
            
            result = cliente.insertOrUpdateUser(payload)
            return json.loads(result), 201
        except Exception as e:
            return {"error": str(e)}, 500

@api.route("/")
class UserList(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.marshal_with(userListDto, mask=False) #Response
    def get(self):
        """Obtener todos los usuarios"""
        try:
            result = cliente.getAllUser()
            result = cliente.getAllUser()
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
           
@api.route("/<int:id>")
class User(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.marshal_with(userDto, mask=False)  # Response
    def get(self, id):
        """Obtener usuario"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.getUser(payload)
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
        
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.expect(userDto) #Request
    @api.marshal_with(userDto, mask=False) #Response
    def put(self, id):
        """Actualizar un usuario"""
        try:
            print("REQUEST JSON:", request.get_json())  # <--- imprime el body
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400
            payload = request.get_json()
            payload["id"] = id
            result = cliente.insertOrUpdateUser(payload)
            return json.loads(result), 200
        except Exception as e:
            print(e)  # <--- imprime el body
            return {"error": str(e)}, 500
