# Revisión de integración B10

Rama revisada: `profesor-b10-integracion_backend` (21 de octubre de 2026). Alcance: 16 controladores, sus DTO, servicios y rutas, junto con el manejador global de errores. Se preservó la configuración local y no se inició la aplicación ni se accedió a la base de datos.

## Comprobaciones ejecutadas

| Comando / revisión | Resultado observado |
|---|---|
| `git status --short --branch` | Rama correcta. Había cambios locales previos en `src/main/resources/application.properties` y archivos locales no rastreados; se conservaron. |
| Inventario estático de `*Controller.java` y `@*Mapping` | 16 controladores. Las rutas implementadas se consignaron en `docs/api.md`; se identificaron alias y rutas que responden 409 por regla de negocio. |
| Contraste de campos de `*RequestDTO.java` y `*ResponseDTO.java` con los controladores | Se agregaron ejemplos de cuerpos, filtros y tipos de salida al contrato. La consulta QR devuelve una proyección y no entidades JPA. |
| `rg 'catch \(Exception' src/main/java/ies/belgrano/medicamentos -g '*Controller.java'` | Se encontró captura general en `RegistroBlockchainController`. Se quitó para permitir el tratamiento H0; los IDs inexistentes ahora lanzan `ResourceNotFoundException`. |
| `mvn -q -DskipTests compile` | Terminó con código 0. Maven imprimió advertencias de `sun.misc.Unsafe` provenientes de Guava instalada en el entorno; no son errores de compilación. |
| `git diff --check` | Terminó con código 0, sin errores de espacios en los cambios. |

Swagger/OpenAPI no se consultó en ejecución: verificarlo requeriría arrancar la aplicación contra una base configurada. Estáticamente, springdoc está declarado en `pom.xml` y no hay especificación OpenAPI fija ni anotaciones que anuncien rutas inexistentes. Se añadieron descripciones explícitas para el cierre B04 deshabilitado y para el CRUD de registros demostrativos.

## Diferencias pendientes, fuera de la integración documental

- Los endpoints administrativos no tienen autenticación ni autorización; pueden exponer datos internos, como correo de usuario o datos de inspector. No son endpoints públicos de consulta QR.
- La documentación H0 cubre excepciones manejadas por `GlobalExceptionHandler`; la conversión inválida de parámetros o JSON no tiene tratamiento específico y puede conservar respuestas del framework o acabar como error genérico.
- Los filtros combinados no son uniformes: la consulta de recepciones prioriza el ID de despacho y puede ignorar farmacia/estado; la de reportes puede ignorar inspector al combinarlo con estado o unidad.
- El estado `EN_TRANSITO` de lote existe en el enum, pero iniciar un despacho no cambia ese lote. No hay rutas para cerrar lotes ni bloquear unidades.
- `RegistroBlockchainRequestDTO` no tiene restricciones Bean Validation. El módulo conserva un CRUD de datos declarados y no aporta una blockchain distribuida.
- La base no tiene migraciones versionadas. Un esquema antiguo con enum MySQL de eventos de custodia requiere sincronización antes de usar `ddl-auto=validate`.

No se agregaron pruebas automatizadas. No se realizaron comprobaciones de arranque ni operaciones sobre datos persistidos durante B10.
