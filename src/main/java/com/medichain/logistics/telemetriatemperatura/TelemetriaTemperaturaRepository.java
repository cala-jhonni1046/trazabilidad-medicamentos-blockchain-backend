package com.medichain.logistics.telemetriatemperatura;

// Importaciones de Spring Data JPA
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// Interfaz del repositorio de acceso a datos para lecturas de temperatura
@Repository
public interface TelemetriaTemperaturaRepository extends JpaRepository<TelemetriaTemperatura, UUID> {
}
