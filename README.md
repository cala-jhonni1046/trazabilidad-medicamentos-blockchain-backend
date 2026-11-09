# MediChain — backend de trazabilidad de medicamentos

API REST (Spring Boot 4.1.1, Java 21, MySQL) para que laboratorios, distribuidores y farmacias registren la cadena de custodia de medicamentos, bajo la supervisión de ANMAT. Incluye login con JWT, permisos por rol y aislamiento por organización: cada empresa ve y modifica únicamente sus propios datos.

Los registros llamados «blockchain» y los hashes de custodia siguen siendo demostrativos: no hay conexión con una red distribuida.

## Documentación

- [docs/seguridad.md](docs/seguridad.md): login, token, permisos por rol, aislamiento por organización, errores 401/403/404/429, auditoría y configuración.
- [docs/api.md](docs/api.md): contrato de rutas, cuerpos, estados y errores de cada módulo.
- Swagger UI en `http://localhost:8080/swagger-ui/index.html` con la aplicación iniciada.

## Requisitos

- JDK 21 o superior.
- MySQL accesible desde la máquina donde corre la API.
- No hace falta instalar Maven: el repositorio incluye el wrapper (`mvnw` / `mvnw.cmd` y la carpeta `.mvn`).

## Variables de entorno

| Variable | Obligatoria | Descripción |
|---|---|---|
| `JWT_SECRET` | Sí | Clave de firma de los tokens, de al menos 32 caracteres. Si falta o es corta, la aplicación no arranca. No tiene valor por defecto. |
| `SPRING_DATASOURCE_URL` | Sí* | URL JDBC, por ejemplo `jdbc:mysql://localhost:3306/medicamentos?createDatabaseIfNotExist=true&serverTimezone=UTC`. |
| `SPRING_DATASOURCE_USERNAME` | Sí* | Usuario de la base. |
| `SPRING_DATASOURCE_PASSWORD` | No | Contraseña de la base. Omitir si el usuario no tiene. |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | No | `update` crea o ajusta las tablas al arrancar (solo en bases propias o de prueba); `validate` solo verifica. |
| `APP_CORS_ALLOWED_ORIGINS` | No | Orígenes permitidos, separados por coma. Por defecto `http://localhost:4200`. En producción, el dominio real del frontend. |
| `APP_JWT_EXPIRACION_MINUTOS` | No | Duración del token en minutos. Por defecto 60. |

\* Si usás un `application.properties` local con tus propias variables (por ejemplo `DB_URL`, `DB_USER`, `DB_PASSWORD`), no lo subas al repositorio: contiene credenciales.

Generar un secreto aleatorio (PowerShell):

```powershell
-join ((48..57)+(65..90)+(97..122) | Get-Random -Count 48 | ForEach-Object {[char]$_})
```

## Levantar el backend

PowerShell (Windows), desde la carpeta del backend. Las variables valen solo para esa ventana:

```powershell
$env:JWT_SECRET="pegá-acá-tu-clave-de-al-menos-32-caracteres"
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/medicamentos?createDatabaseIfNotExist=true&serverTimezone=UTC"
$env:SPRING_DATASOURCE_USERNAME="root"
$env:SPRING_JPA_HIBERNATE_DDL_AUTO="update"
.\mvnw spring-boot:run "-Dspring-boot.run.profiles=demo"
```

Linux / macOS:

```bash
export JWT_SECRET='pegá-acá-tu-clave-de-al-menos-32-caracteres'
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/medicamentos?createDatabaseIfNotExist=true&serverTimezone=UTC'
export SPRING_DATASOURCE_USERNAME='usuario_local'
export SPRING_DATASOURCE_PASSWORD='contraseña_local'
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
./mvnw spring-boot:run -Dspring-boot.run.profiles=demo
```

El perfil `demo` carga datos ficticios (organizaciones, usuarios, lotes, despachos) y es idempotente: se puede arrancar varias veces. **No lo actives en producción.** Los usuarios de prueba están en [docs/seguridad.md](docs/seguridad.md).

Comprobación rápida (debe responder 401 sin token):

```powershell
curl.exe -i http://localhost:8080/api/lotes
```

## Pruebas automáticas

```powershell
$env:JWT_SECRET="una-clave-de-prueba-de-al-menos-32-caracteres"
.\mvnw test
```

Hay pruebas unitarias de JWT, límite de intentos de login, control de acceso por organización y login. La prueba `MedicamentosApplicationTests` arranca la aplicación completa, así que necesita MySQL accesible con las mismas variables de conexión.

## Antes de publicar en producción

- Servir la API detrás de HTTPS (proxy inverso) y definir `APP_CORS_ALLOWED_ORIGINS` con el dominio real.
- Usar un `JWT_SECRET` largo y propio, guardado fuera del repositorio.
- No activar el perfil `demo`.
- Deshabilitar Swagger si no debe ser público: `springdoc.api-docs.enabled=false` y `springdoc.swagger-ui.enabled=false`.
- Usar `SPRING_JPA_HIBERNATE_DDL_AUTO=validate` con migraciones controladas; el repositorio todavía no incluye Flyway ni Liquibase.
- Hacer copias de seguridad de la base de datos.

## Límites actuales

- Los tokens duran 60 minutos y no hay renovación ni revocación anticipada.
- El límite de intentos de login es en memoria: se reinicia al reiniciar la aplicación y no se comparte entre varias instancias.
- Los registros «blockchain» no están anclados a una red real.
- No hay recuperación de contraseña por correo.
