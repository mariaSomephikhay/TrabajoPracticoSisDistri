# ApiDocumentada.SoapApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**postOngsSoap**](SoapApi.md#postOngsSoap) | **POST** /soap/ongs | Consultar datos de ONGs por IDs (SOAP)
[**postPresidentesSoap**](SoapApi.md#postPresidentesSoap) | **POST** /soap/presidentes | Consultar datos de Presidentes por IDs (SOAP)



## postOngsSoap

> [ONG] postOngsSoap(payload)

Consultar datos de ONGs por IDs (SOAP)

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.SoapApi();
let payload = new ApiDocumentada.OrgIds(); // OrgIds | 
apiInstance.postOngsSoap(payload, (error, data, response) => {
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
 **payload** | [**OrgIds**](OrgIds.md)|  | 

### Return type

[**[ONG]**](ONG.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## postPresidentesSoap

> [Presidente] postPresidentesSoap(payload)

Consultar datos de Presidentes por IDs (SOAP)

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.SoapApi();
let payload = new ApiDocumentada.OrgIds(); // OrgIds | 
apiInstance.postPresidentesSoap(payload, (error, data, response) => {
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
 **payload** | [**OrgIds**](OrgIds.md)|  | 

### Return type

[**[Presidente]**](Presidente.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

