# Contrato API del backend (B10)

Este documento describe el código de esta rama. Todas las rutas usan JSON y tienen prefijo `/api`. No existe autenticación ni autorización: los IDs de farmacia, inspector u organización declarados por un cliente no prueban su identidad. Swagger UI se genera desde los controladores en `/swagger-ui/index.html`; el esquema OpenAPI está en `/v3/api-docs`. Las rutas de rechazo conservadas por compatibilidad también aparecen allí, aunque no realizan la operación solicitada.

## Estado de implementación

| Área | Estado | Alcance real |
|---|---|---|
| Organizaciones, medicamentos, usuarios, inspectores, lotes y enlaces CUIT | Implementado | CRUD y reglas locales indicadas abajo. `Usuario` administra datos y roles; no inicia sesión. |
| Despachos, eventos de custodia, telemetría y recepciones | Implementado con límites | Telemetría ingresada mediante API; hashes locales demostrativos. La recepción B06 cierra la entrega. |
| Cuarentenas, unidades, dispensaciones, reportes y consulta QR | Implementado con límites | No hay verificación regulatoria, receta clínica ni autenticación del actor. Generación de unidades por lote implementada en `/api/lotes/{id}/unidades`. |
| `/registros-blockchain` | Parcial / demostrativo | CRUD de metadatos declarados con Bean Validation; no envía transacciones a una red blockchain ni verifica firmas. |
| Resolución de recepción `OBSERVADA`, bloqueo explícito de unidad, autenticación y blockchain distribuida | Pendiente / fuera del MVP | No existe endpoint que complete estas capacidades. |

## Convenciones de rutas, cuerpos y estados HTTP

En las tablas, `{id}` es un ID numérico, `R` es el `*RequestDTO` del módulo y `S` es su `*ResponseDTO`; las listas son arreglos de `S`. Los DTO de respuesta proyectan datos y no serializan entidades JPA. Los campos opcionales pueden aparecer como `null`; los `LocalDateTime` se representan como `YYYY-MM-DDTHH:mm:ss` y no contienen zona horaria. La aplicación usa UTC como valor predeterminado en los flujos que asignan fecha automáticamente.

> **Nota sobre códigos HTTP de altas y documentación OpenAPI**: Las operaciones de creación (altas) responden con estado HTTP **`201 Created`**. Nótese que las herramientas de documentación basadas en springdoc / Swagger UI muestran HTTP `200 OK` por defecto para las operaciones `@PostMapping` si el controlador no declara explícitamente anotaciones `@ResponseStatus(HttpStatus.CREATED)` o `@ApiResponse(responseCode = "201")`.

