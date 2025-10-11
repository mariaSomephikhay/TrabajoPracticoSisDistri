import React, { useState } from "react"
import { NavLink } from "react-router-dom"
import DonationService from '../../../services/DonationService.js'

export const DonationNewForm = () => {
  const [isNew, setIsNew] = useState(false)
  const [newDonation, setNewDonation] = useState({
    categoria: {
      id: 0,
      descripcion: ""
    },
    descripcion: "",
    cantidad: ""
  })

  const categorias = [
    { id: 1, descripcion: 'ALIMENTO' },
    { id: 2, descripcion: 'JUGUETE' },
    { id: 3, descripcion: 'ROPA' },
    { id: 4, descripcion: 'UTIL_ESCOLAR' }
  ]


  const handleNewDonation = async (e) => {
    e.preventDefault()
    try {
      await DonationService.CrearDonacion(newDonation) 
      setIsNew(true)
    } catch (err) { 
      console.error(err) 
      alert('Error al crear donacion') 
    } 
  }

  const handleChangeDonation = (e) => { 
    const { name, value } = e.target
    if (name === "categoria") {
      setNewDonation((prev) => ({
        ...prev,
        categoria: {
          id: parseInt(value),
          descripcion: categorias.find((c) => c.id === parseInt(value))?.descripcion || ""
        }
      }))
    } else {
      setNewDonation((prev) => ({ ...prev, [name]: value }))
    }
  }

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div
        className="card shadow-lg p-4 text-center"
        style={{
          maxWidth: "500px",
          width: "100%",
          borderRadius: "1rem",
          border: "1px solid #eaeaea",
        }}
      >
        {!isNew ? (
          <>
            <h2 className="mb-4 fw-bold text-primary">Nueva donacion</h2>
            <p className="text-muted mb-4">
              Completa los campos para crear una nueva donacion.
            </p>            
            <form onSubmit={handleNewDonation}>

              <div className="mb-3 text-start">
                <label htmlFor="categoria" className="form-label">Categoria
                  <span className="text-danger">*</span>
                </label>
                <select
                  className="form-select"
                  name="categoria"
                  value={newDonation.categoria?.id ?? ""}
                  onChange={handleChangeDonation}
                  required>
                  <option value="">Seleccione la categoria a la que pertenece</option>
                  {categorias.map((categoria) => (
                    <option key={categoria.id} value={categoria.id}>
                      {categoria.descripcion}
                    </option>
                  ))}
                </select>
              </div>

              <div className="mb-3 text-start">
                <label htmlFor="descripcion" className="form-label">
                  Descripcion <span className="text-danger">*</span>
                </label>
                <input
                  type="text"
                  id="descripcion"
                  className="form-control"
                  name="descripcion"
                  placeholder="Describe la donacion"
                  value={newDonation.descripcion ?? ''} 
                  onChange={handleChangeDonation}
                  required
                />
              </div>

              <div className="mb-3 text-start">
                <label htmlFor="cantidad" className="form-label">
                  Cantidad<span className="text-danger">*</span>
                </label>
                <input
                  type="cantidad"
                  id="cantidad"
                  className="form-control"
                  name="cantidad"
                  placeholder="1"
                  value={newDonation.cantidad ?? ''} 
                  onChange={handleChangeDonation}
                  required
                />
              </div>

              <button type="submit" className="btn btn-primary">
                Agregar donacion
              </button>
              
            </form>

            <p className="text-center mt-3 mb-0">
              ¿Desea Volver al inventario?{" "}
              <NavLink className="fw-bold text-decoration-none" to="/donation-inventory">
                Volver
              </NavLink>
            </p>
          </>
        ) : (
          <>
            <h3 className="mb-3 text-success">Donacion creada con exito!</h3>
            <p className="mb-4">
              Vuelva al inventario para ver la donacion creada.
            </p>
            <NavLink to="/donation-inventory" className="btn btn-success btn-lg">
              Inventario de donaciones
            </NavLink>
          </>
        )}
      </div>
    </div>
  )
}