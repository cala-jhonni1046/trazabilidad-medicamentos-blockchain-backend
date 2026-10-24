package ies.belgrano.medicamentos.unidadTrazable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadTrazableRepository extends JpaRepository<UnidadTrazable, Long> {

    boolean existsByLoteId(Long loteId);

    boolean existsByCodigoUnicoQR(String codigoUnicoQR);

    boolean existsByCodigoUnicoQRAndIdNot(String codigoUnicoQR, Long id);

    java.util.Optional<UnidadTrazable> findByCodigoUnicoQR(String codigoUnicoQR);

    java.util.List<UnidadTrazable> findByLoteId(Long loteId);

    java.util.List<UnidadTrazable> findByLoteIdAndEstado(Long loteId, EstadoUnidadTrazable estado);

    java.util.List<UnidadTrazable> findByCodigoUnicoQRIn(java.util.Collection<String> codigos);

    @org.springframework.data.jpa.repository.Query("""
            select u from UnidadTrazable u
            where (:loteId is null or u.lote.id = :loteId)
              and (:codigoQr is null or u.codigoUnicoQR = :codigoQr)
              and (:estado is null or u.estado = :estado)
            """)
    java.util.List<UnidadTrazable> findByFiltros(
            @org.springframework.data.repository.query.Param("loteId") Long loteId,
            @org.springframework.data.repository.query.Param("codigoQr") String codigoQr,
            @org.springframework.data.repository.query.Param("estado") EstadoUnidadTrazable estado);
}

