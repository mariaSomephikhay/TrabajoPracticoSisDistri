import React from "react";

export const NotFound = () => {
  return (
    <div className="text-center mt-5">
      <h1 className="display-3">404</h1>
      <p className="lead">Oops! La página que buscas no existe.</p>
      <a href="/" className="btn btn-primary">Volver al inicio</a>
    </div>
  )
}
