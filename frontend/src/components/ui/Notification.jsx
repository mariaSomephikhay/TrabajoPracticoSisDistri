import React from "react"

export const Notification = ({ notification, onClose }) => {
  if (!notification) return null

  return (
    <div 
      className={`alert alert-${notification.type} alert-dismissible fade show position-fixed top-0 start-50 translate-middle-x mt-3`} 
      role="alert"
      style={{ zIndex: 1050, minWidth: "300px" }}
    >
      {notification.message}
      <button 
        type="button" 
        className="btn-close" 
        aria-label="Close"
        onClick={onClose}
      ></button>
    </div>
  )
}