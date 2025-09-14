from flask import request
from flask_restx import Namespace, Resource, fields
import json
from utils.password_utils import hashPassword, verifyPassword, generateRandomPassword
from utils.mails_utils import enviarPasswordPorEmail, MailSendError
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
    "password": fields.String(required=False),
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
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})

#######################################################
# Definición de endpoints para el swagger
#######################################################
@api.route("/login")
class UserLogin(Resource):
    @api.expect(loginDto)
    @api.response(201, "Success", model=tokenDto)
    @api.response(401, "Invalid credentials", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Login de usuario y devolución de JWT"""
        try:
            payload = request.get_json()
            username = payload["username"]
            password = payload["password"]

            user = json.loads(cliente.getUserByUsername({'username': username}))
            
            if (not user) or (not verifyPassword(password, user["password"])):
                return {"error": "Credenciales inválidas"}, 401

            token = SecurityConfig.generateToken(user)

            return {"token": token, "expires_in": SecurityConfig.expMinutes * 60}, 201
        except Exception as e:
            print(e)
            return {"error": str(e)}, 500
             
@api.route("/register")
class UserRegister(Resource):
    @api.expect(userDto)
    @api.response(201, "Success", model=userDto)
    @api.response(400, "Request body must be JSON", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Registrar un nuevo usuario"""
        try:
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 400

            payload = request.get_json()

            # Generar contraseña aleatoria
            randomPassword = generateRandomPassword()

            # Envio la contraseña al usuario por su mail ingresado
            try:
                enviarPasswordPorEmail(payload["email"], payload["username"], randomPassword)
            except MailSendError as e:
                # Retorna el código de error definido en MailSendError
                return {"error": str(e)}, e.code or 500

            # Guardo la contraseña encriptada
            payload["password"] = hashPassword(randomPassword)
            
            result = cliente.insertOrUpdateUser(payload)
            return json.loads(result), 201
        except Exception as e:
            return {"error": str(e)}, 500

@api.route("/")
class UserList(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.response(200, "Success", model=userListDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
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
    @api.response(200, "Success", model=userDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener usuario"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.getUserById(payload)
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
        
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.expect(userDto) #Request
    @api.response(200, "Success", model=userDto)
    @api.response(400, "Request body must be JSON", model=errorDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
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
            return {"error": str(e)}, 500
        
@api.route("/delete/<int:id>")
class User(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.response(200, "Success", model=userDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def delete(self, id):
        """Eliminar usuario"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.deleteUser(payload)
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500
