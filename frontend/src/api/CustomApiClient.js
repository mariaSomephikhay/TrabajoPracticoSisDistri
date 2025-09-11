// src/custom/CustomApiClient.js
import superagent from "superagent";
import ApiClient from '../swaggerApi/src/ApiClient.js';

/**
 * Extiende ApiClient generado por Swagger/OpenAPI para aplicar modificaciones custom.
 */
class CustomApiClient extends ApiClient {
  constructor(basePath) {
    super(basePath);

    // Eliminamos los headers por defecto
    this.defaultHeaders = null;
  }

  /**
   * Sobrescribimos callApi con la implementación actual de ApiClient
   * para mantener tus cambios y quitar defaultHeaders.
   */
  callApi(path, httpMethod, pathParams, queryParams, headerParams, formParams, bodyParam,
          authNames, contentTypes, accepts, returnType, apiBasePath, callback) {

    // Construir URL con pathParams
    const url = this.buildUrl(path, pathParams, apiBasePath);

    // Crear request con superagent
    const request = superagent(httpMethod, url);

    // --- Headers ---
    if (contentTypes && contentTypes.length > 0) {
      const ct = this.jsonPreferredMime(contentTypes);
      if (ct) request.set('Content-Type', ct);
    }

    if (accepts && accepts.length > 0) {
      request.set('Accept', accepts.join(', '));
    }

    // Headers extra pasados por usuario
    if (headerParams) {
      Object.keys(headerParams).forEach(key => {
        request.set(key, headerParams[key]);
      });
    }

    // Query params
    if (queryParams) request.query(this.normalizeParams(queryParams));

    // Form params (x-www-form-urlencoded)
    if (formParams && Object.keys(formParams).length > 0) {
      request.type('form');
      request.send(this.normalizeParams(formParams));
    }

    // Body
    if (bodyParam) {
      if (request.get('Content-Type')?.includes('application/json')) {
        request.send(JSON.stringify(bodyParam));
      } else {
        request.send(bodyParam);
      }
    }

    // Autenticación
    if (authNames && authNames.length > 0) {
      this.applyAuthToRequest(request, authNames);
    }

    // Devuelve una promesa si no hay callback
    if (!callback) {
      return new Promise((resolve, reject) => {
        request.end((error, response) => {
          if (error) {
            reject(error);
          } else {
            try {
              const data = this.deserialize(response, returnType);
              resolve(data);
            } catch (err) {
              reject(err);
            }
          }
        });
      });
    } else {
      // Compatibilidad con callback antiguo
      request.end((error, response) => {
        let data = null;
        if (!error) {
          try {
            data = this.deserialize(response, returnType);
          } catch (err) {
            error = err;
          }
        }
        callback(error, data, response);
      });
      return request;
    }
  }
}

export default CustomApiClient;
