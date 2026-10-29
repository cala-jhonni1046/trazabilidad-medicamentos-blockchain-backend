-- =====================================================================
--  MediChain — Script de inicialización (seed) de la base de datos
--  Datos ficticios de demostración para el ARRANQUE del backend.
--  Verificado contra el último main del backend (fix #42, "cierre backend demo").
--
--  CÓMO USARLO (dos formas):
--  A) Automático con Spring Boot: copiar este archivo como
--       src/main/resources/data.sql
--     y agregar en application.properties:
--       spring.sql.init.mode=always
--       spring.jpa.defer-datasource-initialization=true
--     Al arrancar el backend sobre una BASE VACÍA, se cargan solos.
--  B) Manual: importarlo contra MySQL (DBeaver/Workbench) o:
--       mysql -u usuario -p medichain < data.sql
--
--  IMPORTANTE:
--  - Usar una BASE FRESCA (vacía) la primera vez.
--  - INSERT IGNORE: si se corre de nuevo, no duplica ni rompe.
--  - Inserta directo en las tablas: NO pasa por el POST de despachos (evita el 409).
--  - Cantidad: 3 a 5 ejemplos de cada entidad + una cadena completa de punta a punta.
--  - Datos 100% ficticios. Los hashes "blockchain" son demostrativos.
-- =====================================================================

