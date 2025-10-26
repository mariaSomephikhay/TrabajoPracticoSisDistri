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

    updateFilter: async (filtroId,updateFiltro) => {
        try {
            const response = await filtrosApi.updateFilter(filtroId,updateFiltro);
            return response; 
        } catch (err) {
            console.error("Error al actualizar el filtro:", err);
            throw err;
        }
    },

    
    filterSavedGRAPHQL: async (body) => {
        try {
          
            const response = await filtrosApi.traerFiltrosGraphQL(body);
            return response; 
        } catch (err) {
            console.error("Error al obtener filtros:", err);
            throw err;
        }
    },

    deleteFilterGRAPHQL: async (body) => {
        try {
            const response = await filtrosApi.borrarFiltrosGraphQL(body);
            return response; 
        } catch (err) {
            console.error("Error al eliminar el filtro:", err);
            throw err;
        }
    },

    saveFilterGRAPHQL: async (newFiltro) => {
        try {
            const response = await filtrosApi.subirQueryGraphqlDto(newFiltro);
            return response; 
        } catch (err) {
            console.error("Error al obtener filtros de evento:", err);
            throw err;
        }
    },


}

export default FilterService