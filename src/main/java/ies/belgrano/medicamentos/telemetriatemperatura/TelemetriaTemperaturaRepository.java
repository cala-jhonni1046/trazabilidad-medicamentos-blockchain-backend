package ies.belgrano.medicamentos.telemetriatemperatura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaTemperaturaRepository extends JpaRepository<TelemetriaTemperatura, Long> {

    List<TelemetriaTemperatura> findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(Long despachoLogisticoId);

    List<TelemetriaTemperatura> findAllByOrderByFechaHoraAscIdAsc();

    /** Lecturas de los despachos en los que participa la organización (laboratorio, distribuidor o farmacia). */
    @org.springframework.data.jpa.repository.Query("""
            select t from TelemetriaTemperatura t
            where t.despachoLogistico.lote.laboratorio.id = :orgId
               or t.despachoLogistico.distribuidor.id = :orgId
               or t.despachoLogistico.enlaceCuit.farmacia.id = :orgId
            order by t.fechaHora asc, t.id asc
            """)
    List<TelemetriaTemperatura> findVisiblesPorOrganizacion(
            @org.springframework.data.repository.query.Param("orgId") Long orgId);
}
