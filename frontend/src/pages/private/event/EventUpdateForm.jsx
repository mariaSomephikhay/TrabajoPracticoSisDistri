import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate, NavLink } from 'react-router-dom';
import { Loading } from '../../../components/ui/Loading.jsx';
import EventService from '../../../services/EventService.js';

export const EventUpdateForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [event, setEvent] = useState(null);
  const [loading, setLoading] = useState(true);

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

  const handleChangeEvento = (e) => { 
    const { name, value } = e.target
    console.log("Cambio:", name, value);  // 👈 chequealo en consola
    setEvent((prev) => ({ ...prev, [name]: value }))
    
  }

  const handleUpdateEvento = async (e) => { 
    e.preventDefault() 
    try { 
      const fechaISO = new Date(event.fecha).toISOString();

      const dataToSend = {...event,fecha: fechaISO,};

      await EventService.modificarEvento(id, dataToSend) 
      alert('Evento actualizado correctamente') 
      navigate('/events') 
    } catch (err) { 
      console.error(err) 
      alert('Error al actualizar el evento') 
    } 
  }

  const formatDateTimeLocal = (date) => {
  if (!date) return '';
  const d = new Date(date);

  const pad = (n) => n.toString().padStart(2, '0');

  const yyyy = d.getFullYear();
  const mm = pad(d.getMonth() + 1); // los meses van de 0 a 11
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
          <input className="form-control" type="text" name="descripcion" value={event.descripcion ?? ''} onChange={handleChangeEvento}
          />
        </div>
        <div className="mb-3">
          <label>Fecha del evento</label>
          <input className="form-control" type="datetime-local" name="fecha" value={formatDateTimeLocal(event.fecha)} onChange={handleChangeEvento}/>
        </div>
  
        <button type="submit" className="btn btn-primary">
          Guardar cambios
        </button>
        
        <p className="text-center mt-3 mb-0">
          <NavLink className="fw-bold text-decoration-none" to="/users">
            Cancelar
          </NavLink>
        </p>
        
      </form>
    )
};
