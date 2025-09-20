import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import editIcon from "../../../../public/icons/edit.svg" 
import deleteIcon from "../../../../public/icons/delete.svg"
import { Loading } from '../../../components/ui/Loading.jsx'
import { Table } from '../../../components/ui/Table.jsx'
import { Modal } from "../../../components/ui/Modal.jsx"
import DonationService from '../../../services/DonationService.js'

export const DonationTable = () => {
  const navigate = useNavigate()
  const [donation, setDonation] = useState([])
  const [donationSelected, setDonationSelected] = useState(null)
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState(null)
  
  useEffect(() => {
    const fetchDonation = async () => {
      try {
        const data = await DonationService.obtenerListadoDonaciones()
        console.log(data)
        setDonation(data.donaciones)
      } catch (err) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    fetchDonation()
  }, [])

  const handleEditDonation = (id) => {
    navigate(`/donation/edit/${id}`)
  }

  const handleDeleteDonationOnClick = (donation) => { 
    setDonationSelected(donation) 
    setShowModal(true) 
  }
  const handleConfirmDeleteDonation = async() => { 
    try {
      await DonationService.eliminarDonacion(donationSelected.id)
      donationSelected(null) // Se limpia la selección

      // Actualización optimista: cambio activo a false inmediatamente
      setDonation(prevDonation => prevDonation.map(u => u.id === donationSelected.id ? { ...u, activo: false } : u))
    } catch (err) { 
      console.error(err) 
      alert('Error al actualizar la donacion')
      setDonation(prevDonation) // Se revierte el cambio si falla
    } finally { 
      setShowModal(false) 
    } 
  }

  if (loading) return <Loading />
  if (error) return <p className="text-center mt-5">{error}</p>

  return (
    <div className="col-8 align-self-center mt-5">

      <Table
        columns={[
          { key: "categoria.descripcion", header: "Categoria", render: (_, row) => row.categoria?.descripcion ?? "-" },
          { key: "descripcion", header: "Descripcion" },
          { key: "cantidad", header: "Cantidad" },
          { key: "eliminado", header: "Eliminado", render: (val) => val ? "Existente" : "Eliminado" }
        ]}
        data={donation}
        actions={[
          { label: "Editar", icon: editIcon, onClick: (d) => handleEditDonation(d.id) },
          { label: "Eliminar", icon: deleteIcon, onClick: (d) => handleDeleteDonationOnClick(d), 
              hidden: (d) => !d.eliminado  //La donacion eliminada no tiene sentido que se le renderice el boton de eliminar
          },
        ]}
        emptyMessage="No hay donaciones disponibles"
      />

      {/* Popup para eliminar al donacion */} 
      <Modal
        show={showModal}
        title="Eliminar donacion"
        message={`¿Estás seguro que desea eliminar la donacion "${donationSelected?.descripcion}" ?`}
        onConfirm={handleConfirmDeleteDonation}
        onCancel={() => setShowModal(false)}
      />
    </div>
  )
}