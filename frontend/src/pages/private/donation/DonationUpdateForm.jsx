import React, { useEffect, useState, useContext } from 'react'
import { useParams, useNavigate, NavLink } from 'react-router-dom'
import { Loading } from '../../../components/ui/Loading.jsx'
import DonationService from '../../../services/DonationService.js'

export const DonationUpdateForm = () => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [donation, setDonation] = useState([])
  const [loading, setLoading] = useState(true)

  const categorias = [
    { id: 1, descripcion: 'ALIMENTO' },
    { id: 2, descripcion: 'JUGUETE' },
    { id: 3, descripcion: 'ROPA' },
    { id: 4, descripcion: 'UTIL_ESCOLAR' }
  ]


  useEffect(() => {
    const fetchDonation = async () => {
      try {
        const data = await DonationService.obtenerDonacion(id)
        setDonation(data)
      } catch (err) {
        console.error(err)
      } finally {
        setLoading(false)
      }
    }
    fetchDonation()
  }, [id])

  const handleChangeDonation = (e) => { 
    const { name, value } = e.target
    setDonation((prev) => ({ ...prev, [name]: value }))
  }

  const handleUpdateDonation = async (e) => { 
    e.preventDefault() 
    try { 
      await DonationService.modificarDonacion(id, donation) 
      alert('Donacion actualizada correctamente') 
      navigate('/donation-inventory') 
    } catch (err) { 
      console.error(err) 
      alert('Error al actualizar la donacion') 
    } 
  }

  if (loading) return <Loading />
  if (!donation) return <p>Donacion no encontrado</p>

  return (
    <form className="col-6 mx-auto mt-5" onSubmit={handleUpdateDonation}>

      <div className="mb-3">
        <label className="form-label">Categoria</label>
        <select
          className="form-select"
          name="categoria"
          value={donation.categoria?.id ?? ""}
          disabled>
          <option value="">Categoria seleccionada</option>
          {categorias.map((categoria) => (
            <option key={categoria.id} value={categoria.id}>
              {categoria.descripcion}
            </option>
          ))}
        </select>
      </div>

      <div className="mb-3">
        <label>Descripcion</label>
        <input className="form-control" type="text" name="descripcion" value={donation.descripcion ?? ''} onChange={handleChangeDonation}/>
      </div>
      <div className="mb-3">
        <label>Cantidad</label>
        <input className="form-control" type="int" name="cantidad" value={donation.cantidad ?? ''} onChange={handleChangeDonation}
        />
      </div>

      <button type="submit" className="btn btn-primary">
        Guardar cambios
      </button>
      
      <p className="text-center mt-3 mb-0">
        <NavLink className="fw-bold text-decoration-none" to="/donation-inventory">
          Cancelar
        </NavLink>
      </p>
      
    </form>
  )
}