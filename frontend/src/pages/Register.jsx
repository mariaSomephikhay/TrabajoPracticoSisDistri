import React, { useState } from "react"
import { NavLink } from "react-router-dom"
import UserService from "../services/UserService.js"

export const Register = () => {
  const [isRegistered, setIsRegistered] = useState(false)
  const [newUser, setNewUser] = useState({
    username: "",
    email: "",
    nombre: "",
    apellido: "",
    telefono: "",
    activo: true,
    rol: {
      id: 1,
      descripcion: ""
    }
  })

  const roles = [
    { id: 2, nombre: 'VOLUNTARIO' },
    { id: 3, nombre: 'COORDINADOR' },
    { id: 4, nombre: 'VOCAL' }
  ]

  const handleRegisterUser = async (e) => {
    e.preventDefault()
    try {
      await UserService.registrarUsuario(newUser) 
      setIsRegistered(true)
    } catch (err) { 
      console.error(err) 
      alert('Error al registrar el usuario') 
    } 
  }

  const handleChangeUser = (e) => { 
    const { name, value } = e.target
    if (name === "rol") {
      setNewUser((prev) => ({
        ...prev,
        rol: {
          id: parseInt(value),
          descripcion: roles.find((r) => r.id === parseInt(value))?.nombre || ""
        }
      }))
    } else {
      setNewUser((prev) => ({ ...prev, [name]: value }))
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
        {!isRegistered ? (
          <>
            <h2 className="mb-4 fw-bold text-primary">Crea tu cuenta</h2>
            <p className="text-muted mb-4">
              Completa los campos para registrarte y acceder al sistema.
            </p>            
            <form onSubmit={handleRegisterUser}>
              <div className="mb-3 text-start">
                <label htmlFor="username" className="form-label">
                  Username <span className="text-danger">*</span>
                </label>
                <input
                  type="text"
                  id="username"
                  className="form-control"
                  name="username"
                  placeholder="Elige un nombre de usuario"
                  value={newUser.username ?? ''} 
                  onChange={handleChangeUser}
                  required
                />
              </div>

              <div className="mb-3 text-start">
                <label htmlFor="email" className="form-label">
                  Correo electrónico <span className="text-danger">*</span>
                </label>
                <input
                  type="email"
                  id="email"
                  className="form-control"
                  name="email"
                  placeholder="ejemplo@correo.com"
                  value={newUser.email ?? ''} 
                  onChange={handleChangeUser}
                  required
                />
              </div>

              <div className="mb-3 text-start">
                <label htmlFor="rol" className="form-label">Rol
                  <span className="text-danger">*</span>
                </label>
                <select
                  className="form-select"
                  name="rol"
                  value={newUser.rol?.id ?? ""}
                  onChange={handleChangeUser}
                  required>
                  <option value="">Seleccione un rol</option>
                  {roles.map((rol) => (
                    <option key={rol.id} value={rol.id}>
                      {rol.nombre}
                    </option>
                  ))}
                </select>
              </div>

              <div className="row g-2 mb-3">
                <div className="col-md-6 text-start">
                  <label htmlFor="nombre" className="form-label">
                    Nombre <span className="text-danger">*</span>
                  </label>
                  <input
                    type="text"
                    id="nombre"
                    className="form-control"
                    name="nombre"
                    placeholder="Tu nombre"
                    value={newUser.nombre ?? ''} 
                    onChange={handleChangeUser}
                    required
                  />
                </div>
                <div className="col-md-6 text-start">
                  <label htmlFor="apellido" className="form-label">
                    Apellido <span className="text-danger">*</span>
                  </label>
                  <input
                    type="text"
                    id="apellido"
                    className="form-control"
                    name="apellido"
                    placeholder="Tu apellido"
                    value={newUser.apellido ?? ''} 
                    onChange={handleChangeUser}
                    required
                  />
                </div>
              </div>

              <div className="mb-3 text-start">
                <label htmlFor="telefono" className="form-label">
                  Teléfono
                </label>
                <input
                  type="tel"
                  id="telefono"
                  className="form-control"
                  name="telefono"
                  value={newUser.telefono ?? ''} 
                  onChange={handleChangeUser}
                  placeholder="+54 9 11 1234-5678"
                />
              </div>

              <div className="d-grid">
                <button type="submit" className="btn btn-primary btn-lg">
                  Registrarse
                </button>
              </div>
            </form>

            <p className="text-center mt-3 mb-0">
              ¿Ya tienes cuenta?{" "}
              <NavLink className="fw-bold text-decoration-none" to="/login">
                Inicia sesión
              </NavLink>
            </p>
          </>
        ) : (
          <>
            <h3 className="mb-3 text-success">¡Registro exitoso!</h3>
            <p className="mb-4">
              Hemos enviado un correo con tu contraseña de acceso.  
              Por favor revisa tu bandeja de entrada y continúa con tu inicio de sesión.
            </p>
            <NavLink to="/login" className="btn btn-success btn-lg">
              Iniciar sesión ahora
            </NavLink>
          </>
        )}
      </div>
    </div>
  )
}