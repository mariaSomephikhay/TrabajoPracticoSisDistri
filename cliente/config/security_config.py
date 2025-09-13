from decouple import config
from functools import wraps
from flask import request, jsonify
import datetime
import jwt
import pytz

class SecurityConfig():

    # Configuracion para el jwtToken
    secret = config('JWT_KEY', cast=str)
    expMinutes = config('JWT_EXP_MINUTES', cast=int)
    jwtAlgorithm = config('JWT_ALGORITHM', cast=str) 

    # Defino la zona horaria
    tz = pytz.timezone("America/Argentina/Buenos_Aires")

    @staticmethod
    def generateToken(user):
        payload={
            'iat': datetime.datetime.now(tz= SecurityConfig.tz),  # Horario de creacion del token
            'exp': datetime.datetime.now(tz= SecurityConfig.tz) + datetime.timedelta(minutes= SecurityConfig.expMinutes), # Horario de expiracion del token (Hora de creacion + 10 minutos)
            'username': user['username'],
            'fullname': user['nombre'] + ' ' + user['apellido'],
            'rol': { # Guardo el rol completo                      
                'id': user['rol']['id'],
                'descripcion': user['rol']['descripcion']        
            },
        }
        token = jwt.encode(payload, SecurityConfig.secret, algorithm=SecurityConfig.jwtAlgorithm)
        return token

    @staticmethod
    def authRequired(*roles): # Decora un endpoint para requerir autenticación y opcionalmente roles específicos
        def decorator(f):
            @wraps(f)
            def decorated(*args, **kwargs):
                auth = request.headers.get("Authorization", "")
                token = auth.split(" ")[1] if auth.startswith("Bearer ") else None

                if not token:
                    return {"error": "Token is missing"}, 401

                try:
                    payload = jwt.decode(token, SecurityConfig.secret, algorithms=[SecurityConfig.jwtAlgorithm])
                    request.userAuth = payload  # Guardo el body del token decodificado en la request
                except jwt.ExpiredSignatureError:
                    return {"error": "Token has expired"}, 401
                except jwt.InvalidTokenError:
                    return {"error": "Invalid token"}, 401

                # Validar roles solo si se pasan como argumento
                if roles and payload.get("rol", {}).get("descripcion") not in roles:
                    return {"error": "Access forbidden: insufficient role"}, 403

                return f(*args, **kwargs)
            return decorated
        return decorator

