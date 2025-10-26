from decouple import config
import requests
from flask import request, Response
from flask_restx import Namespace, Resource, fields
import json
from config.security_config import SecurityConfig
from datetime import datetime
import random
from grpc_manager_service import ManagerServiceImpl
import grpc

api = Namespace("donaciones", description="Operaciones de donaciones")
cliente = ManagerServiceImpl()

PRODUCER_URL  = config('PRODUCER_URL', cast=str)
GRAPHQL_URL  = config('GRAPHQL_URL', cast=str)
APIREST_URL  = config('APIREST_URL', cast=str)

api = Namespace("solicitudes", description="Operaciones de solicitudes de donaciones")

#######################################################
# Definición de modelos para el swagger Kafka
#######################################################
errorDto = api.model("Solicitud.Error", {
    "error": fields.String(required=True)
})
categoriaDto = api.model("Solicitud.Categoria", {
    "id": fields.Integer(required=True),
    "descripcion": fields.String(required=True)
})
donacionDto = api.model("Solicitud.Donacion", {
    "id": fields.Integer(required=False),
    "categoria": fields.Nested(categoriaDto, required=True),
    "descripcion": fields.String(required=True),
    "cantidad": fields.Integer(required=True)
})
OrganizacionDto =  api.model("Solicitud.OrganizacionDto", {
    "id": fields.Integer(required=True),
    "nombre": fields.String(required=True),
    "externa": fields.Boolean(required=False)
})
SolicitudDonacionDto = api.model("Solicitud", {
    "id_solicitud_donacion": fields.String(required=True),
    "id_organizacion_solicitante": fields.Integer(required=True),
    "donaciones": fields.List(fields.Nested(donacionDto))
})
SolicitudDonacionListDto = api.model("SolicitudList", {
    "solicitudes": fields.List(fields.Nested(SolicitudDonacionDto))
})
SolicitudDonacionGetDto = api.model("SolicitudGet", {
    "id": fields.String(required=True),
    "organizacionSolicitante": fields.Nested(OrganizacionDto),
    "activa": fields.Boolean(required=True),
    "donaciones": fields.List(fields.Nested(donacionDto))
})
SolicitudDonacionGetListDto = api.model("SolicitudGetList", {
    "solicitudes": fields.List(fields.Nested(SolicitudDonacionGetDto))
})
SolicitudDonacionBajaDto = api.model("SolicitudBaja", {
    "id_solicitud_donacion": fields.String(required=True),
    "id_organizacion_solicitante": fields.Integer(required=True)
})
SolicitudDonacionTransferenciaDto = api.model("SolicitudTransferDonation", {
    "id_solicitud": fields.String(required=True),
    "id_organizacion_donante": fields.Integer(required=True),
    "donaciones": fields.List(fields.Nested(donacionDto))
})

OfertaDto = api.model("Oferta", {
    "id_oferta": fields.Integer(required=True),
    "id_organizacion_donante": fields.Integer(required=True),
    "donaciones": fields.List(fields.Nested(donacionDto))
})
OfertaGetDto = api.model("OfertaGet", {
    "id_orferta": fields.String(required=True),
    "organizacionDonante": fields.Nested(OrganizacionDto),
    "donaciones": fields.List(fields.Nested(donacionDto))
})
OfertaLisGetDto = api.model("OfertaGetList", {
    "ofertas": fields.List(fields.Nested(OfertaGetDto))
})


#######################################################
# Definición de modelos para el swagger GraphQL
#######################################################
InformeSolicitudDto = api.model("InformeSolicitud", {
    "categoria": fields.String(required=True),
    "eliminado": fields.String(required=True),
    "cantidad": fields.Integer(required=True),
    "recibida": fields.Boolean(required=True),
})

InformeSolicitudListDto = api.model("InformeSolicitudList", {
    "status": fields.String(required=True),
    "message": fields.String(required=True),
    "data": fields.List(fields.Nested(InformeSolicitudDto))
})

GraphQLInformeEnvelopeDto = api.model("Solicitud.GraphQLInformeEnvelope", {
    "informeSolicitudes": fields.Nested(InformeSolicitudListDto)
})
GraphQLResponseDto = api.model("Solicitud.GraphQLResponse", {
    "data": fields.Nested(GraphQLInformeEnvelopeDto)
})

VariablesInformeSolicitudDto = api.model("Solicitud.VariablesInformeSolicitud", {
    "categoria":  fields.String(required=False),
    "fechaDesde":  fields.String(required=False),
    "fechaHasta":  fields.String(required=False),
    "eliminado" :  fields.String(required=False)
})

FiltroInformeSolicitudDto = api.model("Solicitud.FiltronformeSolicitud", {
    "filtro":  fields.Nested(VariablesInformeSolicitudDto, requerided=True)
})

queryInformeSolicitudDto = api.model("Solicitud.QueryInformeSolicitud", {
    "query":  fields.String(required=True,
                            example="query InformeSolicitudes($filtro: FiltroSolicitudInput) { informeSolicitudes(filtro: $filtro) { status message data { categoria eliminado cantidad recibida } } }",
                            default="query InformeSolicitudes($filtro: FiltroSolicitudInput) { informeSolicitudes(filtro: $filtro) { status message data { categoria eliminado cantidad recibida } } }"),
    "variables":   fields.Nested(FiltroInformeSolicitudDto,required=True)
})

#######################################################
# Definición de modelos para el swagger Rest
#######################################################

