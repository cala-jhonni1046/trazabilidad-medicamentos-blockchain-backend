# Seguridad: autenticación, permisos y aislamiento por organización

La API usa JWT (HS256) sin sesión en el servidor. Hay dos capas independientes:

1. **Permisos por rol**: qué operaciones puede hacer cada rol (`SecurityConfig`).
2. **Aislamiento por organización**: qué datos ve cada empresa (`AccesoOrganizacion` y los servicios).

## Login

`POST /api/auth/login` (público)

```json
{"email":"laboratorio@medichain.demo","password":"Demo1234!"}
```

Respuesta 200:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "tipo": "Bearer",
  "expiraEnSegundos": 3600,
  "usuario": {"id": 3, "email": "laboratorio@medichain.demo", "rol": "LABORATORIO", "activo": true, "organizacionId": 1},
  "organizacion": {"id": 1, "cuit": "30111111118", "nombre": "Laboratorio Demo Medichain Lab", "tipo": "LABORATORIO"},
  "inspector": null
}
```

- El email no distingue mayúsculas ni espacios al inicio o final.
- Credenciales incorrectas, usuario inexistente, inactivo o sin contraseña devuelven el **mismo** 401 genérico, para no revelar si el email existe.
- `inspector` solo se completa para usuarios ANMAT; `organizacion` solo para empresas.

`GET /api/auth/me` (requiere token, cualquier rol) devuelve el mismo contenido sin `token`. Sirve para validar la sesión al abrir el frontend.

Todas las demás rutas privadas necesitan el encabezado:

```
Authorization: Bearer <token>
```

El token contiene el id del usuario (`sub`), `rol`, `organizacionId` (si tiene), emisión y vencimiento. En cada pedido el backend recarga al usuario desde la base: si fue desactivado, el token deja de servir aunque no haya vencido.

## Límite de intentos

Después de 5 fallos de login para el mismo email desde la misma IP en 5 minutos, el login responde **429** hasta que pase la ventana. Un login correcto reinicia el contador. Solo se usa la IP de la conexión (no el encabezado `X-Forwarded-For`, que un cliente podría falsificar).

## Roles

| Rol | Descripción |
|---|---|
| `SUPERADMIN` | Soporte y administración. Ve todo y puede administrar usuarios, organizaciones e inspectores. |
| `ANMAT` | Autoridad de control. Ve todo; aprueba o rechaza enlaces, gestiona cuarentenas, reportes y registros. |
| `LABORATORIO` | Producción: medicamentos, lotes, unidades trazables, enlaces CUIT y despachos de sus lotes. |
| `DISTRIBUIDOR` | Despachos y telemetría de los envíos que gestiona. |
| `FARMACIA` | Recepciones, dispensaciones y eventos de custodia de sus entregas. |
| `PACIENTE` | Sin acceso a la API privada. Solo `/api/auth/me` y la consulta pública por QR. |

## Permisos por ruta

Rutas públicas (sin token): `POST /api/auth/login`, `/api/public/**`, `/api/consulta-publica/**`, `POST /api/reportes-ciudadanos`, Swagger y `OPTIONS`.

| Recurso | Lectura (GET) | Escritura (POST/PUT/PATCH/DELETE) |
|---|---|---|
| `/api/usuarios` | SUPERADMIN | SUPERADMIN |
| `/api/auditoria` | SUPERADMIN, ANMAT | — |
| `/api/inspectores` | SUPERADMIN, ANMAT | SUPERADMIN |
| `/api/organizaciones` | SUPERADMIN, ANMAT y las tres empresas | SUPERADMIN |
| `/api/medicamentos` | SUPERADMIN, ANMAT y las tres empresas | LABORATORIO, SUPERADMIN |
| `/api/lotes` | ídem | LABORATORIO, SUPERADMIN |
| `/api/unidades-trazables` | ídem | LABORATORIO, SUPERADMIN |
| `/api/enlaces-cuit` | ídem | Crear: LABORATORIO, SUPERADMIN. Aprobar/rechazar: ANMAT, SUPERADMIN |
| `/api/despachos-logisticos` | ídem | LABORATORIO, DISTRIBUIDOR, SUPERADMIN. Además `entregar`, `recibir` y `eventos`: FARMACIA |
| `/api/telemetrias-gps`, `/api/telemetrias-temperatura` | ídem | ANMAT, LABORATORIO, DISTRIBUIDOR, SUPERADMIN |
| `/api/recepciones-farmacia` | ídem | FARMACIA, SUPERADMIN |
| `/api/dispensaciones` | ídem | FARMACIA, SUPERADMIN |
| `/api/cuarentenas` | ídem | ANMAT, SUPERADMIN |
| `/api/registros-blockchain` | ídem | ANMAT, SUPERADMIN |
| `/api/reportes-ciudadanos` | ANMAT, SUPERADMIN | ANMAT, SUPERADMIN (el alta es pública) |
| Cualquier otra ruta de `/api/**` | SUPERADMIN, ANMAT y las tres empresas | SUPERADMIN |

Un rol sin permiso recibe **403**. Un pedido sin token o con token inválido recibe **401**.

## Aislamiento por organización

SUPERADMIN y ANMAT ven todo. Un usuario de empresa solo accede a lo que pertenece a su organización, que sale del token:

| Recurso | Quién lo ve |
|---|---|
| Lotes y unidades trazables | El laboratorio dueño. Distribuidor y farmacia ven los de los lotes que tienen en un despacho a su nombre. |
| Enlaces CUIT | Las tres partes del enlace: laboratorio, distribuidor y farmacia. |
| Despachos, eventos de custodia, telemetría GPS y de temperatura | Laboratorio del lote, distribuidor y farmacia destino del despacho. |
| Recepciones | La farmacia que recibe. |
| Dispensaciones | Solo la farmacia que dispensa, por contener el DNI del paciente. Laboratorio y distribuidor no las ven. |
| Cuarentenas y registros blockchain | Quien tenga visible el lote asociado. Los registros sin lote, solo SUPERADMIN y ANMAT. |
| Medicamentos y organizaciones | Catálogo compartido entre las empresas. |

Reglas de escritura:

- **Se ignora la organización del cuerpo.** Al crear, el laboratorio (`laboratorioId`), la farmacia (`farmaciaId`) y el actor de un evento salen del token. Un laboratorio no puede crear lotes a nombre de otro, y una farmacia no puede dispensar como otra. SUPERADMIN sí puede indicar la organización.
- **Un recurso de otra organización responde 404, no 403**, para no revelar que existe. Vale para lectura, modificación y borrado.
- Las acciones de estado verifican que la organización sea la parte correspondiente (por ejemplo, solo el distribuidor o laboratorio del despacho lo inicia, y solo la farmacia destino lo recibe).
- Al crear un enlace CUIT, ANMAT toma como inspector al usuario autenticado.

## Errores

401 y 403 usan el formato común:

```json
{"timestamp":"2026-11-08T12:00:00Z","status":401,"code":"NO_AUTENTICADO","message":"...","path":"/api/lotes","fieldErrors":null}
```

| Estado | `code` | Cuándo |
|---|---|---|
| 401 | `NO_AUTENTICADO` | Falta el token, es inválido o está vencido. |
| 401 | `CREDENCIALES_INVALIDAS` | Login incorrecto (mensaje genérico). |
| 403 | `ACCESO_DENEGADO` | El rol no tiene permiso para esa operación. |
| 404 | `RESOURCE_NOT_FOUND` | No existe, o pertenece a otra organización. |
| 429 | `CREDENCIALES_INVALIDAS` | Demasiados intentos de login; reintentar en 5 minutos. |

El resto de errores (400, 409, 500) sigue el formato descrito en [api.md](api.md).

## Auditoría

Toda escritura (POST, PUT, PATCH, DELETE) sobre `/api/**`, excepto el login, queda registrada con usuario, organización, método, ruta, recurso, fecha y resultado HTTP.

`GET /api/auditoria` (SUPERADMIN y ANMAT): paginado, con `page` (desde 0), `size` (50 por defecto, máximo 200) y filtros opcionales `usuarioId` y `organizacionId`.

## Usuarios

- La contraseña se guarda con BCrypt y nunca se devuelve en ninguna respuesta.
- Se crean desde `POST /api/usuarios` (SUPERADMIN) con `email`, `rol`, `password` (mínimo 8 caracteres) y `organizacionId` para los roles de empresa.

## Usuarios de demostración (perfil `demo`)

Todos con la contraseña `Demo1234!`. Los datos son ficticios; no usar en producción.

| Email | Rol | Organización |
|---|---|---|
| `superadmin@medichain.demo` | SUPERADMIN | — |
| `inspector@medichain.demo` | ANMAT | — |
| `laboratorio@medichain.demo` | LABORATORIO | Laboratorio Demo Medichain Lab |
| `laboratorio2@medichain.demo` | LABORATORIO | Laboratorio Innovación Demo |
| `distribuidor@medichain.demo` | DISTRIBUIDOR | Distribuidora Demo Logística Express |
| `farmacia@medichain.demo` | FARMACIA | Farmacia Central Demo |
| `farmacia2@medichain.demo` | FARMACIA | Farmacia Sanitaria Demo |
| `paciente@medichain.demo` | PACIENTE | — |

Los dos laboratorios y las dos farmacias permiten comprobar el aislamiento: cada uno ve solo lo suyo y ANMAT ve el total.

## Configuración

| Propiedad | Variable de entorno | Detalle |
|---|---|---|
| `app.jwt.secret` | `JWT_SECRET` | Obligatoria, mínimo 32 bytes. |
| `app.jwt.expiracion-minutos` | `APP_JWT_EXPIRACION_MINUTOS` | Por defecto 60. |
| `app.cors.allowed-origins` | `APP_CORS_ALLOWED_ORIGINS` | Lista separada por comas. Por defecto `http://localhost:4200`. |

## Limitaciones conocidas

- Los tokens no se pueden revocar antes de vencer, salvo desactivando al usuario.
- No hay renovación de token: al vencer hay que volver a iniciar sesión.
- El límite de intentos de login es en memoria y por instancia.
- No hay recuperación de contraseña por correo.
