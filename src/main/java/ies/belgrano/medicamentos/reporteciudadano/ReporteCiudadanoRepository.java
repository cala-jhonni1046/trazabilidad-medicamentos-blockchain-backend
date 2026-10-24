package ies.belgrano.medicamentos.reporteciudadano;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteCiudadanoRepository extends JpaRepository<ReporteCiudadano, Long> {

    List<ReporteCiudadano> findByEstado(EstadoReporte estado);

    List<ReporteCiudadano> findByUnidadTrazableId(Long unidadTrazableId);

    List<ReporteCiudadano> findByInspectorId(Long inspectorId);

    List<ReporteCiudadano> findByEstadoAndUnidadTrazableId(EstadoReporte estado, Long unidadTrazableId);

    @org.springframework.data.jpa.repository.Query("""
            select r from ReporteCiudadano r
            where (:estado is null or r.estado = :estado)
              and (:unidadTrazableId is null or r.unidadTrazable.id = :unidadTrazableId)
              and (:inspectorId is null or r.inspector.id = :inspectorId)
            """)
    List<ReporteCiudadano> findByFiltros(
            @org.springframework.data.repository.query.Param("estado") EstadoReporte estado,
            @org.springframework.data.repository.query.Param("unidadTrazableId") Long unidadTrazableId,
            @org.springframework.data.repository.query.Param("inspectorId") Long inspectorId);
}
