# Ficha Técnica del Proyecto

**Sistema de Trazabilidad de Medicamentos y Cadena de Custodia — Backend API REST**

---

## 1. Identificación General del Proyecto

| Atributo | Detalle |
| :--- | :--- |
| **Nombre del Sistema** | Trazabilidad de Medicamentos — Backend Académico / MVP |
| **Repositorio** | `cala-jhonni1046/trazabilidad-medicamentos-blockchain-backend` |
| **Grupo y Artefacto Maven** | `ies.belgrano` / `medicamentos` (`0.0.1-SNAPSHOT`) |
| **Paquete Base** | `ies.belgrano.medicamentos` |
| **Institución Académica** | Instituto de Educación Superior (IES) Belgrano |
| **Tipo de Solución** | API RESTful Transaccional con Registro de Cadena de Custodia, Monitoreo de Telemetría y Verificación Ciudadana por QR |
| **Prefijo Base de API** | `/api` (Formato de intercambio JSON, marcas de tiempo en UTC) |

---

## 2. Resumen Ejecutivo (Propuesta de Valor)

El **Backend de Trazabilidad de Medicamentos** es una plataforma API REST diseñada para resolver la falta de visibilidad unificada, el riesgo de falsificación y la pérdida de cadena de frío en el suministro farmacéutico.

El sistema permite auditar de extremo a extremo el ciclo de vida de cada medicamento:

- Alta y habilitación de **laboratorios, distribuidoras, farmacias e inspectores de ANMAT**, vinculados mediante enlaces societarios (`EnlaceCUIT`).
- Fabricación de **lotes de producción** y **serialización masiva o unitaria** de envases trazables individuales (`UnidadTrazable`).
- **Despacho logístico** monitoreado por telemetría (**temperatura y GPS**) con cadena de custodia respaldada por hashes SHA-256 encadenados.
- **Recepción en farmacias** (conforme, observada o rechazada) y **bloqueo preventivo en cuarentena** ante incidentes sanitarios o térmicos.
- **Dispensación final** al paciente y **consulta pública abierta mediante código QR**, junto con un canal de **reportes ciudadanos**.

---

## 3. Stack Tecnológico y Entorno de Ejecución

| Componente | Tecnología / Herramienta | Versión / Detalle |
| :--- | :--- | :--- |
| **Lenguaje de Programación** | Java (JDK) | `21` (LTS) |
| **Framework Backend** | Spring Boot | `4.1.1` (`spring-boot-starter-parent`) |
| **Capa Web / API** | Spring Web MVC | `spring-boot-starter-webmvc` (REST API en puerto `8080`) |
| **Persistencia y ORM** | Spring Data JPA / Hibernate | `spring-boot-starter-data-jpa` |
| **Validación de Datos** | Jakarta Bean Validation | `spring-boot-starter-validation` |
| **Motor de Base de Datos** | MySQL | `mysql-connector-j` (`utf8mb4` / `utf8mb4_unicode_ci`) |
| **Documentación de API** | SpringDoc OpenAPI / Swagger UI | `3.1.0` (`springdoc-openapi-starter-webmvc-ui`) |
| **Gestión de Dependencias** | Apache Maven | `pom.xml` / Maven Wrapper (`mvnw 3.3.4`) |
| **Suite de Pruebas** | Spring Boot Test Starters | WebMVC Test, Data JPA Test, Validation Test |

---

## 4. Arquitectura de Software y Patrones de Diseño

El backend sigue una **Arquitectura Modular Orientada al Dominio (*Package-by-Feature*)** combinada con una **Arquitectura en Capas estricta** dentro de cada módulo funcional:

1. **Capa de Presentación (`Controller`)**:
   Expone los endpoints RESTful bajo el prefijo `/api`, gestiona parámetros de ruta/consulta combinables y devuelve respuestas HTTP estandarizadas (`200 OK`, `201 Created`, `204 No Content`).

2. **Capa de Contratos de Datos (`RequestDTO` / `ResponseDTO`)**:
   Desacopla completamente el modelo relacional interno de la interfaz pública JSON. Implementa DTOs especializados para operaciones de transición de estado y resoluciones administrativas.

3. **Capa de Transformación (`Mapper`)**:
   Componentes dedicados por módulo encargados de convertir entre entidades JPA y objetos DTO sin contaminar la lógica de negocio.

4. **Capa de Lógica de Negocio (`Service`)**:
   Orquesta las reglas sanitarias, transiciones válidas de estados (lotes, despachos, cuarentenas, reportes), validaciones cruzadas y generación automática de registros de custodia.

5. **Capa de Acceso a Datos (`Repository` y `Entity`)**:
   Interfaces de Spring Data JPA y entidades persistentes que heredan de una superclase común de auditoría (`BaseEntity` con marcas de tiempo en UTC).

