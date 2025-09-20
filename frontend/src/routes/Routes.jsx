import React from 'react'
import { Routes, Route } from "react-router-dom"
import { PrivateRoute } from './PrivateRoutes.jsx'
import { Header } from "../components/Header.jsx"
import { Footer } from "../components/Footer.jsx"
import { Nav } from "../components/ui/Nav.jsx"
import { NotFound } from "../pages/NotFound.jsx"
import { ExpiredSession } from "../pages/ExpiredSession.jsx"
import { Login } from "../pages/Login.jsx"
import { Register } from '../pages/Register.jsx'
import { Home } from "../pages/private/Home.jsx"
import { UserTable } from "../pages/private/users/UserTable.jsx"
import { DonationTable } from "../pages/private/donation/DonationInventory.jsx"
import { UserUpdateForm } from "../pages/private/users/UserUpdateForm.jsx"

export const OperatorRoutes = () => { 
    return ( 
    <> 
        <Header /> 
        <Nav />

        <main className="container pt-4"> 
            <Routes> 
                <Route path="/login" element={<Login />} /> 
                <Route path='/register' element={<Register/ >} /> 
                
                {/* Ruta privada para todos los usuarios autenticados */} 
                <Route path="/" element={<PrivateRoute><Home /></PrivateRoute>} /> 
                
                {/* Rutas privadas solo para PRESIDENTE */} 
                <Route path="/users" element={<PrivateRoute allowedRoles={["PRESIDENTE"]}> <UserTable /></PrivateRoute>} /> 
                <Route path="/users/edit/:id" element={<PrivateRoute allowedRoles={["PRESIDENTE"]}><UserUpdateForm /></PrivateRoute>} /> 

                {/* Rutas privadas solo para PRESIDENTE y VOCAL*/}  
                <Route path="/donation-inventory" element={<PrivateRoute allowedRoles={["PRESIDENTE", "VOCAL"]}> <DonationTable /></PrivateRoute>} /> 

                {/* Ruta catch-all para 404 */} 
                <Route path="*" element={<NotFound />} />

                {/* Ruta para cuando expira la sesion del usuario */} 
                <Route path="/session-expired" element={<ExpiredSession />} /> 
            </Routes> 
        </main>

        <Footer />
    </> 
    ) 
}
