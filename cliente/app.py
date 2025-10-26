from flask import Flask
from flask_cors import CORS
from flask_restx import Api
from config.client_config import configClient
from routes.user_router import api as userNS
from routes.donacion_router import api as donacionNS
from routes.evento_router import api as eventoNS
from routes.solicitud_router import api as solicitudNS
from routes.filter_router import api as filterNS

# Crear la app con una configuración
def create_app(config_name='development'):
    app = Flask(__name__)
    app.config.from_object(configClient[config_name])
    CORS(app, supports_credentials=True, resources={r"*": {"origins": "*"}})

    # Defino esquema de seguridad para el Swagger
    authorizations = {
        'Bearer Auth': {
            'type': 'apiKey',
            'in': 'header',
            'name': 'Authorization',
            'description': 'JWT Authorization header. Ejemplo: "Bearer {token}"'
        }
    }
    # Crear API con Swagger
    api = Api(
        app,
        version="1.0",
        title="Api Documentada",
        description="Documentación de la API de ejemplo con Flask + gRPC",
        doc="/docs", # URL donde se verá Swagger UI
        authorizations=authorizations,
    )
    # Rutas
    api.add_namespace(userNS, path="/user")
    api.add_namespace(donacionNS, path="/donacion")
    api.add_namespace(eventoNS, path="/evento")
    api.add_namespace(solicitudNS, path="/solicitud")
    api.add_namespace(filterNS, path="/filter")

    # Ruta para obtener el swagger en formato .json
    @app.route("/docs/json")
    def swagger_json():
        return api.__schema__

    return app

app = create_app('development')

#Inicio el servidor
if __name__ == '__main__':
    app.run(
        debug=app.config['DEBUG'],
        port=app.config['PORT']
    )