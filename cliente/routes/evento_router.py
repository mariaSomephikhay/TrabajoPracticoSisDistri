from decouple import config
import requests
from flask import request
from flask_restx import Namespace, Resource, fields
from google.protobuf.timestamp_pb2 import Timestamp
#from dateutil import parser
import json
from config.security_config import SecurityConfig
from grpc_manager_service import ManagerServiceImpl
import grpc
from datetime import datetime
import random

PRODUCER_URL  = config('PRODUCER_URL', cast=str)
GRAPHQL_URL  = config('GRAPHQL_URL', cast=str)

api = Namespace("eventos", description="Operaciones de eventos")
cliente = ManagerServiceImpl()



#######################################################
# Definición de modelos para el swagger
#######################################################
eventoDto = api.model("Evento", {
    "id": fields.String(required=False),
    "nombre": fields.String(required=True),
    "descripcion": fields.String(required=True),
    "fecha": fields.DateTime(required=True),
    "idOrganizacion": fields.Integer(required=False),
    "activo": fields.Boolean(required=False),
    "publicado": fields.Boolean(required=False)
})
categoriaDto = api.model("Evento.Categoria", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
donacionObjDto = api.model("Evento.Donacion.Objeto", {
    "id": fields.Integer(required=False),
    "categoria": fields.Nested(categoriaDto, required=True),
    "descripcion": fields.String(required=True),
    "cantidad": fields.Integer(required=True)
})
donacionDto = api.model("Evento.Donacion", {
    "donacion": fields.Nested(donacionObjDto, required=True),
    "cantidad": fields.Integer(required=True)
})
donacionDtoReq = api.model("Evento.Donacion.Req", {
    "donacionId": fields.Integer(required=True),
    "cantidad": fields.Integer(required=True)
})
errorDto = api.model("Error", {
    "error": fields.String(required=True)
})
eventoListDto = api.model("EventoList", {
    "eventos": fields.List(fields.Nested(eventoDto))
})
usersListDto = api.model('Evento.UsersListDto', {
    'usersIds': fields.List(fields.String, required=True, description='Lista de IDs de usuarios')
})
lstDonaciones = api.model('Evento.Lista.Donacion', {
    "idEvento": fields.String(required=False),
    'listaDonacion' : fields.List(fields.Nested(donacionDto), required=True, description='Lista de las donaciones'),
})
rolDto = api.model("Evento.Rol", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
userDto = api.model("Evento.Usuario", {
    "id": fields.String(required=False),
    "username": fields.String(required=True),
    "password": fields.String(required=False),
    "email": fields.String(required=True),
    "nombre": fields.String(required=True),
    "apellido": fields.String(required=True),
    "telefono": fields.String(required=False),
    "activo": fields.Boolean(required=True),
    "rol": fields.Nested(rolDto, required=True)
})
OrganizacionDto =  api.model("Evento.OrganizacionDto", {
    "id": fields.Integer(required=True),
    "nombre": fields.String(required=True),
    "externa": fields.Boolean(required=False)
})
voluntarioEventoDto = api.model("VoluntarioEventoDto", {
    "idVoluntario": fields.String(required=True),
    "nombre": fields.String(required=True),
    "apellido": fields.String(required=True),
    "telefono": fields.String(required=False),
    "email": fields.String(required=True),
    "organizacion": fields.Nested(OrganizacionDto, required=True)
})
eventoUsersDto = api.model("EventoUsersDto", {
    "evento": fields.Nested(eventoDto, required=True),
    "users": fields.List(fields.Nested(userDto, required=False)),
    "voluntarios": fields.List(fields.Nested(voluntarioEventoDto, required=False))
})

#######################################################
# Obetjos de kafka
#######################################################
eventoKafka = api.model("eventoKafka", {
    "id_organizacion": fields.String(required=True),
    "id_evento": fields.String(required=True),
    "nombre": fields.String(required=True),
    "descripcion": fields.String(required=True),
    "fecha": fields.DateTime(required=True)
})

eventoBajaKafka = api.model("eventoBajaKafka", {
    "id_organizacion": fields.String(required=True),
    "id_evento": fields.String(required=True)
})

voluntarioDto = api.model("VoluntarioDto", {
    "idOrganizacion": fields.Integer(required=True),
    "idVoluntario": fields.String(required=True),
    "nombre": fields.String(required=True),
    "apellido": fields.String(required=True),
    "telefono": fields.String(required=False),
    "email": fields.String(required=True)
})

adhesionEventoKafka = api.model("adhesionEventoKafka", {
    "id_evento": fields.String(required=True),
    "voluntario": fields.Nested(voluntarioDto, required=True)
})

#######################################################
# Obetjos de GRAPHQL
#######################################################
filtroEvento = api.model("filtroEvento", {
    "usuarioId": fields.String(required=True),
    "fechaDesde": fields.Date(required=False),
    "fechaHasta": fields.Date(required=False),
    "tieneDonacion": fields.Integer(required=True)
})
eventoDonacionesDtoInforme = api.model("eventoDonacionesDtoInforme", {
    "cantRepartida": fields.Integer(required=True),
    "donacion": fields.Nested(donacionObjDto, required=True)
})
eventoDtoInforme = api.model("eventoDtoInforme", {
    "id": fields.String(required=False),
    "nombre": fields.String(required=True),
    "descripcion": fields.String(required=True),
    "fecha": fields.DateTime(required=True),
    "eventoDonaciones": fields.List(fields.Nested(eventoDonacionesDtoInforme, required=False))
})
informeParticipacionEventos = api.model("informeParticipacionEventos", {
    "mes": fields.String(required=True),
    "eventos": fields.List(fields.Nested(eventoDtoInforme, required=True))
})
informeEventosDto = api.model("informeEventosDto", {
    "informeParticipacionEventos": fields.List(fields.Nested(informeParticipacionEventos, required=True))
})
eventoDtoFiltro = api.model("eventoDtoFiltro", {
    "data": fields.Nested(informeEventosDto, required=True)
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

            if "id" not in payload:
                fecha_hora = datetime.now().strftime("%Y%m%d%H%M%S")
                random_digits = f"{random.randint(0, 9999):04}"
                payload["id"] = f"GK-{fecha_hora}-{random_digits}"

            #print(payload)

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

@api.route("/delete/<string:id>")
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
        
@api.route("/<string:id>")
class GetEvento(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO")
    @api.doc(id="getEventoById") # Esto define el operationId
    @api.response(200, "Success", model=eventoDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener Evento"""
        try:
            payload = {"id": id}  # solo necesitas el id
            result = cliente.getEventoById(payload)
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.NOT_FOUND:
                return  {"error": str(e.details())}, 404
            else:
                return {"error": str(e.details())}, 500
    
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR")
    @api.doc(id="updateEventoById") # Esto define el operationId
    @api.expect(eventoDto) #Request
    @api.response(200, "Success", model=eventoDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def put(self, id):
        """Actualizar un evento"""
        try:
            #print("REQUEST JSON:", request.get_json())  # <--- imprime el body
            if not request.is_json:
                return {"error": "Request body must be JSON"}, 401
            payload = request.get_json()
            payload["id"] = id
            username = SecurityConfig.getUser()
            usuario = json.loads(cliente.getUserByUsername(username))
            payload["usuario"] = usuario
            #print(payload)
            result = cliente.insertOrUpdateEvento(payload)
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            elif e.code() == grpc.StatusCode.INVALID_ARGUMENT:
                return  {"error": str(e.details())}, 400
            else:
                return {"error": str(e.details())}, 500
            
@api.route("/")
class EventoList(Resource):
    @api.doc(security='Bearer Auth') # Esto hace que Swagger agregue el header para el token
    @SecurityConfig.authRequired("PRESIDENTE", "VOLUNTARIO", "COORDINADOR")
    @api.doc(id="listEventos") # Esto define el operationId
    @api.response(200, "Success", model=eventoListDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self):
        """Obtener todos los eventos"""
        try:
            result = cliente.getAllEventos()

            eventos = json.loads(result)

            if eventos.get('eventos'):
                for evento in eventos['eventos']:
                    # Lógica para determinar los valores de 'activo' y 'publicado'
                    evento['activo'] = evento.get('activo', False)  # Si no existe, asignar False
                    evento['publicado'] = evento.get('publicado', False)  # Lo mismo para 'publicado'

            return eventos, 200
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            else:
                return {"error": str(e)}, 500
            
@api.route("/<string:id>/users/add")
class AddUsersToEvento(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO")
    @api.doc(id="insertUsersToEvento")
    @api.response(200, "Success", model=eventoUsersDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    @api.expect(usersListDto)
    def put(self, id):
        """Agregar usuarios a un evento"""
        try:
            
            data = api.payload  
            #print(data)
            payload = {
                "id": id,
                "usersIds": [{"id": user_id} for user_id in data.get("usersIds", [])]
            }
            #print(payload)
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
            
@api.route("/<string:id>/donaciones/add")
class AddDonacionesToEvento(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO")
    @api.doc(id="insertDonacionesToEvento")
    @api.response(200, "Success")
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    @api.expect(donacionDtoReq)
    def put(self, id):
        """Agregar donaciones al evento"""
        try:
            data = api.payload  

            payload = request.get_json()

            username = SecurityConfig.getUser()
            usuario = json.loads(cliente.getUserByUsername(username))
            

            payload = {
                "idEvento": id,
                "donacionId": data.get("donacionId"),
                "cantidad": data.get("cantidad"),
                "usuario": usuario
            }
            
            result = cliente.insertDonacionesToEvento(payload)
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

@api.route("/<string:id>/donaciones")
class getEventoWithDonacionesById(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO")
    @api.doc(id="getEventoWithDonacionesById")
    @api.response(200, "Success", model=lstDonaciones)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener donaciones del evento"""
        try:
            payload = {"id": id}
            result = cliente.getEventoWithDonacionesById(payload)
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

@api.route("/<string:id>/usuarios")
class getEventoWithUsersById(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO")
    @api.doc(id="getEventoWithUsersById")
    @api.response(200, "Success", model=eventoUsersDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(404, "Not Found", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener usuarios del evento"""
        try:
            payload = {"id": id}
            result = cliente.getEventoWithUsersById(payload)
            #print(result)
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

# Kafka - Endpoints
@api.route("/request/new")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR")
    @api.doc(id="newRequesEventoKafka") 
    @api.expect(eventoKafka)
    @api.response(201, "Created", model=eventoKafka)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Publicar evento en kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/event/request/new"
            
            response = requests.post(url, json=data)

            return response.json(), response.status_code
        
        except Exception as e:
            return {"error": str(e)}, 500

# Kafka - Endpoints
@api.route("/request/delete")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR")
    @api.doc(id="deleteRequesEventoKafka") 
    @api.expect(eventoBajaKafka)
    @api.response(201, "Created", model=eventoBajaKafka)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Publicar baja de evento en kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/event/request/delete"
            
            response = requests.post(url, json=data)

            return response.json(), response.status_code
        
        except Exception as e:
            return {"error": str(e)}, 500

# Kafka - Endpoints
@api.route("/adhesion/evento/<int:id_organization>")
class adhesion(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR")
    @api.doc(id="adhesionEvento") # Esto define el operationId
    @api.expect(adhesionEventoKafka) #Request
    @api.response(200, "Success")
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self, id_organization):
        """Adhesion de voluntario a evento"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/event/adhesion-evento/{id_organization}"
            requests.post(url, json=data)

            return 200
        
        except Exception as e:
            return {"error": str(e)}, 500

# GRAPHQL - Endpoints
@api.route("/filtro/")
class consultarFiltro(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "COORDINADOR", "VOLUNTARIO", "VOCAL")
    @api.doc(id="filtroEvento") # Esto define el operationId
    @api.expect(filtroEvento) #Request
    @api.response(200, "Success", model=eventoDtoFiltro)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Consulta de eventos con filtros"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{GRAPHQL_URL}"
            
            data = request.json
            usuarioId = data.get("usuarioId")
            fechaDesde = data.get("fechaDesde") 
            fechaHasta = data.get("fechaHasta")  
            tieneDonacion = data.get("tieneDonacion")

            query = """
            query($usuarioId: ID!, $fechaDesde: String, $fechaHasta: String, $tieneDonacion: String!) {
            informeParticipacionEventos(
                usuarioId: $usuarioId,
                fechaDesde: $fechaDesde,
                fechaHasta: $fechaHasta,
                tieneDonacion: $tieneDonacion
            ) {
                mes
                eventos {
                id
                nombre
                fecha
                descripcion
                eventoDonaciones {
                    cantRepartida
                    donacion {
                    id
                    descripcion
                    cantidad
                    categoria {
                        descripcion
                    }
                    }
                }
                }
            }
            }
            """

            variables = {
                "usuarioId": usuarioId,
                "fechaDesde": fechaDesde,
                "fechaHasta": fechaHasta,
                "tieneDonacion": str(tieneDonacion)
            }

            headers = {"Content-Type": "application/json"}

            response = requests.post(url, headers=headers, json={"query": query, "variables": variables})

            return response.json(), response.status_code
        
        except Exception as e:
            return {"error": str(e)}, 500
