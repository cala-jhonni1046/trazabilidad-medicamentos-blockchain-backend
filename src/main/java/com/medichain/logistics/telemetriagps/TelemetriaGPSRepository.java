package com.medichain.logistics.telemetriagps;

// Repositorio JPA para realizar operaciones de base de datos sobre TelemetriaGPS
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// Define la interfaz repositorio anotada con @Repository
@Repository
public interface TelemetriaGPSRepository extends JpaRepository<TelemetriaGPS, UUID> {
}
