package com.medichain.logistics.telemetriatemperatura;

// Importaciones para los endpoints REST y códigos de respuesta HTTP
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

// Controlador REST encargado del recurso de telemetría de temperatura
@RestController
@RequestMapping("/api/telemetria-temperatura")
public class TelemetriaTemperaturaController {

    @Autowired
    private TelemetriaTemperaturaService service;

    // POST: Recibe datos de temperatura e inserta un nuevo registro. Retorna status 201 CREATED
    @PostMapping
    public ResponseEntity<TelemetriaTemperatura> crear(@RequestBody TelemetriaTemperatura telemetria) {
        try {
            TelemetriaTemperatura nuevaTelemetria = service.crear(telemetria);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTelemetria);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET: Obtiene la lista completa de registros de temperatura. Retorna status 200 OK
    @GetMapping
    public ResponseEntity<List<TelemetriaTemperatura>> obtenerTodos() {
        try {
            return ResponseEntity.ok(service.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET /{id}: Obtiene un registro de temperatura por ID. Retorna 200 OK o 404 NOT FOUND
    @GetMapping("/{id}")
    public ResponseEntity<TelemetriaTemperatura> obtenerPorId(@PathVariable UUID id) {
        try {
            return service.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT /{id}: Actualiza un registro existente de temperatura. Retorna 200 OK o 404 NOT FOUND
    @PutMapping("/{id}")
    public ResponseEntity<TelemetriaTemperatura> actualizar(@PathVariable UUID id, @RequestBody TelemetriaTemperatura detalles) {
        try {
            return service.actualizar(id, detalles)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE /{id}: Borra un registro de temperatura por ID. Retorna 204 NO CONTENT o 404 NOT FOUND
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
