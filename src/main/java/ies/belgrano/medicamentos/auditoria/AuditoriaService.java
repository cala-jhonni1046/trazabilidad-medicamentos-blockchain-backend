package ies.belgrano.medicamentos.auditoria;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.security.AuthUser;
import ies.belgrano.medicamentos.security.AccesoOrganizacion;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final AccesoOrganizacion accesoOrganizacion;

    public AuditoriaService(AuditoriaRepository auditoriaRepository, AccesoOrganizacion accesoOrganizacion) {
        this.auditoriaRepository = auditoriaRepository;
        this.accesoOrganizacion = accesoOrganizacion;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(String metodo, String ruta, String recurso, String resultado) {
        try {
            AuthUser au = accesoOrganizacion.getAuthUser();
            Long usuarioId = au != null ? au.getId() : null;
            Long orgId = au != null ? au.getOrganizacionId() : null;

            Auditoria entrada = new Auditoria(usuarioId, orgId, metodo, ruta, recurso, Instant.now(), resultado);
            auditoriaRepository.save(entrada);
        } catch (Exception e) {
            // No propagar errores de auditoría al flujo principal
        }
    }

    @Transactional(readOnly = true)
    public org.springframework.data.domain.Page<Auditoria> getAll(
            Long usuarioId, Long organizacionId,
            org.springframework.data.domain.Pageable pageable) {
        if (usuarioId != null) {
            return auditoriaRepository.findByUsuarioIdOrderByFechaDesc(usuarioId, pageable);
        }
        if (organizacionId != null) {
            return auditoriaRepository.findByOrganizacionIdOrderByFechaDesc(organizacionId, pageable);
        }
        return auditoriaRepository.findAllByOrderByFechaDesc(pageable);
    }
}

