import React from "react";

export const Modal = ({ 
  show, 
  title = "Confirmar acción", 
  message, 
  onConfirm, 
  onCancel 
}) => {
  if (!show) return null

  return (
    <div className="modal fade show d-block" tabIndex="-1" role="dialog">
      <div className="modal-dialog modal-dialog-centered" role="document">
        <div className="modal-content shadow-lg rounded-3">
          <div className="modal-header bg-light">
            <h5 className="modal-title">{title}</h5>
            <button 
              type="button" 
              className="btn-close" 
              aria-label="Close" 
              onClick={onCancel}
            ></button>
          </div>
          <div className="modal-body">
            <p>{message}</p>
          </div>
          <div className="modal-footer">
            <button 
              type="button" 
              className="btn btn-secondary" 
              onClick={onCancel}
            >
              Cancelar
            </button>
            <button 
              type="button" 
              className="btn btn-danger" 
              onClick={onConfirm}
            >
              Confirmar
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}