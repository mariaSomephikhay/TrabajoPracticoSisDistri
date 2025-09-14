import sys, os
sys.path.append(os.path.join(os.path.dirname(__file__), "generated"))

import grpc
from generated import donaciones_eventos_pb2 as service_pb2
from generated import donaciones_eventos_pb2_grpc as service_grpc
from google.protobuf.json_format import ParseDict, MessageToJson

class ManagerServiceImpl(object):
    def __init__(self):  #inicializo el canal y apartir del stub traigo los servicios del proto
        self.host='127.0.0.1'
        self.server_port=9090
        self.channel =grpc.insecure_channel('{}:{}'.format(self.host,self.server_port))
        self.stub =service_grpc.ManagerServiceStub(self.channel) #aca obtengo todos los servicios que declare en el proto

    ##################################################################
    #Genero los metodos/servicios definidos en el archivo proto
    ##################################################################
    def getUserById(self, id):
        pUsuario = ParseDict(id, service_pb2.UserId(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.getUserById(pUsuario)
        return MessageToJson(response)
    
    def getUserByUsername(self, username):
        pUsuario = ParseDict(username, service_pb2.UserUsername(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.getUserByUsername(pUsuario)
        return MessageToJson(response)
    
    def getAllUser(self): 
        param = service_pb2.Empty() #inicializao param con el valor del mensaje empty
        response = self.stub.getAllUsers(param) #llamo al servicio getAllUsers y le paso Empty (param)como esta declarado en el prot 
        return MessageToJson(response)    

    def insertOrUpdateUser(self, usuario):
        pUsuario = ParseDict(usuario, service_pb2.Usuario(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.insertOrUpdateUser(pUsuario)
        return MessageToJson(response)
    
    def deleteUser(self, id):
        pUsuario = ParseDict(id, service_pb2.UserId(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.deleteUser(pUsuario)
        return MessageToJson(response)
