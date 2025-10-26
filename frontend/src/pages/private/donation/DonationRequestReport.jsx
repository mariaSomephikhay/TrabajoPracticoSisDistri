import React, { useState, useContext, useEffect } from 'react';
import { AuthContext } from "../../../context/AuthContext.jsx";
import RequestService from '../../../services/RequestDonationService.js';
import UserService from '../../../services/UserService.js';
import FilterService from '../../../services/FilterService.js';
import "../../../estilos/EventFilter.css";

export const RequestDonationReport = () => {
  const { authToken, userAuthenticated } = useContext(AuthContext);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);
  const [dataUser, setDataUser] = useState([]);

  const [selectedCategoria, setSelectedCategoria] = useState(null);
  const [fechaDesde, setFechaDesde] = useState(null);
  const [fechaHasta, setFechaHasta] = useState(null);
  const [eliminadoFilter, setEliminadoFilter] = useState(null);

  // Nuevo estado para filtros guardados
  const [filtrosGuardados, setFiltrosGuardados] = useState([]);
  const [selectedFiltroGuardado, setSelectedFiltroGuardado] = useState("");

    const categorias = [
    { categoria: 'ALIMENTO' },
    { categoria: 'JUGUETE' },
    { categoria: 'ROPA' },
    { categoria: 'UTIL_ESCOLAR' }
  ]

  const handleBuscarInforme = async (categoria, fechaDesde, fechaHasta, eliminado) => {
    try {
      const query = `query InformeSolicitudes($filtro: FiltroSolicitudInput) {
        informeSolicitudes(filtro: $filtro) {
            status
            message
            data {
              categoria
              eliminado
              cantidad
              recibida
            }
          }
        }`;

      const variables = {
        filtro: {
          categoria: categoria === "Categoria" ? null : categoria,
          fechaDesde,
          fechaHasta,
          eliminado: eliminado === "Ambos" ? null : eliminado
        }
      };

      const Query = {
        query,
        variables
      };

      const response = await RequestService.obtenerInformeDonaciones(Query);
      console.log("Solicitudes filtrados:", response);
      setData(response?.data?.informeSolicitudes?.data || []);
      console.log("Solicitudes filtrados:", response.data.informeSolicitudes.data);
    } catch (error) {
      console.error(error);
      alert("Error al buscar solicitudes de donación");
    }
  };

  const handleFiltrosGuardados = async (usuario) => {
    try {
      const query = `query TraerFiltros($tipo: String!,$usuario: String!) { traerFiltros(tipo: $tipo, usuario: $usuario) { status message data { id name valueFilter usuario filterType } } }`;
      const tipo = `Donacion`; 
      const Query = {
        query,
        variables: {
          usuario,
          tipo
        }
      };

      const response = await FilterService.filterSavedGRAPHQL(Query);

      const transformFilters = (data) => {
        return data.map((filtro) => {
          const keyValuePairs = filtro.valueFilter
            .split(";")
            .filter((pair) => pair.includes(":"))
            .map((pair) => {
              const [key, value] = pair.split(":");
              return { key, value };
            });

          return {
            id: filtro.id || 0,
            name: filtro.name || "",
            usuario: filtro.usuario || "",
            filterType: filtro.filterType || "",
            valueFilter: keyValuePairs,
          };
        });
      };
      console.log("Filtros cargados:", response.data.traerFiltros.data);
      setFiltrosGuardados(transformFilters(response.data.traerFiltros.data || []));
      
    } catch (error) {
      console.error(error);
      alert("Error al cargar los filtros guardados");
    }
  };

  // Aplica el filtro guardado seleccionado
  const aplicarFiltroGuardado = (filtroId) => {
    const filtro = filtrosGuardados.find(f => f.id === parseInt(filtroId));
    if (!filtro) return;

    const parseValue = (key, val) => {
    if (val === "None" || val === null || val === undefined) {
      if (key === "eliminado") return "Ambos";         // 'Ambos'
      if (key === "fechaDesde" || key === "fechaHasta") return null; // fecha vacía
      if (key === "categoria") return "Categoria";         // categoría vacía
      return null;
    }
    return val;
    };

    // Actualizar los estados con los valores del filtro
    filtro.valueFilter.forEach(fv => {
      const value = parseValue(fv.key, fv.value);
      if (fv.key === "categoria") setSelectedCategoria(value);
      if (fv.key === "fechaDesde") setFechaDesde(value);
      if (fv.key === "fechaHasta") setFechaHasta(value);
      if (fv.key === "eliminado") setEliminadoFilter(value);
    });

    // Ejecutar búsqueda con el filtro aplicado
    handleBuscarInforme(
      parseValue("categoria", filtro.valueFilter.find(fv => fv.key === "categoria")?.value),
      parseValue("fechaDesde", filtro.valueFilter.find(fv => fv.key === "fechaDesde")?.value),
      parseValue("fechaHasta", filtro.valueFilter.find(fv => fv.key === "fechaHasta")?.value),
      parseValue("eliminado", filtro.valueFilter.find(fv => fv.key === "eliminado")?.value)
    );
  };

  const handleDescargarInforme = async () => {
  try {
    handleBuscarInforme(selectedCategoria || null , fechaDesde || null, fechaHasta || null, eliminadoFilter || null);
    const body = {
      categoria: selectedCategoria === "Categoria" || selectedCategoria === "None"? null : selectedCategoria,
      fechaDesde,
      fechaHasta,
      eliminado: eliminadoFilter === "Ambos" || eliminadoFilter === "" ? null : eliminadoFilter
    };

    const blob = await RequestService.obtenerInformeDonacionesExcel(authToken, body);

    const url = window.URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    
    const formatoFiltro = (valor) => (valor ? valor : "Todos");

    const fileName = `informe_donaciones_${formatoFiltro(body.categoria)}_${formatoFiltro(body.fechaDesde)}_${formatoFiltro(body.fechaHasta)}_${formatoFiltro(body.eliminado)}.xlsx`;
    link.setAttribute("download", fileName);
    document.body.appendChild(link);
    link.click();

    window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error(error);
      alert("Error al descargar el informe Excel");
    }
  };

  useEffect(() => {
    const fetchReport = async () => {
      try {
        const dataUser = await UserService.obtenerUsuarioPorUsername(userAuthenticated.username);
        
        //guardamos el usuario id
        setDataUser(dataUser.id);
        handleFiltrosGuardados(dataUser.id);
        handleBuscarInforme(null, null, null, null);

      } catch (err) {
        console.error(err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchReport();
  }, []);

  const handleSubmit = (i) => {
    i.preventDefault();
    handleBuscarInforme(selectedCategoria || null , fechaDesde || null, fechaHasta || null, eliminadoFilter || null);
  };

  // Función para eliminar un filtro
  const handleEliminarFiltro = async (id) => {
    try {
      const idNum = parseInt(id); // Convertimos string a número
      const query = `mutation BorrarFiltro($id: ID!) { borrarFiltro(id: $id) { status message data { id name valueFilter usuario filterType } } }`;
      const Query = {
        query,
        variables :{
          id
        }  

      };
      await FilterService.deleteFilterGRAPHQL(Query);
      alert("Filtro eliminado correctamente");

      // Actualizar la lista de filtros guardados
      setFiltrosGuardados(prev => prev.filter(f => f.id !== idNum));

      // Limpiar selección si era el filtro eliminado
      if (selectedFiltroGuardado === id) {
        setSelectedFiltroGuardado("");
      }
    } catch (error) {
      console.error(error);
      alert("Error al eliminar el filtro");
    }
  };


  // Función para guardar el filtro actual
  const handleGuardarFiltro = async () => {
    try {
      const newFiltro = {
       
        valueFilter: [
          { key: "categoria", value: selectedCategoria || 'None' },
          { key: "fechaDesde", value: fechaDesde || "" },
          { key: "fechaHasta", value: fechaHasta || "" },
          { key: "eliminado", value: eliminadoFilter || 'None' },
        ]
      };

      const revertFilter = (filtro) => {
        return filtro.valueFilter
          .map(({ key, value }) => {
            // Si el valor es null, undefined o "", guardamos "None"
            const safeValue = value === null || value === undefined || value === "" ? "None" : value;
            return `${key}:${safeValue}`;
          })
          .join(";");
      };

      const valueFilter = revertFilter(newFiltro);
      console.log(newFiltro);
      const query = `mutation GuardarFiltro($filtro: FilterInput!) { guardarFiltro(filtro: $filtro) { status message data { id name valueFilter usuario filterType } } }`;
      
      const Query = {
        query,
        variables: {
          filtro: {
            usuario: dataUser,
            filterType: "donacion",
            name: prompt("Ingrese el nombre del filtro:"), 
            valueFilter
          }
        }
      };
      const response = await FilterService.saveFilterGRAPHQL(Query); // Asumiendo que devuelve el filtro guardado con id
      handleFiltrosGuardados(dataUser); // Refrescar la lista de filtros
      alert("Filtro guardado correctamente");
    } catch (error) {
      console.error(error);
      alert("Error al guardar el filtro");
    }
  };

  // funciona para actualizar filtro
  const handleUpdateFiltro = async (filtroId) => {
    try {
      const id = Number(filtroId);
      const newName = prompt("Ingrese el nombre del filtro:");
      if (!newName) return;
      const newFiltro = {
       
        valueFilter: [
          { key: "categoria", value: selectedCategoria || 'None' },
          { key: "fechaDesde", value: fechaDesde || null },
          { key: "fechaHasta", value: fechaHasta || null },
          { key: "eliminado", value: eliminadoFilter || 'None' },
        ]
      };

      const revertFilter = (filtro) => {
        return filtro.valueFilter
          .map(({ key, value }) => {
            // Si el valor es null, undefined o "", guardamos "None"
            const safeValue = value === null || value === undefined || value === "" ? "None" : value;
            return `${key}:${safeValue}`;
          })
          .join(";");
      };

      const valueFilter = revertFilter(newFiltro);

      
      const query = `mutation GuardarFiltro($filtro: FilterInput!) { guardarFiltro(filtro: $filtro) { status message data { id name valueFilter usuario filterType } } }`;
      
      const Query = {
        query,
        variables: {
          filtro: {
            id,
            usuario: dataUser,
            filterType: "donacion",
            name:  newName,
            valueFilter
          }
        }
      };

      //await FilterService.saveFilterGRAPHQL(Query);
    
      const response = await FilterService.saveFilterGRAPHQL(Query);
      
      alert("Filtro actualizado correctamente");
      
      //console.log("Filtros cargados:", response.data.guardarFiltro.data);
      setFiltrosGuardados(prev =>
        prev.map(f => f.id === id ? { ...f, ...newFiltro } : f)
      );

      if (selectedFiltroGuardado === filtroId) {
        setSelectedFiltroGuardado("");
      }
    } catch (error) {
      console.error(error);
      alert("Error al actualizar el filtro");
    }
  };

  if (loading) return <div>Cargando Informe...</div>;
  if (error) return <div>Error: {error}</div>;

  return (
    <div className="event-container">
      <h1 className="title">Filtros de búsqueda</h1>

      {/* Selector de filtros guardados */}
          <div className="filter-group">
            

            {/* Botón para eliminar filtro */}
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

            {/* Botón para guardar el filtro actual */}
            <button
              type="button"
              onClick={handleGuardarFiltro}
              className="filter-button save"
            >
              Guardar filtro actual
            </button>

            <label>Filtros guardados:</label>
            <select
              value={selectedFiltroGuardado}
              onChange={(i) => {
                setSelectedFiltroGuardado(i.target.value);
                aplicarFiltroGuardado(i.target.value);
              }}
              className="filter-input"
            >
              <option value="">-- Seleccione un filtro --</option>
              {filtrosGuardados.map(f => (
                // Convertimos el value a string para evitar problemas de tipo
                <option key={f.id} value={f.id.toString()}>{f.name}</option>
              ))}
            </select>
          </div>
        


      {/* Cabecera de filtros */}
      <form className="filter-bar" onSubmit={handleSubmit}>
        {/* Usuario */}
        <div className="filter-group">
          <label>Categoria:</label>
          
            <select
              value={selectedCategoria}
              onChange={(e) => setSelectedCategoria(e.target.value)}
              className="filter-input"
            >
              <option value="">Categoria</option>
              {categorias.map((cat) => (
                <option key={cat.categoria} value={cat.categoria}>
                  {cat.categoria}
                </option>
              ))}
            </select>
          
        </div>

        {/* Fechas y eliminado */}
        <div className="filter-group">
          <label>Desde:</label>
          <input type="date" value={fechaDesde || ""} onChange={(i) => setFechaDesde(i.target.value)} className="filter-input" />
        </div>

        <div className="filter-group">
          <label>Hasta:</label>
          <input type="date" value={fechaHasta || ""} onChange={(i) => setFechaHasta(i.target.value)} className="filter-input" />
        </div>

        <div className="filter-group">
          <label>Eliminado:</label>
          <select value={eliminadoFilter} onChange={(i) => setEliminadoFilter(i.target.value)} className="filter-input">
            <option value="">Ambos</option>
            <option value="SI">SI</option>
            <option value="NO">NO</option>
          </select>
        </div>

        <button type="submit" className="filter-button">Buscar</button>

        <button type="button" onClick={handleDescargarInforme} className="filter-button">Descargar Excel</button>
      </form>

      {/* Listado de resultados */}
      <h1 className="title">Informe de donaciones</h1>
      {(!data || data.length === 0)  ? (
        <p className="no-data">No hay datos disponibles.</p>
      ) : (
           <div className="month-card">
                <ul className="event-list">
                  {data.map((donacion, idx) => (
                    <li key={idx} className="event-item">
                      <div className="event-header">
                        <strong>📦 {donacion.categoria}</strong>
                        <span className={`status ${donacion.recibida ? "recibida" : "pendiente"}`}>
                          {donacion.recibida ? "Recibida" : "Enviada"}
                        </span>
                      </div>
                      <p className="event-description">
                        Eliminado: <strong>{donacion.eliminado}</strong>
                      </p>
                      <p className="event-description">
                        Cantidad: <strong>{donacion.cantidad}</strong>
                      </p>
                    </li>
                  ))}
                </ul>
              </div>
            )}
      
    </div>
  );
};