filtroSolicitudDetalleDto = api.model("filtroSolicitudDetalleDto", {
    "categoria": fields.String(required=False),
    "fechaDesde": fields.String(required=False),
    "fechaHasta": fields.String(required=False),
    "eliminado": fields.String(required=False)
})

excelResponseDto = api.model("ExcelFileResponse", {
    "file": fields.String(required=True)
})

#######################################################
# Definición de endpoints-kafka para el swagger
#######################################################

# Kafka - Endpoints
@api.route("/request/new")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="newRequestDonacion") # Esto define el operationId
    @api.expect(SolicitudDonacionDto) #Request
    @api.response(201, "Created", model=SolicitudDonacionDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Enviar solicitud de donaciones a kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Generar ID único tipo GK-fechahora-random4digitos
            fecha_hora = datetime.now().strftime("%Y%m%d%H%M%S")
            random_digits = f"{random.randint(0, 9999):04}"
            data["id_solicitud_donacion"] = f"GK-{fecha_hora}-{random_digits}"

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/donation/request/new"
            response = requests.post(url, json=data)

            return response.json(), response.status_code
        
        except Exception as e:
            return {"error": str(e)}, 500
        
@api.route("/")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="getAllRequestDonacion") # Esto define el operationId
    @api.response(200, "Success", model=SolicitudDonacionGetListDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self):
        """Obtener todos las solicitudes donaciones"""
        try:
            result = cliente.getAllSolicitudDonaciones()
            
            return json.loads(result), 200
        except Exception as e:
            if e.code() == grpc.StatusCode.UNAUTHENTICATED:
                return  {"error": str(e.details())}, 401
            else:
                return {"error": str(e)}, 500
            
@api.route("/delete")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="deleteRequestDonacion") # Esto define el operationId
    @api.expect(SolicitudDonacionBajaDto) #Request
    @api.response(201, "Success", model=SolicitudDonacionBajaDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def delete(self):
        """Enviar solicitud de donaciones a kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/donation/request/delete"
            response = requests.post(url, json=data)

            return response.json(), response.status_code
        
        except Exception as e:
            return {"error": str(e)}, 500

@api.route("/transfer/<int:id>") #Es el id de la organizacion solicitante
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="newTransfer") # Esto define el operationId
    @api.expect(SolicitudDonacionTransferenciaDto) #Request
    @api.response(200, "Success")
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self, id):
        """Enviar transferencia de donaciones a organizacion solicitante con kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/donation/transfer/{id}"
            requests.post(url, json=data)

            return 200
        
        except Exception as e:
            return {"error": str(e)}, 500

@api.route("/offer/new")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="newOffer") # Esto define el operationId
    @api.expect(OfertaDto) #Request
    @api.response(201, "Created", model=OfertaDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Ofrecer donaciones de una organizacion a kafka"""
        try:
            if not request.is_json:
                return {"error": "Bad Request"}, 400
            
            data = request.get_json()

            # Endpoint del producer en Java
            url = f"{PRODUCER_URL}/donation/offer/new"
            response = requests.post(url, json=data)

            return response.json(), 201

        except Exception as e:
            return {"error": str(e)}, 500

@api.route("/offer/<int:id>")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="getAllOffersByOrganization") # Esto define el operationId
    @api.response(200, "Success", model=OfertaLisGetDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def get(self, id):
        """Obtener todos las ofertas de la organizacion donante"""
        try:
            payload = {"id": id}
            result = cliente.getAllOffersByIdOrganization(payload)
            
            return json.loads(result), 200
        except Exception as e:
            return {"error": str(e)}, 500

# GRAPHQL - Endpoints
@api.route("/informe/")
class adhesion(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="informeSolicitudesDonaciones") # Esto define el operationId
    @api.expect(queryInformeSolicitudDto) #Request
    @api.response(200, "Success", model=GraphQLResponseDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    def post(self):
        """Consulta de informe de solicitudes con filtros"""
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
        
#Rest Endpoints
@api.route("/informe/excel")
class Solicitud(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE", "VOCAL")
    @api.doc(id="informeSolicitudesDetalle") # Esto define el operationId
    @api.expect(filtroSolicitudDetalleDto) #Request
    @api.response(200, "Success", model=excelResponseDto)
    @api.response(401, "Unauthorized", model=errorDto)
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(500, "Internal server error", model=errorDto)
    @api.doc(security='Bearer Auth')
    def post(self):
        """Solcitudes de donaciones en Excel"""
        try:
            if not request.is_json:
                return {"error": "Bad Request: se esperaba JSON"}, 400
            
            # Obtenemos el JSON del frontend
            data = request.get_json()
            print(data)
            filtro_limpio = {k: v for k, v in data.items() if v is not None}
            filtro_limpio = {k: v for k, v in filtro_limpio.items() if v is not ''}
            # Enviamos la petición al endpoint Java
            headers = {"Content-Type": "application/json"}
            response = requests.post(APIREST_URL+"/solicitudes/informes" , headers=headers, json=filtro_limpio)

            # Si Java responde con éxito (200), reenviamos el Excel al frontend
            if response.status_code == 200:
                return Response(
                    response.content,
                    status=200,
                    content_type="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    headers={
                        "Content-Disposition": "attachment; filename=informe_solicitudes.xlsx"
                    }
                )
            else:
                # Si hubo error, reenviamos el mensaje tal cual
                return {
                    "error": response.text
                }, response.status_code
    

        except Exception as e:
            return {"error": str(e)}, 500        
