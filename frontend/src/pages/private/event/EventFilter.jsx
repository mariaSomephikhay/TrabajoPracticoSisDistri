import React, { useState, useContext, useEffect } from 'react';
import { AuthContext } from "../../../context/AuthContext.jsx";
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js';
import "../../../estilos/EventFilter.css";

export const EventFilter = () => {
  const { userAuthenticated } = useContext(AuthContext);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);

  const [usuarios, setUsuarios] = useState([]);
  const [selectedUser, setSelectedUser] = useState("");
  const [fechaDesde, setFechaDesde] = useState("");
  const [fechaHasta, setFechaHasta] = useState("");
  const [donacionFilter, setDonacionFilter] = useState("0"); // 0=ambos, 1=con, 2=sin
  
  const handleBuscarEvento = async (usuarioId, fechaDesde, fechaHasta, tieneDonacion) => {
  try {
    const filter = {
      usuarioId: usuarioId,
      fechaDesde: fechaDesde,
      fechaHasta: fechaHasta,
      tieneDonacion: tieneDonacion
    };
    const response = await EventService.EventFilter(filter);
    setData(response.data.informeParticipacionEventos);
    console.log("Eventos filtrados:", response.data.informeParticipacionEventos);
  } catch (error) {
    console.error(error);
    alert("Error al crear la solicitud de donacion");
  }
};

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const dataUser = await UserService.obtenerUsuarioPorUsername(userAuthenticated.username);

        setSelectedUser(dataUser.id);

        if(dataUser.rol.descripcion === "PRESIDENTE" || dataUser.rol.descripcion === "COORDINADOR"){
          const allUsers = await UserService.obtenerListadoUsuarios(); 
          console.log("Listado usuarios:", allUsers);
          setUsuarios(allUsers.usuarios);
        } else {
          setUsuarios([dataUser]);
        }

        handleBuscarEvento(dataUser.id, null, null, 0);

      } catch (err) {
        console.error(err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchEvents();
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Buscando eventos con filtros:", { selectedUser, fechaDesde, fechaHasta, donacionFilter });
    handleBuscarEvento(selectedUser, fechaDesde, fechaHasta, donacionFilter);
  };

  if (loading) return <div>Cargando eventos...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="event-container">
      <h1 className="title">Filtros de búsqueda</h1>

      {/* Cabecera de filtros */}
      <form className="filter-bar" onSubmit={handleSubmit}>
        <div className="filter-group">
          <label>Usuario:</label>
          <select
            value={selectedUser}
            onChange={(e) => setSelectedUser(e.target.value)}
            className="filter-input"
          >
            {usuarios.map((u) => (
              <option key={u.id} value={u.id}>
                {`${u.nombre} ${u.apellido}`}
              </option>
            ))}

          </select>
        </div>

        <div className="filter-group">
          <label>Desde:</label>
          <input
            type="date"
            value={fechaDesde || ""}
            onChange={(e) => setFechaDesde(e.target.value)}
            className="filter-input"
          />
        </div>

        <div className="filter-group">
          <label>Hasta:</label>
          <input
            type="date"
            value={fechaHasta || ""}
            onChange={(e) => setFechaHasta(e.target.value)}
            className="filter-input"
          />
        </div>

        <div className="filter-group">
          <label>Donaciones:</label>
          <select
            value={donacionFilter}
            onChange={(e) => setDonacionFilter(e.target.value)}
            className="filter-input"
          >
            <option value="0">Ambos</option>
            <option value="1">Con donaciones</option>
            <option value="2">Sin donaciones</option>
          </select>
        </div>

        <button type="submit" className="filter-button">
          Buscar
        </button>
      </form>

      {/* Listado de resultados */}
      <h1 className="title">📆 Eventos por mes</h1>
      {data.length === 0 ? (
        <p className="no-data">No hay datos disponibles.</p>
      ) : (
        data.map((mesInfo) => (
          <div key={mesInfo.mes} className="month-card">
            <h2 className="month-title">{mesInfo.mes}</h2>
            <ul className="event-list">
              {mesInfo.eventos.map((evento) => (
                <li key={evento.id} className="event-item">
                  <div className="event-header">
                    <strong>{evento.nombre}</strong>
                    <span className="event-date">
                      {new Date(evento.fecha).toLocaleDateString("es-AR")}
                    </span>
                  </div>
                  <p className="event-description">{evento.descripcion}</p>

                  {evento.eventoDonaciones.length > 0 ? (
                    <div className="donation-section">
                      <h4 className="donation-title">🎁 Donaciones</h4>
                      <ul className="donation-list">
                        {evento.eventoDonaciones.map((don, idx) => (
                          <li key={idx} className="donation-item">
                            <span className="don-desc">{don.donacion.descripcion}</span>{" "}
                            <span className="don-cat">{don.donacion.categoria.descripcion}</span>{" "}
                            <span className="don-qty">x{don.cantRepartida}</span>
                          </li>
                        ))}
                      </ul>
                    </div>
                  ) : (
                    <p className="no-donations">No hubo donaciones.</p>
                  )}
                </li>
              ))}
            </ul>
          </div>
        ))
      )}
    </div>
  );
};
