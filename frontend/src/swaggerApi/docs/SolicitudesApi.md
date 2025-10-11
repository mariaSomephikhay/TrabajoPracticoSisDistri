# ApiDocumentada.SolicitudesApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**newRequestDonacion**](SolicitudesApi.md#newRequestDonacion) | **POST** /solicitud/donacion/new | Enviar solicitud de donaciones a kafka



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

