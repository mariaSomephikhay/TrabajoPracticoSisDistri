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

    def insertOrUpdateDonacion(self, donacion):
        pDonacion= ParseDict(donacion, service_pb2.Donacion(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.insertOrUpdateDonacion(pDonacion)
        return MessageToJson(response)

    def getDonacionById(self, id):
        pDonacion = ParseDict(id, service_pb2.DonacionId(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.getDonacionById(pDonacion)
        return MessageToJson(response)    

    def deleteDonacion(self, idUsu):
        pDonacion = ParseDict(idUsu, service_pb2.DonacionIdUsu(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.deleteDonacion(pDonacion)
        return MessageToJson(response)    

    def getAllDonaciones(self): 
        param = service_pb2.Empty() #inicializao param con el valor del mensaje empty
        response = self.stub.getAllDonaciones(param) #llamo al servicio getAllUsers y le paso Empty (param)como esta declarado en el prot 
        return MessageToJson(response)   

    def insertOrUpdateEvento(self, evento):
        pEvento= ParseDict(evento, service_pb2.Evento(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.insertOrUpdateEvento(pEvento)
        return MessageToJson(response)     
    
    def deleteEvento(self, id):
        pEvento= ParseDict(id, service_pb2.Evento(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.deleteEventos(pEvento)
        return MessageToJson(response)
    
    def getEventoById(self, id):
        pEvento = ParseDict(id, service_pb2.EventoId(), ignore_unknown_fields=True) # Convierte el modelo json a un mensaje protobuf solo con los campos presentes
        response = self.stub.getEventoById(pEvento)
        return MessageToJson(response)    
    
    def getAllEventos(self): 
        param = service_pb2.Empty() #inicializao param con el valor del mensaje empty
        response = self.stub.getAllEventos(param) #llamo al servicio getAllUsers y le paso Empty (param)como esta declarado en el prot 
        return MessageToJson(response)   
    
    def insertUsersToEvento(self, evento_dict):
        pEvento = ParseDict(evento_dict, service_pb2.EventoWithListUsers(), ignore_unknown_fields=True)
        response = self.stub.insertUsersToEvento(pEvento)
        return MessageToJson(response)