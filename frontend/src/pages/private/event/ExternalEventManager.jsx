import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js';
import { AuthContext } from "../../../context/AuthContext.jsx";

export const ExternalEventManager = () => {
  const { id } = useParams();
  const [users, setUsers] = useState([]);
  const [event, setEvent] = useState(null);
  const [disabledButtons, setDisabledButtons] = useState({}); // 🔹 Para desactivar botones individualmente
  const { userAuthenticated } = useContext(AuthContext);

  useEffect(() => {
    cargarUsuarios();
    cargarEvento();
  }, []);

  const cargarUsuarios = async () => {
    try {
      const dataUser = await UserService.obtenerUsuarioPorUsername(userAuthenticated.username);
      
      if (dataUser.rol.descripcion === "PRESIDENTE" || dataUser.rol.descripcion === "COORDINADOR") {
        const response = await UserService.obtenerListadoUsuarios();
        const lista = response.usuarios || [];
        setUsers(lista);
        console.log(lista);
      } else {
        setUsers([dataUser]);
        console.log(dataUser);
      }

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

  const handleEnviarNotificacion = async (voluntario) => {
    // 🔹 Mostrar cartel de confirmación
    const confirmar = window.confirm(`¿Deseas adherirte al evento?`);
    if (!confirmar) return;

    try {
      console.log(voluntario);
      await EventService.enviarNotifiacionAdhesionEvento(
        { idOrganization: event.idOrganizacion },
        { idEvento: event.id },
        { voluntario }
      );

      // 🔹 Desactivar botón correspondiente
      setDisabledButtons(prev => ({
        ...prev,
        [voluntario.id]: true
      }));

    } catch (error) {
      console.error('Error al adherirse al evento:', error);
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
                  onClick={() => handleEnviarNotificacion(usuario)}
                  disabled={disabledButtons[usuario.id]} // 🔹 Desactiva botón si ya se usó
                >
                  {disabledButtons[usuario.id] ? 'Enviado' : 'Adherir'}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
