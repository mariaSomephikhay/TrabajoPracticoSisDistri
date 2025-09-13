import React, { useState, useContext } from "react"
import { useNavigate } from "react-router-dom"
import { AuthContext } from "../context/AuthContext.jsx"

export const Login = () => {
  const navigate = useNavigate();
  const { login } = useContext(AuthContext)
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [error, setError] = useState(null)

  const handleLoginUser = async (e) => {
    e.preventDefault()
    setError(null); // Reset error al intentar loguearse
    try {
      await login(username, password)
      navigate("/"); // Redirige al home
    } catch (err) {
      setError("Usuario o contraseña incorrectos")
    }
  }

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100 bg-light">
      <div className="card shadow-lg p-4" style={{ minWidth: "350px" }}>
        <h2 className="text-center mb-4">Iniciar Sesión</h2>
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
      </div>
    </div>
  )
}
