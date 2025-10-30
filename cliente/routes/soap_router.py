from flask import request
from flask_restx import Namespace, Resource, fields
import requests
import xmltodict
from config.security_config import SecurityConfig
from decouple import config

api = Namespace("soap", description="Consultas SOAP de Presidentes y ONGs")

SOAP_HEADERS = {"Content-Type": "text/xml; charset=utf-8"}
SOAP_URL  = config('SOAP_URL', cast=str)
GRUPO  = config('SOAP_GROUP', cast=str)
CLAVE = config('SOAP_PASSWORD', cast=str)

# Modelos para Swagger
orgIdsDto = api.model("OrgIds", {
    "ids": fields.List(fields.Integer, required=True, example=[6, 5, 8, 10])
})

errorDto = api.model("Error", {
    "error": fields.String
})

ongDto = api.model("ONG", {
    "id": fields.Integer,
    "name": fields.String,
    "address": fields.String,
    "phone": fields.String
})

presidenteDto = api.model("Presidente", {
    "id": fields.Integer,
    "name": fields.String,
    "address": fields.String,
    "phone": fields.String,
    "organization_id": fields.Integer
})

# ===================================================
# FUNCIONES AUXILIARES
# ===================================================
def make_soap_request(action, body):
    """Envía un request SOAP genérico y devuelve la respuesta XML"""
    headers = SOAP_HEADERS.copy()
    headers["SOAPAction"] = action

    response = requests.post(SOAP_URL, data=body, headers=headers)
    response.raise_for_status()
    return response.text

def parse_soap_xml(xml_str):
    """Convierte XML SOAP en diccionario limpio"""
    data = xmltodict.parse(xml_str)
    return data


# SOAP ENDPOINTS

@api.route("/ongs")
class OngsSOAP(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.expect(orgIdsDto)
    @api.response(200, "Success", [ongDto])
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    def post(self):
        """Consultar datos de ONGs por IDs (SOAP)"""
        try:
            data = request.get_json()
            ids = data.get("ids", [])
            if not ids:
                return {"error": "Debe proporcionar una lista de IDs"}, 400

            # Construir XML SOAP
            ids_xml = "".join([f"<tns:string>{i}</tns:string>" for i in ids])
            soap_body = f"""<?xml version="1.0" encoding="utf-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:auth="auth.headers" xmlns:tns="soap.backend">
                <soapenv:Header>
                    <auth:Auth>
                    <auth:Grupo>{GRUPO}</auth:Grupo>
                    <auth:Clave>{CLAVE}</auth:Clave>
                    </auth:Auth>
                </soapenv:Header>
                <soapenv:Body>
                    <tns:list_associations>
                    <tns:org_ids>
                        {ids_xml}
                    </tns:org_ids>
                    </tns:list_associations>
                </soapenv:Body>
                </soapenv:Envelope>"""

            xml_response = make_soap_request("list_associations", soap_body)
            parsed = parse_soap_xml(xml_response)

            # Navegar el diccionario hasta los datos
            items = parsed["soap11env:Envelope"]["soap11env:Body"]["tns:list_associationsResponse"]["tns:list_associationsResult"]["s0:OrganizationType"]

            # Convertir a lista de diccionario
            if isinstance(items, dict):
                items = [items]

            result = [{
                "id": int(i["s0:id"]),
                "name": i["s0:name"],
                "address": i["s0:address"],
                "phone": i["s0:phone"]
            } for i in items]

            return result, 200

        except Exception as e:
            return {"error": str(e)}, 500



@api.route("/presidentes")
class PresidentesSOAP(Resource):
    @api.doc(security='Bearer Auth')
    @SecurityConfig.authRequired("PRESIDENTE")
    @api.expect(orgIdsDto)
    @api.response(200, "Success", [presidenteDto])
    @api.response(400, "Bad Request", model=errorDto)
    @api.response(401, "Unauthorized", model=errorDto)
    def post(self):
        """Consultar datos de Presidentes por IDs (SOAP)"""
        try:
            data = request.get_json()
            ids = data.get("ids", [])
            if not ids:
                return {"error": "Debe proporcionar una lista de IDs"}, 400

            # Construir XML SOAP
            ids_xml = "".join([f"<tns:string>{i}</tns:string>" for i in ids])
            soap_body = f"""<?xml version="1.0" encoding="utf-8"?>
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:auth="auth.headers" xmlns:tns="soap.backend">
                <soapenv:Header>
                    <auth:Auth>
                    <auth:Grupo>{GRUPO}</auth:Grupo>
                    <auth:Clave>{CLAVE}</auth:Clave>
                    </auth:Auth>
                </soapenv:Header>
                <soapenv:Body>
                    <tns:list_presidents>
                    <tns:org_ids>
                        {ids_xml}
                    </tns:org_ids>
                    </tns:list_presidents>
                </soapenv:Body>
                </soapenv:Envelope>"""

            xml_response = make_soap_request("list_presidents", soap_body)
            parsed = parse_soap_xml(xml_response)

            # Navegar el diccionario hasta los datos
            items = parsed["soap11env:Envelope"]["soap11env:Body"]["tns:list_presidentsResponse"]["tns:list_presidentsResult"]["s0:PresidentType"]

            # Convertir a lista de diccionario
            if isinstance(items, dict):
                items = [items]

            result = [{
                "id": int(i["s0:id"]),
                "name": i["s0:name"],
                "address": i["s0:address"],
                "phone": i["s0:phone"],
                "organization_id": int(i["s0:organization_id"])
            } for i in items]

            return result, 200

        except Exception as e:
            return {"error": str(e)}, 500
