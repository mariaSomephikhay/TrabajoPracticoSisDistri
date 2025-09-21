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
import { DonationUpdateForm } from "../pages/private/donation/DonationUpdateForm.jsx"
import { DonationNewForm } from "../pages/private/donation/DonationNewForm.jsx"
import { EventList } from '../pages/private/event/EventList.jsx';
import { EventUpdateForm } from '../pages/private/event/EventUpdateForm.jsx';
import { EventNew } from '../pages/private/event/EventNew.jsx';


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
                <Route path="/donation/edit/:id" element={<PrivateRoute allowedRoles={["PRESIDENTE", "VOCAL"]}><DonationUpdateForm /></PrivateRoute>} /> 
                <Route path="/donation/new" element={<PrivateRoute allowedRoles={["PRESIDENTE", "VOCAL"]}><DonationNewForm /></PrivateRoute>} /> 

                {/* RUTAS DEL EVENTO */}
                <Route path="/events" element={<PrivateRoute allowedRoles={["PRESIDENTE","COORDINADOR","VOLUNTARIO"]}> <EventList /></PrivateRoute>} />
                <Route path="/events/edit/:id" element={<PrivateRoute allowedRoles={["PRESIDENTE","COORDINADOR","VOLUNTARIO"]}><EventUpdateForm /></PrivateRoute>} /> 
                <Route path="/events/new/" element={<PrivateRoute allowedRoles={["PRESIDENTE","COORDINADOR"]}><EventNew /></PrivateRoute>} /> 

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
