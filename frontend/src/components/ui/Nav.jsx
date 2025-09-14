import React, { useContext } from "react"
import { NavLink } from "react-router-dom"
import { AuthContext } from "../../context/AuthContext.jsx"

export const Nav = () => {
  const { authToken, user, logout } = useContext(AuthContext)
  const isAuthenticated = !!authToken

  return (
    <nav className="navbar navbar-expand-md navbar-dark bg-primary">
      <div className="container-fluid">
        {isAuthenticated ? (
          <NavLink className="navbar-brand" to="/">
            <img src="/img/home.png" height="30" alt="Home Icon" />
          </NavLink>
        ) : (
          <NavLink className="navbar-brand" to="/login">
            <img src="/img/home.png" height="30" alt="Home Icon" />
          </NavLink>        
          )}


        <div className="collapse navbar-collapse" id="navbarCollapse">
          <ul className="navbar-nav me-auto mb-2 mb-md-0">
            {isAuthenticated && user?.rol.descripcion === "PRESIDENTE" && (
              <>
                <li className="nav-item">
                  <NavLink className="nav-link text-white" to="/users">
                    Gestión de Usuarios
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className="nav-link text-white" to="#">
                    Inventario de donaciones
                  </NavLink>
                </li>
                <li className="nav-item">
                  <NavLink className="nav-link text-white" to="#">
                    Eventos solidarios
                  </NavLink>
                </li>
              </>
            )}
          </ul>

          <ul className="navbar-nav ms-auto mb-2 mb-md-0">
            {!isAuthenticated ? (
              <li className="nav-item">
                <NavLink className="nav-link text-white" to="/login">
                  Iniciar Sesión
                </NavLink>
              </li>
            ) : (
              <li className="nav-item">
                <button className="btn btn-outline-light" onClick={logout}>
                  Cerrar Sesión
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  )
}