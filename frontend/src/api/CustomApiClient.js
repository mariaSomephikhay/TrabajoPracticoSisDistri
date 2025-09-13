import superagent from "superagent";
import ApiClient from '../swaggerApi/src/ApiClient.js';

class CustomApiClient extends ApiClient {
  constructor(basePath) {
    super(basePath);
    this.defaultHeaders = null;
    this.token = null; // Guardamos el token
  }

  setToken(token) {
    this.token = token;
  }

  callApi(path, httpMethod, pathParams, queryParams, headerParams = {}, formParams, bodyParam,
          authNames, contentTypes, accepts, returnType, apiBasePath, callback) {

    // Agregar Authorization si hay token
    if (this.token) {
      headerParams['Authorization'] = `Bearer ${this.token}`;
    }

    const url = this.buildUrl(path, pathParams, apiBasePath);
    const request = superagent(httpMethod, url);

    // Headers
    if (contentTypes && contentTypes.length > 0) {
      const ct = this.jsonPreferredMime(contentTypes);
      if (ct) request.set('Content-Type', ct);
    }

    if (accepts && accepts.length > 0) {
      request.set('Accept', accepts.join(', '));
    }

    Object.keys(headerParams).forEach(key => request.set(key, headerParams[key]));

    // Query params
    if (queryParams) request.query(this.normalizeParams(queryParams));

    // Form params
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

    // Promesa o callback
    if (!callback) {
      return new Promise((resolve, reject) => {
        request.end((error, response) => {
          if (error) reject(error);
          else {
            try { resolve(this.deserialize(response, returnType)); }
            catch (err) { reject(err); }
          }
        });
      });
    } else {
      request.end((error, response) => {
        let data = null;
        if (!error) {
          try { data = this.deserialize(response, returnType); }
          catch (err) { error = err; }
        }
        callback(error, data, response);
      });
      return request;
    }
  }
}

export default CustomApiClient;
