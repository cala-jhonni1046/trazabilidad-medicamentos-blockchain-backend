package ies.belgrano.medicamentos.lote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    boolean existsByCodigoLote(String codigoLote);

    boolean existsByCodigoLoteAndIdNot(String codigoLote, Long id);

    boolean existsByMedicamentoId(Long medicamentoId);

    List<Lote> findByMedicamentoId(Long medicamentoId);

    /**
     * Lotes que una organización puede ver: los propios (si es el laboratorio) y los que figuran en
     * despachos donde participa como distribuidor o como farmacia destino.
     */
    @org.springframework.data.jpa.repository.Query("""
            select l from Lote l
            where (l.laboratorio.id = :orgId
                   or exists (select d.id from DespachoLogistico d
                              where d.lote = l
                                and (d.distribuidor.id = :orgId or d.enlaceCuit.farmacia.id = :orgId)))
              and (:medicamentoId is null or l.medicamento.id = :medicamentoId)
              and (:laboratorioId is null or l.laboratorio.id = :laboratorioId)
              and (:estado is null or l.estado = :estado)
            """)
    List<Lote> findVisiblesPorOrganizacion(
            @org.springframework.data.repository.query.Param("orgId") Long orgId,
            @org.springframework.data.repository.query.Param("medicamentoId") Long medicamentoId,
            @org.springframework.data.repository.query.Param("laboratorioId") Long laboratorioId,
            @org.springframework.data.repository.query.Param("estado") EstadoLote estado);

    @org.springframework.data.jpa.repository.Query("""
            select count(l) > 0 from Lote l
            where l.id = :loteId
              and (l.laboratorio.id = :orgId
                   or exists (select d.id from DespachoLogistico d
                              where d.lote = l
                                and (d.distribuidor.id = :orgId or d.enlaceCuit.farmacia.id = :orgId)))
            """)
    boolean esVisiblePorOrganizacion(
            @org.springframework.data.repository.query.Param("loteId") Long loteId,
            @org.springframework.data.repository.query.Param("orgId") Long orgId);

    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("SELECT l FROM Lote l WHERE l.id = :id")
    java.util.Optional<Lote> findByIdWithLock(@org.springframework.data.repository.query.Param("id") Long id);

    @org.springframework.data.jpa.repository.Query("""
            select l from Lote l
            where (:medicamentoId is null or l.medicamento.id = :medicamentoId)
              and (:laboratorioId is null or l.laboratorio.id = :laboratorioId)
              and (:estado is null or l.estado = :estado)
            """)
    List<Lote> findByFiltros(@org.springframework.data.repository.query.Param("medicamentoId") Long medicamentoId,
                             @org.springframework.data.repository.query.Param("laboratorioId") Long laboratorioId,
                             @org.springframework.data.repository.query.Param("estado") EstadoLote estado);
}

