# ApiDocumentada.TraerQueryGraphql

## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**query** | **String** |  | [default to &#39;query TraerFiltros($tipo: String!,$usuario: String!) { traerFiltros(tipo: $tipo, usuario: $usuario) { status message data { id name valueFilter usuario filterType } } }&#39;]
**variables** | [**TraerVarGraphqlDto**](TraerVarGraphqlDto.md) |  | [optional] 


