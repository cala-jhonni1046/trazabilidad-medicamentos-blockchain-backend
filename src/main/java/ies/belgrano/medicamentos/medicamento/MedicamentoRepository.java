package ies.belgrano.medicamentos.medicamento;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    Optional<Medicamento> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
