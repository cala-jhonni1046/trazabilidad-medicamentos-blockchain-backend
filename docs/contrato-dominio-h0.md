# Contrato de Dominio H0 — Trazabilidad de Medicamentos

Este documento consolida las definiciones y reglas de dominio establecidas para el núcleo H0 del MVP académico y demostrativo de trazabilidad de medicamentos.

---

## 1. Alcance y Carácter Demostrativo
- **Propósito:** Proyecto académico para demostrar la trazabilidad de especialidades medicinales a lo largo de la cadena de suministro.
- **Límites:** El sistema no realiza consultas en tiempo real a padrones tributarios oficiales (AFIP/ARCA), no valida habilitaciones sanitarias legales ni interactúa con una red blockchain productiva en esta instancia.

---

## 2. Organizaciones y Tipos de Actores
Una **Organización** representa una entidad participante en la cadena de comercialización y distribución de medicamentos.

- **Identificador Fiscal (CUIT):**
  - Representado obligatoriamente como una cadena normalizada de 11 dígitos numéricos (`\d{11}`).
  - Se admite la entrada con guiones estándar (`XX-XXXXXXXX-X`), los cuales se normalizan de forma determinista y explícita removiendo los guiones separadores sin modificar los dígitos.
  - Debe ser único en el sistema, protegido por índice único a nivel de persistencia relacional.
- **Razón Social / Nombre:**
  - Cadena obligatoria no vacía, con longitud máxima de 160 caracteres.
- **Tipo de Organización:**
  - Restringido estrictamente al enumerador `TipoOrganizacion`:
    - `LABORATORIO`: Fabricante y titular de registro de medicamentos y lotes.
    - `DISTRIBUIDOR`: Operador logístico y de distribución mayorista.
    - `FARMACIA`: Entidad dispensadora habilitada para la entrega de unidades al paciente.

---

## 3. Enlace CUIT (Habilitación Comercial de la Cadena)
Un **Enlace CUIT** modela el acuerdo y habilitación regulatoria tripartita entre un laboratorio titular, un distribuidor intermedio y una farmacia dispensadora.

- **Relación Tripartita:**
  - Requiere obligatoriamente tres organizaciones preexistentes en el sistema, cada una cumpliendo de forma estricta con su tipo de actor (`LABORATORIO`, `DISTRIBUIDOR`, `FARMACIA`).
- **Estados del Enlace (`EstadoEnlaceCuit`):**
  - `PENDIENTE`: Estado inicial al solicitar el enlace.
  - `ACTIVO`: Enlace resuelto favorablemente por un inspector ANMAT habilitado.
  - `RECHAZADO`: Enlace denegado por un inspector ANMAT habilitado.
- **Reglas de Transición y Resolución:**
  - Todo nuevo enlace inicia de manera obligatoria en estado `PENDIENTE`.
  - La solicitud de creación no puede seleccionar el estado ni fijar un inspector aprobador.
  - Únicamente los enlaces en estado `PENDIENTE` pueden ser resueltos (aprobados o rechazados).
  - Un enlace ya resuelto (`ACTIVO` o `RECHAZADO`) no puede volver a resolverse.
  - No se permite la alteración de estado, inspector o marcas temporales mediante operaciones de actualización genéricas (`PUT`).
- **Inspector ANMAT:**
  - La resolución exige un inspector existente con estado `HABILITADO`.
  - Al aprobar, se persiste la referencia al inspector aprobador y la marca temporal de aprobación UTC.
  - Al rechazar, se actualiza el estado a `RECHAZADO`, sin alterar los campos específicos de aprobación mientras no se definan campos específicos de rechazo en el modelo.
- **Semántica:**
  - La habilitación comercial de un Enlace CUIT habilita la validez comercial del canal de distribución, pero **no equivale** a un despacho logístico ni a un evento de transferencia de custodia física.

---

## 4. Estructura Estándar de Errores H0
Las respuestas de error de la API conservan la estructura estandarizada de transporte:

```json
{
  "timestamp": "2026-10-17T03:50:00Z",
  "status": 400,
  "code": "VALIDATION_ERROR",
  "message": "Error de validación",
  "path": "/api/organizaciones",
  "fieldErrors": [
    {
      "field": "cuit",
      "message": "El CUIT debe contener 11 dígitos numéricos o formato XX-XXXXXXXX-X"
    }
  ]
}
```

Códigos de error (`ApiErrorCode`):
- `VALIDATION_ERROR` (400)
- `INVALID_ARGUMENT` (400)
- `RESOURCE_NOT_FOUND` (404)
- `RESOURCE_CONFLICT` / `STATE_CONFLICT` (409)
- `INTERNAL_ERROR` (500)
