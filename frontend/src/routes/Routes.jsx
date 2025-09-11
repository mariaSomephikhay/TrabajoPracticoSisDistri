// OperatorRoutes.jsx
import React from 'react'
import { Routes, Route } from "react-router-dom"
import { Header } from "../components/Header.jsx"
import { Footer } from "../components/Footer.jsx"
import { Nav } from "../components/ui/Nav.jsx"
import { Home } from "../pages/Home.jsx"
import { UserTable } from "../pages/users/UserTable.jsx"
import { UserUpdateForm } from "../pages/users/UserUpdateForm.jsx"

export const OperatorRoutes = () => {
    return (
        <>
            <Header />
            <Nav />

            <main className="container pt-4">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/users" element={<UserTable />} />
                    <Route path="/users/edit/:id" element={<UserUpdateForm />} />
                </Routes>
            </main>

            <Footer />
        </>
    )
}
