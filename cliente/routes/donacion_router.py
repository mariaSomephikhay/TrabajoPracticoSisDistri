from flask import request
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig
from grpc_manager_service import ManagerServiceImpl

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
    @api.expect(donacionDto)
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.response(201, "Success", model=donacionDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
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
            return {"error": str(e)}, 500
             