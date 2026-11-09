package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import ies.belgrano.medicamentos.security.AccesoOrganizacion;
import ies.belgrano.medicamentos.security.AuthUser;
import ies.belgrano.medicamentos.usuario.RolUsuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.inspector.EstadoInspector;
import ies.belgrano.medicamentos.inspector.InspectorANMAT;
import ies.belgrano.medicamentos.inspector.InspectorANMATRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;
import ies.belgrano.medicamentos.organizacion.TipoOrganizacion;

@Service
public class EnlaceCUITService {

    private final EnlaceCUITRepository enlaceRepo;
    private final OrganizacionRepository organizacionRepo;
    private final InspectorANMATRepository inspectorRepo;
    private final AccesoOrganizacion acceso;

    public EnlaceCUITService(EnlaceCUITRepository enlaceRepo,
                             OrganizacionRepository organizacionRepo,
                             InspectorANMATRepository inspectorRepo,
                             AccesoOrganizacion acceso) {
        this.enlaceRepo = enlaceRepo;
        this.organizacionRepo = organizacionRepo;
        this.inspectorRepo = inspectorRepo;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<EnlaceCUIT> getAll(EstadoEnlaceCuit estado) {
        return getAll(estado, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<EnlaceCUIT> getAll(EstadoEnlaceCuit estado, Long laboratorioId, Long distribuidorId, Long farmaciaId) {
        if (!acceso.esAdminOGlobal()) {
            // Empresas: solo los enlaces en los que participan
            Long orgId = acceso.organizacionActualId();
            if (orgId == null) {
                return List.of();
            }
            return enlaceRepo.findVisiblesPorOrganizacion(orgId, estado, laboratorioId, distribuidorId, farmaciaId);
        }
        if (estado == null && laboratorioId == null && distribuidorId == null && farmaciaId == null) {
            return enlaceRepo.findAll();
        }
        return enlaceRepo.findByFiltros(estado, laboratorioId, distribuidorId, farmaciaId);
    }

    @Transactional(readOnly = true)
    public EnlaceCUIT getById(Long id) {
        EnlaceCUIT enlace = enlaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enlace CUIT no encontrado con ID: " + id));
        // 404 si la organización del usuario no participa del enlace (SUPERADMIN/ANMAT pasan)
        acceso.verificarAccesoMultiple(enlace.getLaboratorio().getId(),
                enlace.getDistribuidor().getId(), enlace.getFarmacia().getId());
        return enlace;
    }

    /** Escritura: solo el laboratorio titular del enlace (o SUPERADMIN/ANMAT, filtrados por rol en SecurityConfig). */
    private EnlaceCUIT getPropio(Long id) {
        EnlaceCUIT enlace = getById(id);
        acceso.verificarAcceso(enlace.getLaboratorio().getId());
        return enlace;
    }

    /**
     * El inspector que resuelve un enlace sale del usuario autenticado cuando es ANMAT (no se puede
     * firmar en nombre de otro inspector). SUPERADMIN indica el inspector en el pedido.
     */
    private InspectorANMAT resolverInspector(EnlaceResolucionRequestDTO resolucion, String accion) {
        AuthUser au = acceso.getAuthUser();
        InspectorANMAT inspector;
        if (au != null && au.getRol() == RolUsuario.ANMAT) {
            inspector = inspectorRepo.findByUsuarioId(au.getId())
                    .orElseThrow(() -> new IllegalStateException("Este usuario ANMAT no tiene perfil de inspector."));
        } else {
            Long inspectorId = resolucion.getInspectorId();
            if (inspectorId == null) {
                throw new IllegalArgumentException("El ID del inspector es obligatorio para " + accion + ".");
            }
            inspector = inspectorRepo.findById(inspectorId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el inspector ANMAT con ID: " + inspectorId));
        }
        if (inspector.getEstado() != EstadoInspector.HABILITADO) {
            throw new IllegalStateException("El inspector ANMAT con ID " + inspector.getId()
                    + " no se encuentra activo/habilitado (estado actual: " + inspector.getEstado() + ").");
        }
        return inspector;
    }

    @Transactional
    public EnlaceCUIT create(EnlaceCUITRequestDTO dto) {
        if (dto.getEstado() != null) {
            throw new IllegalArgumentException("El request de creación no puede definir el estado inicial del enlace; siempre inicia en PENDIENTE.");
        }
        if (dto.getInspectorAprobadorId() != null) {
            throw new IllegalArgumentException("El request de creación no puede asignar un inspector aprobador.");
        }

        // Un laboratorio solo solicita enlaces a su nombre: se ignora el laboratorioId del pedido.
        Long laboratorioId = acceso.organizacionObligatoria();
        if (laboratorioId == null) {
            laboratorioId = dto.getLaboratorioId(); // SUPERADMIN
        }
        if (laboratorioId == null) {
            throw new IllegalArgumentException("El ID de laboratorio es obligatorio.");
        }
        final Long labId = laboratorioId;
        Organizacion lab = organizacionRepo.findById(labId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la organización de laboratorio con ID: " + labId));
        if (lab.getTipo() != TipoOrganizacion.LABORATORIO) {
            throw new IllegalArgumentException("La organización con ID " + labId + " no es de tipo LABORATORIO (tipo actual: " + lab.getTipo() + ").");
        }

        Organizacion dist = organizacionRepo.findById(dto.getDistribuidorId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la organización de distribuidor con ID: " + dto.getDistribuidorId()));
        if (dist.getTipo() != TipoOrganizacion.DISTRIBUIDOR) {
            throw new IllegalArgumentException("La organización con ID " + dto.getDistribuidorId() + " no es de tipo DISTRIBUIDOR (tipo actual: " + dist.getTipo() + ").");
        }

        Organizacion farm = organizacionRepo.findById(dto.getFarmaciaId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la organización de farmacia con ID: " + dto.getFarmaciaId()));
        if (farm.getTipo() != TipoOrganizacion.FARMACIA) {
            throw new IllegalArgumentException("La organización con ID " + dto.getFarmaciaId() + " no es de tipo FARMACIA (tipo actual: " + farm.getTipo() + ").");
        }

        EnlaceCUIT entity = new EnlaceCUIT();
        entity.setLaboratorio(lab);
        entity.setDistribuidor(dist);
        entity.setFarmacia(farm);
        entity.setEstado(EstadoEnlaceCuit.PENDIENTE);
        entity.setFechaSolicitud(LocalDateTime.now(ZoneOffset.UTC));
        entity.setInspectorAprobador(null);
        entity.setFechaAprobacionANMAT(null);
        entity.setNumeroDisposicionANMAT(dto.getNumeroDisposicionANMAT());
        entity.setDocumentoCertificadoUrl(dto.getDocumentoCertificadoUrl());

        return enlaceRepo.save(entity);
    }

    @Transactional
    public EnlaceCUIT update(Long id, EnlaceCUITRequestDTO dto) {
        EnlaceCUIT existente = getPropio(id);

        if (dto.getEstado() != null && dto.getEstado() != existente.getEstado()) {
            throw new IllegalStateException("No está permitido modificar el estado mediante PUT genérico. Use las operaciones de aprobación o rechazo.");
        }
        if (dto.getInspectorAprobadorId() != null) {
            throw new IllegalStateException("No está permitido modificar el inspector aprobador mediante PUT genérico.");
        }
        if (dto.getFechaSolicitud() != null || dto.getFechaAprobacionANMAT() != null) {
            throw new IllegalStateException("No está permitido modificar las fechas de auditoría mediante PUT genérico.");
        }

        existente.setNumeroDisposicionANMAT(dto.getNumeroDisposicionANMAT());
        existente.setDocumentoCertificadoUrl(dto.getDocumentoCertificadoUrl());
        return enlaceRepo.save(existente);
    }

    @Transactional
    public EnlaceCUIT aprobar(Long id, EnlaceResolucionRequestDTO resolucion) {
        EnlaceCUIT enlace = getById(id);

        if (enlace.getEstado() != EstadoEnlaceCuit.PENDIENTE) {
            throw new IllegalStateException("Solo se puede aprobar un enlace en estado PENDIENTE. Estado actual: " + enlace.getEstado());
        }

        InspectorANMAT inspector = resolverInspector(resolucion, "la aprobación");

        enlace.setEstado(EstadoEnlaceCuit.ACTIVO);
        enlace.setInspectorAprobador(inspector);
        enlace.setFechaAprobacionANMAT(LocalDateTime.now(ZoneOffset.UTC));

        if (resolucion.getNumeroDisposicionANMAT() != null && !resolucion.getNumeroDisposicionANMAT().isBlank()) {
            enlace.setNumeroDisposicionANMAT(resolucion.getNumeroDisposicionANMAT());
        }
        if (resolucion.getDocumentoCertificadoUrl() != null && !resolucion.getDocumentoCertificadoUrl().isBlank()) {
            enlace.setDocumentoCertificadoUrl(resolucion.getDocumentoCertificadoUrl());
        }

        return enlaceRepo.save(enlace);
    }

    @Transactional
    public EnlaceCUIT rechazar(Long id, EnlaceResolucionRequestDTO resolucion) {
        EnlaceCUIT enlace = getById(id);

        if (enlace.getEstado() != EstadoEnlaceCuit.PENDIENTE) {
            throw new IllegalStateException("Solo se puede rechazar un enlace en estado PENDIENTE. Estado actual: " + enlace.getEstado());
        }

        // Solo se valida que el inspector exista y esté habilitado; el rechazo no lo guarda en el enlace
        resolverInspector(resolucion, "el rechazo");

        enlace.setEstado(EstadoEnlaceCuit.RECHAZADO);

        if (resolucion.getNumeroDisposicionANMAT() != null && !resolucion.getNumeroDisposicionANMAT().isBlank()) {
            enlace.setNumeroDisposicionANMAT(resolucion.getNumeroDisposicionANMAT());
        }
        if (resolucion.getDocumentoCertificadoUrl() != null && !resolucion.getDocumentoCertificadoUrl().isBlank()) {
            enlace.setDocumentoCertificadoUrl(resolucion.getDocumentoCertificadoUrl());
        }

        return enlaceRepo.save(enlace);
    }

    @Transactional
    public void delete(Long id) {
        EnlaceCUIT enlace = getPropio(id);
        try {
            enlaceRepo.delete(enlace);
            enlaceRepo.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el enlace CUIT con ID " + id + " debido a restricciones de integridad con registros dependientes.");
        }
    }
}
