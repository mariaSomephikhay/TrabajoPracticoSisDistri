import { donacionesApi } from "../api/ApiSwagger.js"

const DonationService = {

  CrearDonacion: async (newDonation) => {
    try {
      return await donacionesApi.createDonation(newDonation)
    } catch (err) {
      console.error("Error al crear donacion:", err)
      throw err;
    }
  },

  obtenerListadoDonaciones: async () => {
    try {
      return await donacionesApi.listDonations()
    } catch (err) {
      console.error("Error al obtener donaciones:", err)
      throw err
    }
  },

  modificarDonacion: async (donacionId, donacion) => {
    try {
      return await donacionesApi.updateDonationById(donacionId, donacion)
    } catch (err) {
      console.error("Error al modificar donacion:", err)
      throw err
    }
  },

  obtenerDonacion: async (donacionId) => {
    try {
      return await donacionesApi.getDonationById(donacionId)
    } catch (err) {
      console.error("Error al obtener donacion:", err)
      throw err
    }
  },

  eliminarDonacion: async (donacionId) => {
    try {
      return await donacionesApi.deleteDonationById(donacionId)
    } catch (err) {
      console.error("Error al eliminar donacion:", err)
      throw err
    }
  },
}

export default DonationService
