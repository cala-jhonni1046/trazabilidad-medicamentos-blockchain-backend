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
}
