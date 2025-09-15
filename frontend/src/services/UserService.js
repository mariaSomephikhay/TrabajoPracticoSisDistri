import { userApi } from "../api/ApiSwagger.js"

const UserService = {

  registrarUsuario: async (newUser) => {
    try {
      return await userApi.postUserRegister(newUser)
    } catch (err) {
      console.error("Error al registrar usuario:", err)
      throw err;
    }
  },

  obtenerListadoUsuarios: async () => {
    try {
      return await userApi.getUserList()
    } catch (err) {
      console.error("Error al obtener usuarios:", err)
      throw err
    }
  },

  modificarUsuario: async (usuarioId, user) => {
    try {
      return await userApi.putUser(usuarioId, user)
    } catch (err) {
      console.error("Error al modificar usuario:", err)
      throw err
    }
  },

  obtenerUsuario: async (usuarioId) => {
    try {
      return await userApi.getUser(usuarioId)
    } catch (err) {
      console.error("Error al obtener usuario:", err)
      throw err
    }
  },
}

export default UserService
