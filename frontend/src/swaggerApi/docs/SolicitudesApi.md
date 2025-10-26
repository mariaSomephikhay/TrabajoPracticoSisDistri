# ApiDocumentada.SolicitudesApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteRequestDonacion**](SolicitudesApi.md#deleteRequestDonacion) | **DELETE** /solicitud/delete | Enviar solicitud de donaciones a kafka
[**getAllRequestDonacion**](SolicitudesApi.md#getAllRequestDonacion) | **GET** /solicitud/ | Obtener todos las solicitudes donaciones
[**informeSolicitudesDonaciones**](SolicitudesApi.md#informeSolicitudesDonaciones) | **POST** /solicitud/informe/ | Consulta de informe de solicitudes con filtros
[**newRequestDonacion**](SolicitudesApi.md#newRequestDonacion) | **POST** /solicitud/request/new | Enviar solicitud de donaciones a kafka



## deleteRequestDonacion

> SolicitudBaja deleteRequestDonacion(payload)

Enviar solicitud de donaciones a kafka

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.SolicitudesApi();
let payload = new ApiDocumentada.SolicitudBaja(); // SolicitudBaja | 
apiInstance.deleteRequestDonacion(payload, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **payload** | [**SolicitudBaja**](SolicitudBaja.md)|  | 

### Return type

[**SolicitudBaja**](SolicitudBaja.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## getAllRequestDonacion

> SolicitudGetList getAllRequestDonacion()

Obtener todos las solicitudes donaciones

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.SolicitudesApi();
apiInstance.getAllRequestDonacion((error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**SolicitudGetList**](SolicitudGetList.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## informeSolicitudesDonaciones

> SolicitudGraphQLResponse informeSolicitudesDonaciones(payload)

Consulta de informe de solicitudes con filtros

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.SolicitudesApi();
let payload = new ApiDocumentada.SolicitudQueryInformeSolicitud(); // SolicitudQueryInformeSolicitud | 
apiInstance.informeSolicitudesDonaciones(payload, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **payload** | [**SolicitudQueryInformeSolicitud**](SolicitudQueryInformeSolicitud.md)|  | 

### Return type

[**SolicitudGraphQLResponse**](SolicitudGraphQLResponse.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## newRequestDonacion

> Solicitud newRequestDonacion(payload)

Enviar solicitud de donaciones a kafka

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.SolicitudesApi();
let payload = new ApiDocumentada.Solicitud(); // Solicitud | 
apiInstance.newRequestDonacion(payload, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **payload** | [**Solicitud**](Solicitud.md)|  | 

### Return type

[**Solicitud**](Solicitud.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

