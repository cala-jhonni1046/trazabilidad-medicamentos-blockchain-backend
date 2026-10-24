package ies.belgrano.medicamentos.enlaceCUIT;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnlaceCUITRepository extends JpaRepository<EnlaceCUIT, Long> {

    List<EnlaceCUIT> findByEstado(EstadoEnlaceCuit estado);

    boolean existsByLaboratorioIdOrDistribuidorIdOrFarmaciaId(Long laboratorioId, Long distribuidorId, Long farmaciaId);

    boolean existsByLaboratorioIdAndDistribuidorIdAndFarmaciaId(Long laboratorioId, Long distribuidorId, Long farmaciaId);

    Optional<EnlaceCUIT> findByLaboratorioIdAndDistribuidorIdAndFarmaciaId(Long laboratorioId, Long distribuidorId, Long farmaciaId);

    @Query("""
            select e from EnlaceCUIT e
            where (:estado is null or e.estado = :estado)
              and (:laboratorioId is null or e.laboratorio.id = :laboratorioId)
              and (:distribuidorId is null or e.distribuidor.id = :distribuidorId)
              and (:farmaciaId is null or e.farmacia.id = :farmaciaId)
            """)
    List<EnlaceCUIT> findByFiltros(@Param("estado") EstadoEnlaceCuit estado,
                                  @Param("laboratorioId") Long laboratorioId,
                                  @Param("distribuidorId") Long distribuidorId,
                                  @Param("farmaciaId") Long farmaciaId);
}
