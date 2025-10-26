import React, { useState, useContext, useEffect } from 'react';
import { AuthContext } from "../../../context/AuthContext.jsx";
import EventService from '../../../services/EventService.js';
import UserService from '../../../services/UserService.js';
import FilterService from '../../../services/FilterService.js';
import "../../../estilos/EventFilter.css";

export const EventFilter = () => {
  const { userAuthenticated } = useContext(AuthContext);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);
  const [dataUser, setDataUser] = useState([]);

  const [usuarios, setUsuarios] = useState([]);
  const [selectedUser, setSelectedUser] = useState("");
  const [fechaDesde, setFechaDesde] = useState("");
  const [fechaHasta, setFechaHasta] = useState("");
  const [donacionFilter, setDonacionFilter] = useState("0"); // 0=ambos, 1=con, 2=sin

  // Nuevo estado para filtros guardados
  const [filtrosGuardados, setFiltrosGuardados] = useState([]);
  const [selectedFiltroGuardado, setSelectedFiltroGuardado] = useState("");

  const handleBuscarEvento = async (usuarioId, fechaDesde, fechaHasta, tieneDonacion) => {
    try {
      const filter = {
        usuarioId,
        fechaDesde,
        fechaHasta,
        tieneDonacion
      };
      const response = await EventService.EventFilter(filter);
      setData(response.data.informeParticipacionEventos);
      console.log("Eventos filtrados:", response.data.informeParticipacionEventos);
    } catch (error) {
      console.error(error);
      alert("Error al buscar eventos");
    }
  };

  const handleFiltrosGuardados = async (usuarioId) => {
    try {
      const response = await FilterService.filterSaveEvent(usuarioId);
      setFiltrosGuardados(response.filtros || []);
      console.log("Filtros cargados:", response.filtros);
    } catch (error) {
      console.error(error);
      alert("Error al cargar los filtros guardados");
    }
  };

  // Aplica el filtro guardado seleccionado
  const aplicarFiltroGuardado = (filtroId) => {
    const filtro = filtrosGuardados.find(f => f.id === parseInt(filtroId));
    if (!filtro) return;

    // Actualizar los estados con los valores del filtro
    filtro.valueFilter.forEach(fv => {
      if (fv.key === "fechaDesde") setFechaDesde(fv.value);
      if (fv.key === "fechaHasta") setFechaHasta(fv.value);
      if (fv.key === "tieneDonacion") setDonacionFilter(fv.value);
    });

    // Ejecutar búsqueda con el filtro aplicado
    handleBuscarEvento(selectedUser, 
      filtro.valueFilter.find(fv => fv.key === "fechaDesde")?.value || null,
      filtro.valueFilter.find(fv => fv.key === "fechaHasta")?.value || null,
      filtro.valueFilter.find(fv => fv.key === "tieneDonacion")?.value || "0"
    );
  };

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const dataUser = await UserService.obtenerUsuarioPorUsername(userAuthenticated.username);
        setSelectedUser(dataUser.id);

        if (dataUser.rol.descripcion === "PRESIDENTE" || dataUser.rol.descripcion === "COORDINADOR") {
          const allUsers = await UserService.obtenerListadoUsuarios(); 
          setUsuarios(allUsers.usuarios);
        } else {
          setUsuarios([dataUser]);
        }
        
        //guardamos el usuario id
        setDataUser(dataUser.id);

        handleBuscarEvento(dataUser.id, null, null, 0);
        handleFiltrosGuardados(dataUser.id);

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
    handleBuscarEvento(selectedUser, fechaDesde, fechaHasta, donacionFilter);
  };

  // Función para eliminar un filtro
  const handleEliminarFiltro = async (filtroId) => {
    try {
      const idNum = parseInt(filtroId); // Convertimos string a número
      await FilterService.deleteFilter(idNum);
      alert("Filtro eliminado correctamente");

      // Actualizar la lista de filtros guardados
      setFiltrosGuardados(prev => prev.filter(f => f.id !== idNum));

      // Limpiar selección si era el filtro eliminado
      if (selectedFiltroGuardado === filtroId) {
        setSelectedFiltroGuardado("");
      }
    } catch (error) {
      console.error(error);
      alert("Error al eliminar el filtro");
    }
  };

  // Función para eliminar un filtro
  const handleUpdateFiltro = async (filtroId) => {
    try {
      const idNum = Number(filtroId);
      const newName = prompt("Ingrese el nombre del filtro:");
      if (!newName) return;

      const updateFiltro = {
        usuario: dataUser,
        filterType: "evento",
        name: newName,
        valueFilter: [
          { key: "fechaDesde", value: fechaDesde || "" },
          { key: "fechaHasta", value: fechaHasta || "" },
          { key: "tieneDonacion", value: donacionFilter || "0" },
        ],
      };

      await FilterService.updateFilter(idNum, updateFiltro);
      alert("Filtro actualizado correctamente");

      // Reemplazar el filtro actualizado en la lista
      setFiltrosGuardados(prev =>
        prev.map(f => f.id === idNum ? { ...f, ...updateFiltro } : f)
      );

      if (selectedFiltroGuardado === filtroId) {
        setSelectedFiltroGuardado("");
      }
    } catch (error) {
      console.error(error);
      alert("Error al actualizar el filtro");
    }
  };



  // Función para guardar el filtro actual
  const handleGuardarFiltro = async () => {
    try {
      const newFiltro = {
        usuario: dataUser,
        filterType: "evento",
        name: prompt("Ingrese el nombre del filtro:"), 
        valueFilter: [
          { key: "fechaDesde", value: fechaDesde || "" },
          { key: "fechaHasta", value: fechaHasta || "" },
          { key: "tieneDonacion", value: donacionFilter || "0" },
        ]
      };
      const response = await FilterService.saveFilter(newFiltro); // Asumiendo que devuelve el filtro guardado con id
      handleFiltrosGuardados(dataUser); // Refrescar la lista de filtros
      alert("Filtro guardado correctamente");
    } catch (error) {
      console.error(error);
      alert("Error al guardar el filtro");
    }
  };


  if (loading) return <div>Cargando eventos...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="event-container">
      <h1 className="title">Filtros de búsqueda</h1>

      {/* Selector de filtros guardados */}
          <div className="filter-group">
          

          {selectedFiltroGuardado && (
            <>
              <button
                type="button"
                onClick={() => handleEliminarFiltro(selectedFiltroGuardado)}
                className="filter-button delete"
              >
                Eliminar
              </button>

              <button
                type="button"
                onClick={() => handleUpdateFiltro(selectedFiltroGuardado)}
                className="filter-button update"
              >
                Actualizar
              </button>
            </>
          )}

          {!selectedFiltroGuardado && (
            <button
              type="button"
              onClick={handleGuardarFiltro}
              className="filter-button save"
            >
              Guardar filtro actual
            </button>
          )}

          <label>Filtros guardados:</label>
          <select
            value={selectedFiltroGuardado}
            onChange={(e) => {
              setSelectedFiltroGuardado(e.target.value);
              aplicarFiltroGuardado(e.target.value);
            }}
            className="filter-input"
          >
            <option value="">-- Seleccione un filtro --</option>
            {filtrosGuardados.map(f => (
              <option key={f.id} value={f.id.toString()}>{f.name}</option>
            ))}
          </select>
        </div>

      {/* Cabecera de filtros */}
      <form className="filter-bar" onSubmit={handleSubmit}>
        {/* Usuario */}
        <div className="filter-group">
          <label>Usuario:</label>
          <select
            value={selectedUser}
            onChange={(e) => setSelectedUser(e.target.value)}
            className="filter-input"
          >
            {usuarios.map(u => (
              <option key={u.id} value={u.id}>{`${u.nombre} ${u.apellido}`}</option>
            ))}
          </select>
        </div>

        {/* Fechas y donación */}
        <div className="filter-group">
          <label>Desde:</label>
          <input type="date" value={fechaDesde} onChange={(e) => setFechaDesde(e.target.value)} className="filter-input" />
        </div>

        <div className="filter-group">
          <label>Hasta:</label>
          <input type="date" value={fechaHasta} onChange={(e) => setFechaHasta(e.target.value)} className="filter-input" />
        </div>

        <div className="filter-group">
          <label>Donaciones:</label>
          <select value={donacionFilter} onChange={(e) => setDonacionFilter(e.target.value)} className="filter-input">
            <option value="0">Ambos</option>
            <option value="1">Con donaciones</option>
            <option value="2">Sin donaciones</option>
          </select>
        </div>

        <button type="submit" className="filter-button">Buscar</button>
      </form>

      {/* Listado de resultados */}
      <h1 className="title">📆 Eventos por mes</h1>
      {data.length === 0 ? (
        <p className="no-data">No hay datos disponibles.</p>
      ) : (
        data.map(mesInfo => (
          <div key={mesInfo.mes} className="month-card">
            <h2 className="month-title">{mesInfo.mes}</h2>
            <ul className="event-list">
              {mesInfo.eventos.map(evento => (
                <li key={evento.id} className="event-item">
                  <div className="event-header">
                    <strong>{evento.nombre}</strong>
                    <span className="event-date">{new Date(evento.fecha).toLocaleDateString("es-AR")}</span>
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
                  ) : <p className="no-donations">No hubo donaciones.</p>}
                </li>
              ))}
            </ul>
          </div>
        ))
      )}
    </div>
  );
};

