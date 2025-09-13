import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext.jsx'

export const Home = () => {
  const { user, isAuthenticated } = useContext(AuthContext)

  return (
    <div className="container p-4">
      <div className="row">
        <div className="col-md-4 mx-auto">
          <div className="card text-center">
            <div className="card-body">
              <h3>Bienvenido{user?.fullname ? `, ${user.fullname}` : ''}</h3>
              {user?.rol?.descripcion && (
                <small className="text-muted">Rol: {user.rol.descripcion}</small>
              )}
              {isAuthenticated && <p className="text-success">Sesión activa</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
