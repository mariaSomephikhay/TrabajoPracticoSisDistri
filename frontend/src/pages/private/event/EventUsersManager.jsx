import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js';
import { Loading } from '../../../components/ui/Loading.jsx';

export const EventUsersManager = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [allUsers, setAllUsers] = useState([]);
  const [selectedUserIds, setSelectedUserIds] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [usuariosSistema, usuariosEvento] = await Promise.all([
          UserService.obtenerListadoUsuarios(),
          EventService.obtenerListadoUsuariosAsociadosAEvento(id),
        ]);

        setAllUsers(usuariosSistema.usuarios);
        const asociados = usuariosEvento.users?.map((u) => u.id.toString()) || [];
        setSelectedUserIds(asociados);
        setLoading(false);
      } catch (err) {
        console.error(err);
        setLoading(false);
      }
    };
    fetchData();
  }, [id]);

  const toggleUser = (userId) => {
    setSelectedUserIds((prev) =>
      prev.includes(userId.toString())
        ? prev.filter((uid) => uid !== userId.toString())
        : [...prev, userId.toString()]
    );
  };

  const handleSave = async () => {
    try {
      const ids = selectedUserIds.map((id) => parseInt(id));
      await EventService.actualizarUsuariosDelEvento(id, ids);
      alert('Usuarios actualizados correctamente');
      navigate(`/events/edit/${id}`);
    } catch (err) {
      console.error(err);
      alert('Error al actualizar los usuarios');
    }
  };

  if (loading) return <Loading />;

  return (
    <div className="container mt-5">
      <h3 className="mb-4">Gestión de usuarios del evento</h3>

      <ul className="list-group mb-4">
        {allUsers.map((user) => {
          const isSelected = selectedUserIds.includes(user.id.toString());
          return (
            <li
              key={user.id}
              className={`list-group-item d-flex justify-content-between align-items-center ${
                isSelected ? 'list-group-item-success' : ''
              }`}
            >
              <span>
                {user.nombre} {user.apellido}
              </span>
              <button
                className={`btn btn-sm ${isSelected ? 'btn-danger' : 'btn-success'}`}
                onClick={() => toggleUser(user.id)}
              >
                {isSelected ? 'Quitar' : 'Agregar'}
              </button>
            </li>
          );
        })}
      </ul>

      <div className="d-flex justify-content-between">
        <button className="btn btn-outline-dark" onClick={() => navigate(`/events/edit/${id}`)}>
          ← Volver al evento
        </button>
        <button className="btn btn-primary" onClick={handleSave}>
          Guardar cambios
        </button>
      </div>
    </div>
  );
};
