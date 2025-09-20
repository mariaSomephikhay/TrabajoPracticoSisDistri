import CustomApiClient from "./CustomApiClient.js";
import UserApi from "../swaggerApi/src/api/UserApi.js";

// Instanciamos cliente Swagger
const apiClient = new CustomApiClient(import.meta.env.VITE_APP_API_URL);

// Función para actualizar token desde contexto o componente
export const setAuthToken = (token) => {
  apiClient.setToken(token);
};

// Instancias de APIs generadas por Swagger
export const userApi = new UserApi(apiClient);

/**
 * Aquí se pueden agregar más APIs a medida que se crean endpoints
 * por ejemplo: export const productApi = new ProductApi(apiClient);
 */
