# Trazabilidad de medicamentos — backend académico

API REST de un MVP académico para registrar organizaciones, lotes, despachos, telemetría, recepciones, cuarentenas, unidades, dispensaciones y reportes ciudadanos. También expone una consulta informativa por QR. Los hashes de custodia y los registros llamados «blockchain» son demostrativos: no hay conexión con una red distribuida ni certificación de autenticidad sanitaria.

El contrato completo de rutas, cuerpos, estados y errores está en [docs/api.md](docs/api.md). La evidencia de la revisión B10 y sus diferencias pendientes está en [docs/integracion-b10.md](docs/integracion-b10.md). No hay login, JWT ni autorización; los roles de usuario son datos persistidos, no controles de acceso.

## Requisitos

- JDK 21.
- Maven instalado (`mvn`). El repositorio incluye `mvnw`, pero no incluye `.mvn/wrapper`, por lo que el wrapper no se puede usar tal como está.
- MySQL accesible desde la máquina donde se inicia la API.
- Dependencias descargables por Maven en la primera compilación.

El proyecto declara Spring Boot 4.1.1 y springdoc OpenAPI 3.1.0 en `pom.xml`. Por defecto Spring Boot escucha en el puerto 8080.

## Preparar MySQL y la configuración local

Crear una base descartable propia para el MVP, por ejemplo `medicamentos`. Un administrador de MySQL puede ejecutar:

```sql
CREATE DATABASE IF NOT EXISTS medicamentos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

La configuración versionada solo declara `spring.application.name`. Definir la conexión mediante variables de entorno de Spring Boot en la terminal donde se iniciará la aplicación:

```bash
export SPRING_DATASOURCE_URL='jdbc:mysql://localhost:3306/medicamentos'
export SPRING_DATASOURCE_USERNAME='usuario_local'
read -r -s -p 'Contraseña MySQL: ' SPRING_DATASOURCE_PASSWORD; echo
export SPRING_DATASOURCE_PASSWORD
export SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

`usuario_local` es un marcador: usar una cuenta propia con permisos en esa base. El modo `update` permite que Hibernate cree o ajuste tablas de una **base descartable**; modifica su esquema al arrancar. Para una base existente cuyo esquema ya sea compatible, usar `SPRING_JPA_HIBERNATE_DDL_AUTO=validate` y aplicar previamente las migraciones que correspondan. El repositorio no incluye Flyway, Liquibase ni scripts versionados de creación. En particular, un esquema MySQL anterior a B11 puede necesitar agregar `DESPACHO_CANCELADO` y `RECEPCION_RECHAZADA` al `ENUM` de `eventos_custodia.tipo_evento` antes de usar `validate`.

Si se dispone de una configuración local en `src/main/resources/application.properties`, evitar publicar sus credenciales. Las variables de entorno permiten mantenerlas fuera del repositorio.

## Compilar e iniciar

Desde la carpeta del backend:

```bash
mvn -DskipTests compile
mvn spring-boot:run
```

Las pruebas automatizadas se pospusieron para la etapa final del proyecto; `-DskipTests` compila sin ejecutarlas. El arranque requiere la base configurada y puede modificar su esquema según `SPRING_JPA_HIBERNATE_DDL_AUTO`.

Con la aplicación iniciada, abrir `http://localhost:8080/swagger-ui/index.html` para Swagger UI o `http://localhost:8080/v3/api-docs` para OpenAPI. Una consulta sin escritura para comprobar que responde es `GET http://localhost:8080/api/medicamentos` (devuelve `[]` si no hay registros). Swagger se genera de los controladores: muestra las rutas disponibles, incluidas algunas que responden con rechazo por regla de negocio. Consultar `docs/api.md` para esas restricciones.

## Límites del MVP

La telemetría se ingresa por API; no hay sensores integrados. La consulta QR no expone DNI, correo, contactos ni coordenadas GPS exactas, pero los endpoints administrativos no están protegidos por autenticación. No se debe usar este backend con datos personales reales ni como sistema sanitario de producción.
