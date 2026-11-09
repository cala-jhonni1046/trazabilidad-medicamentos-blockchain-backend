package ies.belgrano.medicamentos.cuarentena;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuarentenaRepository extends JpaRepository<Cuarentena, Long> {

    boolean existsByCodigoCuarentena(String codigoCuarentena);

    boolean existsByCodigoCuarentenaAndIdNot(String codigoCuarentena, Long id);

    boolean existsByLoteIdAndEstado(Long loteId, EstadoCuarentena estado);

    Optional<Cuarentena> findFirstByLoteIdAndEstado(Long loteId, EstadoCuarentena estado);

    List<Cuarentena> findByLoteId(Long loteId);

    List<Cuarentena> findByEstado(EstadoCuarentena estado);

    List<Cuarentena> findByLoteIdAndEstado(Long loteId, EstadoCuarentena estado);

    /** Cuarentenas de los lotes que la organización puede ver (propios o de sus despachos). */
    @org.springframework.data.jpa.repository.Query("""
            select c from Cuarentena c
            where (c.lote.laboratorio.id = :orgId
                   or exists (select d.id from DespachoLogistico d
                              where d.lote = c.lote
                                and (d.distribuidor.id = :orgId or d.enlaceCuit.farmacia.id = :orgId)))
              and (:loteId is null or c.lote.id = :loteId)
              and (:estado is null or c.estado = :estado)
            """)
    List<Cuarentena> findVisiblesPorOrganizacion(
            @org.springframework.data.repository.query.Param("orgId") Long orgId,
            @org.springframework.data.repository.query.Param("loteId") Long loteId,
            @org.springframework.data.repository.query.Param("estado") EstadoCuarentena estado);
}