| Módulo | Métodos y rutas disponibles | Entrada / filtros | Salida de éxito |
|---|---|---|---|
| Organizaciones | `GET /organizaciones`, `GET /organizaciones/{id}`, `GET /organizaciones/cuit/{cuit}`, `POST /organizaciones`, `PUT /organizaciones/{id}`, `DELETE /organizaciones/{id}` | `GET` admite `cuit`; `POST/PUT`: `OrganizacionRequestDTO` | `GET/PUT` 200 `OrganizacionResponseDTO` o lista; `POST` 201; `DELETE` 204. |
| Medicamentos | `GET /medicamentos`, `GET /medicamentos/{id}`, `POST /medicamentos`, `PUT /medicamentos/{id}`, `DELETE /medicamentos/{id}` | `POST/PUT`: `MedicamentoRequestDTO` | 200 lista/DTO, 201 DTO, 204 sin cuerpo. |
| Usuarios | `GET /usuarios`, `GET /usuarios/{id}`, `POST /usuarios`, `PUT /usuarios/{id}`, `PATCH /usuarios/{id}/activar`, `PATCH /usuarios/{id}/desactivar`, `DELETE /usuarios/{id}` | `POST/PUT`: `UsuarioRequestDTO`; `PATCH` sin cuerpo | 200 lista/DTO, 201 DTO, 204 sin cuerpo. |
| Inspectores | `GET /inspectores`, `GET /inspectores/{id}`, `GET /inspectores/usuario/{usuarioId}`, `POST /inspectores`, `PUT /inspectores/{id}`, `DELETE /inspectores/{id}` | `GET` admite filtro opcional `dni` (normaliza puntos y espacios); `POST/PUT`: `InspectorANMATRequestDTO` | 200 lista/DTO, 201 DTO, 204 sin cuerpo. El DNI es visible en el response (limitación conocida del MVP). |
| Lotes | `GET /lotes`, `GET /lotes/{id}`, `POST /lotes`, `POST /lotes/{id}/unidades`, `PUT /lotes/{id}`, `DELETE /lotes/{id}` | `GET`: filtros combinables `medicamentoId`, `laboratorioId`, `estado` (404 si ID no existe); `POST /lotes`: `LoteRequestDTO`; `POST /lotes/{id}/unidades`: `GenerarUnidadesRequestDTO` (`{"cantidad": n}`); `PUT`: `LoteRequestDTO` | 200 lista/DTO; `POST /lotes` 201 `LoteResponseDTO`; `POST /lotes/{id}/unidades` 201 `GeneracionUnidadesResponseDTO`; `PUT` 200; `DELETE` 204. |
| Enlaces CUIT | `GET /enlaces-cuit`, `GET /enlaces-cuit/{id}`, `POST /enlaces-cuit`, `PUT /enlaces-cuit/{id}`, `POST /enlaces-cuit/{id}/aprobar`, `POST /enlaces-cuit/{id}/rechazar`, `DELETE /enlaces-cuit/{id}` | `GET`: filtros combinables `estado`, `laboratorioId`, `distribuidorId`, `farmaciaId`; `POST`: `EnlaceCUITRequestDTO`; `PUT`: `EnlaceCUITRequestDTO` con `@Valid`; aprobar/rechazar: `EnlaceResolucionRequestDTO` | 200 lista/DTO, 201 DTO, 204 sin cuerpo. |
| Despachos | `GET /despachos-logisticos`, `GET /despachos-logisticos/{id}`, `POST /despachos-logisticos`, `PUT /despachos-logisticos/{id}`, `POST /despachos-logisticos/{id}/iniciar`, `POST /despachos-logisticos/{id}/cancelar`, `DELETE /despachos-logisticos/{id}` | `GET`: `loteId`, `distribuidorId`, `estado`, `laboratorioId`, `farmaciaDestinoId` combinados con AND; `POST/PUT`: `DespachoLogisticoRequestDTO`; `/iniciar`: actualiza lote a `EN_TRANSITO` si está `CREADO` (rechazo 409 si `EN_CUARENTENA` o `CERRADO`) | 200 lista/DTO, 201 DTO, 204 sin cuerpo. |
| Cierre B04 conservado | `POST /despachos-logisticos/{id}/entregar`, `POST /despachos-logisticos/{id}/recibir` | Sin cuerpo | `404` si no existe; si existe, `409 STATE_CONFLICT`. La recepción B06 es el cierre efectivo. |
| Eventos de custodia | `GET /despachos-logisticos/{id}/eventos`, `GET /despachos-logisticos/{id}/eventos-custodia`, `POST` en ambas rutas | `POST`: `EventoCustodiaRequestDTO` (admite tipos incluyendo `INCIDENTE_EN_RUTA`) | `GET` 200 lista `EventoCustodiaResponseDTO`; `POST` 201 DTO. |
| Temperatura | `GET /telemetrias-temperatura`, `GET /telemetrias-temperatura/{id}`, `POST /telemetrias-temperatura` | `GET`: `despachoLogisticoId` o alias `despachoId`; `POST`: `TelemetriaTemperaturaRequestDTO` | 200 lista/DTO, 201 DTO. |
| GPS | `GET /telemetrias-gps`, `GET /telemetrias-gps/{id}`, `POST /telemetrias-gps` | `GET`: `despachoLogisticoId` o alias `despachoId`; `POST`: `TelemetriaGPSRequestDTO` | 200 lista/DTO, 201 DTO. |
| Recepciones | `GET /recepciones-farmacia`, `GET /recepciones-farmacia/{id}`, `POST /recepciones-farmacia`, `PUT /recepciones-farmacia/{id}`, `DELETE /recepciones-farmacia/{id}` | `GET`: filtros combinables `despachoLogisticoId`/`despachoId`, `farmaciaId`, `estadoConformidad` (404 si ID no existe); `POST/PUT`: `RecepcionFarmaciaRequestDTO` | 200 lista/DTO, 201 DTO, 204 sin cuerpo si la operación se admite. |
| Cuarentenas | `GET /cuarentenas`, `GET /cuarentenas/{id}`, `GET /cuarentenas/abierta?loteIdParam={id}`, `GET /cuarentenas/lote/{loteId}/abierta`, `POST /cuarentenas`, `POST /cuarentenas/{id}/resolver`, `POST /cuarentenas/{id}/liberar`, `POST /cuarentenas/{id}/mantener`, `PUT /cuarentenas/{id}`, `DELETE /cuarentenas/{id}` | `GET` lista: `loteId`, `estado`; abrir/editar: `CuarentenaRequestDTO`; resolver: `CuarentenaResolucionRequestDTO`; liberar/mantener: cuerpo opcional de ese DTO | 200 lista/DTO, 201 DTO, 204 solo si se admite. |
| Unidades | `GET /unidades-trazables`, `GET /unidades-trazables/{id}`, `GET /unidades-trazables/qr/{codigoQr}`, `POST /unidades-trazables`, `PUT /unidades-trazables/{id}`, `DELETE /unidades-trazables/{id}` | `GET`: filtros combinables `loteId`, `codigoQr`, `estado` (404 si `loteId` no existe); `POST/PUT`: `UnidadTrazableRequestDTO` | 200 lista/DTO, 201 DTO; `DELETE` siempre se rechaza. |
| Dispensaciones | `GET /dispensaciones`, `GET /dispensaciones/{id}`, `GET /dispensaciones/unidad/{unidadId}`, `POST /dispensaciones`, `PUT /dispensaciones/{id}`, `DELETE /dispensaciones/{id}` | `GET`: `farmaciaId`, `unidadTrazableId`; `POST`: `DispensacionRequestDTO`; `PUT` acepta cuerpo opcional pero siempre se rechaza | 200 lista/DTO, 201 DTO; `PUT/DELETE` se rechazan. |
| Reportes | `GET /reportes-ciudadanos`, `GET /reportes-ciudadanos/{id}`, `POST /reportes-ciudadanos`, `POST` y `PUT /reportes-ciudadanos/{id}/iniciar-revision`, `POST` y `PUT /reportes-ciudadanos/{id}/resolver`, `PUT /reportes-ciudadanos/{id}`, `DELETE /reportes-ciudadanos/{id}` | `GET`: filtros combinables `estado`, `unidadTrazableId`, `inspectorId`; alta/edición: `ReporteCiudadanoRequestDTO`; transiciones: `ReporteRevisionRequestDTO` o `ReporteResolucionRequestDTO` opcionales | 200 lista/DTO, 201 DTO; `DELETE` se rechaza. |
| Consulta QR pública | `GET /public/consulta-qr/{codigoQr}`, `GET /consulta-publica/qr/{codigoQr}` | Solo código QR en ruta | 200 `ConsultaPublicaQRResponseDTO`. |
| Registros demostrativos | `GET /registros-blockchain`, `GET /registros-blockchain/{id}`, `POST /registros-blockchain`, `PUT /registros-blockchain/{id}`, `DELETE /registros-blockchain/{id}` | `POST/PUT`: `RegistroBlockchainRequestDTO` con `@Valid` | Lista 200 o 204 si está vacía; DTO 200, alta 201, borrado 204. |

Los códigos de éxito de la tabla dependen de que el servicio acepte la operación. Una ruta `DELETE` visible no implica que el recurso pueda borrarse. Las consultas inexistentes devuelven 404 en los servicios que lanzan `ResourceNotFoundException`; los registros demostrativos también usan 404 H0 tras B10. En listas, la ausencia de coincidencias suele devolver `200 []`, excepto registros demostrativos (`204`).

## Cuerpos JSON y DTO de respuesta

Los siguientes cuerpos contienen los campos mínimos o habituales aceptados por cada alta. Los identificadores son ejemplos de registros relacionados que deben existir. El alta devuelve `S` del módulo, con `id`, campos del dominio y normalmente `fechaCreacion`/`fechaActualizacion`; los nombres exactos de cada `S` se indican debajo. Los campos omitidos son opcionales, no tienen valor implícito salvo cuando el servicio lo establece.

