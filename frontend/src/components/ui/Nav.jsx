import React from 'react'
import { NavLink } from 'react-router-dom';

export const Nav = () => { 
  return ( 
    <nav className="navbar navbar-expand-md navbar-dark bg-primary">
      <div className="container-fluid">
        <NavLink className="navbar-brand" to="/">
          <img
            src="/img/home.png"
            height="30"
            alt="Home Icon"
            loading="lazy"
          />
        </NavLink>

        {/* Opciones del menú */}
        <div className="collapse navbar-collapse" id="navbarCollapse">
          <ul className="navbar-nav me-auto mb-2 mb-md-0">
            <li className="nav-item">
              <NavLink className="nav-link text-white" aria-current="page" to="/users">
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
          </ul>
        </div>
      </div>
    </nav>
  ) 
}
