# ApiDocumentada.TransferenciasApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**newTransfer**](TransferenciasApi.md#newTransfer) | **POST** /transferencia/donacion/{id_solicitante}/new | Enviar transferencia de donaciones a organizacion



## newTransfer

> Transferencia newTransfer(idSolicitante, payload)

Enviar transferencia de donaciones a organizacion

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.TransferenciasApi();
let idSolicitante = 56; // Number | 
let payload = new ApiDocumentada.Transferencia(); // Transferencia | 
apiInstance.newTransfer(idSolicitante, payload, (error, data, response) => {
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
 **idSolicitante** | **Number**|  | 
 **payload** | [**Transferencia**](Transferencia.md)|  | 

### Return type

[**Transferencia**](Transferencia.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

