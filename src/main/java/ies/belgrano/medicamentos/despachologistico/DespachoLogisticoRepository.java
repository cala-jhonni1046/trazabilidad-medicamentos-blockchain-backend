package ies.belgrano.medicamentos.despachologistico;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("""
            select d from DespachoLogistico d
            where (:loteId is null or d.lote.id = :loteId)
              and (:distribuidorId is null or d.distribuidor.id = :distribuidorId)
              and (:estado is null or d.estado = :estado)
              and (:laboratorioId is null or d.lote.laboratorio.id = :laboratorioId)
              and (:farmaciaDestinoId is null or d.enlaceCuit.farmacia.id = :farmaciaDestinoId)
            """)
    List<DespachoLogistico> findByFiltros(@Param("loteId") Long loteId,
                                         @Param("distribuidorId") Long distribuidorId,
                                         @Param("estado") EstadoDespacho estado,
                                         @Param("laboratorioId") Long laboratorioId,
                                         @Param("farmaciaDestinoId") Long farmaciaDestinoId);

    Optional<DespachoLogistico> findByNumeroRemito(String numeroRemito);

    Optional<DespachoLogistico> findByCodigoSeguimiento(String codigoSeguimiento);
}