6. **Manejo Centralizado de Excepciones y Utilidades (`utils` y `exceptions`)**:
   - `GlobalExceptionHandler`: Captura global de errores de validación, argumentos inválidos, conflictos de estado/integridad y recursos no encontrados (`ResourceNotFoundException`).
   - `ErrorResponseDTO` y `ApiErrorCode` (**Estándar H0**): Contrato uniforme de respuestas de error para el cliente/frontend (`VALIDATION_ERROR`, `INVALID_ARGUMENT`, `RESOURCE_NOT_FOUND`, `RESOURCE_CONFLICT`, `STATE_CONFLICT`, `INTERNAL_ERROR`).
   - `DemoDataInitializer`: Inicializador automático e idempotente de datos semilla bajo el perfil `demo` (`@Profile("demo")`).

---

## 5. Catálogo de Módulos del Dominio (16 Paquetes)

| # | Módulo (Paquete) | Componentes Clave | Responsabilidad Funcional |
| :---: | :--- | :--- | :--- |
| **1** | `organizacion` | `Organizacion`, `TipoOrganizacion` | Registro y clasificación de actores de la cadena (`LABORATORIO`, `DISTRIBUIDOR`, `FARMACIA`). |
| **2** | `usuario` | `Usuario`, `RolUsuario` | Gestión de usuarios operadores y sus roles (`SUPERADMIN`, `ANMAT`, `LABORATORIO`, `DISTRIBUIDOR`, `FARMACIA`, `PACIENTE`). |
| **3** | `enlaceCUIT` | `EnlaceCUIT`, `EstadoEnlaceCuit`, `EnlaceResolucionRequestDTO` | Solicitud, validación y resolución (`PENDIENTE`, `ACTIVO`, `RECHAZADO`) del vínculo operativo entre laboratorio, distribuidor y farmacia. |
| **4** | `inspector` | `InspectorANMAT`, `EstadoInspector` | Padrón y estado de habilitación (`HABILITADO`, `SUSPENDIDO`) de inspectores de fiscalización sanitaria (ANMAT). |
| **5** | `medicamento` | `Medicamento` | Catálogo maestro de especialidades medicinales, principio activo y requerimiento de cadena de frío. |
| **6** | `lote` | `Lote`, `EstadoLote`, `GenerarUnidadesRequestDTO`, `GeneracionUnidadesResponseDTO` | Gestión de lotes de producción, rangos térmicos, estados del lote y emisión masiva correlativa de unidades (`POST /api/lotes/{id}/unidades`). |
| **7** | `unidadTrazable` | `UnidadTrazable`, `EstadoUnidadTrazable` | Seguimiento unitario (nivel envase/caja) con código QR único y estado actual (`DISPONIBLE`, `DISPENSADA`, `BLOQUEADA`). |
| **8** | `despachologistico` | `DespachoLogistico`, `EstadoDespacho`, `EventoCustodia`, `TipoEventoCustodia`, `TransicionDespachoRequestDTO` | Gestión de envíos entre organizaciones y bitácora encadenada de eventos de custodia (incluyendo `DESPACHO_CANCELADO`, `RECEPCION_RECHAZADA` e `INCIDENTE_EN_RUTA`). |
| **9** | `telemetriatemperatura` | `TelemetriaTemperatura` | Registro de mediciones térmicas asociadas al transporte y detección automática de excursión (`alertaExcursion`) para auditar la cadena de frío. |
| **10** | `telemetriagps` | `TelemetriaGPS` | Registro de coordenadas, velocidad y trazas de geolocalización durante el traslado logístico. |
| **11** | `recepcionfarmacia` | `RecepcionFarmacia`, `EstadoRecepcion` | Acto de recepción en destino con validación de conformidad (`CONFORME`, `OBSERVADA`, `RECHAZADA`) y cierre efectivo del despacho. |
| **12** | `cuarentena` | `Cuarentena`, `EstadoCuarentena`, `CuarentenaResolucionRequestDTO` | Aislamiento preventivo de lotes ante incidentes térmicos o regulatorios (`ABIERTA`) y su posterior resolución (`LIBERADA`, `MANTENIDA`). |
| **13** | `dispensacion` | `Dispensacion` | Registro transaccional del expendio final de la unidad trazable en la farmacia con resguardo de privacidad del DNI del paciente. |
| **14** | `consultapublica` | `ConsultaPublicaController`, `ConsultaPublicaQRResponseDTO`, `ConsultaPublicaService` | Consulta abierta vía código QR para verificar el estado e historial del medicamento protegiendo datos sensibles. |
| **15** | `reporteciudadano` | `ReporteCiudadano`, `EstadoReporte`, `ReporteRevisionRequestDTO`, `ReporteResolucionRequestDTO` | Canal de denuncias ciudadanas sobre unidades sospechosas, con flujo de revisión (`EN_REVISION`) y resolución (`RESUELTO`) por inspectores ANMAT. |
| **16** | `blockchain.registroblockchain` | `RegistroBlockchain` | Asiento de hashes criptográficos demostrativos que respaldan la integridad de los lotes y eventos de custodia. |

---

## 6. Flujo Operativo de Trazabilidad (De Punta a Punta)

1. **Habilitación de Actores**:
   Se registran las organizaciones (`Organizacion`), los usuarios (`Usuario`), los enlaces societarios (`EnlaceCUIT`) y los inspectores (`InspectorANMAT`). Un inspector habilitado aprueba el `EnlaceCUIT` (`ACTIVO`).

