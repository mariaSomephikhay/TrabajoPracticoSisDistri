import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import RequestDonationService from '../../../services/RequestDonationService.js';

export const RequestDonationNewForm = () => {
  const [isNew, setIsNew] = useState(false);
  const [donacion, setDonacion] = useState({
    categoria: { id: "", descripcion: "" },
    descripcion: "",
    cantidad: ""
  });
  const [solicitud, setSolicitud] = useState({
    id_organizacion_solicitante: 1,
    id_solicitud_donacion: "",
    donacion: []
  });

  const categorias = [
    { id: 1, descripcion: "ALIMENTO" },
    { id: 2, descripcion: "JUGUETE" },
    { id: 3, descripcion: "ROPA" },
    { id: 4, descripcion: "UTIL_ESCOLAR" }
  ];

  // Manejar cambios en los campos de una donación
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "categoria") {
      const cat = categorias.find(c => c.id === parseInt(value));
      setDonacion(prev => ({
        ...prev,
        categoria: cat ? { id: cat.id, descripcion: cat.descripcion } : { id: "", descripcion: "" }
      }));
    } else {
      setDonacion(prev => ({ ...prev, [name]: value }));
    }
  };
  const handleChangeDonation = (e) => { 
    const { name, value } = e.target
    if (name === "categoria") {
      setDonacion((prev) => ({
        ...prev,
        categoria: {
          id: parseInt(value),
          descripcion: categorias.find((c) => c.id === parseInt(value))?.descripcion || ""
        }
      }))
    } else {
      setDonacion((prev) => ({ ...prev, [name]: value }))
    }
  }
  // Agregar donación al array de la solicitud
  const handleAddDonation = (e) => {
    e.preventDefault();
    if (!donacion.descripcion || !donacion.cantidad || !donacion.categoria.id) {
      alert("Complete todos los campos antes de agregar.");
      return;
    }

    setSolicitud(prev => ({
      ...prev,
      donacion: [...prev.donacion, { ...donacion, cantidad: parseInt(donacion.cantidad) }]
    }));

    // Resetear los campos de donación
    setDonacion({ categoria: { id: "", descripcion: "" }, descripcion: "", cantidad: "" });
  };

  // Eliminar donación de la lista
  const handleRemoveDonation = (index) => {
    setSolicitud(prev => ({
      ...prev,
      donacion: prev.donacion.filter((_, i) => i !== index)
    }));
  };

  // Enviar la solicitud completa
  const handleSubmitRequest = async (e) => {
    e.preventDefault();
    if (solicitud.donacion.length === 0) {
      alert("Agregue al menos una donación antes de enviar la solicitud.");
      return;
    }
    try {
      await RequestDonationService.CrearSolicitudDonacion(solicitud);
      setIsNew(true);
    } catch (error) {
      console.error(error);
      alert("Error al crear la solicitud de donacion");
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card shadow-lg p-4" style={{ maxWidth: "600px", width: "100%", borderRadius: "1rem" }}>
        {!isNew ? (
          <>
            <h2 className="mb-4 text-primary text-center">Nueva Solicitud de Donacion</h2>

            {/* Formulario para agregar una donación */}
            <form onSubmit={handleAddDonation}>
              <div className="row g-2 mb-3">
                <div className="col">
                  <label className="form-label">Categoria <span className="text-danger">*</span></label>
                  <select
                    className="form-select"
                    name="categoria"
                    value={donacion.categoria.id}
                    onChange={handleChangeDonation}
                    required
                  >
                    <option value="">Seleccione categoria</option>
                    {categorias.map(c => (
                      <option key={c.id} value={c.id}>{c.descripcion}</option>
                    ))}
                  </select>
                </div>
                <div className="col">
                  <label className="form-label">Descripcion <span className="text-danger">*</span></label>
                  <input
                    type="text"
                    className="form-control"
                    name="descripcion"
                    value={donacion.descripcion}
                    onChange={handleChangeDonation}
                    required
                  />
                </div>
                <div className="col">
                  <label className="form-label">Cantidad <span className="text-danger">*</span></label>
                  <input
                    type="number"
                    className="form-control"
                    name="cantidad"
                    value={donacion.cantidad}
                    onChange={handleChangeDonation}
                    required
                    min={1}
                  />
                </div>
              </div>
              <button type="submit" className="btn btn-secondary w-100 mb-3">Agregar Donacion</button>
            </form>

            {/* Lista de donaciones agregadas */}
            {solicitud.donacion.length > 0 && (
              <div className="mb-3">
                <h5>Donaciones agregadas:</h5>
                <ul className="list-group">
                  {solicitud.donacion.map((d, i) => (
                    <li key={i} className="list-group-item d-flex justify-content-between align-items-center">
                      Categoria: {d.categoria.descripcion} - Descripcion: {d.descripcion} - Cantidad: {d.cantidad} 
                      <button type="button" className="btn btn-sm btn-danger" onClick={() => handleRemoveDonation(i)}>
                        Eliminar
                      </button>
                    </li>
                  ))}
                </ul>
              </div>
            )}

            <button
              className="btn btn-primary w-100"
              onClick={handleSubmitRequest}
            >
              Enviar Solicitud
            </button>
          </>
        ) : (
          <div className="text-center">
            <h3 className="text-success mb-3">¡Solicitud creada con éxito!</h3>
            <NavLink to="/..." className="btn btn-success">Ir a ...</NavLink>
          </div>
        )}
      </div>
    </div>
  );
};
