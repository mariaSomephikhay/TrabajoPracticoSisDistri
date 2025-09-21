import React, { useState } from "react"
import { NavLink } from "react-router-dom"
import { Loading } from '../../../components/ui/Loading.jsx'
import UserService from "../../../services/UserService.js"
import { useNotification } from "../../../context/NotificationContext.jsx"
import { useNavigate } from "react-router-dom"

export const UserNewForm = () => {
  const navigate = useNavigate()
  const { showNotification, notification, clearNotification } = useNotification()
  const [loading, setLoading] = useState(false)
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
    setLoading(true)
    try {
        await UserService.registrarUsuario(newUser)
        setLoading(false)
        showNotification(
        <>
            <strong>¡Usuario registrado exitosamente!</strong>
            <br />
            Hemos enviado al correo la contraseña de acceso.
            <br />
            El nuevo usuario tendrá que revisar su bandeja de entrada para poder continuar con su inicio de sesión.
        </>,
        "success",
        false)
        navigate("/users")
    } catch (err) { 
        setLoading(false)
        console.error(err) 
        showNotification(
        <><strong>Error al registrar el nuevo usuario</strong></>,
        "danger",
        false)
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

  if (loading) return <Loading />

  return (
    <div className="position-relative">
        {/* Notificación */}
        {notification && (
            <div
            className={`alert alert-${notification.type} alert-dismissible fade show position-absolute top-0 start-50 translate-middle-x mt-3`}
            role="alert"
            style={{ zIndex: 1050 }}
            >
            {notification.message}
            <button type="button" className="btn-close" onClick={clearNotification}></button>
            </div>
        )}

        {/* Formulario */}
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
                <h2 className="mb-4 fw-bold text-primary">Crear usuario</h2>
                <p className="text-muted mb-4">
                    Completa los campos para dar de alta un nuevo usuario.
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
                        Guardar
                    </button>
                    </div>
                </form>

                <p className="text-center mt-3 mb-0">
                    <NavLink className="fw-bold text-decoration-none" to="/users" onClick={clearNotification}>
                    Cancelar
                    </NavLink>
                </p>
            </div>
        </div>

    </div>

  )
}