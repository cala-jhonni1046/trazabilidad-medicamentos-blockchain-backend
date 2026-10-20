package ies.belgrano.medicamentos.telemetriatemperatura;

import java.util.List;

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

    public TelemetriaTemperaturaService(TelemetriaTemperaturaRepository repo,
                                       DespachoLogisticoRepository despachoRepo) {
        this.repo = repo;
        this.despachoRepo = despachoRepo;
    }

    @Transactional(readOnly = true)
    public List<TelemetriaTemperatura> getAll(Long despachoLogisticoId) {
        if (despachoLogisticoId != null) {
            if (!despachoRepo.existsById(despachoLogisticoId)) {
                throw new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + despachoLogisticoId);
            }
            return repo.findByDespachoLogisticoIdOrderByFechaHoraAscIdAsc(despachoLogisticoId);
        }
        return repo.findAllByOrderByFechaHoraAscIdAsc();
    }

    @Transactional(readOnly = true)
    public List<TelemetriaTemperatura> getAll() {
        return getAll(null);
    }

    @Transactional(readOnly = true)
    public TelemetriaTemperatura getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lectura de telemetría de temperatura no encontrada con ID: " + id));
    }

    @Transactional
    public TelemetriaTemperatura create(TelemetriaTemperaturaRequestDTO dto) {
        if (dto.getDespachoLogisticoId() == null) {
            throw new IllegalArgumentException("El ID de despacho logístico es obligatorio.");
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

