import CustomApiClient  from './CustomApiClient.js' 
import UserApi  from '../swaggerApi/src/api/UserApi' 
/** * Aca importar todos los Tags nuevos cuando se creen los demas enpoints **/ 

const apiClient = new CustomApiClient(import.meta.env.VITE_APP_API_URL); 

/**
 * Instancias de cada API generada por Swagger 
 */
export const userApi = new UserApi(apiClient);