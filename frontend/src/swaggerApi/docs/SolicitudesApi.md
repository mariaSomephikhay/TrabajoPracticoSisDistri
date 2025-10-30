# ApiDocumentada.SolicitudesApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteRequestDonacion**](SolicitudesApi.md#deleteRequestDonacion) | **DELETE** /solicitud/delete | Enviar solicitud de donaciones a kafka
[**getAllOffersByOrganization**](SolicitudesApi.md#getAllOffersByOrganization) | **GET** /solicitud/offer/{id} | Obtener todos las ofertas de la organizacion donante
[**getAllRequestDonacion**](SolicitudesApi.md#getAllRequestDonacion) | **GET** /solicitud/ | Obtener todos las solicitudes donaciones
[**getLastOfferCreated**](SolicitudesApi.md#getLastOfferCreated) | **GET** /solicitud/offer | Obtener ultima oferta creada del sistema
[**informeSolicitudesDetalle**](SolicitudesApi.md#informeSolicitudesDetalle) | **POST** /solicitud/informe/excel | Solcitudes de donaciones en Excel
[**informeSolicitudesDonaciones**](SolicitudesApi.md#informeSolicitudesDonaciones) | **POST** /solicitud/informe/ | Consulta de informe de solicitudes con filtros
[**newOffer**](SolicitudesApi.md#newOffer) | **POST** /solicitud/offer/new | Ofrecer donaciones de una organizacion a kafka
[**newRequestDonacion**](SolicitudesApi.md#newRequestDonacion) | **POST** /solicitud/request/new | Enviar solicitud de donaciones a kafka
[**newTransfer**](SolicitudesApi.md#newTransfer) | **POST** /solicitud/transfer/{id} | Enviar transferencia de donaciones a organizacion solicitante con kafka



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


## getAllOffersByOrganization

> OfertaGetList getAllOffersByOrganization(id)

Obtener todos las ofertas de la organizacion donante

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
let id = 56; // Number | 
apiInstance.getAllOffersByOrganization(id, (error, data, response) => {
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
 **id** | **Number**|  | 

### Return type

[**OfertaGetList**](OfertaGetList.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
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


## getLastOfferCreated

> OfertaGet getLastOfferCreated()

Obtener ultima oferta creada del sistema

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
apiInstance.getLastOfferCreated((error, data, response) => {
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

[**OfertaGet**](OfertaGet.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## informeSolicitudesDetalle

> ExcelFileResponse informeSolicitudesDetalle(payload)

Solcitudes de donaciones en Excel

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
let payload = new ApiDocumentada.FiltroSolicitudDetalleDto(); // FiltroSolicitudDetalleDto | 
apiInstance.informeSolicitudesDetalle(payload, (error, data, response) => {
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
 **payload** | [**FiltroSolicitudDetalleDto**](FiltroSolicitudDetalleDto.md)|  | 

### Return type

[**ExcelFileResponse**](ExcelFileResponse.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
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


## newOffer

> Oferta newOffer(payload)

Ofrecer donaciones de una organizacion a kafka

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
let payload = new ApiDocumentada.Oferta(); // Oferta | 
apiInstance.newOffer(payload, (error, data, response) => {
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
 **payload** | [**Oferta**](Oferta.md)|  | 

### Return type

[**Oferta**](Oferta.md)

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


## newTransfer

> newTransfer(id, payload)

Enviar transferencia de donaciones a organizacion solicitante con kafka

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
let id = 56; // Number | 
let payload = new ApiDocumentada.SolicitudTransferDonation(); // SolicitudTransferDonation | 
apiInstance.newTransfer(id, payload, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully.');
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Number**|  | 
 **payload** | [**SolicitudTransferDonation**](SolicitudTransferDonation.md)|  | 

### Return type

null (empty response body)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

