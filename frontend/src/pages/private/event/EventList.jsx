import React, { useState, useContext, useEffect } from 'react';
import { Table } from '../../../components/ui/Table.jsx';
import { AuthContext } from "../../../context/AuthContext.jsx";
import editIcon from "../../../../public/icons/edit.svg";
import deleteIcon from "../../../../public/icons/delete.svg";
import publishIcon from "../../../../public/icons/publish.svg";
import addIcon from "../../../../public/icons/add.png" 
import EventService from '../../../services/EventService.js';
import { Modal } from "../../../components/ui/Modal.jsx"
import { useNavigate } from 'react-router-dom'

export const EventList = () => {
  const { userAuthenticated } = useContext(AuthContext);
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showModalPublicar, setShowModalPublicar] = useState(false);
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

  const handleAddExternalEvent = (id) => {
    navigate(`/events/external/event/${id}`)
  };

  const handleDeleteEventOnClick = (event) => { 
    setEventSelected(event) 
    setShowModal(true) 
  }

  const handlePublicarEventOnClick = (event) => { 
    setEventSelected(event) 
    setShowModalPublicar(true) 
  }

  const handlePublicarEvento = async () => {
  console.log("Publicar evento: ", eventSelected);
  try {
    EventService.enviarNotifiacionKafka(eventSelected);

    const updatedEvent = { ...eventSelected, publicado: true };

    await EventService.modificarEvento(eventSelected.id, updatedEvent);

    // Actualización optimista: cambio publicado a true inmediatamente
    setEvents(prevEvent => prevEvent.map(e => e.id === eventSelected.id ? { ...e, publicado: true } : e));
    /*setEvents((prevEvents) =>
      prevEvents.map((ev) =>
        ev.id === eventSelected.id ? updatedEvent : ev
      )
    );*/
  } catch (error) {
    console.error(error);
    alert("Error al crear la solicitud de donacion");
  }finally { 
    setShowModalPublicar(false) 
  } 
};


  const handleDeleteEvent = async () => {
  try {
    await EventService.eliminarEvento(eventSelected.id);
    EventService.enviarNotifiacionBajaKafka(eventSelected);
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
                icon: publishIcon,
                onClick: (u) => handlePublicarEventOnClick(u),
                hidden: (u) => u.publicado || u.idOrganizacion !== 1
              },
              { label: "Adherirse", icon: addIcon, show: (u) => u.idOrganizacion !== 1, onClick: (u) => handleAddExternalEvent(u.id) },

            ]
                  
        }
        emptyMessage="No hay eventos disponibles"
      />
        <Modal
              show={showModalPublicar}
              title="Publicar evento"
              message={`¿Estás seguro que deseas publicar el evento para las ONGs de la Red?`}
              onConfirm={handlePublicarEvento}
              onCancel={() => setShowModalPublicar(false)}
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
