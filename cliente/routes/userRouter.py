from flask import Blueprint, request
from flask_cors import cross_origin
import json
from grpcManagerService import ManagerServiceImpl

userBp = Blueprint("user", __name__, url_prefix="/user")
cliente = ManagerServiceImpl()

@userBp.get("/")
@cross_origin()
def get_users():
    result = cliente.getAllUser()
    return json.loads(result), 200

@userBp.post("/")
@cross_origin()
def create_user():
    result = cliente.insertOrUpdateUser(request.json)
    return json.loads(result), 201

@userBp.put("/<int:id>")
@cross_origin()
def update_user(id):
    request.json["id"] = id
    result = cliente.insertOrUpdateUser(request.json)
    return json.loads(result), 200
