from decouple import config
import requests
from flask import request
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig

api = Namespace("filtros", description="Operaciones de filtros")
APIREST_URL  = config('APIREST_URL', cast=str)
GRAPHQL_URL  = config('GRAPHQL_URL', cast=str)

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
# Obetjos de la API GraphQL
#######################################################

traerVarGraphqlDto = api.model("traerVarGraphqlDto", {
    "usuario": fields.String(required=True),
    "tipo": fields.String(required=True)
})

traerQueryGraphqlDto = api.model("traerQueryGraphql", {
    "query": fields.String(required=True,
                            example="query TraerFiltros($tipo: String!,$usuario: String!) { traerFiltros(tipo: $tipo, usuario: $usuario) { status message data { id name valueFilter usuario filterType } } }",
                            default="query TraerFiltros($tipo: String!,$usuario: String!) { traerFiltros(tipo: $tipo, usuario: $usuario) { status message data { id name valueFilter usuario filterType } } }"),
    "variables": fields.Nested(traerVarGraphqlDto),
})

subirFilGraphqlDto = api.model("subirFilGraphql", {
    "name": fields.String(required=True),
    "valueFilter": fields.String(required=True),
    "usuario": fields.String(required=True),
    "filterType": fields.String(required=True)
})

subirVarGraphqlDto = api.model("subirVarGraphql", {
    "filtro": fields.Nested(subirFilGraphqlDto)
})


subirQueryGraphqlDto = api.model("subirQueryGraphql", {
    "query": fields.String(required=True,
                            example="mutation GuardarFiltro($filtro: FilterInput!) { guardarFiltro(filtro: $filtro) { status message data { id name valueFilter usuario filterType } } }",
                            default="mutation GuardarFiltro($filtro: FilterInput!) { guardarFiltro(filtro: $filtro) { status message data { id name valueFilter usuario filterType } } }"),
    "variables": fields.Nested(subirVarGraphqlDto)
})

borrarVarGraphqlDto = api.model("borrarVarGraphq", {
    "id": fields.Integer(required=True)
})

borrarQueryGraphqlDto = api.model("borrarQueryGraphql", {
    "query": fields.String(required=True,
                            example="mutation BorrarFiltro($id: ID!) { borrarFiltro(id: $id) { status message data { id name valueFilter usuario filterType } } }",
                            default="mutation BorrarFiltro($id: ID!) { borrarFiltro(id: $id) { status message data { id name valueFilter usuario filterType } } }"),
    "variables": fields.Nested(borrarVarGraphqlDto),
})

resDataGraphqlDto = api.model("resDataGraphql", {
    "id": fields.Integer(required=True),
    "name": fields.String(required=True),
    "valueFilter": fields.String(required=True),
    "usuario": fields.String(required=True),
    "filterType": fields.String(required=True)
})

traerFiltrosGraphqlDto = api.model("traerFiltrosGraphqlDto", {
    "status": fields.String(required=True),
    "message": fields.String(required=True),
    "data": fields.List(fields.Nested(resDataGraphqlDto))
})

traerFiltrosGraphQLEnvelopeDto = api.model("traerFiltrosGraphQLEnvelope", {
    "traerFiltros": fields.Nested(traerFiltrosGraphqlDto)
})
FiltrosGraphQLResponseDto = api.model("FiltrosGraphQLResponse", {
    "data": fields.Nested(traerFiltrosGraphQLEnvelopeDto)
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

@api.route("/update/<int:id>")
class updateFilter(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="updateFilter") # Esto define el operationId
    @api.expect(filtroDto)
    @api.response(200, "Success", model=filtroDto)
    @api.response(403, "Access forbidden", model=errorDto)
    @api.response(404, "Resource not found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def put(self, id):
        """actualizar filtro por id"""
        try:

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

            url = APIREST_URL + "filter/update/" + str(id)
            headers = {"Content-Type": "application/json"}

            response = requests.put(url, json=new_request, headers=headers)

            if response.status_code == 200:
                return {"message": "Filtro actualizado correctamente"}, 200
            else:
                return {"error": response.text}, response.status_code
        except Exception as e:
            error_msg = getattr(e, "details", lambda: str(e))()
            # Error de gRPC que devuelve el servicio
            if "NOT_FOUND" in str(e):
                return {"error": error_msg}, 404
            else:
                return {"error": error_msg}, 500

# GRAPHQL - Endpoints
@api.route("/guardar/graphql/")
class adhesion(Resource):
    @api.doc(security='Bearer Auth')
    #@SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="subirQueryGraphqlDto") # Esto define el operationId
    @api.expect(subirQueryGraphqlDto) #Request
    @api.response(200, "Success", model=FiltrosGraphQLResponseDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """guarda filtros con graphql"""
        try:
            
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{GRAPHQL_URL}"
            print(data)
            headers = {"Content-Type": "application/json"}

            response = requests.post(url, headers=headers, json=data)
            print(response.json())
            return response.json(), response.status_code

        except Exception as e:
            return {"error": str(e)}, 500        

# GRAPHQL - Endpoints
@api.route("/traer/graphql/")
class adhesion(Resource):
    @api.doc(security='Bearer Auth')
    #@SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="traerFiltrosGraphQL") # Esto define el operationId
    @api.expect(traerQueryGraphqlDto) #Request
    @api.response(200, "Success", model=FiltrosGraphQLResponseDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """trae filtros con graphql"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{GRAPHQL_URL}"
            headers = {"Content-Type": "application/json"}

            response = requests.post(url, headers=headers, json=data)
            print(response.json())
            return response.json(), response.status_code

        except Exception as e:
            return {"error": str(e)}, 500   

# GRAPHQL - Endpoints
@api.route("/borrar/graphql/")
class adhesion(Resource):
    @api.doc(security='Bearer Auth')
    #@SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="borrarFiltrosGraphQL") # Esto define el operationId
    @api.expect(borrarQueryGraphqlDto) #Request
    @api.response(200, "Success", model=FiltrosGraphQLResponseDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """borra filtros con graphql"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()
            print(data)
                
            # Endpoint del producer en Java
            url = f"{GRAPHQL_URL}"
            print(data)
            headers = {"Content-Type": "application/json"}

            response = requests.post(url, headers=headers, json=data)
            print(response.json())
            return response.json(), response.status_code

        except Exception as e:
            return {"error": str(e)}, 500                   