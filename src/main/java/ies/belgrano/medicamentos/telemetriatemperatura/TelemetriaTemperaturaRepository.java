package ies.belgrano.medicamentos.telemetriatemperatura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaTemperaturaRepository extends JpaRepository<TelemetriaTemperatura, Long> {

    List<TelemetriaTemperatura> findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(Long despachoLogisticoId);

    List<TelemetriaTemperatura> findAllByOrderByFechaHoraAscIdAsc();
}
