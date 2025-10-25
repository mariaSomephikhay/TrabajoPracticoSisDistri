from decouple import config
import requests
from flask import request
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig

api = Namespace("filtros", description="Operaciones de filtros")
APIREST_URL  = config('APIREST_URL', cast=str)

#######################################################
# Obetjos de la API REST
#######################################################
filtroDto = api.model("filtroDto", {
    "id": fields.Integer(required=True),
    "name": fields.String(required=True),
    "valueFilter": fields.String(required=True),
    "usuario": fields.String(required=True),
    "filterType": fields.String(required=True)
})
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})
#######################################################
# Definición de endpoints para el swagger
#######################################################
@api.route("/new")
class FilterInsert(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="createFilter") # Esto define el operationId
    @api.expect(filtroDto)
    @api.response(201, "Created", model=filtroDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Insertar un nuevo filtro"""
        if not request.is_json:
            return {"error": "Request body must be JSON"}, 400

        payload = request.get_json()  
        data_to_send = {k: v for k, v in payload.items() if k != "id"} 

        url = APIREST_URL + "filter/new"
        headers = {"Content-Type": "application/json"}

        response = requests.post(url, json=data_to_send, headers=headers)

        print("Status code:", response.status_code)

        if response.status_code == 201:
            return {"message": "Filtro creado correctamente"}, 201
        else:
            return {"error": response.text}, response.status_code

@api.route("/<string:type>/<string:idUsuario>")
class getFilter(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="getFilter") # Esto define el operationId
    @api.response(200, "Success", model=filtroDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, type, idUsuario):
        """traer filtro por usuario y tipo"""

        url = APIREST_URL + "filter/" + type + "/"+ idUsuario

        response = requests.get(url)

        print("Status:", response.status_code)
        print("Body:", response.text)

        if response.status_code == 200:
            return response.json(), 200
        else:
            return {"error": response.text}, response.status_code

@api.route("/delete/<int:id>")
class deleteFilter(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="deleteFilter") # Esto define el operationId
    @api.response(200, "Success")
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(404, "Resource not found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def delete(self, id):
        """Eliminar filtro por id"""
        try:

            url = APIREST_URL + "filter/delete/" + str(id)

            response = requests.delete(url)

            if response.status_code == 200:
                return {"message": "Filtro eliminado correctamente"}, 200
            else:
                return {"error": response.text}, response.status_code
        except Exception as e:
            error_msg = getattr(e, "details", lambda: str(e))()
            # Error de gRPC que devuelve el servicio
            if "NOT_FOUND" in str(e):
                return {"error": error_msg}, 404
            else:
                return {"error": error_msg}, 500

