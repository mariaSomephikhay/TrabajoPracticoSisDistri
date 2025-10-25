import { filtrosApi } from "../api/ApiSwagger.js";

const FilterService = {

    filterSaveEvent: async (idUsuario) => {
        try {
            const response = await filtrosApi.getFilter("evento", idUsuario);
            return response; 
        } catch (err) {
            console.error("Error al obtener filtros de evento:", err);
            throw err;
        }
    },

    deleteFilter: async (filtroId) => {
        try {
            const response = await filtrosApi.deleteFilter(filtroId);
            return response; 
        } catch (err) {
            console.error("Error al eliminar el filtro:", err);
            throw err;
        }
    },

    saveFilter: async (newFiltro) => {
        try {
            const response = await filtrosApi.createFilter(newFiltro);
            return response; 
        } catch (err) {
            console.error("Error al obtener filtros de evento:", err);
            throw err;
        }
    },

}

export default FilterService