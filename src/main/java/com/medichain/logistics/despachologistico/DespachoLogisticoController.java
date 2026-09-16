package com.medichain.logistics.despachologistico;

// Importaciones de Spring Web MVC y estados HTTP
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

// Define esta clase como un controlador REST que maneja peticiones HTTP e intercambia datos en formato JSON
@RestController
// Ruta base para todos los endpoints de este controlador
@RequestMapping("/api/despachos-logisticos")
public class DespachoLogisticoController {

    // Referencia al servicio de lógica de negocio
    @Autowired
    private DespachoLogisticoService service;

    // HTTP POST: Crea un nuevo despacho. Retorna estado 201 (Created) con el objeto creado
    @PostMapping
    public ResponseEntity<DespachoLogistico> crear(@RequestBody DespachoLogistico despacho) {
        try {
            DespachoLogistico nuevoDespacho = service.crear(despacho);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDespacho);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // HTTP GET: Obtiene el listado completo de despachos. Retorna estado 200 (OK)
    @GetMapping
    public ResponseEntity<List<DespachoLogistico>> obtenerTodos() {
        try {
            return ResponseEntity.ok(service.obtenerTodos());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // HTTP GET /{id}: Busca un despacho específico por su ID. Retorna 200 (OK) o 404 (Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<DespachoLogistico> obtenerPorId(@PathVariable UUID id) {
        try {
            return service.obtenerPorId(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // HTTP PUT /{id}: Actualiza los datos de un despacho existente. Retorna 200 (OK) o 404 (Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<DespachoLogistico> actualizar(@PathVariable UUID id, @RequestBody DespachoLogistico despachoDetalles) {
        try {
            return service.actualizar(id, despachoDetalles)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // HTTP DELETE /{id}: Elimina un despacho por su ID. Retorna 204 (No Content) o 404 (Not Found)
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
