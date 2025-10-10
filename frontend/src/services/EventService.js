import { eventosApi } from "../api/ApiSwagger.js";

const EventService = {
    EventList: async () => {
      try {
        const response = await eventosApi.listEventos(); 
        return response; 
      } catch (err) {
        console.error("Error al obtener eventos:", err);
        throw err;
      }
    },

    eliminarEvento: async (eventoId) => {
      try {
        return await eventosApi.deleteEventById(eventoId)
      } catch (err) {
        console.error("Error al eliminar usuario:", err)
        throw err
      }
    },

    obtenerEvento: async (eventoId) => {
      try {
        return await eventosApi.getEventoById(eventoId)
      } catch (err) {
        console.error("Error al obtener el evento:", err)
        throw err
      }
    },
  
    modificarEvento: async (eventoid, evento) => {
    try {
      return await eventosApi.updateEventoById(eventoid, evento)
    } catch (err) {
      console.error("Error al modificar el evento:", err)
      throw err
    }
    },

    registrarEvento: async (newEvento) => {
        try {
          return await eventosApi.createEvent(newEvento)
        } catch (err) {
          console.error("Error al registrar el evento:", err)
          throw err;
        }
      },

    obtenerListadoUsuariosAsociadosAEvento: async (idEvento) => {
        try {
          return await eventosApi.getEventoWithUsersById(idEvento)
        } catch (err) {
          console.error("Error al buscar informacion del evento:", err)
          throw err;
        }
    },

    actualizarUsuariosDelEvento: async (idEvento, usersIds) => {
        try {
          const body = { usersIds };
          return await eventosApi.insertUsersToEvento(idEvento, body);
        } catch (err) {
          console.error("Error al insertar usuarios al evento:", err);
          throw err;
        }
    },

};

export default EventService;
