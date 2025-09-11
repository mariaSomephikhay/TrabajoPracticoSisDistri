# TrabajoPracticoSisDistri

## 🔹 Descripción
Pasos para levantar los proyectos de Sistemas Distribuidos:

- Cliente **React** consumiendo Swagger/OpenAPI
- Cliente **Flask** con gRPC
- Servidor **gRPC en Java**

---

## ⚡ Comandos rápidos

| Proyecto | Comando | Nota |
|----------|---------|------|
| Cliente React (Swagger) | `npx openapi-generator-cli generate -i <ruta_a_openapi.json> -g javascript -o ./src/swaggerApi` | Reemplazar `<ruta_a_openapi.json>` por la ubicación real de tu archivo OpenAPI |
| Cliente Flask para consumir servicios gRPC| `pipenv run python -m grpc_tools.protoc -I=protos --python_out=generated --grpc_python_out=generated protos/donaciones_eventos.proto` | Ajustar ruta `.proto` según tu proyecto |
| Servidor Java gRPC | `mvn clean install` | Compila y genera stubs gRPC |

---

## ✅ Recomendaciones
- Ejecutar los comandos en el orden: **Flask → React → Java**  
- Revisar rutas de archivos `.proto` y `openapi.json` antes de ejecutar  
- Mantener dependencias actualizadas:  
  - `pipenv install` → Python  
  - `npm install` → React  
  - `mvn clean install` → Java
