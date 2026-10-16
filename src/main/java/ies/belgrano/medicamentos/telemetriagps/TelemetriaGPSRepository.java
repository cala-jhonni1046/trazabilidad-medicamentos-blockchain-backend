package ies.belgrano.medicamentos.telemetriagps;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaGPSRepository extends JpaRepository<TelemetriaGPS, Long> {
}
