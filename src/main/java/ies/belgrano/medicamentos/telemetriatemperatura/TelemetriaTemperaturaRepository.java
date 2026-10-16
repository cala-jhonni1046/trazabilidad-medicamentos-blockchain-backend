package ies.belgrano.medicamentos.telemetriatemperatura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaTemperaturaRepository extends JpaRepository<TelemetriaTemperatura, Long> {
}
