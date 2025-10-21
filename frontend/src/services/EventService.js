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

    obtenerListadoDonacionesAsociadosAEvento: async (idEvento) => {
        try {
          return await eventosApi.getEventoWithDonacionesById(idEvento)
        } catch (err) {
          console.error("Error al buscar informacion del evento:", err)
          throw err;
        }
    },

    agregarDonacionAlEvento: async (idEvento, donacionId, data) => {
      try {
        const body = {
          donacionId,
          cantidad: data.cantidad
        };

        return await eventosApi.insertDonacionesToEvento(idEvento,body);
      } catch (err) {
        console.error("Error al agregar donación al evento:", err);
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

    enviarNotifiacionKafka: async (evento) => {
        try {
          const body = {
          id_organizacion : 1,
          id_evento : evento.id,
          nombre : evento.nombre,
          descripcion : evento.descripcion,
          fecha : evento.fecha
          };
          return await eventosApi.newRequesEventoKafka(body);
        } catch (err) {
          console.error("Error al enviar notifiacar un nuevo evento:", err);
          throw err;
        }
    },

    enviarNotifiacionBajaKafka: async (evento) => {
        try {
          const body = {
          id_organizacion : 1,
          id_evento : evento.id
          };
          return await eventosApi.deleteRequesEventoKafka(body);
        } catch (err) {
          console.error("Error al enviar notifiacion de baja de evento:", err);
          throw err;
        }
    },

    enviarNotifiacionAdhesionEvento: async (idOrganization,idEvento , user) => {
        try {
          const voluntarioDto = {
            idOrganizacion: 1,
            idVoluntario: user.voluntario.id,
            nombre: user.voluntario.nombre,
            apellido: user.voluntario.apellido,
            telefono: user.voluntario.telefono,
            email: user.voluntario.email
          };

          const body = {
          id_evento : idEvento.idEvento,
          voluntario: voluntarioDto
          };
          return await eventosApi.adhesionEvento(idOrganization.idOrganization,body);
        } catch (err) {
          console.error("Error al enviar notifiacion de adhesion:", err);
          throw err;
        }
    },

};

export default EventService;
