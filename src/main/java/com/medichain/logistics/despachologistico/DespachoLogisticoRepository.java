package com.medichain.logistics.despachologistico;

// Importa JpaRepository de Spring Data JPA y la anotación Repository
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// Anotación que indica a Spring que esta interfaz es un componente de acceso a datos (DAO)
@Repository
// Al extender JpaRepository<DespachoLogistico, UUID>, heredamos automáticamente métodos como save, findById, findAll y deleteById
public interface DespachoLogisticoRepository extends JpaRepository<DespachoLogistico, UUID> {
}
