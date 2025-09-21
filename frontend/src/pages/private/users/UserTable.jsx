import React, { useEffect, useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import editIcon from "../../../../public/icons/edit.svg" 
import deleteIcon from "../../../../public/icons/delete.svg"
import addIcon from "../../../../public/icons/add.png" 
import { Loading } from '../../../components/ui/Loading.jsx'
import { Table } from '../../../components/ui/Table.jsx'
import { Modal } from "../../../components/ui/Modal.jsx"
import UserService from '../../../services/UserService.js'
import { AuthContext } from "../../../context/AuthContext.jsx" 
import { useNotification } from "../../../context/NotificationContext.jsx"

export const UserTable = () => {
  const navigate = useNavigate()
  const [users, setUsers] = useState([])
  const [userSelected, setUserSelected] = useState(null)
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState(null)
  const { userAuthenticated } = useContext(AuthContext)
  const { notification, clearNotification } = useNotification()
  
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await UserService.obtenerListadoUsuarios()
        
        setUsers(data.usuarios)
      } catch (err) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    fetchUsers()
  }, [])

  const handleEditUser = (id) => {
    navigate(`/users/edit/${id}`)
  }

  const handleDeleteUserOnClick = (user) => { 
    setUserSelected(user) 
    setShowModal(true) 
  }
  const handleConfirmDeleteUser = async() => { 
    try {
      await UserService.eliminarUsuario(userSelected.id)
      setUserSelected(null) // Se limpia la selección

      // Actualización optimista: cambio activo a false inmediatamente
      setUsers(prevUsers => prevUsers.map(u => u.id === userSelected.id ? { ...u, activo: false } : u))
    } catch (err) { 
      console.error(err) 
      alert('Error al actualizar el usuario')
      setUsers(prevUsers) // Se revierte el cambio si falla
    } finally { 
      setShowModal(false) 
    } 
  }

  if (loading) return <Loading />
  if (error) return <p className="text-center mt-5">{error}</p>

  return (
    <div className="container mt-4">
      
      {/* 🔹 Notificación local debajo del título */}
      {notification && (
        <div className={`alert alert-${notification.type} alert-dismissible fade show`} role="alert">
          {notification.message}
          <button type="button" className="btn-close" onClick={clearNotification}></button>
        </div>
      )}

      {/* 🔹 Título + Botón */}
      <div className="d-flex justify-content-between align-items-center mb-3">
        <button 
          className="btn btn-white btn-sm me-1"
          onClick={() => navigate("/users/new")}
        >
          <img src={addIcon} alt='Agregar' width={29} height={29}/>
        </button>
      </div>

      <Table
        columns={[
          { key: "rol.descripcion", header: "Rol", render: (_, row) => row.rol?.descripcion ?? "-" },
          { key: "username", header: "Username" },
          { key: "email", header: "Email" },
          { key: "nombre", header: "Nombre" },
          { key: "apellido", header: "Apellido" },
          { key: "telefono", header: "Teléfono", render: (val) => val || "-" },
          { key: "activo", header: "Estado", render: (val) => val ? "Activo" : "Inactivo" }
        ]}
        data={users}
        actions={[
          { label: "Editar", icon: editIcon, onClick: (u) => handleEditUser(u.id) },
          { label: "Eliminar", icon: deleteIcon, onClick: (u) => handleDeleteUserOnClick(u), 
              hidden: (u) => !u.activo || u.username === userAuthenticated.username //El usuario logueado y los usuarios inactivos no tiene sentido que se le renderice el boton de eliminar
          },
        ]}
        emptyMessage="No hay usuarios disponibles"
      />

      {/* Popup para eliminar al usuario */} 
      <Modal
        show={showModal}
        title="Deshabilitar usuario"
        message={`¿Estás seguro que deseas deshabilitar al usuario "${userSelected?.username}" ?`}
        onConfirm={handleConfirmDeleteUser}
        onCancel={() => setShowModal(false)}
      />
    </div>
  )
}