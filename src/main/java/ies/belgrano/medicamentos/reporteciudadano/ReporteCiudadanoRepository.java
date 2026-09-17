package ies.belgrano.medicamentos.reporteciudadano;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReporteCiudadanoRepository extends JpaRepository<ReporteCiudadano, UUID> {
}
