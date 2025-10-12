import React, { useState } from 'react';
import { useNavigate, NavLink } from 'react-router-dom';
import EventService from '../../../services/EventService.js';

export const EventNew = () => {
  const navigate = useNavigate();
  const [event, setEvent] = useState({ nombre: '', descripcion: '', fecha: '' });

  const handleChangeEvento = (e) => {
    const { name, value } = e.target;
    setEvent((prev) => ({ ...prev, [name]: value }));
  };

  const handleCreateEvento = async (e) => {
    e.preventDefault();
    try {
      const fechaISO = new Date(event.fecha).toISOString();
      const dataToSend = { ...event, fecha: fechaISO, idOrganizacion: 1 , publicado: false};
      await EventService.registrarEvento(dataToSend);
      alert('Evento creado correctamente');
      navigate('/events');
    } catch (err) {
      console.error(err);
      alert('Error al crear el evento');
    }
  };

  return (
    <form className="col-6 mx-auto mt-5" onSubmit={handleCreateEvento}>
      <div className="mb-3">
        <label>Nombre</label>
        <input
          className="form-control"
          type="text"
          name="nombre"
          value={event.nombre}
          onChange={handleChangeEvento}
        />
      </div>
      <div className="mb-3">
        <label>Descripción</label>
        <input
          className="form-control"
          type="text"
          name="descripcion"
          value={event.descripcion}
          onChange={handleChangeEvento}
        />
      </div>
      <div className="mb-3">
        <label>Fecha del evento</label>
        <input
          className="form-control"
          type="datetime-local"
          name="fecha"
          value={event.fecha}
          onChange={handleChangeEvento}
        />
      </div>

      <button type="submit" className="btn btn-primary">
        Guardar cambios
      </button>

      <p className="text-center mt-3 mb-0">
        <NavLink className="fw-bold text-decoration-none" to="/events">
          Cancelar
        </NavLink>
      </p>
    </form>
  );
};
