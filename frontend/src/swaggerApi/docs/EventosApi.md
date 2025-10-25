# ApiDocumentada.EventosApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**adhesionEvento**](EventosApi.md#adhesionEvento) | **POST** /evento/adhesion/evento/{id_organization} | Adhesion de voluntario a evento
[**createEvent**](EventosApi.md#createEvent) | **POST** /evento/new | Insertar nuevo evento
[**deleteEventById**](EventosApi.md#deleteEventById) | **DELETE** /evento/delete/{id} | Eliminar evento
[**deleteRequesEventoKafka**](EventosApi.md#deleteRequesEventoKafka) | **POST** /evento/request/delete | Publicar baja de evento en kafka
[**filtroEvento**](EventosApi.md#filtroEvento) | **POST** /evento/filtro/ | Consulta de eventos con filtros
[**getEventoById**](EventosApi.md#getEventoById) | **GET** /evento/{id} | Obtener Evento
[**getEventoWithDonacionesById**](EventosApi.md#getEventoWithDonacionesById) | **GET** /evento/{id}/donaciones | Obtener donaciones del evento
[**getEventoWithUsersById**](EventosApi.md#getEventoWithUsersById) | **GET** /evento/{id}/usuarios | Obtener usuarios del evento
[**insertDonacionesToEvento**](EventosApi.md#insertDonacionesToEvento) | **PUT** /evento/{id}/donaciones/add | Agregar donaciones al evento
[**insertUsersToEvento**](EventosApi.md#insertUsersToEvento) | **PUT** /evento/{id}/users/add | Agregar usuarios a un evento
[**listEventos**](EventosApi.md#listEventos) | **GET** /evento/ | Obtener todos los eventos
[**newRequesEventoKafka**](EventosApi.md#newRequesEventoKafka) | **POST** /evento/request/new | Publicar evento en kafka
[**updateEventoById**](EventosApi.md#updateEventoById) | **PUT** /evento/{id} | Actualizar un evento



## adhesionEvento

> adhesionEvento(idOrganization, payload)

Adhesion de voluntario a evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let idOrganization = 56; // Number | 
let payload = new ApiDocumentada.AdhesionEventoKafka(); // AdhesionEventoKafka | 
apiInstance.adhesionEvento(idOrganization, payload, (error, data, response) => {
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
 **idOrganization** | **Number**|  | 
 **payload** | [**AdhesionEventoKafka**](AdhesionEventoKafka.md)|  | 

### Return type

null (empty response body)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## createEvent

> Evento createEvent(payload)

Insertar nuevo evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let payload = new ApiDocumentada.Evento(); // Evento | 
apiInstance.createEvent(payload, (error, data, response) => {
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
 **payload** | [**Evento**](Evento.md)|  | 

### Return type

[**Evento**](Evento.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## deleteEventById

> Evento deleteEventById(id)

Eliminar evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
apiInstance.deleteEventById(id, (error, data, response) => {
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
 **id** | **String**|  | 

### Return type

[**Evento**](Evento.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## deleteRequesEventoKafka

> EventoBajaKafka deleteRequesEventoKafka(payload)

Publicar baja de evento en kafka

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let payload = new ApiDocumentada.EventoBajaKafka(); // EventoBajaKafka | 
apiInstance.deleteRequesEventoKafka(payload, (error, data, response) => {
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
 **payload** | [**EventoBajaKafka**](EventoBajaKafka.md)|  | 

### Return type

[**EventoBajaKafka**](EventoBajaKafka.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## filtroEvento

> EventoDtoFiltro filtroEvento(payload)

Consulta de eventos con filtros

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let payload = new ApiDocumentada.FiltroEvento(); // FiltroEvento | 
apiInstance.filtroEvento(payload, (error, data, response) => {
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
 **payload** | [**FiltroEvento**](FiltroEvento.md)|  | 

### Return type

[**EventoDtoFiltro**](EventoDtoFiltro.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## getEventoById

> Evento getEventoById(id)

Obtener Evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
apiInstance.getEventoById(id, (error, data, response) => {
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
 **id** | **String**|  | 

### Return type

[**Evento**](Evento.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getEventoWithDonacionesById

> EventoListaDonacion getEventoWithDonacionesById(id)

Obtener donaciones del evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
apiInstance.getEventoWithDonacionesById(id, (error, data, response) => {
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
 **id** | **String**|  | 

### Return type

[**EventoListaDonacion**](EventoListaDonacion.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getEventoWithUsersById

> EventoUsersDto getEventoWithUsersById(id)

Obtener usuarios del evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
apiInstance.getEventoWithUsersById(id, (error, data, response) => {
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
 **id** | **String**|  | 

### Return type

[**EventoUsersDto**](EventoUsersDto.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## insertDonacionesToEvento

> insertDonacionesToEvento(id, payload)

Agregar donaciones al evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
let payload = new ApiDocumentada.EventoDonacionReq(); // EventoDonacionReq | 
apiInstance.insertDonacionesToEvento(id, payload, (error, data, response) => {
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
 **id** | **String**|  | 
 **payload** | [**EventoDonacionReq**](EventoDonacionReq.md)|  | 

### Return type

null (empty response body)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## insertUsersToEvento

> EventoUsersDto insertUsersToEvento(id, payload)

Agregar usuarios a un evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
let payload = new ApiDocumentada.EventoUsersListDto(); // EventoUsersListDto | 
apiInstance.insertUsersToEvento(id, payload, (error, data, response) => {
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
 **id** | **String**|  | 
 **payload** | [**EventoUsersListDto**](EventoUsersListDto.md)|  | 

### Return type

[**EventoUsersDto**](EventoUsersDto.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## listEventos

> EventoList listEventos()

Obtener todos los eventos

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
apiInstance.listEventos((error, data, response) => {
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

[**EventoList**](EventoList.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## newRequesEventoKafka

> EventoKafka newRequesEventoKafka(payload)

Publicar evento en kafka

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let payload = new ApiDocumentada.EventoKafka(); // EventoKafka | 
apiInstance.newRequesEventoKafka(payload, (error, data, response) => {
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
 **payload** | [**EventoKafka**](EventoKafka.md)|  | 

### Return type

[**EventoKafka**](EventoKafka.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## updateEventoById

> Evento updateEventoById(id, payload)

Actualizar un evento

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.EventosApi();
let id = "id_example"; // String | 
let payload = new ApiDocumentada.Evento(); // Evento | 
apiInstance.updateEventoById(id, payload, (error, data, response) => {
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
 **id** | **String**|  | 
 **payload** | [**Evento**](Evento.md)|  | 

### Return type

[**Evento**](Evento.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

