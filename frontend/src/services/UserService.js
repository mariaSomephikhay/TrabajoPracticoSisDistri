import { userApi } from '../api/ApiSwagger'

const UserService = {
  
  obtenerListadoUsuarios: async () => {
    try {
      const data = await userApi.getUserList(); // solo await, sin callback
      return data; // ya contiene data.usuarios
    } catch (error) {
      console.error('Error al obtener usuarios:', error);
      throw error;
    }
  },

  crearUsuario: async (newUser) => {
    try {
      return await userApi.postUserList({
        ...newUser,
        rol: { ...newUser.rol, descripcion: newUser.rol.descripcion?.toUpperCase() }
      })
    } catch (error) {
      console.error('Error al crear usuario:', error)
      throw error
    }
  },

  modificarUsuario: async (usuarioId, user) => {
    try {
      return await userApi.putUser(usuarioId, {
        ...user,
        rol: { ...user.rol, descripcion: user.rol.descripcion?.toUpperCase() }
      });
    } catch (error) {
      console.error('Error al modificar usuario:', error);
      throw error;
    }
  },

  obtenerUsuario: async (usuarioId) => {
    try {
      return await userApi.getUser(usuarioId)
    } catch (error) {
      console.error('Error al obtener el usuario:', error)
      throw error
    }
  },
};

export default UserService
