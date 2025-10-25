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
ListaValue = api.model("ListaValue", {
    "key": fields.String(required=True),
    "value": fields.String(required=True)
})
filtroDto = api.model("filtroDto", {
    "id": fields.Integer(required=True),
    "name": fields.String(required=True),
    "usuario": fields.String(required=True),
    "filterType": fields.String(required=True),
    "valueFilter": fields.List(fields.Nested(ListaValue), required=True)
})
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})
ListaFiltrosDto = api.model("ListaFiltrosDto", {
    "filtros": fields.List(fields.Nested(filtroDto), required=True)
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

        # Convertir valueFilter a string
        value_filter_str = ";".join(f"{item['key']}:{item['value']}" for item in payload["valueFilter"])

        # Crear nueva estructura
        new_request = {
            "name": payload["name"],
            "valueFilter": value_filter_str,
            "usuario": payload["usuario"],
            "filterType": payload["filterType"]
        }


        url = APIREST_URL + "filter/new"
        headers = {"Content-Type": "application/json"}

        response = requests.post(url, json=new_request, headers=headers)

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
    @api.response(200, "Success", model=ListaFiltrosDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, type, idUsuario):
        """traer filtro por usuario y tipo"""

        url = APIREST_URL + "filter/" + type + "/"+ idUsuario

        response = requests.get(url)

        # Primero comprobar si la respuesta fue exitosa
        if response.status_code == 200:
            try:
                data = response.json()  # Esto es una lista de filtros
            except ValueError:
                return {"error": "Respuesta no es JSON válido"}, 500

            new_data = {"filtros": []}

            for filtro in data:  # <-- data es una lista, no dict
                key_value_pairs = []
                for pair in filtro.get("valueFilter", "").split(";"):
                    if ":" in pair:
                        key, value = pair.split(":", 1)
                        key_value_pairs.append({"key": key, "value": value})

                new_data["filtros"].append({
                    "id": filtro.get("id", 0),
                    "name": filtro.get("name", ""),
                    "usuario": filtro.get("usuario", ""),
                    "filterType": filtro.get("filterType", ""),
                    "valueFilter": key_value_pairs
                })

            return new_data, 200

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

