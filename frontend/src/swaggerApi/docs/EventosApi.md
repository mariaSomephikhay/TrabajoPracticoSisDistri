# ApiDocumentada.EventosApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createEvent**](EventosApi.md#createEvent) | **POST** /evento/new | Insertar nuevo evento
[**deleteEventById**](EventosApi.md#deleteEventById) | **DELETE** /evento/delete/{id} | Eliminar evento



## createEvent

> Donacion createEvent(payload)

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
let payload = new ApiDocumentada.Donacion(); // Donacion | 
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
 **payload** | [**Donacion**](Donacion.md)|  | 

### Return type

[**Donacion**](Donacion.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## deleteEventById

> Donacion deleteEventById(id)

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
let id = 56; // Number | 
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
 **id** | **Number**|  | 

### Return type

[**Donacion**](Donacion.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

