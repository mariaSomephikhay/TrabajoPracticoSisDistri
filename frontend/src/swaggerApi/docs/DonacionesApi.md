# ApiDocumentada.DonacionesApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createDonation**](DonacionesApi.md#createDonation) | **POST** /donacion/new | Insertar una nueva donacion
[**deleteDonationById**](DonacionesApi.md#deleteDonationById) | **DELETE** /donacion/delete/{id} | Eliminar donacion
[**getDonationById**](DonacionesApi.md#getDonationById) | **GET** /donacion/{id} | Obtener Donacion
[**listDonations**](DonacionesApi.md#listDonations) | **GET** /donacion/ | Obtener todos las donaciones
[**updateDonationById**](DonacionesApi.md#updateDonationById) | **PUT** /donacion/{id} | Actualizar un donacion



## createDonation

> Donacion createDonation(payload)

Insertar una nueva donacion

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.DonacionesApi();
let payload = new ApiDocumentada.Donacion(); // Donacion | 
apiInstance.createDonation(payload, (error, data, response) => {
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


## deleteDonationById

> Donacion deleteDonationById(id)

Eliminar donacion

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.DonacionesApi();
let id = 56; // Number | 
apiInstance.deleteDonationById(id, (error, data, response) => {
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


## getDonationById

> Donacion getDonationById(id)

Obtener Donacion

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.DonacionesApi();
let id = 56; // Number | 
apiInstance.getDonationById(id, (error, data, response) => {
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


## listDonations

> DonacionList listDonations()

Obtener todos las donaciones

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.DonacionesApi();
apiInstance.listDonations((error, data, response) => {
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

[**DonacionList**](DonacionList.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## updateDonationById

> Donacion updateDonationById(id, payload)

Actualizar un donacion

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.DonacionesApi();
let id = 56; // Number | 
let payload = new ApiDocumentada.Donacion(); // Donacion | 
apiInstance.updateDonationById(id, payload, (error, data, response) => {
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
 **payload** | [**Donacion**](Donacion.md)|  | 

### Return type

[**Donacion**](Donacion.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

