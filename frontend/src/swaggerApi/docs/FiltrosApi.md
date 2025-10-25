# ApiDocumentada.FiltrosApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createFilter**](FiltrosApi.md#createFilter) | **POST** /filter/new | Insertar un nuevo filtro
[**deleteFilter**](FiltrosApi.md#deleteFilter) | **DELETE** /filter/delete/{id} | Eliminar filtro por id
[**getFilter**](FiltrosApi.md#getFilter) | **GET** /filter/{type}/{idUsuario} | traer filtro por usuario y tipo



## createFilter

> FiltroDto createFilter(payload)

Insertar un nuevo filtro

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.FiltrosApi();
let payload = new ApiDocumentada.FiltroDto(); // FiltroDto | 
apiInstance.createFilter(payload, (error, data, response) => {
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
 **payload** | [**FiltroDto**](FiltroDto.md)|  | 

### Return type

[**FiltroDto**](FiltroDto.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## deleteFilter

> deleteFilter(id)

Eliminar filtro por id

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.FiltrosApi();
let id = 56; // Number | 
apiInstance.deleteFilter(id, (error, data, response) => {
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

### Return type

null (empty response body)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getFilter

> ListaFiltrosDto getFilter(type, idUsuario)

traer filtro por usuario y tipo

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.FiltrosApi();
let type = "type_example"; // String | 
let idUsuario = "idUsuario_example"; // String | 
apiInstance.getFilter(type, idUsuario, (error, data, response) => {
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
 **type** | **String**|  | 
 **idUsuario** | **String**|  | 

### Return type

[**ListaFiltrosDto**](ListaFiltrosDto.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

