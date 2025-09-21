# ApiDocumentada.UserApi

All URIs are relative to *http://localhost*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUserById**](UserApi.md#deleteUserById) | **DELETE** /user/{id} | Eliminar usuario
[**getUserById**](UserApi.md#getUserById) | **GET** /user/{id} | Obtener usuario
[**listUsers**](UserApi.md#listUsers) | **GET** /user/ | Obtener todos los usuarios
[**loginUser**](UserApi.md#loginUser) | **POST** /user/login | Login de usuario y devolución de JWT
[**registerUser**](UserApi.md#registerUser) | **POST** /user/register | Registrar un nuevo usuario
[**updateUserById**](UserApi.md#updateUserById) | **PUT** /user/{id} | Actualizar un usuario



## deleteUserById

> Usuario deleteUserById(id)

Eliminar usuario

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.UserApi();
let id = 56; // Number | 
apiInstance.deleteUserById(id, (error, data, response) => {
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

[**Usuario**](Usuario.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getUserById

> Usuario getUserById(id)

Obtener usuario

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.UserApi();
let id = 56; // Number | 
apiInstance.getUserById(id, (error, data, response) => {
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

[**Usuario**](Usuario.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## listUsers

> UsuarioList listUsers()

Obtener todos los usuarios

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.UserApi();
apiInstance.listUsers((error, data, response) => {
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

[**UsuarioList**](UsuarioList.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## loginUser

> Token loginUser(payload)

Login de usuario y devolución de JWT

### Example

```javascript
import ApiDocumentada from 'api_documentada';

let apiInstance = new ApiDocumentada.UserApi();
let payload = new ApiDocumentada.Login(); // Login | 
apiInstance.loginUser(payload, (error, data, response) => {
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
 **payload** | [**Login**](Login.md)|  | 

### Return type

[**Token**](Token.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## registerUser

> Usuario registerUser(payload)

Registrar un nuevo usuario

### Example

```javascript
import ApiDocumentada from 'api_documentada';

let apiInstance = new ApiDocumentada.UserApi();
let payload = new ApiDocumentada.Usuario(); // Usuario | 
apiInstance.registerUser(payload, (error, data, response) => {
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
 **payload** | [**Usuario**](Usuario.md)|  | 

### Return type

[**Usuario**](Usuario.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json


## updateUserById

> Usuario updateUserById(id, payload)

Actualizar un usuario

### Example

```javascript
import ApiDocumentada from 'api_documentada';
let defaultClient = ApiDocumentada.ApiClient.instance;
// Configure API key authorization: Bearer Auth
let Bearer Auth = defaultClient.authentications['Bearer Auth'];
Bearer Auth.apiKey = 'YOUR API KEY';
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer Auth.apiKeyPrefix = 'Token';

let apiInstance = new ApiDocumentada.UserApi();
let id = 56; // Number | 
let payload = new ApiDocumentada.Usuario(); // Usuario | 
apiInstance.updateUserById(id, payload, (error, data, response) => {
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
 **payload** | [**Usuario**](Usuario.md)|  | 

### Return type

[**Usuario**](Usuario.md)

### Authorization

[Bearer Auth](../README.md#Bearer Auth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

