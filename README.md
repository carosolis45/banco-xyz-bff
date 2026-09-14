# Banco XYZ - Backend for Frontend (BFF)

## Descripción del Proyecto

Sistema Backend for Frontend (BFF) para el Banco XYZ, diseñado para optimizar la comunicación entre distintos frontends (Web, Móvil y Cajeros Automáticos). Cada frontend cuenta con su propio backend personalizado que entrega los datos en el formato específico que necesita, con autenticación y autorización diferenciada por canal.

## Objetivo

- Personalizar la información según las necesidades de cada frontend
- Reducir el consumo de ancho de banda en dispositivos móviles
- Proporcionar respuestas optimizadas para cada plataforma
- Centralizar la lógica de negocio en un solo lugar
- Implementar autenticación y autorización específica por canal
- Garantizar comunicaciones seguras con HTTPS

## Estructura del Proyecto

```text
banco-xyz-bff/
├── src/
│   └── main/
│       ├── java/com/bancoxyz/
│       │   ├── bff/                              # Paquete BFF principal
│       │   │   ├── controller/
│       │   │   │   ├── AuthController.java       # Login por canal
│       │   │   │   ├── WebBffController.java     # BFF Web
│       │   │   │   ├── MovilBffController.java   # BFF Móvil
│       │   │   │   └── CajeroBffController.java  # BFF Cajero
│       │   │   ├── dto/
│       │   │   │   ├── CuentaWebDTO.java
│       │   │   │   ├── CuentaMovilDTO.java
│       │   │   │   ├── CuentaCajeroDTO.java
│       │   │   │   ├── SaldoMovilDTO.java
│       │   │   │   ├── SaldoCajeroDTO.java
│       │   │   │   ├── LoginRequestDTO.java
│       │   │   │   ├── TransaccionWebDTO.java
│       │   │   │   ├── TransaccionMovilDTO.java
│       │   │   │   └── TransaccionCajeroDTO.java
│       │   │   ├── model/
│       │   │   │   ├── Cuenta.java
│       │   │   │   └── Transaccion.java
│       │   │   ├── repository/
│       │   │   │   ├── CuentaRepository.java
│       │   │   │   └── TransaccionRepository.java
│       │   │   └── service/
│       │   │       ├── CuentaService.java
│       │   │       └── TransaccionService.java
│       │   └── banco_xyz_bff/
│       │       ├── BancoXyzBffApplication.java   # Clase principal
│       │       ├── config/
│       │       │   ├── SecurityConfig.java       # 3 cadenas por canal
│       │       │   ├── SecurityBeansConfig.java  # Beans de seguridad
│       │       │   └── DataLoader.java
│       │       ├── exception/
│       │       │   ├── GlobalExceptionHandler.java
│       │       │   ├── RecursoNoEncontradoException.java
│       │       │   └── SolicitudInvalidaException.java
│       │       └── security/
│       │           ├── JwtUtil.java
│       │           ├── JwtAuthenticationFilter.java
│       │           └── UserDetailsServiceImpl.java
│       └── resources/
│           ├── application.properties
│           ├── keystore.p12                      # Certificado SSL
│           └── data/
│               ├── intereses.csv
│               └── transacciones.csv
├── captura/
├── README.md
└── pom.xml

Tecnologías Utilizadas
Tecnología	Versión	Propósito
Java	17	Lenguaje de programación
Spring Boot	3.3.4	Framework principal
Spring Security	6.3.3	Autenticación y autorización
JJWT	0.12.6	Generación y validación de tokens JWT
Spring Data JPA	-	Acceso a datos
H2 Database	-	Base de datos en memoria
Lombok	-	Reducción de código boilerplate
Maven	-	Gestión de dependencias

Seguridad
HTTPS
Puerto: 8443

Certificado autofirmado (desarrollo)

Todas las comunicaciones cifradas con TLS

Autenticación y Autorización por Canal
Canal	Ruta	Autenticación	Rol Requerido
Web	/api/web/**	JWT	WEB_USER
Móvil	/api/movil/**	JWT	MOVIL_USER
Cajero	/api/cajero/**	JWT + Roles	CAJERO_CONSULTA, CAJERO_RETIRO
Usuarios de Prueba
Canal	Usuario	Contraseña	Rol
Web	webuser	web123	ROLE_WEB_USER
Móvil	moviluser	movil123	ROLE_MOVIL_USER
Cajero	cajerouser	cajero123	ROLE_CAJERO_CONSULTA, ROLE_CAJERO_RETIRO

Endpoints de la API
Autenticación
Método	Endpoint	Descripción
POST	/api/auth/login/web	Login para canal Web
POST	/api/auth/login/movil	Login para canal Móvil
POST	/api/auth/login/cajero	Login para canal Cajero
Body de login:

json
{
  "username": "webuser",
  "password": "web123"
}
Respuesta:

json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "webuser",
  "rol": "ROLE_WEB_USER",
  "mensaje": "Autenticación exitosa"
}

BFF Web (requiere token WEB_USER)
Método	Endpoint	Descripción
GET	/api/web/	Bienvenida (público)
GET	/api/web/cuentas	Lista completa de cuentas
GET	/api/web/transacciones	Lista completa de transacciones
GET	/api/web/cuentas/{id}	Detalle de una cuenta

BFF Móvil (requiere token MOVIL_USER)
Método	Endpoint	Descripción
GET	/api/movil/transacciones	Transacciones (formato ligero)
GET	/api/movil/cuentas	Resumen de cuentas
GET	/api/movil/saldo/{cuentaId}	Saldo de una cuenta
 
BFF Cajero (requiere token CAJERO_CONSULTA)
Método	Endpoint	Descripción
GET	/api/cajero/transacciones/{cuentaId}	Transacciones de una cuenta
GET	/api/cajero/saldo/{cuentaId}	Saldo de una cuenta
GET	/api/cajero/cuenta/{cuentaId}	Detalle de una cuenta

Instalación y Ejecución
Requisitos previos
Java 17 o superior

Maven 3.6 o superior

Pasos para ejecutar
Clonar el repositorio:

bash
git clone https://github.com/carosolis45/banco-xyz-bff.git
cd banco-xyz-bff
Generar el certificado SSL:

bash
keytool -genkeypair -alias bancoxyz -keyalg RSA -keysize 2048 \
  -storetype PKCS12 -keystore keystore.p12 -validity 3650 \
  -storepass changeit -keypass changeit \
  -dname "CN=localhost, OU=Desarrollo, O=BancoXYZ, L=Santiago, ST=RM, C=CL"
Mover keystore.p12 a src/main/resources/

Compilar:

bash
mvn clean compile
Ejecutar:

bash
mvn spring-boot:run
Acceder:

API HTTPS: https://localhost:8443/api/web/

H2 Console: https://localhost:8443/h2-console

JDBC URL: jdbc:h2:mem:banco_xyz_bff

Usuario: sa

Contraseña: (vacío)

Pruebas con Postman
1. Login Web
text
POST https://localhost:8443/api/auth/login/web
Content-Type: application/json

{
  "username": "webuser",
  "password": "web123"
}
2. Acceder a recurso protegido (CON token)
text
GET https://localhost:8443/api/web/cuentas
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
3. Acceder sin token (SIN token)
text
GET https://localhost:8443/api/web/cuentas
Resultado: 401 Unauthorized

4. Validación de entrada
text
POST https://localhost:8443/api/auth/login/web
Content-Type: application/json

{
  "username": "webuser",
  "password": "ab"
}
Resultado: 400 Bad Request con mensaje de validación

Manejo Global de Excepciones
El proyecto usa @ControllerAdvice para estandarizar todas las respuestas de error:

Excepción	HTTP Status	Descripción
RecursoNoEncontradoException	404	Recurso no encontrado
SolicitudInvalidaException	400	Solicitud inválida
MethodArgumentNotValidException	400	Error de validación
Exception	500	Error interno
Ejemplo de respuesta 404:

json
{
  "timestamp": "2026-09-13T22:08:06",
  "status": 404,
  "error": "Not Found",
  "mensaje": "Cuenta no encontrada con ID: 999",
  "path": "/api/web/cuentas/999"
}

Datos Precargados
ID	Cuenta	Nombre	Saldo	Edad	Tipo
1	101	John Doe	5,000.00	30	Ahorro
2	102	Jane Smith	8,000.00	25	Préstamo
3	103	Bob Johnson	12,000.00	30	Préstamo
4	104	Alice Brown	0.00	45	Ahorro
5	105	Charlie Green	7,000.00	35	Hipoteca
6	106	John Doe	5,000.00	30	Ahorro
7	107	Diana Prince	15,000.00	40	Préstamo
8	108	Steve Rogers	10,000.00	80	Ahorro

Evidencias
Las capturas de ejecución están en la carpeta captura/:

Login Web exitoso

Acceso SIN token (401)

Acceso CON token (200)

Validación de entrada (400)

Endpoints de cada BFF

Autores
Carolina Solis - carosolis45