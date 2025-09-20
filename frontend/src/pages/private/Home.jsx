import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext.jsx'

export const Home = () => {
  const { userAuthenticated, isAuthenticated } = useContext(AuthContext)

  return (
    <div className="container p-4">
      <div className="row">
        <div className="col-md-4 mx-auto">
          <div className="card text-center">
            <div className="card-body">
              <h3>Bienvenido{userAuthenticated?.fullname ? `, ${userAuthenticated.fullname}` : ''}</h3>
              {userAuthenticated?.rol?.descripcion && (
                <small className="text-muted">Rol: {userAuthenticated.rol.descripcion}</small>
              )}
              {isAuthenticated && <p className="text-success">Sesión activa</p>}
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
