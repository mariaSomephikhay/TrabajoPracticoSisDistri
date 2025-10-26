import { solicitudApi } from "./ApiSwagger.js";

export const solicitudApiExtended = {
  informeSolicitudesDetalleExcel: (body) => {
    // Usa el apiClient interno para hacer POST con blob
    return solicitudApi.apiClient.request({
      url: '/solicitudes/informe/', // endpoint de tu backend
      method: 'POST',
      data: body,                 // tu payload
      responseType: 'blob'        // para recibir Excel
    });
  }
};