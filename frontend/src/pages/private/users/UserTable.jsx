import React, { useEffect, useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import editIcon from "../../../../public/icons/edit.svg" 
import deleteIcon from "../../../../public/icons/delete.svg"
import { Loading } from '../../../components/ui/Loading.jsx'
import { Modal } from "../../../components/ui/Modal.jsx"
import UserService from '../../../services/UserService.js'

export const UserTable = () => {
  const navigate = useNavigate()
  const [users, setUsers] = useState([])
  const [userSelected, setUserSelected] = useState(null)
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState(null)
  const { userAuthenticated } = useContext(AuthContext)
  
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
      await UserService.eliminarUsuario(id) 
    } catch (err) { 
      console.error(err) 
      alert('Error al actualizar el usuario') 
    } finally { 
      setShowModal(false) 
    } 
  }

  if (loading) return <Loading />
  if (error) return <p className="text-center mt-5">{error}</p>
  if (!users || users.length === 0) return <p className="text-center mt-5">No hay usuarios disponibles</p>

  return (
    <div className="col-8 align-self-center mt-5">
      <table className="table" align="center">
        <thead>
          <tr>
            <th scope="col">Acciones</th>
            <th scope="col">Rol</th>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Nombre</th>
            <th scope="col">Apellido</th>
            <th scope="col">Teléfono</th>
            <th scope="col">Estado</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user, index) => (
            <tr key={user.id || index}>
              <td>
                <button className="btn btn-white btn-sm me-1" onClick={() => handleEditUser(user.id)}> 
                  <img src={editIcon} alt="Editar" width={20} height={20} /> 
                </button> 
                {user.activo && user.username !== userAuthenticated.username ? (
                  <button className="btn btn-white btn-sm me-1" onClick={() => handleDeleteUserOnClick(user)}> 
                    <img src={deleteIcon} alt="Eliminar" width={20} height={20} /> 
                  </button>
                ): (<></>)}
              </td>
              <td>{user.rol?.descripcion ?? '-'}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.nombre}</td>
              <td>{user.apellido}</td>
              <td>{user.telefono || '-'}</td>
              <td>{user.activo ? 'Activo' : 'Inactivo'}</td>
            </tr>
          ))}
        </tbody>
      </table>
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