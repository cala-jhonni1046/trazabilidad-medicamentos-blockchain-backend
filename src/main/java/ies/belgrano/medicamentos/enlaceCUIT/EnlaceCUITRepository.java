package ies.belgrano.medicamentos.enlaceCUIT;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnlaceCUITRepository extends JpaRepository<EnlaceCUIT, Long> {

    List<EnlaceCUIT> findByEstado(EstadoEnlaceCuit estado);

    boolean existsByLaboratorioIdOrDistribuidorIdOrFarmaciaId(Long laboratorioId, Long distribuidorId, Long farmaciaId);
}
