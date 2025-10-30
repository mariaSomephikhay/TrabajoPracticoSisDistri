import { soapApi } from "../api/ApiSwagger.js";

const SoapService = {

  obtenerDatosONGPorId: async (ongIds) => {
    try {
      return await soapApi.postOngsSoap(ongIds)
    } catch (err) {
      console.error("Error al obtener los datos de la ONG con los id pasados", err)
      throw err;
    }
  },

  obtenerPresidentesPrId: async (presidentIds) => {
    try {
      return await soapApi.postPresidentesSoap(presidentIds)
    } catch (err) {
      console.error("Error al obtener los persidentes con los id pasados", err)
      throw err;
    }
  },

}
 
export default SoapService

