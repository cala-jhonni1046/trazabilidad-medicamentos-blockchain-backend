package com.medichain.logistics.telemetriagps;

// Controladores REST y respuestas HTTP para la API de ubicación GPS
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

// Define la clase como controlador REST en la ruta /api/telemetria-gps
@RestController
@RequestMapping("/api/telemetria-gps")
public class TelemetriaGPSController {

    @Autowired
    private TelemetriaGPSService service;

    // POST: Recibe datos de posición GPS y los guarda. Retorna 201 CREATED
    @PostMapping
    public ResponseEntity<TelemetriaGPS> crear(@RequestBody TelemetriaGPS telemetria) {
        try {
            TelemetriaGPS nuevaTelemetria = service.crear(telemetria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTelemetria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET: Recupera todo el historial de ubicaciones GPS. Retorna 200 OK
    @GetMapping
    public ResponseEntity<List<TelemetriaGPS>> obtenerTodos() {
        try {
            return ResponseEntity.ok(service.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /{id}: Consulta un registro de GPS por su ID. Retorna 200 OK o 404 NOT FOUND
    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaGPS> obtenerPorId(@PathVariable UUID id) {
        try {
            return service.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT /{id}: Actualiza las coordenadas y velocidad de un registro GPS. Retorna 200 OK o 404 NOT FOUND
    @PutMapping("/{id}")
    public ResponseEntity<TelemetriaGPS> actualizar(@PathVariable UUID id, @RequestBody TelemetriaGPS detalles) {
        try {
            return service.actualizar(id, detalles)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /{id}: Borra la lectura GPS indicada. Retorna 204 NO CONTENT o 404 NOT FOUND
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        try {
            if (service.eliminar(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