| Módulo | Ejemplo de request JSON | Response DTO y campos propios relevantes |
|---|---|---|
| Organizaciones | `{"cuit":"30123456789","nombre":"Laboratorio Ejemplo","tipo":"LABORATORIO"}` | `OrganizacionResponseDTO`: `id`, `cuit`, `nombre`, `tipo`, fechas. |
| Medicamentos | `{"nombre":"Producto ejemplo","principioActivo":"Sustancia ejemplo","requiereCadenaFrio":false}` | `MedicamentoResponseDTO`: `id`, esos campos, fechas. |
| Usuarios | `{"email":"usuario@example.invalid","rol":"LABORATORIO"}` | `UsuarioResponseDTO`: `id`, `email`, `rol`, `activo`, fechas. No hay contraseña ni token. |
| Inspectores | `{"usuarioId":1,"nombreCompleto":"Inspector de ejemplo"}` | `InspectorANMATResponseDTO`: `id`, `usuarioId`, `usuarioEmail`, `usuarioRol`, `nombreCompleto`, `estado`, otros campos internos de inspector y fechas. |
| Lotes | `{"codigoLote":"LOTE-001","medicamentoId":1,"laboratorioId":1}` | `LoteResponseDTO`: `id`, `codigoLote`, IDs/nombres relacionados, `tempMin`, `tempMax`, `fechaHoraFabricacion`, `estado`, fechas. |
| Enlaces CUIT | `{"laboratorioId":1,"distribuidorId":2,"farmaciaId":3}` | `EnlaceCUITResponseDTO`: `id`, IDs/nombres/CUIT de las tres organizaciones, `estado`, inspector y datos de aprobación, fechas. |
| Despachos | `{"numeroRemito":"R-001","loteId":1,"distribuidorId":2,"enlaceCuitId":1}` | `DespachoLogisticoResponseDTO`: `id`, remito, relaciones, farmacia destino derivada del enlace, temperaturas, `estado`, fechas. |
| Eventos | `{"tipo":"TELEMETRIA_REGISTRADA","descripcion":"Lectura recibida"}` | `EventoCustodiaResponseDTO`: `id`, `tipo`, `fechaHoraEvento`, `despachoLogisticoId`, actor, descripción, `hashAnterior`, `hashEvento`, fechas. |
| Temperatura | `{"sensorId":"S-001","temperatura":4.5,"fechaHora":"2026-10-20T12:00:00","despachoLogisticoId":1}` | `TelemetriaTemperaturaResponseDTO`: lectura, `alertaExcursion`, despacho, observaciones, fechas. |
| GPS | `{"latitud":-32.89,"longitud":-68.84,"fechaHora":"2026-10-20T12:00:00","despachoLogisticoId":1}` | `TelemetriaGPSResponseDTO`: coordenadas y metadatos de lectura, despacho, fechas. Uso interno; no aparece en consulta QR. |
| Recepciones | `{"numeroComprobante":"C-001","farmaciaId":3,"despachoLogisticoId":1,"estadoConformidad":"CONFORME","cadenaFrioIntacta":true}` | `RecepcionFarmaciaResponseDTO`: `id`, comprobante, farmacia, despacho, estado, cadena de frío, fechas. `despachoId` es alias de entrada. |
| Cuarentenas | `{"codigoCuarentena":"Q-001","motivo":"Revisión interna","loteId":1}` | `CuarentenaResponseDTO`: `id`, código, motivo, lote, inspector, `estado`, resolución y fechas. Uso interno. |
| Unidades | `{"codigoUnicoQR":"QR-001","loteId":1}` | `UnidadTrazableResponseDTO`: `id`, QR, lote, `estado`, fechas. |
| Dispensaciones | `{"unidadTrazableId":1,"farmaciaId":3}` | `DispensacionResponseDTO`: `id`, QR, farmacia, receta opcional, fechas; omite `dniPaciente`. |
| Reportes | `{"motivoReporte":"Envase dañado","codigoUnicoQR":"QR-001"}` | `ReporteCiudadanoResponseDTO`: `id`, motivo, unidad, inspector, `estado`, fechas. No se publica en el QR. |
| Registro demostrativo | `{"transactionHash":"hash-de-ejemplo","bloque":1,"confirmado":false}` | `RegistroBlockchainResponseDTO`: `id`, hash declarado, bloque, firma declarada, marca de tiempo, confirmado, fechas. No certifica cadena real. |

Ejemplo completo de respuesta de alta de medicamento (`201`):

```json
{"id":1,"nombre":"Producto ejemplo","principioActivo":"Sustancia ejemplo","requiereCadenaFrio":false,"fechaCreacion":"2026-10-20T12:00:00","fechaActualizacion":"2026-10-20T12:00:00"}
```

Resolución de enlace (`POST /enlaces-cuit/{id}/aprobar` o `/rechazar`): `{"inspectorId":1,"numeroDisposicionANMAT":"DISP-001"}`. Resolución de cuarentena: `{"estado":"LIBERADA","resolucionFinal":"Revisión concluida"}`; los alias `/liberar` y `/mantener` fijan el estado y aceptan cuerpo opcional. Transición de despacho: `{"observaciones":"Salida registrada"}`. Revisión de reporte: `{"inspectorAsignadoId":1}`.

## Estados y transiciones observadas

| Recurso | Estados | Alta y transiciones reales |
|---|---|---|
| Enlace CUIT | `PENDIENTE`, `ACTIVO`, `RECHAZADO` | Alta pendiente; aprobar/rechazar mediante inspector habilitado. El despacho exige `ACTIVO`. |
| Lote | `CREADO`, `EN_TRANSITO`, `RECIBIDO`, `EN_CUARENTENA`, `LIBERADO`, `CERRADO` | Alta creado; recepción conforme u observada lo lleva a recibido; apertura de cuarentena a en cuarentena; resolución puede liberar o mantener la retención. Iniciar un despacho transiciona de forma atómica el lote de `CREADO` a `EN_TRANSITO`; si el lote está en `EN_CUARENTENA` o `CERRADO`, la operación se rechaza con `409 STATE_CONFLICT`. Admite generación masiva de unidades vía `POST /api/lotes/{id}/unidades`. |
| Despacho | `PREPARADO`, `EN_TRANSITO`, `RECIBIDO`, `CANCELADO` | Alta preparado; `/iniciar` pasa a tránsito (y transiciona el lote si está creado); recepción B06 conforme/observada pasa a recibido; cancelación B04 o recepción rechazada lo cancela. `/entregar` y `/recibir` devuelven 409. |
| Recepción | `CONFORME`, `OBSERVADA`, `RECHAZADA` | Se registra mediante `POST`; observada deja despacho recibido pero no habilita dispensación B08; rechazada cancela despacho. No hay resolución posterior de observación. |
| Cuarentena | `ABIERTA`, `LIBERADA`, `MANTENIDA` | Apertura, resolución por `/resolver`, `/liberar` o `/mantener`; una cuarentena resuelta no puede editarse con `PUT`. |
| Unidad | `DISPONIBLE`, `DISPENSADA`, `BLOQUEADA` | Alta disponible (individual o por lote); dispensación la cambia a dispensada. `BLOQUEADA` existe y genera advertencia pública, pero no hay ruta propia para bloquear. |
| Reporte | `PENDIENTE`, `EN_REVISION`, `RESUELTO` | Alta pendiente; revisión y resolución tienen rutas específicas; `PUT` genérico no cambia estado ni modifica reportes resueltos. |
| Evento de custodia | Tipos `LOTE_DESPACHADO`, `CUSTODIA_ASUMIDA`, `TELEMETRIA_REGISTRADA`, `RECEPCION_CONFIRMADA`, `RECEPCION_RECHAZADA`, `DESPACHO_CANCELADO`, `CUARENTENA_ABIERTA`, `CUARENTENA_RESUELTA`, `UNIDAD_DISPENSADA`, `INCIDENTE_EN_RUTA` | Hitos asociados a despacho; `INCIDENTE_EN_RUTA` permite al distribuidor notificar avisos de retraso en ruta. `hashAnterior` y `hashEvento` son hashes SHA-256 locales demostrativos, no bloques distribuidos. |

