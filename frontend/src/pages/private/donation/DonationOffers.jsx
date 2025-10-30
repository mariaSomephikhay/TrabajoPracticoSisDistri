import { useEffect, useState } from 'react'
import { Loading } from '../../../components/ui/Loading.jsx'
import { Table } from '../../../components/ui/Table.jsx'
import RequestDonationService from '../../../services/RequestDonationService.js'
import { useParams, NavLink, useNavigate } from 'react-router-dom'
import { Title } from '../../../components/ui/Title.jsx'

export const DonationOffers = () => {
  const navigate = useNavigate()
  const { id } = useParams()
  const [offers, setOffers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  
  useEffect(() => {
    const fetchOffers = async () => {
      try {
        const data = await RequestDonationService.obtenerListadoDeOfertas(id)
        console.log(data)
        setOffers(data.ofertas ?? [])
      } catch (err) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    fetchOffers()
  }, [id])


  const organizationName = offers.length > 0 
  ? offers[0].organizacionDonante?.nombre ?? ""
  : null

  if (loading) return <Loading />
  if (error) return <p className="text-center mt-5">{error}</p>
  if (offers.length === 0 && !loading) 
    return( 
    <div>
      <p className="text-center mt-5">La organizacion no tiene ofertas para mostrar.</p>
        <p className="text-center mt-3 mb-0">
          <NavLink className="fw-bold text-decoration-none" to="/donation-request">
            Volver
          </NavLink>
        </p>
   </div>)

  return (
    <div className="col-8 align-self-center mt-5">
    
        <Title
        level="h2"
        text={organizationName
            ? `Ofertas de la organización donante: ${organizationName}`
            : "No hay ofertas disponibles"}
        className="text-center my-5 fw-semibold text-primary border-bottom border-3 pb-3"
        />

        <div className="d-flex justify-content-between align-items-center mb-3">
          <button className="btn btn-primary btn-lg" onClick={() => navigate(`/donation/offers/new/${id}`)}>
            + Ofrecer donaciones
          </button>
        </div>

        <hr/>

        <Table
        className="table table-striped table-hover mt-4"
        columns={[
            { key: "id", header: "#Id oferta", render: (_, row) => row.id },
            { 
            key: "donaciones",
            header: "Donaciones",
            render: (_, row) => (
                <ul className="mb-0 ps-3">
                {row.donaciones?.map((d) => (
                    <li key={d.id}>
                    {d.descripcion} 
                    ({d.categoria?.descripcion ?? "-"}) — Cant: {d.cantidad}
                    </li>
                ))}
                </ul>
            )
            },
        ]}
        data={offers}
        />

        <p className="text-center mt-3 mb-0">
          <NavLink className="fw-bold text-decoration-none" to="/donation-request">
            Volver
          </NavLink>
        </p>

    </div>
  )
}