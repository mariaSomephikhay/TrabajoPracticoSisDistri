import React, { createContext, useContext, useState, useCallback, useRef } from "react"

const NotificationContext = createContext()

export const NotificationProvider = ({ children }) => {
  const [notification, setNotification] = useState(null)
  const timeoutRef = useRef(null)

  const showNotification = useCallback((message, type = "success", autoDismiss = true, duration = 6000) => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current) // Limpio timeout previo si ya existia uno
    }
    
    setNotification({ message, type, autoDismiss })

    if (autoDismiss) { //Valido si la notifiacion va a ser persistente o no
      timeoutRef.current = setTimeout(() => {
        setNotification(null)
      }, duration)
    }
  }, [])

  const clearNotification = useCallback(() => {
    if (timeoutRef.current) {
      clearTimeout(timeoutRef.current)
    }
    setNotification(null)
  }, [])

  return (
    <NotificationContext.Provider value={{ notification, showNotification, clearNotification }}>
      {children}
    </NotificationContext.Provider>
  )
}

export const useNotification = () => useContext(NotificationContext)