Las transiciones inválidas lanzan `IllegalStateException` y se traducen a `409 STATE_CONFLICT`. Una relación incompatible suele producir `400 INVALID_ARGUMENT`; un ID relacionado inexistente produce `404 RESOURCE_NOT_FOUND`; un identificador duplicado o una restricción de persistencia, `409 RESOURCE_CONFLICT`.

## Error común H0

Todas las respuestas de error siguen el formato estandarizado H0:

```json
{"timestamp":"2026-10-20T12:00:00Z","status":400,"code":"VALIDATION_ERROR","message":"Error de validación","path":"/api/medicamentos","fieldErrors":[{"field":"nombre","message":"El nombre es obligatorio"}]}
```

`fieldErrors` es `null` para errores sin desglose por campo. El manejador global (`GlobalExceptionHandler`) mapea de forma consistente:
- `VALIDATION_ERROR` (400): errores de Bean Validation (`MethodArgumentNotValidException`, `@Valid`).
- `INVALID_ARGUMENT` (400): argumentos de negocio inválidos (`IllegalArgumentException`), cuerpos de solicitud ilegibles o tipos/enums inválidos en JSON (`HttpMessageNotReadableException`), parámetros con tipos no coincidentes (`MethodArgumentTypeMismatchException`, ej. `/api/lotes/abc` o `?estado=INVALIDO`), y parámetros obligatorios faltantes (`MissingServletRequestParameterException`).
- `RESOURCE_NOT_FOUND` (404): recursos solicitados inexistentes (`ResourceNotFoundException`) y rutas web inexistentes (`NoResourceFoundException`).
- `RESOURCE_CONFLICT` (409): conflictos de unicidad o restricciones de integridad en base de datos (`DataIntegrityViolationException`).
- `STATE_CONFLICT` (409): transiciones de estado no permitidas para el recurso actual (`IllegalStateException`).
- `INVALID_ARGUMENT` (405): método HTTP no permitido para la ruta invocada (`HttpRequestMethodNotSupportedException`). Se seleccionó el código existente más representativo del contrato.
- `INTERNAL_ERROR` (500): excepciones genéricas imprevistas como último recurso, sin exponer trazas de pila ni datos sensibles.

`UNAUTHORIZED` y `FORBIDDEN` existen en el enum compartido, pero no hay autenticación ni rutas que los emitan como política de acceso. La respuesta pública no incluye DNI, correo, contactos ni GPS exacto; los módulos internos sí pueden devolver datos según su DTO y carecen de control de acceso en este MVP.

## Discrepancias y pendientes técnicos

- **Autenticación y autorización**: los endpoints administrativos no cuentan con login, token JWT ni restricción por roles de acceso; cualquier cliente puede invocar cualquier ruta. Los roles persistidos son solo valores informativos (`SUPERADMIN`, `ANMAT`, `LABORATORIO`, `DISTRIBUIDOR`, `FARMACIA`, `PACIENTE`).
- **Visibilidad del DNI de inspectores**: el endpoint `GET /api/inspectores` y los DTO de inspectores incluyen el DNI oficial con fines de auditoría demostrativa. Al no contar con autenticación, este dato es visible para cualquier cliente del MVP (limitación conocida).
- **Vínculo permanente Usuario ↔ Organización**: no se encuentra implementado en el backend; el frontend resuelve la asignación de identidad mediante sesión simulada.
- **Cuarentena automática por excursión**: ante lecturas de temperatura fuera de rango en telemetría, se registra `alertaExcursion=true`, pero el paso a `EN_CUARENTENA` permanece como operación manual.
- **Campos de auditoría de rechazo en Enlace CUIT**: no se persiste qué inspector ni en qué fecha exacta se produjo un rechazo de enlace.
- **Vínculo de RegistroBlockchain con lote/evento**: el módulo funciona como un CRUD de metadatos declarados de demostración con Bean Validation; no implementa vinculación criptográfica obligatoria con el lote ni conexión a una red blockchain distribuida.
- **Migración requerida en MySQL**: para bases MySQL existentes, agregar `INCIDENTE_EN_RUTA` al tipo enum de `eventos_custodia` requiere un `ALTER TABLE` manual (ver detalle en `docs/integracion-b10.md`), ya que `ddl-auto=update` no modifica enums nativos de columnas existentes.

---

# Detalle B08: Unidades Trazables y Dispensación

> **Alcance académico**: Este MVP no implementa autenticación, autorización, recetas médicas, pagos ni validación clínica.
> El `farmaciaId` recibido en el request **no prueba identidad**; cualquier cliente puede enviar cualquier ID.

---

## 1. Unidades Trazables

Base path: `/api/unidades-trazables`

### 1.1 Crear unidad trazable

```
POST /api/unidades-trazables
```

**Request body**:
```json
{
  "codigoUnicoQR": "QR-UNIT-001",
  "loteId": 1
}
```

| Campo          | Tipo   | Obligatorio | Validación                          |
|----------------|--------|-------------|-------------------------------------|
| `codigoUnicoQR`| String | Sí          | No blanco, máx 160 chars, único     |
| `loteId`       | Long   | Sí          | Debe referenciar un lote existente   |

- El estado se asigna automáticamente como `DISPONIBLE`. El cliente **no puede** elegir otro estado.
- No se permite crear unidades para lotes en estado `CERRADO`.

**Response** (`201 Created`):
```json
{
  "id": 1,
  "codigoUnicoQR": "QR-UNIT-001",
  "loteId": 1,
  "codigoLote": "LOTE-2024-001",
  "estado": "DISPONIBLE",
  "fechaCreacion": "2026-10-20T19:00:00",
  "fechaActualizacion": "2026-10-20T19:00:00"
}
```

**Errores posibles**:
| HTTP | Código              | Condición                                    |
|------|---------------------|----------------------------------------------|
| 400  | VALIDATION_ERROR    | Campos obligatorios faltantes o inválidos     |
| 404  | RESOURCE_NOT_FOUND  | Lote no encontrado                            |
| 409  | RESOURCE_CONFLICT   | Código QR duplicado                           |
| 409  | STATE_CONFLICT      | Lote en estado CERRADO                        |

