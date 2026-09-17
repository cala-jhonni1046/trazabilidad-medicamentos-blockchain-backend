package ies.belgrano.medicamentos.operaciones.dispensacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DispensacionRepository extends JpaRepository<Dispensacion, UUID> {
}
