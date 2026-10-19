package ies.belgrano.medicamentos.despachologistico;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespachoLogisticoRepository extends JpaRepository<DespachoLogistico, Long> {

    boolean existsByLoteId(Long loteId);

    boolean existsByNumeroRemito(String numeroRemito);

    boolean existsByNumeroRemitoAndIdNot(String numeroRemito, Long id);

    boolean existsByCodigoSeguimiento(String codigoSeguimiento);

    boolean existsByCodigoSeguimientoAndIdNot(String codigoSeguimiento, Long id);

    boolean existsByDistribuidorId(Long distribuidorId);

    boolean existsByEnlaceCuitId(Long enlaceCuitId);

    List<DespachoLogistico> findByLoteId(Long loteId);

    List<DespachoLogistico> findByDistribuidorId(Long distribuidorId);

    List<DespachoLogistico> findByEstado(EstadoDespacho estado);

    List<DespachoLogistico> findByLoteIdAndDistribuidorId(Long loteId, Long distribuidorId);

    Optional<DespachoLogistico> findByNumeroRemito(String numeroRemito);

    Optional<DespachoLogistico> findByCodigoSeguimiento(String codigoSeguimiento);
}