### 1.2 Consultar unidad por ID

```
GET /api/unidades-trazables/{id}
```

**Response** (`200 OK`): Mismo formato que la respuesta de creación.

**Errores**: `404` si no existe.

### 1.3 Consultar unidad por código QR

```
GET /api/unidades-trazables/qr/{codigoQr}
```

**Response** (`200 OK`): Mismo formato que la respuesta de creación.

**Errores**: `404` si no existe.

### 1.4 Listar unidades (con filtros opcionales)

```
GET /api/unidades-trazables
GET /api/unidades-trazables?loteId=1
GET /api/unidades-trazables?codigoQr=QR-UNIT-001
```

**Response** (`200 OK`): Array de `UnidadTrazableResponseDTO`.

**Errores**: `404` si el `loteId` no existe.

### 1.5 Actualizar unidad (PUT)

```
PUT /api/unidades-trazables/{id}
```

**Restricciones**:
- **No se permite** cambiar el `loteId` de una unidad ya registrada.
- **No se permite** cambiar el `estado` mediante PUT genérico.
- Solo se puede actualizar el `codigoUnicoQR` si es distinto y no duplicado.

**Errores**: `409 STATE_CONFLICT` si intenta cambiar lote o estado.

### 1.6 Eliminar unidad

```
DELETE /api/unidades-trazables/{id}
```

**Restricción**: No se permite eliminar unidades trazables para preservar el registro y trazabilidad.

**Error**: `409 STATE_CONFLICT` siempre.

---

## 2. Dispensaciones

Base path: `/api/dispensaciones`

### 2.1 Registrar dispensación

```
POST /api/dispensaciones
```

**Request body**:
```json
{
  "unidadTrazableId": 1,
  "farmaciaId": 5,
  "recetaNumero": "REC-2024-001",
  "fechaDispensacion": "2026-10-20T15:30:00"
}
```

| Campo              | Tipo          | Obligatorio | Validación                      |
|--------------------|---------------|-------------|---------------------------------|
| `unidadTrazableId` | Long          | Sí          | Unidad existente y DISPONIBLE   |
| `farmaciaId`       | Long          | Sí          | Organización tipo FARMACIA      |
| `recetaNumero`     | String        | No          | Máx 100 chars                   |
| `dniPaciente`      | String        | No          | Máx 16 chars                    |
| `fechaDispensacion`| LocalDateTime | No          | Si ausente, se usa UTC actual   |

> **Privacidad del DNI**: El `dniPaciente` se persiste únicamente en la tabla de dispensaciones como registro académico interno. **Nunca se expone** en responses, consultas públicas, logs, eventos, hashes ni errores.

**Condiciones de elegibilidad** (todas deben cumplirse):

1. **Unidad existe y está DISPONIBLE**: La unidad trazable referenciada debe existir y tener estado `DISPONIBLE`.
2. **No existe otra dispensación para la unidad**: Verifica tanto por repositorio (`existsByUnidadTrazableId`) como por relación inversa (`unidad.getDispensacion()`).
3. **Lote existe y no está en cuarentena ni en estado no dispensable**:
   - El lote asociado a la unidad debe existir.
   - No puede estar en estado `EN_CUARENTENA` ni `CERRADO`.
   - No debe tener cuarentenas en estado `ABIERTA` registradas en la tabla `cuarentenas`.
   - Solo se permite dispensar si el lote está en estado `RECIBIDO` o `LIBERADO`.
4. **Farmacia válida**: La organización referenciada por `farmaciaId` debe existir y ser de tipo `FARMACIA`.
5. **Recepción válida conforme a B06**: Debe existir al menos una `RecepcionFarmacia` para el lote en esa farmacia que cumpla:
   - Estado de conformidad **`CONFORME`**. `OBSERVADA` no habilita la dispensación; este MVP no incluye un endpoint para resolverla posteriormente.
   - Despacho logístico asociado en estado `RECIBIDO`.
   - Enlace CUIT del despacho en estado `ACTIVO`.
   - La farmacia del enlace CUIT coincide con la farmacia declarada en la dispensación.

**Transacción atómica**: El cambio de estado de la unidad (`DISPONIBLE` → `DISPENSADA`) y la creación del registro de dispensación ocurren dentro de la misma transacción `@Transactional`.

**Response** (`201 Created`):
```json
{
  "id": 1,
  "recetaNumero": "REC-2024-001",
  "unidadTrazableId": 1,
  "codigoUnicoQR": "QR-UNIT-001",
  "farmaciaId": 5,
  "farmaciaNombre": "Farmacia Central",
  "farmaciaCuit": "30123456789",
  "fechaDispensacion": "2026-10-20T15:30:00",
  "fechaCreacion": "2026-10-20T19:00:00",
  "fechaActualizacion": "2026-10-20T19:00:00"
}
```

> Notar que **`dniPaciente` no aparece** en la respuesta.

**Errores posibles**:
| HTTP | Código              | Condición                                                    |
|------|---------------------|--------------------------------------------------------------|
| 400  | VALIDATION_ERROR    | Campos obligatorios faltantes                                 |
| 400  | INVALID_ARGUMENT    | Organización no es tipo FARMACIA                              |
| 404  | RESOURCE_NOT_FOUND  | Unidad, lote o farmacia no encontrados                        |
| 409  | RESOURCE_CONFLICT   | Ya existe dispensación para la unidad                          |
| 409  | STATE_CONFLICT      | Unidad no DISPONIBLE, lote en cuarentena/cerrado, sin recepción válida |

### 2.2 Consultar dispensación por ID

```
GET /api/dispensaciones/{id}
```

**Response** (`200 OK`): Mismo formato que la respuesta de creación (sin `dniPaciente`).

### 2.3 Consultar dispensación por unidad trazable

```
GET /api/dispensaciones/unidad/{unidadId}
```

**Response** (`200 OK`): Una `DispensacionResponseDTO`.

**Errores**: `404` si no existe dispensación para esa unidad.

### 2.4 Listar dispensaciones (con filtros opcionales)

```
GET /api/dispensaciones
GET /api/dispensaciones?farmaciaId=5
GET /api/dispensaciones?unidadTrazableId=1
GET /api/dispensaciones?farmaciaId=5&unidadTrazableId=1
```

**Response** (`200 OK`): Array de `DispensacionResponseDTO`, ordenado por fecha de dispensación descendente.

### 2.5 Actualizar dispensación (PUT)

```
PUT /api/dispensaciones/{id}
```

**Restricción**: No se permite modificar una dispensación registrada.

**Error**: `409 STATE_CONFLICT` siempre.

### 2.6 Eliminar dispensación

```
DELETE /api/dispensaciones/{id}
```

