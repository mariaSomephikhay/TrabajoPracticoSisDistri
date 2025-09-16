import React, { createContext, useState, useEffect } from "react"
import { jwtDecode } from "jwt-decode"
import { useNavigate } from "react-router-dom"
import { userApi, setAuthToken as setApiAuthToken } from "../api/ApiSwagger.js" 

export const AuthContext = createContext()

export const AuthProvider = ({ children }) => { 
  const navigate = useNavigate() 
  const [authToken, setAuthTokenState] = useState(() => localStorage.getItem("authToken") || null ) 
  const [userAuthenticated, setUserAuthenticated] = useState(null)
  const [isTokenExpired, setIsTokenExpired] = useState(false) // Actualiza token en localStorage y en ApiClient 
   
  useEffect(() => { 
    if (authToken) { 
      try { const decoded = jwtDecode(authToken) // Verifica si el token expiró 
        const now = Date.now() / 1000 // Segundos 
        if (decoded.exp < now) { 
          setAuthTokenState(null) 
          setUserAuthenticated(null) 
          setApiAuthToken(null) 
          setIsTokenExpired(true) 
          navigate("/session-expired", { replace: true }) 
          return 
        } 
        // Token válido: guardar info del usuario y token en ApiClient 
        setUserAuthenticated({ username: decoded.username, fullname: decoded.fullname, rol: decoded.rol, }) 
        localStorage.setItem("authToken", authToken) 
        setApiAuthToken(authToken) 
      } catch (err) { 
        console.error("Error decodificando JWT:", err) 
        setAuthTokenState(null) 
        setUserAuthenticated(null) 
        setApiAuthToken(null) 
        navigate("/login", { replace: true }) 
      } 
    } else { 
      // No hay token 
      localStorage.removeItem("authToken") 
      setUserAuthenticated(null) 
      setApiAuthToken(null) 
    } 
  }, [authToken, navigate]) 
    
  const login = async (username, password) => { 
    try { 
      const payload = { username, password } 
      const tokenResponse = await userApi.loginUser(payload) 
      const token = tokenResponse.token 
      setAuthTokenState(token) 
      setIsTokenExpired(false) 
      navigate("/") // Redirige a home 
    } catch (err) { 
      console.error("Error en login:", err) 
      throw err 
    } 
  }
    
  const logout = () => { 
    setAuthTokenState(null) 
    setIsTokenExpired(false) 
    navigate("/login") 
  }

  return ( 
    <AuthContext.Provider value={
        { 
          authToken, 
          userAuthenticated, 
          login, 
          logout, 
          isAuthenticated: !!authToken, 
          isTokenExpired, 
        }
      } > {children} 
    </AuthContext.Provider> 
  )
}