-- 1) USUARIOS (uno por rol + un 2do inspector) ------------------------
INSERT IGNORE INTO usuarios (id, email, rol, activo, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'superadmin@medichain.demo',  'SUPERADMIN',  1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (2, 'inspector@medichain.demo',   'ANMAT',       1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (3, 'laboratorio@medichain.demo', 'LABORATORIO', 1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (4, 'distribuidor@medichain.demo','DISTRIBUIDOR',1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (5, 'farmacia@medichain.demo',    'FARMACIA',    1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (6, 'paciente@medichain.demo',    'PACIENTE',    1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (7, 'inspector2@medichain.demo',  'ANMAT',       1, '2026-10-20 09:00:00', '2026-10-20 09:00:00');

-- 2) ORGANIZACIONES (2 laboratorios, 2 distribuidores, 2 farmacias) ---
INSERT IGNORE INTO organizaciones (id, cuit, nombre, tipo, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, '30700000015', 'Laboratorios Nordfarma S.A.',  'LABORATORIO',  '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (2, '30700000026', 'Distribuidora LogiMed S.R.L.', 'DISTRIBUIDOR', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (3, '30700000037', 'Farmacia Central del Sur',     'FARMACIA',     '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (4, '30700000048', 'BioLab Austral S.A.',          'LABORATORIO',  '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (5, '30700000059', 'Farmacia del Norte',           'FARMACIA',     '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (6, '30700000060', 'TransCargo del Plata S.A.',    'DISTRIBUIDOR', '2026-10-20 09:00:00', '2026-10-20 09:00:00');

-- 3) INSPECTORES ANMAT (uno HABILITADO, uno SUSPENDIDO) ---------------
INSERT IGNORE INTO inspectores_anmat
 (id, usuario_id, dni, legajo_oficial, nombre_completo, email_institucional, fecha_hora_alta_utc, jefe_autorizador, estado, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 2, '30111222', 'ANMAT-0001', 'Dra. Maria Figueroa',  'mfigueroa@anmat.gob.ar', '2026-10-20 09:00:00', 'Dir. Nacional de Fiscalizacion', 'HABILITADO', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (2, 7, '28444555', 'ANMAT-0002', 'Dr. Carlos Beltran',   'cbeltran@anmat.gob.ar',  '2026-10-20 09:00:00', 'Dir. Nacional de Fiscalizacion', 'SUSPENDIDO', '2026-10-20 09:00:00', '2026-10-20 09:00:00');

-- 4) ENLACES CUIT (ACTIVO x2, PENDIENTE, RECHAZADO) ------------------
INSERT IGNORE INTO enlaces_cuit
 (id, laboratorio_id, distribuidor_id, farmacia_id, inspector_aprobador_id, numero_disposicion_anmat, documento_certificado_url, estado, fecha_solicitud, fecha_aprobacion_anmat, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 1, 2, 3, 1,    'DISP-2026-00123', NULL, 'ACTIVO',    '2026-10-18 11:00:00', '2026-10-19 16:00:00', '2026-10-18 11:00:00', '2026-10-19 16:00:00'),
 (2, 1, 2, 5, 1,    'DISP-2026-00124', NULL, 'ACTIVO',    '2026-10-18 11:00:00', '2026-10-19 16:00:00', '2026-10-18 11:00:00', '2026-10-19 16:00:00'),
 (3, 4, 6, 5, NULL, NULL,              NULL, 'PENDIENTE', '2026-10-22 11:00:00', NULL,                  '2026-10-22 11:00:00', '2026-10-22 11:00:00'),
 (4, 4, 2, 3, 1,    NULL,              NULL, 'RECHAZADO', '2026-10-17 11:00:00', NULL,                  '2026-10-17 11:00:00', '2026-10-18 10:00:00');

-- 5) MEDICAMENTOS (5) -------------------------------------------------
INSERT IGNORE INTO medicamentos (id, nombre, principio_activo, requiere_cadena_frio, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'Insulina Glargina 100 UI/ml', 'Insulina glargina', 1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (2, 'Amoxicilina 500 mg',          'Amoxicilina',       0, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (3, 'Vacuna Antigripal',           'Virus inactivado',  1, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (4, 'Paracetamol 500 mg',          'Paracetamol',       0, '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (5, 'Heparina Sodica 5000 UI',     'Heparina sodica',   1, '2026-10-20 09:00:00', '2026-10-20 09:00:00');

-- 6) LOTES (7, cubriendo todos los estados) ---------------------------
INSERT IGNORE INTO lotes (id, codigo_lote, medicamento_id, laboratorio_id, temperatura_minima_celsius, temperatura_maxima_celsius, fecha_fabricacion, estado, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'LOTE-INS-0001', 1, 1, 2.0, 8.0,   '2026-10-10 08:00:00', 'RECIBIDO',      '2026-10-20 09:00:00', '2026-10-21 15:00:00'),
 (2, 'LOTE-AMX-0002', 2, 1, 15.0, 25.0, '2026-10-12 08:00:00', 'EN_TRANSITO',   '2026-10-20 09:00:00', '2026-10-25 10:00:00'),
 (3, 'LOTE-VAC-0003', 3, 4, 2.0, 8.0,   '2026-10-11 08:00:00', 'EN_CUARENTENA', '2026-10-20 09:00:00', '2026-10-20 09:30:00'),
 (4, 'LOTE-PAR-0004', 4, 1, 15.0, 25.0, '2026-10-13 08:00:00', 'RECIBIDO',      '2026-10-20 09:00:00', '2026-10-16 17:00:00'),
 (5, 'LOTE-AMX-0005', 2, 1, 15.0, 25.0, '2026-10-09 08:00:00', 'RECIBIDO',      '2026-10-20 09:00:00', '2026-10-18 17:00:00'),
 (6, 'LOTE-HEP-0006', 5, 4, 2.0, 8.0,   '2026-10-08 08:00:00', 'LIBERADO',      '2026-10-20 09:00:00', '2026-10-15 09:30:00'),
 (7, 'LOTE-VAC-0007', 3, 4, 2.0, 8.0,   '2026-10-07 08:00:00', 'EN_CUARENTENA', '2026-10-20 09:00:00', '2026-10-14 09:30:00');

-- 7) UNIDADES TRAZABLES (con QR, varias por lote) --------------------
INSERT IGNORE INTO unidades_trazables (id, codigo_qr, lote_id, estado, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1,  'QR-INS-0001-001', 1, 'DISPENSADA', '2026-10-20 09:00:00', '2026-10-21 18:00:00'),
 (2,  'QR-INS-0001-002', 1, 'DISPENSADA', '2026-10-20 09:00:00', '2026-10-21 18:30:00'),
 (3,  'QR-INS-0001-003', 1, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (4,  'QR-INS-0001-004', 1, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (5,  'QR-AMX-0002-001', 2, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (6,  'QR-AMX-0002-002', 2, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (7,  'QR-AMX-0002-003', 2, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (8,  'QR-VAC-0003-001', 3, 'BLOQUEADA',  '2026-10-20 09:00:00', '2026-10-20 09:30:00'),
 (9,  'QR-VAC-0003-002', 3, 'BLOQUEADA',  '2026-10-20 09:00:00', '2026-10-20 09:30:00'),
 (10, 'QR-PAR-0004-001', 4, 'DISPENSADA', '2026-10-20 09:00:00', '2026-10-16 18:00:00'),
 (11, 'QR-PAR-0004-002', 4, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (12, 'QR-PAR-0004-003', 4, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (13, 'QR-AMX-0005-001', 5, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (14, 'QR-AMX-0005-002', 5, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (15, 'QR-HEP-0006-001', 6, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (16, 'QR-HEP-0006-002', 6, 'DISPONIBLE', '2026-10-20 09:00:00', '2026-10-20 09:00:00'),
 (17, 'QR-VAC-0007-001', 7, 'BLOQUEADA',  '2026-10-20 09:00:00', '2026-10-14 09:30:00'),
 (18, 'QR-VAC-0007-002', 7, 'BLOQUEADA',  '2026-10-20 09:00:00', '2026-10-14 09:30:00');

-- 8) DESPACHOS LOGISTICOS (RECIBIDO x3, EN_TRANSITO, CANCELADO) -------
INSERT IGNORE INTO despachos_logisticos
 (id, numero_remito, codigo_seguimiento, origen, destino, distribuidor_id, vehiculo_patente, fecha_salida_utc, fecha_estimada_entrega_utc, fecha_entrega_real_utc, estado, temperatura_minima_celsius, temperatura_maxima_celsius, observaciones, lote_id, enlace_cuit_id, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'REM-0001', 'TRK-0001', 'Planta Nordfarma - CABA', 'Farmacia Central del Sur - La Plata', 2, 'AB123CD', '2026-10-20 10:00:00', '2026-10-21 12:00:00', '2026-10-21 15:00:00', 'RECIBIDO',    2.0,  8.0,  'Entrega conforme.',           1, 1, '2026-10-20 10:00:00', '2026-10-21 15:00:00'),
 (2, 'REM-0002', 'TRK-0002', 'Planta Nordfarma - CABA', 'Farmacia Central del Sur - La Plata', 2, 'EF456GH', '2026-10-25 10:00:00', '2026-10-26 12:00:00', NULL,                  'EN_TRANSITO', 15.0, 25.0, 'En ruta, monitoreo activo.',  2, 1, '2026-10-25 10:00:00', '2026-10-25 10:00:00'),
 (3, 'REM-0003', 'TRK-0003', 'Planta Nordfarma - CABA', 'Farmacia del Norte - San Isidro',     2, 'IJ789KL', '2026-10-15 10:00:00', '2026-10-16 12:00:00', '2026-10-16 17:00:00', 'RECIBIDO',    15.0, 25.0, 'Recepcion con observacion.',  4, 2, '2026-10-15 10:00:00', '2026-10-16 17:00:00'),
 (4, 'REM-0004', 'TRK-0004', 'Planta Nordfarma - CABA', 'Farmacia Central del Sur - La Plata', 2, 'MN012OP', '2026-10-17 10:00:00', '2026-10-18 12:00:00', '2026-10-18 17:00:00', 'RECIBIDO',    15.0, 25.0, 'Recepcion rechazada.',        5, 1, '2026-10-17 10:00:00', '2026-10-18 17:00:00'),
 (5, 'REM-0005', 'TRK-0005', 'Planta Nordfarma - CABA', 'Farmacia del Norte - San Isidro',     2, 'QR345ST', '2026-10-24 10:00:00', '2026-10-25 12:00:00', NULL,                  'CANCELADO',   15.0, 25.0, 'Cancelado por el laboratorio.',2, 2, '2026-10-24 10:00:00', '2026-10-24 14:00:00');

-- 9) EVENTOS DE CUSTODIA (hashes demostrativos encadenados) ----------
INSERT IGNORE INTO eventos_custodia
 (id, tipo_evento, fecha_hora_evento_utc, despacho_logistico_id, organizacion_actor_id, descripcion, hash_anterior, hash_evento, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1,  'LOTE_DESPACHADO',      '2026-10-20 10:00:00', 1, 1, 'Lote despachado desde el laboratorio.', NULL,             'demo-hash-0001', '2026-10-20 10:00:00', '2026-10-20 10:00:00'),
 (2,  'CUSTODIA_ASUMIDA',     '2026-10-20 11:00:00', 1, 2, 'El distribuidor asume la custodia.',    'demo-hash-0001', 'demo-hash-0002', '2026-10-20 11:00:00', '2026-10-20 11:00:00'),
 (3,  'RECEPCION_CONFIRMADA', '2026-10-21 15:00:00', 1, 3, 'La farmacia confirma la recepcion.',    'demo-hash-0002', 'demo-hash-0003', '2026-10-21 15:00:00', '2026-10-21 15:00:00'),
 (4,  'LOTE_DESPACHADO',      '2026-10-25 10:00:00', 2, 1, 'Lote despachado desde el laboratorio.', NULL,             'demo-hash-0004', '2026-10-25 10:00:00', '2026-10-25 10:00:00'),
 (5,  'CUSTODIA_ASUMIDA',     '2026-10-25 11:00:00', 2, 2, 'El distribuidor asume la custodia.',    'demo-hash-0004', 'demo-hash-0005', '2026-10-25 11:00:00', '2026-10-25 11:00:00'),
 (6,  'LOTE_DESPACHADO',      '2026-10-15 10:00:00', 3, 1, 'Lote despachado desde el laboratorio.', NULL,             'demo-hash-0006', '2026-10-15 10:00:00', '2026-10-15 10:00:00'),
 (7,  'CUSTODIA_ASUMIDA',     '2026-10-15 11:00:00', 3, 2, 'El distribuidor asume la custodia.',    'demo-hash-0006', 'demo-hash-0007', '2026-10-15 11:00:00', '2026-10-15 11:00:00'),
 (8,  'RECEPCION_CONFIRMADA', '2026-10-16 17:00:00', 3, 5, 'Recepcion con observacion registrada.', 'demo-hash-0007', 'demo-hash-0008', '2026-10-16 17:00:00', '2026-10-16 17:00:00'),
 (9,  'LOTE_DESPACHADO',      '2026-10-17 10:00:00', 4, 1, 'Lote despachado desde el laboratorio.', NULL,             'demo-hash-0009', '2026-10-17 10:00:00', '2026-10-17 10:00:00'),
 (10, 'CUSTODIA_ASUMIDA',     '2026-10-17 11:00:00', 4, 2, 'El distribuidor asume la custodia.',    'demo-hash-0009', 'demo-hash-0010', '2026-10-17 11:00:00', '2026-10-17 11:00:00'),
 (11, 'RECEPCION_RECHAZADA',  '2026-10-18 17:00:00', 4, 3, 'Recepcion rechazada por la farmacia.',  'demo-hash-0010', 'demo-hash-0011', '2026-10-18 17:00:00', '2026-10-18 17:00:00'),
 (12, 'LOTE_DESPACHADO',      '2026-10-24 10:00:00', 5, 1, 'Lote despachado desde el laboratorio.', NULL,             'demo-hash-0012', '2026-10-24 10:00:00', '2026-10-24 10:00:00'),
 (13, 'DESPACHO_CANCELADO',   '2026-10-24 14:00:00', 5, 1, 'Despacho cancelado.',                   'demo-hash-0012', 'demo-hash-0013', '2026-10-24 14:00:00', '2026-10-24 14:00:00');

-- 10) TELEMETRIA GPS (de los despachos en ruta / recorridos) ---------
INSERT IGNORE INTO telemetrias_gps
 (id, dispositivo_gps_id, latitud, longitud, altitud_metros, velocidad, fecha_hora_utc, despacho_logistico_id, direccion_aproximada, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'GPS-TRK-0002', -34.6037, -58.3816, 25.0, 62.0, '2026-10-25 10:30:00', 2, 'Av. 9 de Julio, CABA',      '2026-10-25 10:30:00', '2026-10-25 10:30:00'),
 (2, 'GPS-TRK-0002', -34.7200, -58.5000, 20.0, 85.0, '2026-10-25 11:30:00', 2, 'Autopista La Plata, km 25', '2026-10-25 11:30:00', '2026-10-25 11:30:00'),
 (3, 'GPS-TRK-0002', -34.8500, -58.0500, 18.0, 70.0, '2026-10-25 12:00:00', 2, 'Autopista La Plata, km 48', '2026-10-25 12:00:00', '2026-10-25 12:00:00'),
 (4, 'GPS-TRK-0002', -34.9214, -57.9544, 15.0, 10.0, '2026-10-25 12:30:00', 2, 'Ingreso a La Plata',        '2026-10-25 12:30:00', '2026-10-25 12:30:00'),
 (5, 'GPS-TRK-0003', -34.6037, -58.3816, 25.0, 55.0, '2026-10-15 10:30:00', 3, 'Av. General Paz, CABA',     '2026-10-15 10:30:00', '2026-10-15 10:30:00'),
 (6, 'GPS-TRK-0003', -34.4700, -58.5100, 22.0, 40.0, '2026-10-15 11:30:00', 3, 'San Isidro',                '2026-10-15 11:30:00', '2026-10-15 11:30:00');

-- 11) TELEMETRIA TEMPERATURA (incluye lecturas FUERA de rango) -------
INSERT IGNORE INTO telemetrias_temperatura
 (id, sensor_id, temperatura_celsius, fecha_hora_utc, alerta_excursion, despacho_logistico_id, observaciones, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'TEMP-TRK-0001', 5.0,  '2026-10-20 12:00:00', 0, 1, NULL,                           '2026-10-20 12:00:00', '2026-10-20 12:00:00'),
 (2, 'TEMP-TRK-0001', 6.2,  '2026-10-21 10:00:00', 0, 1, NULL,                           '2026-10-21 10:00:00', '2026-10-21 10:00:00'),
 (3, 'TEMP-TRK-0002', 20.5, '2026-10-25 10:30:00', 0, 2, NULL,                           '2026-10-25 10:30:00', '2026-10-25 10:30:00'),
 (4, 'TEMP-TRK-0002', 22.0, '2026-10-25 11:30:00', 0, 2, NULL,                           '2026-10-25 11:30:00', '2026-10-25 11:30:00'),
 (5, 'TEMP-TRK-0002', 27.8, '2026-10-25 12:30:00', 1, 2, 'Temperatura fuera de rango.',  '2026-10-25 12:30:00', '2026-10-25 12:30:00'),
 (6, 'TEMP-TRK-0003', 24.0, '2026-10-15 11:00:00', 0, 3, NULL,                           '2026-10-15 11:00:00', '2026-10-15 11:00:00'),
 (7, 'TEMP-TRK-0003', 26.5, '2026-10-15 12:00:00', 1, 3, 'Temperatura fuera de rango.',  '2026-10-15 12:00:00', '2026-10-15 12:00:00');

-- 12) CUARENTENAS (ABIERTA, LIBERADA, MANTENIDA) ---------------------
INSERT IGNORE INTO cuarentenas
 (id, codigo_cuarentena, fecha_inicio, fecha_fin, motivo, estado, lote_id, inspector_anmat_id, resolucion_final, observaciones, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'CUA-20261020-001', '2026-10-20 09:30:00', NULL,                  'Sospecha por excursion de temperatura en transito.', 'ABIERTA',   3, 1, NULL,                             'Lote retenido para inspeccion.', '2026-10-20 09:30:00', '2026-10-20 09:30:00'),
 (2, 'CUA-20261014-002', '2026-10-14 09:30:00', '2026-10-15 09:30:00', 'Control de rutina.',                                  'LIBERADA',  6, 1, 'Sin hallazgos. Lote liberado.',  NULL,                             '2026-10-14 09:30:00', '2026-10-15 09:30:00'),
 (3, 'CUA-20261013-003', '2026-10-13 09:30:00', '2026-10-14 09:30:00', 'Reporte ciudadano de envase dudoso.',                 'MANTENIDA', 7, 1, 'Se mantiene la cuarentena.',     'Pendiente de peritaje.',         '2026-10-13 09:30:00', '2026-10-14 09:30:00');

-- 13) RECEPCIONES EN FARMACIA (CONFORME, OBSERVADA, RECHAZADA) -------
INSERT IGNORE INTO recepciones_farmacia
 (id, numero_comprobante, fecha_hora_recepcion, farmacia_id, farmaceutico_responsable, despacho_logistico_id, estado_conformidad, cadena_frio_intacta, observaciones, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'REC-0001', '2026-10-21 15:00:00', 3, 'Lic. Juan Perez',  1, 'CONFORME',  1, NULL,                            '2026-10-21 15:00:00', '2026-10-21 15:00:00'),
 (2, 'REC-0002', '2026-10-16 17:00:00', 5, 'Lic. Ana Gomez',   3, 'OBSERVADA', 0, 'Caja exterior con golpe leve.',  '2026-10-16 17:00:00', '2026-10-16 17:00:00'),
 (3, 'REC-0003', '2026-10-18 17:00:00', 3, 'Lic. Juan Perez',  4, 'RECHAZADA', 0, 'Cadena de frio comprometida.',   '2026-10-18 17:00:00', '2026-10-18 17:00:00');

-- 14) DISPENSACIONES (3 unidades entregadas a pacientes) -------------
INSERT IGNORE INTO dispensaciones
 (id, receta_numero, dni_paciente, farmacia_id, fecha_dispensacion_utc, unidad_trazable_id, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'REC-2026-00001', '28999111', 3, '2026-10-21 18:00:00', 1,  '2026-10-21 18:00:00', '2026-10-21 18:00:00'),
 (2, 'REC-2026-00002', '30222333', 3, '2026-10-21 18:30:00', 2,  '2026-10-21 18:30:00', '2026-10-21 18:30:00'),
 (3, 'REC-2026-00003', '27555666', 5, '2026-10-16 18:00:00', 10, '2026-10-16 18:00:00', '2026-10-16 18:00:00');

-- 15) REPORTES CIUDADANOS (PENDIENTE x2, EN_REVISION, RESUELTO) ------
INSERT IGNORE INTO reportes_ciudadanos
 (id, motivo_reporte, fecha_hora_reporte_utc, estado, unidad_trazable_id, inspector_anmat_id, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'El envase parece adulterado.',          '2026-10-22 10:00:00', 'PENDIENTE',   3,  NULL, '2026-10-22 10:00:00', '2026-10-22 10:00:00'),
 (2, 'Dudas sobre la cadena de frio.',        '2026-10-21 10:00:00', 'EN_REVISION', 5,  1,    '2026-10-21 10:00:00', '2026-10-23 10:00:00'),
 (3, 'Consulta sobre fecha de vencimiento.',  '2026-10-19 10:00:00', 'RESUELTO',    11, 1,    '2026-10-19 10:00:00', '2026-10-20 10:00:00'),
 (4, 'El QR no coincide con la caja.',         '2026-10-23 10:00:00', 'PENDIENTE',   13, NULL, '2026-10-23 10:00:00', '2026-10-23 10:00:00');

-- 16) REGISTROS BLOCKCHAIN (demostrativos) --------------------------
INSERT IGNORE INTO registros_blockchain
 (id, transaction_hash, numero_bloque, firma_inspector_anmat, fecha_hora_utc, confirmado, lote_id, inspector_id, fecha_creacion_utc, fecha_actualizacion_utc) VALUES
 (1, 'demo-tx-0000000000000001', 1, 'firma-demo-anmat-0001', '2026-10-21 16:00:00', 1, 1, 1, '2026-10-21 16:00:00', '2026-10-21 16:00:00'),
 (2, 'demo-tx-0000000000000002', 2, 'firma-demo-anmat-0002', '2026-10-25 12:00:00', 1, 2, 1, '2026-10-25 12:00:00', '2026-10-25 12:00:00'),
 (3, 'demo-tx-0000000000000003', 3, 'firma-demo-anmat-0003', '2026-10-16 18:00:00', 1, 4, 1, '2026-10-16 18:00:00', '2026-10-16 18:00:00'),
 (4, 'demo-tx-0000000000000004', 4, 'firma-demo-anmat-0004', '2026-10-15 10:00:00', 1, 6, 1, '2026-10-15 10:00:00', '2026-10-15 10:00:00'),
 (5, 'demo-tx-0000000000000005', 5, 'firma-demo-anmat-0005', '2026-10-18 18:00:00', 0, 5, 1, '2026-10-18 18:00:00', '2026-10-18 18:00:00');
