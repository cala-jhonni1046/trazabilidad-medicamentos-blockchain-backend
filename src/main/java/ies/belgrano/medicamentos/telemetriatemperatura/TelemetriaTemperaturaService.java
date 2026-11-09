package ies.belgrano.medicamentos.telemetriatemperatura;

import java.util.List;

import ies.belgrano.medicamentos.security.AccesoOrganizacion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoRepository;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.Lote;

@Service
public class TelemetriaTemperaturaService {

    private final TelemetriaTemperaturaRepository repo;
    private final DespachoLogisticoRepository despachoRepo;
    private final AccesoOrganizacion acceso;

    public TelemetriaTemperaturaService(TelemetriaTemperaturaRepository repo,
                                       DespachoLogisticoRepository despachoRepo,
                                AccesoOrganizacion acceso) {
        this.repo = repo;
        this.despachoRepo = despachoRepo;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<TelemetriaTemperatura> getAll(Long despachoLogisticoId) {
        boolean admin = acceso.esAdminOGlobal();
        Long orgId = acceso.organizacionActualId();
        if (!admin && orgId == null) {
            return List.of();
        }
        if (despachoLogisticoId != null) {
            boolean existe = admin ? despachoRepo.existsById(despachoLogisticoId)
                    : despachoRepo.esVisiblePorOrganizacion(despachoLogisticoId, orgId);
            if (!existe) {
                throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + despachoLogisticoId);
            }
            return repo.findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(despachoLogisticoId);
        }
        return admin ? repo.findAllByOrderByFechaHoraAscIdAsc() : repo.findVisiblesPorOrganizacion(orgId);
    }

    @Transactional(readOnly = true)
    public List<TelemetriaTemperatura> getAll() {
        return getAll(null);
    }

    @Transactional(readOnly = true)
    public TelemetriaTemperatura getById(Long id) {
        TelemetriaTemperatura lectura = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lectura de telemetría de temperatura no encontrada con ID: " + id));
        if (!acceso.esAdminOGlobal()) {
            Long orgId = acceso.organizacionActualId();
            if (orgId == null || !despachoRepo.esVisiblePorOrganizacion(lectura.getDespachoLogistico().getId(), orgId)) {
                throw new ResourceNotFoundException("Lectura de telemetría de temperatura no encontrada con ID: " + id);
            }
        }
        return lectura;
    }

    @Transactional
    public TelemetriaTemperatura create(TelemetriaTemperaturaRequestDTO dto) {
        if (dto.getDespachoLogisticoId() == null) {
            throw new IllegalArgumentException("El ID de despacho logístico es obligatorio.");
        }
        if (!acceso.esAdminOGlobal()) {
            // Solo el laboratorio del lote o el distribuidor cargan telemetría de un despacho
            Long orgId = acceso.organizacionActualId();
            if (orgId == null || !despachoRepo.esGestionablePorOrganizacion(dto.getDespachoLogisticoId(), orgId)) {
                throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + dto.getDespachoLogisticoId());
            }
        }
        DespachoLogistico despacho = despachoRepo.findById(dto.getDespachoLogisticoId())
                .orElseThrow(() -> new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + dto.getDespachoLogisticoId()));

        if (dto.getTemperatura() == null || !Double.isFinite(dto.getTemperatura())) {
            throw new IllegalArgumentException("La temperatura debe ser un número finito válido.");
        }

        Lote lote = despacho.getLote();
        Double tempMin = lote != null ? lote.getTempMin() : null;
        Double tempMax = lote != null ? lote.getTempMax() : null;

        Boolean alertaExcursion;
        if (tempMin == null || tempMax == null) {
            // Rango térmico no parametrizado en el lote: estado no evaluable
            alertaExcursion = null;
        } else {
            // Comparación con límites inclusivos: menor al mínimo o mayor al máximo es excursión
            alertaExcursion = dto.getTemperatura() < tempMin || dto.getTemperatura() > tempMax;
        }

        TelemetriaTemperatura entidad = new TelemetriaTemperatura();
        entidad.setSensorId(dto.getSensorId() != null ? dto.getSensorId().trim() : null);
        entidad.setTemperatura(dto.getTemperatura());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setAlertaExcursion(alertaExcursion);
        entidad.setDespachoLogistico(despacho);
        entidad.setObservaciones(dto.getObservaciones() != null ? dto.getObservaciones().trim() : null);

        return repo.save(entidad);
    }
}

