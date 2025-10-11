import React from "react"
import { useNavigate } from "react-router-dom"

export const ExpiredSession = () => {
  const navigate = useNavigate()

  const handleLogin = () => {
    navigate("/login")
  }

  return (
    <div className="text-center mt-5">
      <h1 className="display-4 text-danger">Sesión expirada</h1>
      <p className="lead">Tu sesión ha caducado. Por favor, vuelve a iniciar sesión.</p>
      <button className="btn btn-primary" onClick={handleLogin}>
        Volver a iniciar sesión
      </button>
    </div>
  )
}
