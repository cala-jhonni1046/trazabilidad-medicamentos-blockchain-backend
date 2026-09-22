package ies.belgrano.medicamentos.operaciones.dispensacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispensacionRepository extends JpaRepository<Dispensacion, Long> {
}
