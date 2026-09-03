# Banco XYZ - Backend for Frontend (BFF)

## Descripción del Proyecto

Sistema Backend for Frontend (BFF) para el Banco XYZ, diseñado para optimizar la comunicación entre distintos frontends (Web, Móvil y Cajeros Automáticos). Cada frontend cuenta con su propio backend personalizado que entrega los datos en el formato específico que necesita, mejorando la experiencia del usuario y la eficiencia del sistema.

## Objetivo

Implementar un sistema BFF que permita:

- Personalizar la información según las necesidades de cada frontend
- Reducir el consumo de ancho de banda en dispositivos móviles
- Proporcionar respuestas optimizadas para cada plataforma
- Centralizar la lógica de negocio en un solo lugar
- Gestionar autenticación y autorización por canal

## Estructura del Proyecto

banco-xyz-bff/
├── src/main/java/com/bancoxyz/bff/
│ ├── controller/
│ │ ├── WebBffController.java # BFF para navegadores web
│ │ ├── MovilBffController.java # BFF para aplicación móvil
│ │ └── CajeroBffController.java # BFF para cajeros automáticos
│ ├── dto/
│ │ ├── TransaccionWebDTO.java # Formato para web
│ │ ├── TransaccionMovilDTO.java # Formato para móvil
│ │ └── TransaccionCajeroDTO.java # Formato para cajeros
│ ├── model/
│ │ ├── Cuenta.java
│ │ └── Transaccion.java
│ ├── repository/
│ │ ├── CuentaRepository.java
│ │ └── TransaccionRepository.java
│ ├── service/
│ │ ├── CuentaService.java
│ │ └── TransaccionService.java
│ └── config/
│ └── SecurityConfig.java # Configuración de seguridad
├── src/main/resources/
│ ├── application.properties
│ └── data/
│ ├── intereses.csv
│ └── transacciones.csv
└── pom.xml

text

## Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.3.4 | Framework principal |
| Spring Security | 6.3.3 | Autenticación y autorización |
| Spring Data JPA | - | Acceso a datos |
| H2 Database | - | Base de datos en memoria (desarrollo) |
| Lombok | - | Reducción de código boilerplate |
| Maven | - | Gestión de dependencias |

## Endpoints de la API

### BFF Web (Frontend Web)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/web/` | Bienvenida con información del API |
| GET | `/api/web/cuentas` | Lista completa de cuentas |
| GET | `/api/web/transacciones` | Lista completa de transacciones |
| GET | `/api/web/cuentas/{id}` | Detalle de una cuenta específica |

**Características:** Datos completos, soporte para interfaces complejas.

---

### BFF Móvil (App Móvil)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/movil/transacciones` | Transacciones en formato ligero |
| GET | `/api/movil/cuentas` | Resumen de cuentas |
| GET | `/api/movil/saldo/{cuentaId}` | Saldo de una cuenta específica |

**Características:** Respuestas ligeras, datos esenciales, menor consumo de ancho de banda.

---

### BFF Cajeros Automáticos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/cajero/transacciones/{cuentaId}` | Transacciones de una cuenta |
| GET | `/api/cajero/saldo/{cuentaId}` | Saldo de una cuenta |
| GET | `/api/cajero/cuenta/{cuentaId}` | Detalle completo de una cuenta |

**Características:** Interfaz segura, operaciones críticas, consultas rápidas.

---

## Seguridad

La seguridad está configurada con Spring Security y actualmente todos los endpoints están públicos para facilitar el desarrollo. En producción se recomienda:

- **Web:** Acceso público (solo lectura)
- **Móvil:** Autenticación requerida
- **Cajero:** Autenticación requerida con roles específicos

### Credenciales de desarrollo (para H2 Console)
JDBC URL: jdbc:h2:mem:banco_xyz_bff
Usuario: sa
Contraseña: (vacío)

text

## 📊 Base de Datos

El proyecto utiliza H2 Database en memoria con datos precargados desde archivos CSV:

- **Cuentas:** 8 cuentas de ejemplo (`data/intereses.csv`)
- **Transacciones:** 10 transacciones de ejemplo (`data/transacciones.csv`)

### Consultas SQL de ejemplo
```sql
-- Ver todas las cuentas
SELECT * FROM cuentas;

-- Ver transacciones de una cuenta específica
SELECT * FROM transacciones WHERE cuenta_id = '101';