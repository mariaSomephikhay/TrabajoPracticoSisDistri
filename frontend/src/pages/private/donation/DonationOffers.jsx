import { useEffect, useState } from 'react'
import { Loading } from '../../../components/ui/Loading.jsx'
import { Table } from '../../../components/ui/Table.jsx'
import RequestDonationService from '../../../services/RequestDonationService.js'
import { useParams } from 'react-router-dom'
import { Title } from '../../../components/ui/Title.jsx'

export const DonationOffers = () => {
  const { id } = useParams()
  const [offers, setOffers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  
  useEffect(() => {
    const fetchOffers = async () => {
      try {
        const data = await RequestDonationService.obtenerListadoDeOfertas(id)
        console.log(data)
        setOffers(data.ofertas)
      } catch (err) {
        console.error(err)
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }
    fetchOffers()
  }, [id])


  if (loading) return <Loading />
  if (error) return <p className="text-center mt-5">{error}</p>

  return (
    <div className="col-8 align-self-center mt-5">
    
        <Title 
            level="h2" 
            text="Listado de ofertas de donaciones" 
            className="text-center my-4 fw-bold text-primary border-bottom pb-2"
        />

        <Table
        columns={[
            { key: "id", header: "#", render: (_, row) => row.id},
            { key: "organizacion", header: "Organizacion donante", render: (_, row) => row.organizacionDonante?.nombre ?? "-"},
            { key: "donaciones",
            header: "Donaciones",
            render: (_, row) => (
                <ul className="mb-0">
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
        emptyMessage="No hay ofertas para visualizar"
        />

    </div>
  )
}