# Sistema de Gestión de Facturación (Boletas) - Proyecto Final

Proyecto desarrollado como parte de la Evaluación Final Transversal (CDY2204), que consiste en un sistema de facturación basado en microservicios, con integración de servicios en la nube, mensajería, autenticación y despliegue continuo.

---

## 🧩 Tecnologías utilizadas

- ☕ Java 17 + Spring Boot 3
- 🔐 Azure Entra ID (OAuth2 / JWT)
- 🌐 API Manager / API Gateway para exposición segura
- 🐰 RabbitMQ (con DLQ configurado)
- ☁️ AWS S3 (almacenamiento de archivos)
- 🐳 Docker + Docker Compose
- 💾 Oracle DB local (con Spring Data JPA)
- 🔄 GitHub Actions (CI/CD automático)

---

## ⚙️ Funcionalidades principales

### 📦 Microservicios REST

- **BoletaController**  
  CRUD de boletas con persistencia en Oracle.

- **MensajeController**  
  Envío y recepción de mensajes de texto, usuarios y productos vía RabbitMQ.

- **RabbitMQAdminController**  
  Creación dinámica de colas, exchanges y bindings.

- **AwsS3Controller**  
  Subida, descarga, eliminación y movimiento de archivos dentro del bucket S3.

---

## 🔐 Seguridad: Azure Entra ID (OAuth2)

- Autenticación basada en tokens JWT, verificados desde Azure.
- Integración con `spring.security.oauth2.resourceserver` para proteger los endpoints.
- Uso de `issuer-uri` y `jwk-set-uri` directamente desde Azure.

### Fragmento de configuración:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://login.microsoftonline.com/{tenant-id}/v2.0
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://login.microsoftonline.com/{tenant-id}/discovery/v2.0/keys
```

---

## 🌐 API Gateway / API Manager

- Todos los endpoints protegidos son accesibles solo a través del API Gateway.
- Se puede aplicar control de tráfico, auditoría de tokens y CORS.
- Ideal para desacoplar el backend de los consumidores.

---

## ☁️ AWS S3

- Subida de archivos desde `MultipartFile` o desde disco.
- Descarga directa como stream (`ResponseEntity<byte[]>`)
- Movimiento de archivos con `CopyObjectRequest`.
- Bucket: `bucketcloudduoc`

---

## 🐰 RabbitMQ + DLQ

- Productores y consumidores con `@RabbitListener`
- Configuración de colas con `x-dead-letter-exchange` y DLQ separadas para errores
- Administración de listeners en tiempo real (pausar/reanudar)

---

## 🔄 CI/CD con GitHub Actions

- Build automático de `.jar` con Maven
- Construcción de imagen Docker
- Push a DockerHub
- Despliegue a EC2 desde el workflow
- Archivo `Dockerfile` y `docker-compose.yml` incluidos

---

## 🔧 Configuración (`application.properties`)

```properties
spring.application.name=ms-administracion-archivos
server.port=8080

# AWS S3
spring.cloud.aws.region.static=us-east-1
spring.cloud.aws.credentials.access-key=...
spring.cloud.aws.credentials.secret-key=...
aws.s3.bucket-name=bucketcloudduoc

# JWT con Azure
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://login.microsoftonline.com/...
```

---

## ▶️ Cómo ejecutar

```bash
# Compilar
mvn clean install

# Ejecutar en Docker
docker-compose up
```

---

## 📸 Evidencia

- ✔️ Pruebas desde Postman con token Bearer
- ✔️ Subida de archivos a S3 y descarga correcta
- ✔️ Mensajes enviados y recibidos vía RabbitMQ
- ✔️ CI/CD ejecutado desde GitHub Actions

---


