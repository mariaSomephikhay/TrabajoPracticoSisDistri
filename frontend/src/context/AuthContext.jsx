import React, { createContext, useState, useEffect } from "react"
import { jwtDecode } from "jwt-decode"
import { useNavigate } from "react-router-dom"
import { userApi, setAuthToken as setApiAuthToken } from "../api/ApiSwagger.js"

export const AuthContext = createContext()

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate()

  const [authToken, setAuthTokenState] = useState(() =>
    localStorage.getItem("authToken") || null
  )
  const [user, setUser] = useState(null)

  // Actualiza token en localStorage y en ApiClient
  useEffect(() => {
    if (authToken) {
      localStorage.setItem("authToken", authToken)
      setApiAuthToken(authToken) // Actualiza token en CustomApiClient

      // Decodificar JWT para obtener info del usuario
      try {
        const decoded = jwtDecode(authToken)
        setUser({
          username: decoded.username,
          fullname: decoded.fullname,
          rol: decoded.rol
        });
      } catch (err) {
        console.error("Error decodificando JWT:", err)
        setUser(null)
      }
    } else {
      localStorage.removeItem("authToken")
      setUser(null)
      setApiAuthToken(null)
    }
  }, [authToken])

  const login = async (username, password) => {
    try {
      const payload = { username, password }
      const tokenResponse = await userApi.postUserLogin(payload)
      const token = tokenResponse.token

      setAuthTokenState(token);
      navigate("/") // Redirige a home
    } catch (err) {
      console.error("Error en login:", err)
      throw err
    }
  }

  const logout = () => {
    setAuthTokenState(null)
    navigate("/login")
  }

  return (
    <AuthContext.Provider
      value={{
        authToken,
        user,
        login,
        logout,
        isAuthenticated: !!authToken,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}