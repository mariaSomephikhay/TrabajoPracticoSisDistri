from flask import Flask, request, jsonify
from flask_cors import CORS
from config import config
from routes.userRouter import userBp

# Crear la app con una configuración
def create_app(config_name='development'):
    app = Flask(__name__)
    CORS(app)
    app.config.from_object(config[config_name])
    return app

app = create_app('development')

#RUTAS
app.register_blueprint(userBp)


#Inicio el servidor
if __name__ == '__main__':
    app.run(
        debug=app.config['DEBUG'],
        port=app.config['PORT']
    )