2. **Fabricación y Serialización**:
   El laboratorio registra el `Medicamento`, da de alta un `Lote` de producción (estado `CREADO`) y ejecuta la generación individual o masiva de cada `UnidadTrazable` (`QR-<codigoLote>-<NNNNN>`, estado `DISPONIBLE`).

3. **Despacho y Cadena de Custodia**:
   Al trasladar mercadería hacia una farmacia a través de una distribuidora vinculada por un `EnlaceCUIT` activo, se crea un `DespachoLogistico` (`PREPARADO`) y al iniciarlo pasa a `EN_TRANSITO` junto con el lote. Cada cambio de manos o hito genera un `EventoCustodia` con hashes encadenados.

4. **Monitoreo en Tránsito (Telemetría)**:
   Durante el viaje se registran lecturas en `TelemetriaTemperatura` y `TelemetriaGPS`, además de eventuales avisos de `INCIDENTE_EN_RUTA`. Si se detecta una excursión térmica o anomalía, el lote puede ser aislado en `Cuarentena` (`EN_CUARENTENA`).

5. **Recepción y Expendio**:
   La farmacia destinataria registra la `RecepcionFarmacia`:
   - Si es `CONFORME`, el despacho y el lote quedan en `RECIBIDO` y las unidades quedan habilitadas para su `Dispensacion` (pasando a `DISPENSADA`).
   - Si es `OBSERVADA`, el despacho y el lote se marcan como `RECIBIDO`, pero las unidades no se habilitan para dispensación.
   - Si es `RECHAZADA`, el despacho pasa a `CANCELADO` y se asienta el evento `RECEPCION_RECHAZADA`.

6. **Auditoría Ciudadana (QR)**:
   Cualquier consumidor puede consultar el estado e historial público de su unidad mediante `ConsultaPublica` (`/api/public/consulta-qr/{codigoQr}`) y, ante cualquier irregularidad física o de estado, emitir un `ReporteCiudadano`.

---

## 7. Seguridad, Privacidad por Diseño y Configuración

### 7.1 Privacidad en Consulta Pública y Dispensación
- **Consulta Pública (QR)**: Filtra deliberadamente datos sensibles. No expone números de DNI, números de receta médica, correos electrónicos, teléfonos de contacto, nombres de distribuidoras ni coordenadas GPS exactas del transporte.
- **Resguardo del DNI del Paciente**: En `Dispensacion`, el campo opcional `dniPaciente` se almacena solo internamente y está excluido de todos los DTO de respuesta, logs y consultas.

### 7.2 Configuración Desacoplada (12-Factor App)
Las credenciales no se almacenan en el código fuente (`application.properties` solo define `spring.application.name=medicamentos`). La conexión se inyecta mediante variables de entorno:

| Variable de Entorno | Descripción |
| :--- | :--- |
| `SPRING_DATASOURCE_URL` | URL JDBC de conexión a MySQL (ej. `jdbc:mysql://localhost:3306/medicamentos`) |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos MySQL |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos MySQL |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` para bases de desarrollo/demo o `validate` para esquemas preexistentes |

### 7.3 Ejecución y Sembrado de Datos de Demostración
- **Sembrador automático en Java (Perfil `demo`)**:
  ```bash
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
  ```
- **Script SQL de datos ficticios de punta a punta**:
  Disponible en `docs/schema.sql` (utiliza `INSERT IGNORE` sobre los 16 módulos para poblar una cadena completa lista para demostración).

### 7.4 Documentación Interactiva y Técnica
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`
- **Contrato detallado de Endpoints**: `docs/api.md`
- **Notas de Integración y Migraciones MySQL**: `docs/integracion-b10.md`

---

## 8. Alcance Actual del MVP y Hoja de Ruta (Evolución a Producción)

Para efectos de evaluación en Hackathon o defensa académica, se destacan las siguientes definiciones de alcance:

### 8.1 Estado Actual (MVP Académico Funcional)
- Cobertura completa de los **16 dominios funcionales** de trazabilidad farmacéutica.
- Validación de reglas de negocio, máquinas de estado transaccionales y manejo estandarizado de errores (**formato H0**).
- Simulación demostrativa de hashes de custodia encadenados y registros blockchain persistidos localmente.
- Ingesta de telemetría (temperatura y GPS) mediante endpoints REST.

### 8.2 Próximos Pasos (Roadmap Técnico a Producción)
- **Integración Web3 Real**: Conexión del módulo `registroblockchain` con una red distribuida (ej. *Ethereum Sepolia Testnet* mediante *Web3j* y *Smart Contracts*) para anclaje inmutable en cadena.
- **Seguridad y Control de Acceso**: Incorporación de *Spring Security* con autenticación basada en *JWT (JSON Web Tokens)* para aplicar control de acceso estricto basado en `RolUsuario` y vincular cada operador autenticado con su `Organizacion`.
- **Migraciones de Base de Datos**: Incorporación de *Flyway* o *Liquibase* para el versionado determinístico del esquema SQL.
- **Integración IoT Directa**: Conexión con brokers *MQTT* para recepción automática de sensores físicos de temperatura y GPS en tiempo real.
