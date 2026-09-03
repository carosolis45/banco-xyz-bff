# Banco XYZ - Backend for Frontend (BFF)

## Descripción del Proyecto

Sistema Backend for Frontend (BFF) para el Banco XYZ, diseñado para optimizar la comunicación entre distintos frontends (Web, Móvil y Cajeros Automáticos). Cada frontend cuenta con su propio backend personalizado que entrega los datos en el formato específico que necesita.

## Objetivo

- Personalizar la información según las necesidades de cada frontend
- Reducir el consumo de ancho de banda en dispositivos móviles
- Proporcionar respuestas optimizadas para cada plataforma
- Centralizar la lógica de negocio en un solo lugar

## Estructura del Proyecto
banco-xyz-bff/
├── src/
│ └── main/
│ ├── java/
│ │ └── com/
│ │ └── bancoxyz/
│ │ └── bff/
│ │ ├── controller/
│ │ │ ├── WebBffController.java
│ │ │ ├── MovilBffController.java
│ │ │ └── CajeroBffController.java
│ │ ├── dto/
│ │ │ ├── TransaccionWebDTO.java
│ │ │ ├── TransaccionMovilDTO.java
│ │ │ └── TransaccionCajeroDTO.java
│ │ ├── model/
│ │ │ ├── Cuenta.java
│ │ │ └── Transaccion.java
│ │ ├── repository/
│ │ │ ├── CuentaRepository.java
│ │ │ └── TransaccionRepository.java
│ │ ├── service/
│ │ │ ├── CuentaService.java
│ │ │ └── TransaccionService.java
│ │ └── config/
│ │ └── SecurityConfig.java
│ └── resources/
│ ├── application.properties
│ └── data/
│ ├── intereses.csv
│ └── transacciones.csv
├── captura/
│ ├── 1-web-bienvenida.png
│ ├── 2-web-cuentas.png
│ └── ...
├── README.md
└── pom.xml

text

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.3.4 | Framework principal |
| Spring Security | 6.3.3 | Autenticación y autorización |
| Spring Data JPA | - | Acceso a datos |
| H2 Database | - | Base de datos en memoria |
| Lombok | - | Reducción de código boilerplate |
| Maven | - | Gestión de dependencias |

## Endpoints de la API

### BFF Web
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/web/` | Bienvenida con información del API |
| GET | `/api/web/cuentas` | Lista completa de cuentas |
| GET | `/api/web/transacciones` | Lista completa de transacciones |
| GET | `/api/web/cuentas/{id}` | Detalle de una cuenta específica |

### BFF Móvil
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/movil/transacciones` | Transacciones en formato ligero |
| GET | `/api/movil/cuentas` | Resumen de cuentas |
| GET | `/api/movil/saldo/{cuentaId}` | Saldo de una cuenta específica |

### BFF Cajeros Automáticos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cajero/transacciones/{cuentaId}` | Transacciones de una cuenta |
| GET | `/api/cajero/saldo/{cuentaId}` | Saldo de una cuenta |
| GET | `/api/cajero/cuenta/{cuentaId}` | Detalle completo de una cuenta |

## Seguridad

Actualmente todos los endpoints están públicos para facilitar el desarrollo.

### Credenciales H2 Console
JDBC URL: jdbc:h2:mem:banco_xyz_bff
Usuario: sa
Contraseña: (vacío)

text

## Base de Datos

El proyecto utiliza H2 Database en memoria con datos precargados desde archivos CSV.

### Consultas SQL de ejemplo
```sql
-- Ver todas las cuentas
SELECT * FROM cuentas;

-- Ver transacciones de una cuenta
SELECT * FROM transacciones WHERE cuenta_id = '101';

Instalación y Ejecución
Requisitos previos
Java 17 o superior

Maven 3.6 o superior

Pasos para ejecutar
bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
Acceder a la API
Bienvenida: http://localhost:8080/api/web/

H2 Console: http://localhost:8080/h2-console/

Ejemplos de Respuestas
GET /api/web/
json
{
  "nombre": "Banco XYZ BFF",
  "version": "1.0.0",
  "estado": "activo",
  "descripcion": "Backend For Frontend para servicios bancarios",
  "endpoints": [
    "/api/web/cuentas",
    "/api/web/transacciones",
    "/api/web/cuentas/{id}"
  ]
}
GET /api/web/cuentas
json
[
  {
    "id": 1,
    "cuentaId": "101",
    "nombre": "John Doe",
    "saldo": 5000.00,
    "edad": 30,
    "tipo": "ahorro"
  }
]

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

Pruebas 

# Probar BFF Web
curl http://localhost:8080/api/web/cuentas

# Probar BFF Móvil
curl http://localhost:8080/api/movil/transacciones

# Probar BFF Cajero
curl http://localhost:8080/api/cajero/saldo/101