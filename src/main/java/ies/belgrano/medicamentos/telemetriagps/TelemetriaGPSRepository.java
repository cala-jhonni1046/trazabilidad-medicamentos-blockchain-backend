package ies.belgrano.medicamentos.telemetriagps;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaGPSRepository extends JpaRepository<TelemetriaGPS, Long> {

    List<TelemetriaGPS> findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(Long despachoLogisticoId);

    List<TelemetriaGPS> findAllByOrderByFechaHoraAscIdAsc();
}
