import React, { useEffect, useState } from "react";
import { NavLink, useNavigate, useParams } from "react-router-dom";
import DonationService from "../../../services/DonationService.js";
import RequestDonationService from "../../../services/RequestDonationService.js";
import { Loading } from "../../../components/ui/Loading.jsx";

export const DonationNewOfferForm = () => {
  const { id } = useParams(); // id de la organización
  const navigate = useNavigate();

  const [donations, setDonations] = useState([]);
  const [lastOffer, setLastOffer] = useState(null);
  const [selectedDonations, setSelectedDonations] = useState([]);
  const [loading, setLoading] = useState(true);

  // Cargo donaciones y última oferta en paralelo
    useEffect(() => {
    const fetchData = async () => {
        try {
        setLoading(true);
        const [donationsData, lastOfferData] = await Promise.all([
            DonationService.obtenerDonacionesSinOfertasPorOrganizacion(id),
            RequestDonationService.obtenerUltimaOferta(),
        ])

        // Asegurarse de que sea un array siempre
        const listaDonaciones = Array.isArray(donationsData?.donaciones)
            ? donationsData.donaciones
            : Array.isArray(donationsData) ? donationsData: []

        setDonations(listaDonaciones)
        setLastOffer(lastOfferData || null)
        } catch (error) {
        console.error("Error cargando datos:", error)
        setDonations([]); // fallback seguro
        } finally {
        setLoading(false);
        }
    };

    fetchData()
    }, [id])


  // Manejar selección de donaciones
  const handleSelectDonation = (donacionId) => {
    setSelectedDonations((prev) =>
      prev.includes(donacionId)
        ? prev.filter((id) => id !== donacionId)
        : [...prev, donacionId]
    )
  }

  // Crear oferta
  const handleCreateOffer = async () => {
    if (selectedDonations.length === 0) {
      alert("Debes seleccionar al menos una donación para crear la oferta.");
      return
    }

    try {
      setLoading(true)

      const offerRequest = {
        id_oferta: (Number(lastOffer?.id) || 0) + 1,
        id_organizacion_donante: id,
        donaciones: selectedDonations.map((donacionId) => ({ id: donacionId })),
      };

      console.log("Creando oferta:", offerRequest);
      await RequestDonationService.crearOferta(offerRequest);

      alert("Oferta creada exitosamente");
      navigate(`/donation/offers/${id}`);
    } catch (error) {
      console.error("Error al crear la oferta:", error);
      alert("Ocurrió un error al crear la oferta.");
    } finally {
      setLoading(false)
    }
  };

  if (loading) return <Loading />

  return (
    <div style={{ maxHeight: "80vh", overflowY: "auto" }}>
      <form className="col-8 mx-auto mt-5" onSubmit={(e) => e.preventDefault()}>
        <h3 className="text-center mb-4">Crear nueva oferta</h3>

        {donations.length === 0 ? (
          <div className="text-center mt-5">
            <div
              className="alert alert-warning"
              style={{ maxWidth: "600px", margin: "0 auto" }}
            >
              No hay donaciones disponibles para crear una oferta en este momento.
            </div>

            <div className="d-flex justify-content-center mt-4">
              <NavLink className="btn btn-secondary" to={`/donation/offers/${id}`}>
                Volver
              </NavLink>
            </div>
          </div>
        ) : (
          <>
            <div className="mb-3 mt-4">
              <label className="form-label fw-bold">
                Donaciones disponibles para incluir en la oferta
              </label>

              <div className="table-responsive">
                <table className="table table-bordered table-striped mt-2">
                  <thead className="table-primary">
                    <tr>
                      <th style={{ width: "10%" }}>Seleccionar</th>
                      <th style={{ width: "30%" }}>Categoría</th>
                      <th style={{ width: "40%" }}>Descripción</th>
                      <th style={{ width: "20%" }}>Cantidad</th>
                    </tr>
                  </thead>
                  <tbody>
                    {donations.map((item) => (
                      <tr key={item.id}>
                        <td className="text-center">
                          <input
                            type="checkbox"
                            checked={selectedDonations.includes(item.id)}
                            onChange={() => handleSelectDonation(item.id)}
                          />
                        </td>
                        <td>{item.categoria?.descripcion || "Sin categoría"}</td>
                        <td>{item.descripcion}</td>
                        <td className="text-center">{item.cantidad}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>

            <div className="d-flex justify-content-center mt-4 gap-3">
              <button
                type="button"
                className="btn btn-success"
                onClick={handleCreateOffer}
                disabled={selectedDonations.length === 0}
              >
                Crear oferta
              </button>

              <NavLink className="btn btn-secondary" to={`/donation/offers/${id}`}>
                Cancelar
              </NavLink>
            </div>
          </>
        )}
      </form>
    </div>
  )
  
}