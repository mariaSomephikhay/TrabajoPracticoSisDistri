import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import editIcon from "../../../../public/icons/edit.svg" 
import deleteIcon from "../../../../public/icons/delete.svg"
import { Loading } from '../../../components/ui/Loading.jsx'
import { Table } from '../../../components/ui/Table.jsx'
import { Modal } from "../../../components/ui/Modal.jsx"
import RequestDonationService from '../../../services/RequestDonationService.js';

export const RequestDonationInventory = () => {
  const navigate = useNavigate()
  const [solicitud, setSolicitud] = useState([])
  const [solicitudSelected, setSolicitudSelected] = useState(null)
  const [loading, setLoading] = useState(true)
  const [showModal, setShowModal] = useState(false);
  const [error, setError] = useState(null)
  const [bajaSolicitud, setBajaSolicitud] = useState({
      id_solicitud_donacion: '',
      id_organizacion_solicitante: ''
      });
  
  useEffect(() => {
    const fetchDonation = async () => {
      try {
        const data = await RequestDonationService.obtenerListadoSolicitudDonaciones()
        
        setSolicitud(data.solicitudes)
        //console.log(data) 
      } catch (err) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    fetchDonation()
  }, [])

/*  const handleDeleteDonationOnClick = (solicitud) => { 
    setSolicitudSelected(solicitud) 
    setShowModal(true) 
  }*/

const handleDeleteDonationOnClick = (solicitud) => {
  // Guardamos la solicitud completa (por si la necesitás para mostrar info)
  setSolicitudSelected(solicitud)
  
  // Armamos el objeto que se enviará para la baja
  setBajaSolicitud({
    id_solicitud_donacion: solicitud.id, 
    id_organizacion_solicitante: solicitud.organizacionSolicitante?.id ?? ''
  })
  
  setShowModal(true)
}
  const handleConfirmDeleteDonation = async() => { 
    try {
      //baja producir en kafka
      const data = await RequestDonationService.bajaSolicitudDonacion(bajaSolicitud)
      setSolicitudSelected(null) // Se limpia la selección

      // Actualización optimista: cambio eliminado a true inmediatamente
      setSolicitud(prevRequest => prevRequest.map(d => d.id === solicitudSelected.id ? { ...d, activo: false } : d))
    } catch (err) { 
      console.error(err) 
      alert('Error al bajar la solicitud donacion')
      setSolicitud(prevRequest) // Se revierte el cambio si falla
    } finally { 
      setShowModal(false) 
    } 
  }

  if (loading) return <Loading />

  return (
    <div className="col-8 align-self-center mt-5">

      <div className="d-grid">
        <button className="btn btn-primary btn-lg" onClick={() => navigate("/donation/request/new")}>
          + Agregar Solicitud
        </button>
      </div>

      <Table
        columns={[
          { key: "id", header: "Identificador de solicitud" },
          { key: "organizacionSolicitante", header: "Organización", render: (_, row) => row.organizacionSolicitante?.nombre ?? "-"},
          {
            key: "donaciones",
            header: "Donaciones Asociadas",
            render: (_, row) => (
              <ul className="mb-0">
                {row.donaciones?.map((d) => (
                  <li key={d.id}>
                    {d.descripcion} ({d.categoria?.descripcion ?? "-"}) — Cant: {d.cantidad}
                  </li>
                )) ?? <i>Sin donaciones</i>}
              </ul>
            )
          },
          { key: "activa", header: "Activo", render: (_, row) => row.activa ? "SI" : "NO" }
        ]}
        data={solicitud}
        actions={[
          { label: "Eliminar", icon: deleteIcon, onClick: (d) => handleDeleteDonationOnClick(d), 
              hidden: (d) => !d.activa || d.organizacionSolicitante.id !== 1  //La donacion eliminada no tiene sentido que se le renderice el boton de eliminar
          },
        ]}
        emptyMessage="No hay donaciones disponibles"
      />

      {/* Popup para eliminar al donacion */} 
      <Modal
        show={showModal}
        title="Eliminar donacion"
        message={`¿Estás seguro que desea eliminar la donacion "${solicitudSelected?.id}" ?`}
        onConfirm={handleConfirmDeleteDonation}
        onCancel={() => setShowModal(false)}
      />
      
    </div>
  )
}