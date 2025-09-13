from decouple import config

class DevelopmentConfig():
    DEBUG = config('DEBUG_CONFIG', cast=bool)
    PORT = config('PORT_CONFIG', cast=int)
    
# Diccionario con los distintos ambientes para levantar el client
configClient = {
    'development': DevelopmentConfig
}