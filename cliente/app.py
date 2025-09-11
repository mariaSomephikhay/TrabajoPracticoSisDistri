from flask import Flask
from flask_cors import CORS
from flask_restx import Api
from config import config
from routes.userRouter import api as userNS

# Crear la app con una configuración
def create_app(config_name='development'):
    app = Flask(__name__)
    app.config.from_object(config[config_name])
    CORS(app, supports_credentials=True, resources={r"*": {"origins": "*"}})

    # Crear API con Swagger
    api = Api(
        app,
        version="1.0",
        title="Api Documentada",
        description="Documentación de la API de ejemplo con Flask + gRPC",
        doc="/docs" # URL donde se verá Swagger UI
    )
    # Rutas
    api.add_namespace(userNS, path="/user")

    @app.route("/json")
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