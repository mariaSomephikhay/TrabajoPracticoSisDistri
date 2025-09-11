import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import UserService from '../../services/UserService'

export const UserTable = () => {
  const navigate = useNavigate()
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await UserService.obtenerListadoUsuarios()
        setUsers(data.usuarios)
      } catch (err) {
        console.error(err)
        setError(err)
      }
      finally {
        setLoading(false)
      }
    };

    fetchUsers();
  }, []);


  const handleEditUser = (id) => {
    navigate(`/users/edit/${id}`) // redirige con el id del usuario
  }

  //Estas vistas condicionales se podrian mejorar
  if (loading) return <p className="text-center mt-5">Cargando...</p>
  if (error) return <p className="text-center mt-5"> {error} </p>
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
                <button 
                  className="btn btn-primary btn-sm me-1"
                  onClick={() => handleEditUser(user.id)}>
                  Modificar
                </button>
                {/** Aca se podria agregar el boton para deshabilitar un usuario */}
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
    </div>
  )
}