**Restricción**: No se permite eliminar registros de dispensación para preservar trazabilidad.

**Error**: `409 STATE_CONFLICT` siempre.

---

## 3. Estados

### Estados de Unidad Trazable (`EstadoUnidadTrazable`)
| Estado       | Descripción                              |
|-------------|------------------------------------------|
| `DISPONIBLE` | Unidad lista para dispensación            |
| `DISPENSADA`  | Unidad ya entregada a paciente           |
| `BLOQUEADA`   | Unidad retenida (ej. por cuarentena)     |

### Estados de Lote que permiten dispensación
Solo `RECIBIDO` y `LIBERADO`.

---

## 4. Formato de errores

Todas las respuestas de error siguen el formato estándar H0:

```json
{
  "timestamp": "2026-10-20T19:00:00.000Z",
  "status": 409,
  "code": "STATE_CONFLICT",
  "message": "La operación no está permitida para el estado actual del recurso.",
  "path": "/api/dispensaciones",
  "fieldErrors": null
}
```

Para errores de validación (`400`), `fieldErrors` contiene la lista:
```json
{
  "fieldErrors": [
    { "field": "unidadTrazableId", "message": "El ID de unidad trazable es obligatorio." }
  ]
}
```

---

## 5. Identificación de farmacia (sin autenticación)

El MVP académico **no tiene autenticación ni autorización**. El `farmaciaId` en el request de dispensación es un ID declarado por el cliente. No existe verificación de que el llamante sea realmente esa farmacia.

Esto es una **limitación aceptada** del alcance académico. En un sistema productivo, la identidad de la farmacia se obtendría del token de sesión/JWT.

---

## 6. Privacidad del DNI

- El `dniPaciente` se acepta **opcionalmente** en el request de dispensación.
- Se persiste internamente en la tabla `dispensaciones` como registro académico.
- **No se expone** en:
  - Responses de dispensación (`DispensacionResponseDTO`)
  - Consultas públicas
  - Logs de aplicación
  - Eventos o hashes públicos
  - Mensajes de error

---

## 7. Generación masiva de unidades trazables por lote

Base path: `/api/lotes/{id}/unidades`

```
POST /api/lotes/{id}/unidades
```

Genera en una sola transacción un lote de $n$ unidades trazables en estado `DISPONIBLE`, asignándoles códigos QR correlativos con formato `QR-<codigoLote>-<NNNNN>` (con relleno de ceros a 5 dígitos). La numeración continúa correlativamente desde la última unidad existente del lote si ya hubiera unidades registradas.

**Request body**:
```json
{
  "cantidad": 10
}
```

| Campo | Tipo | Obligatorio | Validación |
|---|---|---|---|
| `cantidad` | Integer | Sí | Entero entre 1 y 1000 inclusive (`@Valid`) |

**Response** (`201 Created`):
```json
{
  "loteId": 1,
  "codigoLote": "LOTE-2024-001",
  "cantidadGenerada": 10,
  "primerCodigo": "QR-LOTE-2024-001-00001",
  "ultimoCodigo": "QR-LOTE-2024-001-00010"
}
```

**Errores posibles**:
| HTTP | Código | Condición |
|---|---|---|
| 400 | VALIDATION_ERROR / INVALID_ARGUMENT | `cantidad` ausente, menor a 1 o mayor a 1000. |
| 404 | RESOURCE_NOT_FOUND | Lote no encontrado para el `{id}` provisto. |
| 409 | STATE_CONFLICT | El lote se encuentra en estado `CERRADO` o `EN_CUARENTENA`. |
| 409 | RESOURCE_CONFLICT | Alguno de los códigos calculados ya existe previamente en el sistema. |

---

# API — B09: Reportes Ciudadanos y Consulta Pública por QR

> **Alcance académico**: Este MVP no cuenta con autenticación ni autorización. 
> El endpoint de consulta pública no certifica autenticidad sanitaria ante la ANMAT ni se conecta a una red blockchain distribuida de producción. Los hashes generados son con fines demostrativos.

---

## 8. Reportes Ciudadanos (Rutas Administrativas)

Base path: `/api/reportes-ciudadanos`

### 8.1 Crear reporte ciudadano

```
POST /api/reportes-ciudadanos
```

Permite a un ciudadano registrar un reporte o reclamo sobre un medicamento o unidad trazable (QR opcional).

**Request body**:
```json
{
  "motivoReporte": "El empaque presentaba roturas y alteración del sello de seguridad.",
  "codigoUnicoQR": "QR-UNIT-001",
  "fechaHoraReporte": "2026-10-20T20:00:00"
}
```

| Campo               | Tipo          | Obligatorio | Validación / Regla                                         |
|---------------------|---------------|-------------|------------------------------------------------------------|
| `motivoReporte`     | String        | Sí          | No en blanco, máx. 1000 caracteres.                       |
| `codigoUnicoQR`     | String        | No          | Máx. 160 caracteres. Si se envía, debe existir la unidad. |
| `unidadTrazableId`  | Long          | No          | Si se envía, debe existir la unidad trazable.              |
| `fechaHoraReporte`  | LocalDateTime | No          | Si no se envía, se asigna la fecha y hora actual en UTC.  |

**Reglas de negocio**:
- El reporte siempre se inicializa en estado `PENDIENTE`. El cliente **no puede** crearlo en estado `EN_REVISION` ni `RESUELTO`.
- No se puede asignar inspector en el momento de creación ciudadana.
- Si se indica `codigoUnicoQR`, se verifica su existencia en el sistema; si no existe, devuelve `404 RESOURCE_NOT_FOUND`.

**Response** (`201 Created`):
```json
{
  "id": 1,
  "motivoReporte": "El empaque presentaba roturas y alteración del sello de seguridad.",
  "fechaHoraReporte": "2026-10-20T20:00:00",
  "estado": "PENDIENTE",
  "unidadTrazableId": 1,
  "codigoUnicoQR": "QR-UNIT-001",
  "inspectorAsignadoId": null,
  "inspectorNombre": null,
  "fechaCreacion": "2026-10-20T20:00:00",
  "fechaActualizacion": "2026-10-20T20:00:00"
}
```

**Errores posibles**:
| HTTP | Código              | Condición                                                 |
|------|---------------------|-----------------------------------------------------------|
| 400  | VALIDATION_ERROR    | `motivoReporte` vacío o supera los 1000 caracteres.       |
| 404  | RESOURCE_NOT_FOUND  | Unidad trazable no encontrada para el QR o ID provisto.   |

---

### 8.2 Consultar reporte por ID

```
GET /api/reportes-ciudadanos/{id}
```

**Response** (`200 OK`): Mismo formato que la respuesta de creación.

**Errores**: `404 RESOURCE_NOT_FOUND` si no existe.

---

