import React, { useContext } from "react"
import { Navigate } from "react-router-dom"
import { AuthContext } from "../context/AuthContext.jsx"

/**
 * Protege rutas según autenticación y opcionalmente roles.
 * @param children Componentes que se quieren renderizar
 * @param allowedRoles Array opcional con roles permitidos
 */
export const PrivateRoute = ({ children, allowedRoles }) => {
  const { isAuthenticated, user } = useContext(AuthContext)

  if (!isAuthenticated) {
    // No autenticado → login
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(user?.rol.descripcion)) {
    // Usuario autenticado pero rol no permitido → home
    return <Navigate to="/" replace />
  }

  // Pasa todos los checks → renderiza hijos
  return children
};
