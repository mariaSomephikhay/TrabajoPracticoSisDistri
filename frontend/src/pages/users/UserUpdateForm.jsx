import React, { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import UserService from '../../services/UserService'

export const UserUpdateForm = () => {
  const { id } = useParams()
  const navigate = useNavigate() // Para redirigir después de modificar al usuario
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  // Roles hardcodeados (Se puede mejorar la manera)
  const roles = [
    { id: 1, nombre: 'PRESIDENTE' },
    { id: 2, nombre: 'VOLUNTARIO' },
    { id: 3, nombre: 'COORDINADOR' },
    { id: 4, nombre: 'VOCAL' }
  ]

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const data = await UserService.obtenerUsuario(id)
        setUser(data)
      } catch (err) {
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchUser()
  }, [id])

  /**
   * EVENTOS
   **/
  const handleChangeUser = (e) => { 
    const { name, value } = e.target

    // Si el campo que cambia es "rol"
    if (name === "rol") {
      setUser((prev) => ({
        ...prev,
        rol: {
          id: parseInt(value),
          descripcion: roles.find((r) => r.id === parseInt(value))?.nombre || ""
        }
      }))
    } else {
      setUser((prev) => ({
        ...prev,
        [name]: value
      }))
    }
  }
  const handleSubmitUser = async (e) => { 
    e.preventDefault() 
    try { 
      await UserService.modificarUsuario(id, user) 
      alert('Usuario actualizado correctamente') 
      navigate('/users') 
    } catch (err) { 
      console.error(err) 
      alert('Error al actualizar el usuario') 
    } 
  }

  //Estas vistas condicionales se podrian mejorar
  if (loading) return <p>Cargando...</p>
  if (!user) return <p>Usuario no encontrado</p>

  return (
    <form className="col-6 mx-auto mt-5" onSubmit={handleSubmitUser}>
      <div className="mb-3">
        <label>Username</label>
        <input className="form-control" type="text" name="username" value={user.username ?? ''} onChange={handleChangeUser}/>
      </div>
      <div className="mb-3">
        <label>Email</label>
        <input className="form-control" type="email" name="email" value={user.email ?? ''} onChange={handleChangeUser}
        />
      </div>
      <div className="mb-3">
        <label>Nombre</label>
        <input className="form-control" type="text" name="nombre" value={user.nombre ?? ''} onChange={handleChangeUser}/>
      </div>
      <div className="mb-3">
        <label>Apellido</label>
        <input className="form-control" type="text" name="apellido" value={user.apellido ?? ''} onChange={handleChangeUser}/>
      </div>
      <div className="mb-3">
        <label>Teléfono</label>
        <input className="form-control" type="tel" name="telefono" value={user.telefono ?? ''} onChange={handleChangeUser} />
      </div>

      <div className="mb-3">
        <label className="form-label">Rol</label>
        <select
          className="form-select"
          name="rol"
          value={user.rol?.id ?? ""}
          onChange={handleChangeUser}>
          <option value="">Seleccione un rol</option>
          {roles.map((rol) => (
            <option key={rol.id} value={rol.id}>
              {rol.nombre}
            </option>
          ))}
        </select>
      </div>

      <button type="submit" className="btn btn-primary">
        Guardar cambios
      </button>
    </form>
  )

}