### 8.3 Listar reportes ciudadanos

```
GET /api/reportes-ciudadanos
GET /api/reportes-ciudadanos?estado=PENDIENTE
GET /api/reportes-ciudadanos?unidadTrazableId=1
GET /api/reportes-ciudadanos?inspectorId=2
```

Filtros opcionales por query parameters:
- `estado` (`PENDIENTE`, `EN_REVISION`, `RESUELTO`)
- `unidadTrazableId`
- `inspectorId`

**Response** (`200 OK`): Lista JSON con los reportes correspondientes (vacía `[]` si no hay coincidencias).

---

### 8.4 Iniciar revisión de reporte

```
POST /api/reportes-ciudadanos/{id}/iniciar-revision
PUT  /api/reportes-ciudadanos/{id}/iniciar-revision
```

Avanza el estado del reporte de `PENDIENTE` a `EN_REVISION`, permitiendo opcionalmente la asignación de un inspector activo de ANMAT.

**Request body** (opcional):
```json
{
  "inspectorAsignadoId": 2
}
```

**Reglas de negocio**:
- Transición permitida: Exclusivamente `PENDIENTE` → `EN_REVISION`.
- Si el reporte no está en `PENDIENTE`, devuelve `409 STATE_CONFLICT`.
- Si se envía `inspectorAsignadoId`:
  - El inspector debe existir (si no, `404 RESOURCE_NOT_FOUND`).
  - El inspector debe encontrarse en estado `HABILITADO` (si está `SUSPENDIDO`, devuelve `409 STATE_CONFLICT`).

**Response** (`200 OK`): `ReporteCiudadanoResponseDTO` con estado `EN_REVISION`.

---

### 8.5 Resolver reporte ciudadano

```
POST /api/reportes-ciudadanos/{id}/resolver
PUT  /api/reportes-ciudadanos/{id}/resolver
```

Avanza el estado del reporte de `EN_REVISION` a `RESUELTO`.

**Request body** (opcional):
```json
{
  "inspectorAsignadoId": 2
}
```

**Reglas de negocio**:
- Transición permitida: Exclusivamente `EN_REVISION` → `RESUELTO`.
- No se permiten saltos directos desde `PENDIENTE` ni reaperturas desde `RESUELTO` (`409 STATE_CONFLICT`).
- Si se envía `inspectorAsignadoId`, debe existir y estar `HABILITADO`.

**Response** (`200 OK`): `ReporteCiudadanoResponseDTO` con estado `RESUELTO`.

---

### 8.6 Modificar reporte (PUT genérico)

```
PUT /api/reportes-ciudadanos/{id}
```

- Permite actualizar el motivo o reasociar unidad si el reporte no está resuelto.
- **No se permite** alterar el estado mediante este endpoint genérico (deben usarse `/iniciar-revision` y `/resolver`). Si se intenta modificar el estado, devuelve `409 STATE_CONFLICT`.
- Si el reporte está `RESUELTO`, devuelve `409 STATE_CONFLICT`.

---

### 8.7 Eliminar reporte (DELETE)

```
DELETE /api/reportes-ciudadanos/{id}
```

**Restricción**: No se permite la eliminación de reportes ciudadanos para garantizar trazabilidad y auditoría. Devuelve siempre `409 STATE_CONFLICT` (o `404 RESOURCE_NOT_FOUND` si el reporte no existe).

---

## 9. Consulta Pública por Código QR

Base paths: 
- `/api/public/consulta-qr/{codigoQr}`
- `/api/consulta-publica/qr/{codigoQr}`

### 9.1 Consultar información pública de unidad trazable

```
GET /api/public/consulta-qr/{codigoQr}
```

Ejemplo:
```
GET /api/public/consulta-qr/QR-UNIT-001
```

**Response** (`200 OK`):
```json
{
  "codigoConsultado": "QR-UNIT-001",
  "estadoUnidad": "DISPONIBLE",
  "medicamento": {
    "nombre": "Amoxicilina 500mg",
    "principioActivo": "Amoxicilina Trihidrato",
    "requiereCadenaFrio": false
  },
  "lote": {
    "codigoLote": "LOTE-2024-001",
    "fechaFabricacion": "2026-10-15T08:00:00",
    "estadoLote": "LIBERADO"
  },
  "cuarentena": {
    "enCuarentena": false,
    "resumen": null,
    "fechaInicio": null
  },
  "historialCustodia": [
    {
      "tipoEvento": "LOTE_DESPACHADO",
      "fechaHoraEvento": "2026-10-16T10:00:00",
      "descripcion": "Lote despachado.",
      "hashDemostrativo": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
    }
  ],
  "advertencias": [],
  "informacionRegulatoria": "Consulta pública informativa con fines demostrativos y académicos (MVP). La información provista no constituye certificación sanitaria regulatoria oficial de la ANMAT ni certificación de autenticidad en red blockchain distribuida. Los hashes mostrados son demostrativos y no prueban autenticidad ni existencia de una blockchain distribuida."
}
```

`advertencias` contiene mensajes basados únicamente en estados reales: unidad `BLOQUEADA` o `DISPENSADA`, y lote `EN_CUARENTENA` o `CERRADO`. Puede incluir más de un mensaje y queda vacío en los demás estados. Son avisos informativos, no evaluaciones sanitarias.

**Errores posibles**:
| HTTP | Código              | Condición                                                  |
|------|---------------------|------------------------------------------------------------|
| 404  | RESOURCE_NOT_FOUND  | No existe ninguna unidad trazable para el código QR dado.  |

---

## 10. Políticas de Privacidad y Exclusión de Datos

En estricto cumplimiento del principio de privacidad por diseño y minimización de datos:

### Datos devueltos en la Consulta Pública por QR:
- Código QR consultado y estado actual de la unidad (`DISPONIBLE`, `DISPENSADA`, `BLOQUEADA`).
- Denominación comercial y principio activo del medicamento; indicación de requerimiento de cadena de frío.
- Código de lote y fecha de fabricación (si está disponible).
- Estado de cuarentena del lote (indicador de cuarentena abierta, resumen fijo y fecha de inicio).
- Resumen/historial de eventos de custodia logística (tipo de hito, fecha/hora, descripción normalizada por tipo y hash de demostración).
- Advertencias informativas para estados de unidad o lote que requieren atención.
- Aviso regulatorio explícito aclarando el carácter informativo y no vinculante del MVP.

