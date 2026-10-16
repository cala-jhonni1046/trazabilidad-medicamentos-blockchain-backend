package ies.belgrano.medicamentos.despachologistico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoLogisticoRepository extends JpaRepository<DespachoLogistico, Long> {
}
