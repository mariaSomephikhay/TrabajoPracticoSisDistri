import React, { useContext } from "react"
import { Navigate } from "react-router-dom"
import { AuthContext } from "../context/AuthContext.jsx"

/**
 * Protege rutas según autenticación y opcionalmente roles.
 * @param children Componentes que se quieren renderizar
 * @param allowedRoles Array opcional con roles permitidos
 */
export const PrivateRoute = ({ children, allowedRoles }) => {
  const { isAuthenticated, userAuthenticated, isTokenExpired } = useContext(AuthContext);

  if (isTokenExpired) {
    // Token expirado → forzar ir a la página de sesión expirada
    return <Navigate to="/session-expired" replace />
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />
  }

  if (allowedRoles && !allowedRoles.includes(userAuthenticated?.rol.descripcion)) {
    return <Navigate to="/" replace />
  }

  return children
}
