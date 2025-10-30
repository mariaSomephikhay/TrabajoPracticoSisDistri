import { solicitudApi } from "../api/ApiSwagger.js";
import { solicitudApiExtended } from "../api/CustomApiSolicitud.js";

const RequestDonationService = {

  CrearSolicitudDonacion: async (newRequestDonation) => {
    try {
      return await solicitudApi.newRequestDonacion(newRequestDonation)
    } catch (err) {
      console.error("Error al crear solicitud de donacion:", err)
      throw err;
    }
  },

  bajaSolicitudDonacion: async (RequestDonation) => {
    try {
      return await solicitudApi.deleteRequestDonacion(RequestDonation)
    } catch (err) {
      console.error("Error al dar de baja la solicitud de donacion:", err)
      throw err;
    }
  },

  obtenerListadoSolicitudDonaciones: async () => {
    try {
      return await solicitudApi.getAllRequestDonacion()
    } catch (err) {
      console.error("Error al obtener donaciones:", err)
      throw err
    }
  },

  procesarSolicitudExterna: async (id, transferRequest) => {
    try {
      return await solicitudApi.newTransfer(id, transferRequest)
    } catch (err) {
      console.error("Error al procesar la solicitud externa", err)
      throw err
    }
  },

  crearOferta: async (nuevaOferta) => {
    try {
      return await solicitudApi.newOffer(nuevaOferta)
    } catch (err) {
      console.error("Error al crear nueva ofertas", err)
      throw err
    }
  },

  obtenerUltimaOferta: async () => {
    try {
      return await solicitudApi.getLastOfferCreated()
    } catch (err) {
      console.error("Error al obtener la ultima oferta creada", err)
      throw err
    }
  },

  obtenerListadoDeOfertas: async (idOrganization) => {
    try {
      return await solicitudApi.getAllOffersByOrganization(idOrganization)
    } catch (err) {
      console.error("Error al obtener ofertas de la organizacion:" + idOrganization, err)
      throw err
    }
  },

  obtenerInformeDonaciones: async (Query) => {
    try {
      return await solicitudApi.informeSolicitudesDonaciones(Query)
    } catch (err) {
      console.error("Error al obtener informe:", err)
      throw err
    }
  },

  obtenerInformeDonacionesExcel: async (token, body) => {
    try {
      const response = await fetch(`${import.meta.env.VITE_APP_API_URL}/solicitud/informe/excel`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(body)
      });
      const blob = await response.blob();
      if (!response.ok) {
        throw new Error("Error al generar el informe");
      }
      
      return blob;
    } catch (err) {
      console.error("Error al obtener informe:", err);
      throw err;
    }
  },

}
 
export default RequestDonationService

