package ies.belgrano.medicamentos.enlaceCUIT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
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

    public EnlaceCUITService(EnlaceCUITRepository enlaceRepo,
                             OrganizacionRepository organizacionRepo,
                             InspectorANMATRepository inspectorRepo) {
        this.enlaceRepo = enlaceRepo;
        this.organizacionRepo = organizacionRepo;
        this.inspectorRepo = inspectorRepo;
    }

    @Transactional(readOnly = true)
    public List<EnlaceCUIT> getAll(EstadoEnlaceCuit estado) {
        if (estado != null) {
            return enlaceRepo.findByEstado(estado);
        }
        return enlaceRepo.findAll();
    }

    @Transactional(readOnly = true)
    public EnlaceCUIT getById(Long id) {
        return enlaceRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enlace CUIT no encontrado con ID: " + id));
    }

    @Transactional
    public EnlaceCUIT create(EnlaceCUITRequestDTO dto) {
        if (dto.getEstado() != null) {
            throw new IllegalArgumentException("El request de creación no puede definir el estado inicial del enlace; siempre inicia en PENDIENTE.");
        }
        if (dto.getInspectorAprobadorId() != null) {
            throw new IllegalArgumentException("El request de creación no puede asignar un inspector aprobador.");
        }

        Organizacion lab = organizacionRepo.findById(dto.getLaboratorioId())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la organización de laboratorio con ID: " + dto.getLaboratorioId()));
        if (lab.getTipo() != TipoOrganizacion.LABORATORIO) {
            throw new IllegalArgumentException("La organización con ID " + dto.getLaboratorioId() + " no es de tipo LABORATORIO (tipo actual: " + lab.getTipo() + ").");
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
        EnlaceCUIT existente = getById(id);

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

        Long inspectorId = resolucion.getInspectorId();
        if (inspectorId == null) {
            throw new IllegalArgumentException("El ID del inspector es obligatorio para la aprobación.");
        }

        InspectorANMAT inspector = inspectorRepo.findById(inspectorId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el inspector ANMAT con ID: " + inspectorId));

        if (inspector.getEstado() != EstadoInspector.HABILITADO) {
            throw new IllegalStateException("El inspector ANMAT con ID " + inspectorId + " no se encuentra activo/habilitado (estado actual: " + inspector.getEstado() + ").");
        }

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

        Long inspectorId = resolucion.getInspectorId();
        if (inspectorId == null) {
            throw new IllegalArgumentException("El ID del inspector es obligatorio para el rechazo.");
        }

        InspectorANMAT inspector = inspectorRepo.findById(inspectorId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el inspector ANMAT con ID: " + inspectorId));

        if (inspector.getEstado() != EstadoInspector.HABILITADO) {
            throw new IllegalStateException("El inspector ANMAT con ID " + inspectorId + " no se encuentra activo/habilitado (estado actual: " + inspector.getEstado() + ").");
        }

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
        EnlaceCUIT enlace = getById(id);
        try {
            enlaceRepo.delete(enlace);
            enlaceRepo.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el enlace CUIT con ID " + id + " debido a restricciones de integridad con registros dependientes.");
        }
    }
}
