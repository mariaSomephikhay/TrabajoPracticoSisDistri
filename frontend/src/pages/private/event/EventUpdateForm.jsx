import React, { useEffect, useContext, useState } from 'react';
import { useParams, useNavigate, NavLink } from 'react-router-dom';
import { Loading } from '../../../components/ui/Loading.jsx';
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js';
import { AuthContext } from "../../../context/AuthContext.jsx";

export const EventUpdateForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [event, setEvent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [associatedUsers, setAssociatedUsers] = useState([]);
  const { userAuthenticated } = useContext(AuthContext);
  const [selectedUserIds, setSelectedUserIds] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);

  // Cargar evento
  useEffect(() => {
    EventService.obtenerEvento(id)
      .then((data) => {
        setEvent(data);
        setLoading(false);
        console.log("Evento cargado:", data);
      })
      .catch((error) => {
        console.error(error);
        setLoading(false);
      });
  }, [id]);

  //cargar usuario actual
  useEffect(() => {
    UserService.obtenerUsuarioPorUsername(userAuthenticated.username)
      .then((data) =>{
        setCurrentUser(data)
      })
      .catch((error) => {
        console.error(error);
        setLoading(false);
      });
  }, [userAuthenticated.username]);

  // Cargar usuarios asociados
  useEffect(() => {
    EventService.obtenerListadoUsuariosAsociadosAEvento(id)
      .then((data) => {
        const usuariosAsociados = data.users || [];
        setAssociatedUsers(usuariosAsociados);
        const asociados = data.users?.map((u) => u.id.toString()) || [];
        setSelectedUserIds(asociados);
      })
      .catch((error) => {
        console.error("Error al obtener usuarios asociados:", error);
      });
  }, [id]);

  const handleChangeEvento = (e) => {
    const { name, value } = e.target;
    setEvent((prev) => ({ ...prev, [name]: value }));
  };

  const handleUpdateEvento = async (e) => {
    e.preventDefault();
    try {
      const fechaISO = new Date(event.fecha).toISOString();
      const dataToSend = { ...event, fecha: fechaISO };
      await EventService.modificarEvento(id, dataToSend);
      alert('Evento actualizado correctamente');
      navigate('/events');
    } catch (err) {
      console.error(err);
      alert('Error al actualizar el evento');
    }
  };

  const eventoYaPaso = () => {
  if (!event?.fecha) return false;
  const hoy = new Date();
  const fechaEvento = new Date(event.fecha);
  return fechaEvento < hoy;
  };

  const handleGestionUsuarios = () => {
  navigate(`/events/${id}/users`);
  };

  const formatDateTimeLocal = (date) => {
    if (!date) return '';
    const d = new Date(date);
    const pad = (n) => n.toString().padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
  };

  const handleSave = async () => {
  if (!currentUser) return; // proteger si no cargó aún
  try {
    let updatedIds;
    setSelectedUserIds((prev) => {
      if (prev.includes(currentUser.username)) {
        updatedIds = prev.filter((u) => u !== currentUser.username);
        return updatedIds;
      } else {
        updatedIds = [...prev, currentUser.username];
        return updatedIds;
      }
    });

    setAssociatedUsers((prev) => {
      if (prev.some((u) => u.username === currentUser.username)) {
        return prev.filter((u) => u.username !== currentUser.username);
      } else {
        return [...prev, currentUser];
      }
    });

    await EventService.actualizarUsuariosDelEvento(id, updatedIds);
    alert('Se actualizó tu participación en el evento');
    navigate(`/events/edit/${id}`);
  } catch (err) {
    console.error(err);
    alert('Error al actualizar tu participación');
  }
};

  const puedeModificar = () => {
  const fechaPasada = new Date(event.fecha) < new Date();
  const esVoluntario = userAuthenticated.rol.descripcion == 'VOLUNTARIO';
  return !fechaPasada && !esVoluntario;
  };


  if (loading) return <Loading />;
  if (!event) return <p>Evento no encontrado</p>;
  return (
    <>
    {!puedeModificar() ? (
    eventoYaPaso() ? (
      <div className="d-flex justify-content-center mt-3">
        <div className="alert alert-warning text-center" style={{ maxWidth: '500px' }}>
          Este evento ya ocurrió. No se pueden realizar modificaciones.
        </div>
      </div>
    ) : (
      <div className="d-flex justify-content-center mt-3">
        <div className="alert alert-info text-center" style={{ maxWidth: '500px' }}>
          Como voluntario, solo podés visualizar la información del evento.
        </div>
      </div>
    )
    ) : null}

    <form className="col-6 mx-auto mt-5" onSubmit={handleUpdateEvento}>
      <div className="mb-3">
        <label>Nombre</label>
        <input
          className="form-control"
          type="text"
          name="nombre"
          value={event.nombre ?? ''}
          onChange={handleChangeEvento}
          disabled={eventoYaPaso() || !puedeModificar()}
        />
      </div>
      <div className="mb-3">
        <label>Descripción</label>
        <input
          className="form-control"
          type="text"
          name="descripcion"
          value={event.descripcion ?? ''}
          onChange={handleChangeEvento}
          disabled={eventoYaPaso() || !puedeModificar()}
        />
      </div>
      <div className="mb-3">
        <label>Fecha del evento</label>
        <input
          className="form-control"
          type="datetime-local"
          name="fecha"
          value={formatDateTimeLocal(event.fecha)}
          onChange={handleChangeEvento}
          disabled={eventoYaPaso() || !puedeModificar()}
        />
      </div>

      {/* Usuarios asociados */}
      <div className="mb-3">
        <label>Usuarios asociados al evento</label>
        <ul className="list-group">
          {associatedUsers.length > 0 ? (
            associatedUsers.map((user) => {
              const isCurrentUser = user.username === userAuthenticated.username;
              return (
                <li
                  key={user.username} // usar username como key si no hay id
                  className={`list-group-item ${isCurrentUser ? 'list-group-item-warning' : ''}`}
                >
                  {user.nombre} {user.apellido} {isCurrentUser && '(Usuario actual)'}
                </li>
              );
            })
          ) : (
            <li className="list-group-item text-muted">No hay usuarios asociados.</li>
          )}
        </ul>
      </div>

      <div className="d-flex justify-content-center mt-3 gap-2">
        {puedeModificar() && !eventoYaPaso() && (
          <>
            <button type="submit" className="btn btn-primary">
              Guardar cambios
            </button>

            <button type="button" className="btn btn-primary" onClick={handleGestionUsuarios}>
              Gestionar usuarios
            </button>
          </>
        )}

        {userAuthenticated.rol.descripcion === 'VOLUNTARIO' && !eventoYaPaso() && (
          <button
            type="button"
            className={`btn ${associatedUsers.some(u => u.username === userAuthenticated.username) ? 'btn-danger' : 'btn-success'}`}
            onClick={handleSave}
          >
            {associatedUsers.some(u => u.username === userAuthenticated.username)
              ? 'Salir del evento'
              : 'Unirse al evento'}
          </button>
        )}

        <NavLink className="btn btn-primary" to="/events">
          Cancelar
        </NavLink>
      </div>
    </form>
    </>
 );
};
