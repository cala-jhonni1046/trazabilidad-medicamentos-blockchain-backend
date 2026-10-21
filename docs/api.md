# API — B08: Unidades Trazables y Dispensación

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
  "dniPaciente": "12345678",
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
   - Estado de conformidad **distinto de** `RECHAZADA` (puede ser `CONFORME` u `OBSERVADA`).
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

## 7. Nota sobre generación masiva

La issue B08 implementa **alta individual** de unidades trazables (una por request). No se implementa generación masiva por cantidad en esta versión.

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
    "codigoCuarentena": null,
    "motivo": null,
    "fechaInicio": null
  },
  "historialCustodia": [
    {
      "tipoEvento": "DESPACHADO",
      "fechaHoraEvento": "2026-10-16T10:00:00",
      "organizacion": "Laboratorio Farmacéutico Central",
      "descripcion": "Salida de planta hacia distribuidor",
      "hashDemostrativo": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
    }
  ],
  "informacionRegulatoria": "Consulta pública informativa con fines demostrativos y académicos (MVP). La información provista no constituye certificación sanitaria regulatoria oficial de la ANMAT ni certificación de autenticidad en red blockchain distribuida."
}
```

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
- Estado de cuarentena del lote (indicador si se encuentra en cuarentena abierta, motivo y fecha de inicio, derivado de la issue B07).
- Resumen/historial de eventos de custodia logística (tipo de hito, fecha/hora, razón social de la organización participante, descripción general y hash de demostración, derivado de la issue B04).
- Aviso regulatorio explícito aclarando el carácter informativo y no vinculante del MVP.

### Datos expresamente excluidos de la Consulta Pública por QR:
- **DNI de pacientes**: El DNI registrado en dispensaciones nunca es expuesto ni devuelto.
- **Número de receta médica**: Datos clínicos o de prescripción se mantienen fuera de la consulta pública.
- **Datos de contacto personal**: Teléfonos, correos electrónicos particulares o institucionales y domicilios particulares.
- **Identificadores internos de base de datos de usuarios**: IDs primarios de la tabla `usuarios` o credenciales.
- **Telemetría continua de sensores y ubicación GPS precisa**: Coordenadas geográficas exactas en tiempo real o registros crudos de registradores de temperatura (provenientes de B05) no se exponen al público general.
- **Búsquedas masivas**: No se habilita endpoint público de listado de reportes ni búsqueda masiva o comodín de códigos QR.

