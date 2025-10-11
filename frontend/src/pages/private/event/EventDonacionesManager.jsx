import React, { useEffect, useState } from 'react';
import { useParams, NavLink } from 'react-router-dom';
import EventService from '../../../services/EventService.js';
import DonationService from '../../../services/DonationService.js'

export const EventDonacionesManager = () => {
  const { id } = useParams(); // ID del evento
  const [donacionesGenerales, setDonacionesGenerales] = useState([]);
  const [donaciones, setDonaciones] = useState([]);
  const [nuevaDonacion, setNuevaDonacion] = useState({
    donacionId: '',
    cantidad: ''
    });
  const [cantidadesEditadas, setCantidadesEditadas] = useState({});

  // Cargar las donaciones del evento al montar
  useEffect(() => {
    cargarDonaciones();
  }, []);

  //cargar listado de donaciones generales
  useEffect(() => {
    const cargarDonacionesGenerales = async () => {
        try {
        const data = await DonationService.obtenerListadoDonaciones(); // <- await aquí
        setDonacionesGenerales(data.donaciones || []); // por si viene undefined
        } catch (err) {
        console.error(err);
        }
    };

    cargarDonacionesGenerales();
    }, []);


  const cargarDonaciones = async () => {
  try {
    const response = await EventService.obtenerListadoDonacionesAsociadosAEvento(id);
    const lista = response.listaDonacion || [];
    setDonaciones(lista);

    // Inicializar cantidadesEditadas para que refleje las cantidades correctas
    const cantidades = {};
    lista.forEach(d => {
      cantidades[d.donacion.id] = d.cantidad;
    });
    setCantidadesEditadas(cantidades);

    console.log(lista);
  } catch (error) {
    console.error('Error al cargar las donaciones del evento:', error);
  }
};

  const handleAgregarDonacion = async (e) => {
    e.preventDefault();
    if (!nuevaDonacion.donacionId || !nuevaDonacion.cantidad) return;

    try {
      console.log(nuevaDonacion)
      await EventService.agregarDonacionAlEvento(id,nuevaDonacion.donacionId, nuevaDonacion);
      await cargarDonaciones();
      
    } catch (error) {
      console.error('Error al agregar la donación:', error);
    }
  };

  const handleEditarCantidad = async (donacionId, nuevaCantidad) => {
    try {
      await EventService.agregarDonacionAlEvento(id, donacionId, { cantidad: nuevaCantidad });
      await cargarDonaciones();
    } catch (error) {
      console.error('Error al actualizar cantidad:', error);
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4 text-center">Gestión de Donaciones del Evento</h2>

      {/* Tabla de donaciones existentes */}
      {donaciones && donaciones.length > 0 ? (
        <table className="table table-bordered text-center">
          <thead className="table-light">
            <tr>
              <th>Categoría</th>
              <th>Descripción</th>
              <th>Cantidad Donada</th>
              <th>Acción</th>
            </tr>
          </thead>
          <tbody>
            {donaciones.map((don) => {
                const donGeneral = donacionesGenerales.find(d => d.id === don.donacion.id);
                const cantidadTotal = donGeneral?.cantidad || Infinity; // stock total
                const cantidadActual = don.cantidad; // cantidad ya asignada al evento
                const cantidadEditadaRaw = cantidadesEditadas[don.donacion.id] ?? cantidadActual;

                const maxPermitido = cantidadTotal + cantidadActual; // máximo permitido = stock total + cantidad ya asignada
                const cantidadEditada = Math.min(cantidadEditadaRaw, maxPermitido);
                const cambioRealizado = cantidadEditada !== cantidadActual;

                return (
                <tr key={don.donacion.id}>
                    <td>{don.donacion.categoria.descripcion}</td>
                    <td>{don.donacion.descripcion}</td>
                    <td>
                    <input
                        type="number"
                        className="form-control text-center"
                        value={cantidadEditada}
                        min={1}
                        max={maxPermitido}
                        onChange={(e) => {
                        const valor = Math.min(Number(e.target.value), maxPermitido);
                        setCantidadesEditadas({
                            ...cantidadesEditadas,
                            [don.donacion.id]: valor,
                        });
                        }}
                    />
                    </td>
                    <td>
                    <button
                        className="btn btn-sm btn-outline-primary"
                        onClick={() => handleEditarCantidad(don.donacion.id, cantidadEditada)}
                        disabled={!cambioRealizado}
                    >
                        Guardar
                    </button>
                    </td>
                </tr>
                );
            })}
            </tbody>
        </table>
      ) : (
        <div className="alert alert-info text-center">
          No hay donaciones cargadas para este evento.
        </div>
      )}

      {/* Formulario para agregar nueva donación */}
        <h4 className="mt-5">Agregar nueva donación</h4>
        <form className="row g-3 mt-2" onSubmit={handleAgregarDonacion}>
        <div className="col-md-8">
            <select
            className="form-control"
            name="donacionId"
            value={nuevaDonacion.donacionId}
            onChange={(e) =>
                setNuevaDonacion({
                ...nuevaDonacion,
                donacionId: e.target.value
                })
            }
            required
            >
            <option value="">Seleccione una donación</option>
            {donacionesGenerales
                ?.filter(
                (d) => !donaciones.some((donacionAsoc) => donacionAsoc.donacion.id === d.id)
                )
                .map((d) => (
                <option key={d.id} value={d.id}>
                    {d.categoria.descripcion} - {d.descripcion}
                </option>
                ))}
            </select>
        </div>

        <div className="col-md-3">
        <input
            type="number"
            className="form-control"
            name="cantidad"
            placeholder="Cantidad"
            value={nuevaDonacion.cantidad}
            onChange={(e) => {
            const donSeleccionada = donacionesGenerales.find(
                (d) => d.id.toString() === nuevaDonacion.donacionId
            );
            const max = donSeleccionada?.cantidad || Infinity; // cantidad máxima permitida
            const valor = Math.min(Number(e.target.value), max); // limitar al máximo
            setNuevaDonacion({
                ...nuevaDonacion,
                cantidad: valor
            });
            }}
            required
            min={1} //no permitir 0 o negativo
            max={donacionesGenerales.find(d => d.id.toString() === nuevaDonacion.donacionId)?.cantidad || undefined}
        />
        </div>


        <div className="col-md-1 d-grid">
            <button type="submit" className="btn btn-success">
            +
            </button>
        </div>
        </form>



      {/* Botón para volver */}
      <div className="text-center mt-4">
        <NavLink to={`/events/edit/${id}`} className="btn btn-secondary">
          Volver al evento
        </NavLink>
      </div>
    </div>
  );
};
