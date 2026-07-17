# API Gateway

Microservicio encargado del enrutamiento y seguridad de las APIs.

## Funcionalidades

- Enrutamiento dinámico.
- Registro con Eureka.
- Autenticación mediante JWT.
- Filtro de autorización.

## Puerto

```
8080
```

## Docker

```bash
docker build -t api-gateway .
```

## Infraestructura

La infraestructura Docker se encuentra en el repositorio **bank-infra**.