import React, { useState, useContext, useEffect } from 'react';
import { Table } from '../../../components/ui/Table.jsx';
import { AuthContext } from "../../../context/AuthContext.jsx";
import editIcon from "../../../../public/icons/edit.svg";
import deleteIcon from "../../../../public/icons/delete.svg";
import EventService from '../../../services/EventService.js';
import { Modal } from "../../../components/ui/Modal.jsx"
import { useNavigate } from 'react-router-dom'

export const EventList = () => {
  const { userAuthenticated } = useContext(AuthContext);
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [eventSelected, setEventSelected] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const data = await EventService.EventList();
        console.log("Datos recibidos:", data);
        setEvents(data.eventos); // Verifica que esta clave sea correcta
      } catch (err) {
        console.error(err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, []);

  const handleEditEvent = (id) => {
    navigate(`/events/edit/${id}`)
  };

  const handleDeleteEventOnClick = (event) => { 
    setEventSelected(event) 
    setShowModal(true) 
  }

  const handlePublicarEvento = async (event) => {
  console.log("Publicar evento: ", event);
  try {
    EventService.enviarNotifiacionKafka(event);

    const updatedEvent = { ...event, publicado: true };

    await EventService.modificarEvento(event.id, updatedEvent);

    setEvents((prevEvents) =>
      prevEvents.map((ev) =>
        ev.id === event.id ? updatedEvent : ev
      )
    );
  } catch (error) {
    console.error(error);
    alert("Error al crear la solicitud de donacion");
  }
};


  const handleDeleteEvent = async () => {
  try {
    await EventService.eliminarEvento(eventSelected.id);
    setEvents(prevEvents => prevEvents.filter(event => event.id !== eventSelected.id));
    setEventSelected(null);
  } catch (err) {
    console.error(err);
    alert('Error al eliminar el evento');
  } finally {
    setShowModal(false);
  }
  };

  if (loading) return <div>Cargando eventos...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="col-8 align-self-center mt-5">
      {userAuthenticated.rol.descripcion !== 'VOLUNTARIO' && (
        <button
          onClick={() => navigate('/events/new')}
          style={{
            marginBottom: "20px",
            padding: "10px 20px",
            fontSize: "16px",
            cursor: "pointer",
          }}
        >
          Crear Evento
        </button>
      )}


      <h1>Lista de Eventos</h1>
      <Table
        columns={[
          { key: "nombre", header: "Nombre" },
          { key: "descripcion", header: "Descripción" },
          { key: "fecha", header: "Fecha Evento", render: (value) => value.toLocaleString(),},
          { key: "idOrganizacion", header: "Tipo evento", render: (value) => ( <span style={{ color: value !== 1 ? "red" : "green", fontWeight: "bold" }}> {value !== 1 ? "Externo" : "Propio"}</span>),},
        ]}
        data={events}
        actions={
            [
              { label: "Editar", icon: editIcon,show: (u) => u.idOrganizacion === 1, onClick: (u) => handleEditEvent(u.id) },
              { label: "Eliminar", icon: deleteIcon, show: (u) => u.idOrganizacion === 1, onClick: (u) => handleDeleteEventOnClick(u) },
              {
                label: "Publicar",
                icon: null,
                show: (u) => u.publicado === false && u.idOrganizacion === 1,
                onClick: (u) => handlePublicarEvento(u),
              }

            ]
          
        }
        emptyMessage="No hay eventos disponibles"
      />

        <Modal
              show={showModal}
              title="Eliminar evento"
              message={`¿Estás seguro que deseas eliminar el evento?`}
              onConfirm={handleDeleteEvent}
              onCancel={() => setShowModal(false)}
        />
    </div>
  );
};
