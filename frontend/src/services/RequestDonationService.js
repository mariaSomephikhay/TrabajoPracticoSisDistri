import { solicitudApi } from "../api/ApiSwagger.js";

const RequestDonationService = {

  CrearSolicitudDonacion: async (newRequestDonation) => {
    try {
      return await solicitudApi.newRequestDonacion(newRequestDonation)
    } catch (err) {
      console.error("Error al crear solicitud de donacion:", err)
      throw err;
    }
  }

}
 
export default RequestDonationService