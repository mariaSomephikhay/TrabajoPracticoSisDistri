import { userApi } from "../api/ApiSwagger.js"

const UserService = {

  registrarUsuario: async (newUser) => {
    try {
      return await userApi.createUser(newUser)
    } catch (err) {
      console.error("Error al registrar usuario:", err)
      throw err;
    }
  },

  obtenerListadoUsuarios: async () => {
    try {
      return await userApi.listUsers()
    } catch (err) {
      console.error("Error al obtener usuarios:", err)
      throw err
    }
  },

  modificarUsuario: async (usuarioId, user) => {
    try {
      return await userApi.updateUserById(usuarioId, user)
    } catch (err) {
      console.error("Error al modificar usuario:", err)
      throw err
    }
  },

  obtenerUsuario: async (usuarioId) => {
    try {
      return await userApi.getUserById(usuarioId)
    } catch (err) {
      console.error("Error al obtener usuario:", err)
      throw err
    }
  },

  eliminarUsuario: async (usuarioId) => {
    try {
      return await userApi.deleteUserById(usuarioId)
    } catch (err) {
      console.error("Error al eliminar usuario:", err)
      throw err
    }
  },
}

export default UserService
