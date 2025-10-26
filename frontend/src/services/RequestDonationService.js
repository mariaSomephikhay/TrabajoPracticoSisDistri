import { solicitudApi } from "../api/ApiSwagger.js";

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

  
  obtenerInformeDonaciones: async (Query) => {
    try {
      return await solicitudApi.informeSolicitudesDonaciones(Query)
    } catch (err) {
      console.error("Error al obtener informe:", err)
      throw err
    }
  }

}
 
export default RequestDonationService