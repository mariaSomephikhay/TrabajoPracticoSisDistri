import React, { useEffect, useState } from 'react';
import { useParams, NavLink } from 'react-router-dom';
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js'

export const ExternalEventManager = () => {
  const { id } = useParams(); // ID del evento
  const [users, setUsers] = useState([]);
  const [event, setEvent] = useState(null);

  // Cargar usuarios disponibles
  useEffect(() => {
    cargarUsuarios();
    cargarEvento();
  }, []);

  const cargarUsuarios = async () => {
  try {
    const response = await UserService.obtenerListadoUsuarios();
    const lista = response.usuarios || [];
    setUsers(lista);
    console.log(lista);
  } catch (error) {
    console.error('Error al cargar los usuarios: ', error);
  }
  };

const cargarEvento = async () => {
  try {
    console.log("Cargando evento con ID: ", id);
    const data = await EventService.obtenerEvento(id);
    setEvent(data);
    console.log(data);
  } catch (error) {
    console.error('Error al cargar el evento: ', error);
  }
};

  const handleEnviarNotifiacion = async (voluntario) => {
    try {
      console.log(voluntario)
      await EventService.enviarNotifiacionAdhesionEvento({idOrganization: event.idOrganizacion}, {idEvento: event.id}, {voluntario});
      
    } catch (error) {
      console.error('Error al adherirse al evento :', error);
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4 text-center">Adhesión a Evento Externo</h2>

      {event && (
        <div className="mb-4">
          <h4>Evento: {event.nombre}</h4>
          <p><strong>Descripción:</strong> {event.descripcion}</p>
          <p><strong>Fecha:</strong> {new Date(event.fecha).toLocaleDateString()}</p>
        </div>
      )}

      <table className="table table-striped">
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {users.map((usuario) => (
            <tr key={usuario.id}>
              <td>{usuario.nombre}</td>
              <td>{usuario.apellido}</td>
              <td>
                <button
                  className="btn btn-primary btn-sm"
                  onClick={() => handleEnviarNotifiacion(usuario)}
                >
                  Adherir
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
