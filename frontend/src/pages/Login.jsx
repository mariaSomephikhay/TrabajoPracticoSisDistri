import React, { useState, useContext } from "react"
import { useNavigate, NavLink } from "react-router-dom"
import { AuthContext } from "../context/AuthContext.jsx"
import { Title } from "../components/ui/Title.jsx"

export const Login = () => {
  const navigate = useNavigate()
  const { login } = useContext(AuthContext)
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [error, setError] = useState(null)

  const handleLoginUser = async (e) => {
    e.preventDefault()
    setError(null) // Reset error al intentar loguearse
    try {
      await login(username, password)
      navigate("/") // Redirige al home
    } catch (err) {
      setError("Usuario o contraseña incorrectos")
    }
  }

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ minWidth: "350px", borderRadius: "1rem" }}>
      <Title level="h2" text="Registrarse en el sistema" className="mb-4 text-center" />
        
        <form onSubmit={handleLoginUser}>
          <div className="mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Usuario"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <input
              type="password"
              className="form-control"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <div className="alert alert-danger">{error}</div>}
          <button type="submit" className="btn btn-primary w-100">
            Ingresar
          </button>
        </form>

        {/* Mensaje debajo */}
        <p className="text-center mt-3 mb-0">
          ¿No tienes una cuenta?{" "}
          <NavLink className="fw-bold text-decoration-none" to="/register">
            Regístrate
          </NavLink>
        </p>
      </div>
    </div>
  )
}