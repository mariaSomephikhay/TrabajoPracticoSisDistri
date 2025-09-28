import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, NavLink } from 'react-router-dom';
import { Loading } from '../../../components/ui/Loading.jsx';
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js';

export const EventUpdateForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [event, setEvent] = useState(null);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedUsers, setSelectedUsers] = useState([]);
  

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

  useEffect(() => {
    console.log("id :", id);
    EventService.obtenerListadoUsuariosAsociadosAEvento(id)
    .then((data) => {
      console.log(typeof data, data);

      const usuariosAsociados = data.users || []; // accede a la propiedad correcta

      // Guardamos solo los IDs como strings
      setSelectedUsers(usuariosAsociados.map((user) => user.id.toString()));
    })
    .catch((error) => {
      console.error("Error al obtener usuarios asociados:", error);
    });
  }, [id]);


  useEffect(() => {
    UserService.obtenerListadoUsuarios()
      .then((data) => {
        setUsers(data.usuarios);
      })
      .catch((error) => {
        console.error("Error al obtener usuarios:", error);
      });
  }, []);


  const handleChangeEvento = (e) => { 
    const { name, value } = e.target;
    setEvent(prev => ({ ...prev, [name]: value }));
  };

  const handleAddUser = (userId) => {
    setSelectedUsers((prev) => [...prev, userId.toString()]);
  };

  const handleRemoveUser = (userId) => {
    setSelectedUsers((prev) => prev.filter((id) => id !== userId.toString()));
  };

  const handleChangeUsuarios = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions, option => option.value);
    setEvent(prev => ({ ...prev, usuarios: selectedOptions }));
  };

  const handleUpdateEvento = async (e) => { 
    e.preventDefault();
    try { 
      const fechaISO = new Date(event.fecha).toISOString();
      const dataToSend = {...event, fecha: fechaISO};
      await EventService.modificarEvento(id, dataToSend);
      alert('Evento actualizado correctamente');
      navigate('/events');
    } catch (err) { 
      console.error(err);
      alert('Error al actualizar el evento');
    } 
  };

  const formatDateTimeLocal = (date) => {
    if (!date) return '';
    const d = new Date(date);
    const pad = (n) => n.toString().padStart(2, '0');
    const yyyy = d.getFullYear();
    const mm = pad(d.getMonth() + 1);
    const dd = pad(d.getDate());
    const hh = pad(d.getHours());
    const min = pad(d.getMinutes());
    return `${yyyy}-${mm}-${dd}T${hh}:${min}`;
  };

  if (loading) return <Loading />
  if (!event) return <p>Evento no encontrado</p>

  return (
    <form className="col-6 mx-auto mt-5" onSubmit={handleUpdateEvento}>
      <div className="mb-3">
        <label>Nombre</label>
        <input className="form-control" type="text" name="nombre" value={event.nombre ?? ''} onChange={handleChangeEvento}/>
      </div>
      <div className="mb-3">
        <label>Descripción</label>
        <input className="form-control" type="text" name="descripcion" value={event.descripcion ?? ''} onChange={handleChangeEvento}/>
      </div>
      <div className="mb-3">
        <label>Fecha del evento</label>
        <input className="form-control" type="datetime-local" name="fecha" value={formatDateTimeLocal(event.fecha)} onChange={handleChangeEvento}/>
      </div>
      
      {/* Usuarios disponibles */}
      <div className="mb-3">
        <label>Usuarios disponibles</label>
        <ul className="list-group">
          {users
            .filter((user) => !selectedUsers.includes(user.id.toString()))
            .map((user) => (
              <li
                key={user.id}
                className="list-group-item d-flex justify-content-between align-items-center"
              >
                {user.nombre} {user.apellido}
                <button
                  type="button"
                  className="btn btn-sm btn-success"
                  onClick={() => handleAddUser(user.id)}
                >
                  Agregar
                </button>
              </li>
            ))}
        </ul>
      </div>

      {/* Usuarios asociados */}
      <div className="mb-3">
        <label>Usuarios asociados al evento</label>
        <ul className="list-group">
          {users
            .filter((user) => selectedUsers.includes(user.id.toString()))
            .map((user) => (
              <li
                key={user.id}
                className="list-group-item d-flex justify-content-between align-items-center"
              >
                {user.nombre} {user.apellido}
                <button
                  type="button"
                  className="btn btn-sm btn-danger"
                  onClick={() => handleRemoveUser(user.id)}
                >
                  Quitar
                </button>
              </li>
            ))}
        </ul>
      </div>

      <button type="submit" className="btn btn-primary">Guardar cambios</button>
      <p className="text-center mt-3 mb-0">
        <NavLink className="fw-bold text-decoration-none" to="/users">
          Cancelar
        </NavLink>
      </p>
    </form>
  );
};