### Datos expresamente excluidos de la Consulta Pública por QR:
- **DNI de pacientes**: El DNI registrado en dispensaciones nunca es expuesto ni devuelto.
- **Número de receta médica**: Datos clínicos o de prescripción se mantienen fuera de la consulta pública.
- **Datos de contacto personal**: Teléfonos, correos electrónicos particulares o institucionales y domicilios particulares.
- **Identificadores internos de base de datos de usuarios**: IDs primarios de la tabla `usuarios` o credenciales.
- **Telemetría continua de sensores y ubicación GPS precisa**: Coordenadas geográficas exactas en tiempo real o registros crudos de registradores de temperatura (provenientes de B05) no se exponen al público general.
- **Organizaciones distribuidoras, textos libres de custodia y motivos libres de cuarentena**: La consulta pública no presenta nombres de distribuidores, actores de eventos, descripciones ingresadas por usuarios ni el motivo original de cuarentena. El hash mostrado es demostrativo y no acredita autenticidad.
- **Búsquedas masivas**: No se habilita endpoint público de listado de reportes ni búsqueda masiva o comodín de códigos QR.

## 11. Ajustes puntuales B11 del flujo logístico

- `POST /api/despachos-logisticos` deriva la farmacia destino del `EnlaceCUIT` activo; el enlace debe tener una organización de tipo `FARMACIA`. Si el request envía `farmaciaDestinoId`, debe coincidir. `destino` es una descripción logística y no reemplaza la relación con la farmacia. El lote y distribuidor deben corresponder al mismo enlace.
- `POST /api/despachos-logisticos/{id}/entregar` y `/recibir` rechazan el cierre directo con `409 STATE_CONFLICT`. La única operación que marca un despacho como `RECIBIDO` es `POST /api/recepciones-farmacia` con resultado `CONFORME` u `OBSERVADA`; guarda la recepción, el evento y los cambios de estado en la misma transacción. Una recepción `RECHAZADA` cancela el despacho y registra un evento `RECEPCION_RECHAZADA` sin marcar el lote como recibido.
- `POST /api/despachos-logisticos/{id}/cancelar` registra un evento `DESPACHO_CANCELADO` en la misma transacción. La organización del evento es la asociada al flujo, sin representar una identidad autenticada.
- En una base MySQL ya creada, el `ENUM` de `eventos_custodia.tipo_evento` debe incorporar `DESPACHO_CANCELADO` y `RECEPCION_RECHAZADA`. La configuración local `ddl-auto=update` sincroniza el esquema; una instalación que use `validate` necesita esa migración previamente.
- Los filtros `loteId`, `distribuidorId` y `estado` de `GET /api/despachos-logisticos` se aplican conjuntamente con lógica AND.
- `PUT /api/cuarentenas/{id}` rechaza cambios a cuarentenas `LIBERADA` o `MANTENIDA`; no altera registros ya resueltos.

---

## 12. Sembrado de datos de demostración (perfil `demo`)

El componente `DemoDataInitializer` (`@Profile("demo")`, `ApplicationRunner`, `@Transactional`) siembra un conjunto de datos iniciales totalmente ficticios para permitir la prueba inmediata de los flujos de la interfaz de usuario sin requerir pasos previos de configuración.

### Activación del perfil

Para activar el sembrador demostrativo se puede:
1. Agregar `spring.profiles.active=demo` en el archivo local no versionado `src/main/resources/application.properties`.
2. O bien iniciar la aplicación por línea de comandos pasando la propiedad de Spring Boot:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
   ```
   (en Windows: `.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=demo`).

### Comportamiento idempotente

El sembrador es **completamente idempotente**: antes de insertar cada entidad verifica su existencia en la base de datos (por correo electrónico, CUIT de organización o legajo oficial de inspector). En el arranque informa mediante logger la cantidad de registros sembrados nuevos y la cantidad de registros que ya existían previamente.

### Datos sembrados (totalmente ficticios)

Todos los datos utilizados son estrictamente ficticios, sin vinculación con entidades, personas, marcas ni dominios gubernamentales o comerciales reales:

1. **Usuarios activos**:
   - `superadmin@medichain.demo` (rol `SUPERADMIN`)
   - `inspector@medichain.demo` (rol `ANMAT`)
   - `laboratorio@medichain.demo` (rol `LABORATORIO`)
   - `distribuidor@medichain.demo` (rol `DISTRIBUIDOR`)
   - `farmacia@medichain.demo` (rol `FARMACIA`)
   - `paciente@medichain.demo` (rol `PACIENTE`)
2. **Inspector ANMAT**:
   - Legajo: `LEG-DEMO-001`
   - Nombre: `Auditor Sanitario Ficticio`
   - DNI: `11222333`
   - Correo institucional: `auditor@medichain.demo`
   - Jefe autorizador: `Dirección de Fiscalización Ficticia`
   - Estado: `HABILITADO`
   - Fecha de alta: marca de tiempo en UTC
   - Vinculado al usuario con rol `ANMAT`.
3. **Organizaciones principales** (CUITs inventados de exactamente 11 dígitos):
   - Laboratorio: `Laboratorio Demo Medichain Lab` (CUIT `30111111118`, tipo `LABORATORIO`)
   - Distribuidor: `Distribuidora Demo Logística Express` (CUIT `30222222228`, tipo `DISTRIBUIDOR`)
   - Farmacia: `Farmacia Central Demo` (CUIT `30333333338`, tipo `FARMACIA`)
4. **Medicamento ficticio**:
   - Nombre: `Vacuna Demostrativa Termosensible 10ml`
   - Principio activo: `Antígeno Ficticio Demo`
   - `requiereCadenaFrio = true`
5. **Enlace CUIT ACTIVO**:
   - Enlace entre el laboratorio, distribuidor y farmacia principales sembrados.
   - Estado: `ACTIVO`.
   - Aprobado por el inspector sembrado con fecha de auditoría en UTC y número de disposición `DISP-DEMO-2026-001`.
   - Permite despachar lotes de prueba inmediatamente sin requerir trámites administrativos previos.
6. **Organizaciones y Enlace CUIT PENDIENTE para demostración en vivo**:
   - Laboratorio secundario: `Laboratorio Innovación Demo` (CUIT `30444444448`, tipo `LABORATORIO`).
   - Farmacia secundaria: `Farmacia Sanitaria Demo` (CUIT `30555555558`, tipo `FARMACIA`).
   - Enlace CUIT en estado `PENDIENTE` entre el laboratorio secundario, el distribuidor sembrado y la farmacia secundaria, sin inspector asignado ni fecha de aprobación.
   - Permite al usuario demostrar la aprobación o rechazo en vivo desde la pantalla de auditoría ANMAT.

> **Aclaración de seguridad y roles**: El backend académico no implementa autenticación ni autorización. El rol `SUPERADMIN` es únicamente un valor dentro del enum `RolUsuario` persistido en la base de datos; no provee credenciales, permisos elevados ni control de acceso al API. De la misma forma, el DNI del inspector es un dato administrativo visible públicamente en las respuestas JSON para soporte de la pantalla de auditoría.
