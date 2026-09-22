package ies.belgrano.medicamentos.reporteciudadano;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteCiudadanoRepository extends JpaRepository<ReporteCiudadano, Long> {
}
