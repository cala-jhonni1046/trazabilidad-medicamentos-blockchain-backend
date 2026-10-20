package ies.belgrano.medicamentos.despachologistico;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUIT;
import ies.belgrano.medicamentos.enlaceCUIT.EnlaceCUITRepository;
import ies.belgrano.medicamentos.enlaceCUIT.EstadoEnlaceCuit;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.lote.LoteRepository;
import ies.belgrano.medicamentos.organizacion.Organizacion;
import ies.belgrano.medicamentos.organizacion.OrganizacionRepository;
import ies.belgrano.medicamentos.organizacion.TipoOrganizacion;

@Service
public class DespachoLogisticoService {

    private final DespachoLogisticoRepository despachoRepo;
    private final EventoCustodiaRepository eventoRepo;
    private final LoteRepository loteRepo;
    private final OrganizacionRepository organizacionRepo;
    private final EnlaceCUITRepository enlaceRepo;

    public DespachoLogisticoService(DespachoLogisticoRepository despachoRepo,
                                    EventoCustodiaRepository eventoRepo,
                                    LoteRepository loteRepo,
                                    OrganizacionRepository organizacionRepo,
                                    EnlaceCUITRepository enlaceRepo) {
        this.despachoRepo = despachoRepo;
        this.eventoRepo = eventoRepo;
        this.loteRepo = loteRepo;
        this.organizacionRepo = organizacionRepo;
        this.enlaceRepo = enlaceRepo;
    }

    @Transactional(readOnly = true)
    public List<DespachoLogistico> getAll(Long loteId, Long distribuidorId, EstadoDespacho estado) {
        if (loteId != null && distribuidorId != null) {
            return despachoRepo.findByLoteIdAndDistribuidorId(loteId, distribuidorId);
        }
        if (loteId != null) {
            return despachoRepo.findByLoteId(loteId);
        }
        if (distribuidorId != null) {
            return despachoRepo.findByDistribuidorId(distribuidorId);
        }
        if (estado != null) {
            return despachoRepo.findByEstado(estado);
        }
        return despachoRepo.findAll();
    }

    @Transactional(readOnly = true)
    public DespachoLogistico getById(Long id) {
        return despachoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despacho logístico no encontrado con ID: " + id));
    }

    @Transactional
    public DespachoLogistico create(DespachoLogisticoRequestDTO dto) {
        String remito = dto.getNumeroRemito() != null ? dto.getNumeroRemito().trim() : null;
        if (remito == null || remito.isBlank()) {
            throw new IllegalArgumentException("El número de remito es obligatorio.");
        }
        if (despachoRepo.existsByNumeroRemito(remito)) {
            throw new DataIntegrityViolationException("Ya existe un despacho logístico registrado con el número de remito: " + remito);
        }

        String tracking = dto.getCodigoSeguimiento() != null && !dto.getCodigoSeguimiento().isBlank()
                ? dto.getCodigoSeguimiento().trim() : null;
        if (tracking != null && despachoRepo.existsByCodigoSeguimiento(tracking)) {
            throw new DataIntegrityViolationException("Ya existe un despacho logístico registrado con el código de seguimiento: " + tracking);
        }

        if (dto.getLoteId() == null) {
            throw new IllegalArgumentException("El ID de lote es obligatorio.");
        }
        Lote lote = loteRepo.findById(dto.getLoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote no encontrado con ID: " + dto.getLoteId()));

        if (dto.getDistribuidorId() == null) {
            throw new IllegalArgumentException("El ID de distribuidor es obligatorio.");
        }
        Organizacion distribuidor = organizacionRepo.findById(dto.getDistribuidorId())
                .orElseThrow(() -> new ResourceNotFoundException("Distribuidor no encontrado con ID: " + dto.getDistribuidorId()));
        if (distribuidor.getTipo() != TipoOrganizacion.DISTRIBUIDOR) {
            throw new IllegalArgumentException("La organización indicada con ID " + dto.getDistribuidorId()
                    + " no es de tipo DISTRIBUIDOR (tipo actual: " + distribuidor.getTipo() + ").");
        }

        if (dto.getEnlaceCuitId() == null) {
            throw new IllegalArgumentException("El ID de enlace CUIT es obligatorio.");
        }
        EnlaceCUIT enlace = enlaceRepo.findById(dto.getEnlaceCuitId())
                .orElseThrow(() -> new ResourceNotFoundException("Enlace CUIT no encontrado con ID: " + dto.getEnlaceCuitId()));

        if (enlace.getEstado() != EstadoEnlaceCuit.ACTIVO) {
            throw new IllegalStateException("El enlace CUIT con ID " + dto.getEnlaceCuitId()
                    + " debe estar en estado ACTIVO (estado actual: " + enlace.getEstado() + ").");
        }

        // Comprobar que el laboratorio del lote coincide con el laboratorio del enlace
        if (lote.getLaboratorio() == null || enlace.getLaboratorio() == null ||
                !enlace.getLaboratorio().getId().equals(lote.getLaboratorio().getId())) {
            Long enlaceLabId = enlace.getLaboratorio() != null ? enlace.getLaboratorio().getId() : null;
            Long loteLabId = lote.getLaboratorio() != null ? lote.getLaboratorio().getId() : null;
            throw new IllegalArgumentException("El laboratorio del enlace CUIT (ID " + enlaceLabId
                    + ") no coincide con el laboratorio del lote (ID " + loteLabId + ").");
        }

        // Comprobar que el distribuidor del enlace coincide con el distribuidor del despacho
        if (enlace.getDistribuidor() == null || !enlace.getDistribuidor().getId().equals(distribuidor.getId())) {
            Long enlaceDistId = enlace.getDistribuidor() != null ? enlace.getDistribuidor().getId() : null;
            throw new IllegalArgumentException("El distribuidor del enlace CUIT (ID " + enlaceDistId
                    + ") no coincide con el distribuidor asignado al despacho (ID " + distribuidor.getId() + ").");
        }

        // Comprobar la farmacia de destino si fue provista explícitamente en el request
        if (dto.getFarmaciaDestinoId() != null) {
            Organizacion farmacia = organizacionRepo.findById(dto.getFarmaciaDestinoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Farmacia destino no encontrada con ID: " + dto.getFarmaciaDestinoId()));
            if (farmacia.getTipo() != TipoOrganizacion.FARMACIA) {
                throw new IllegalArgumentException("La organización indicada como farmacia destino con ID "
                        + dto.getFarmaciaDestinoId() + " no es de tipo FARMACIA (tipo actual: " + farmacia.getTipo() + ").");
            }
            if (enlace.getFarmacia() == null || !enlace.getFarmacia().getId().equals(farmacia.getId())) {
                Long enlaceFarmId = enlace.getFarmacia() != null ? enlace.getFarmacia().getId() : null;
                throw new IllegalArgumentException("La farmacia destino indicada (ID " + dto.getFarmaciaDestinoId()
                        + ") no coincide con la farmacia habilitada en el enlace CUIT (ID " + enlaceFarmId + ").");
            }
        }

        if (dto.getTemperaturaMinimaPermitida() != null && dto.getTemperaturaMaximaPermitida() != null
                && dto.getTemperaturaMinimaPermitida() > dto.getTemperaturaMaximaPermitida()) {
            throw new IllegalArgumentException("La temperatura mínima permitida (" + dto.getTemperaturaMinimaPermitida()
                    + "°C) no puede superar la temperatura máxima permitida (" + dto.getTemperaturaMaximaPermitida() + "°C).");
        }

        DespachoLogistico entity = new DespachoLogistico();
        entity.setNumeroRemito(remito);
        entity.setCodigoSeguimiento(tracking);
        entity.setOrigen(dto.getOrigen());
        entity.setDestino(dto.getDestino());
        entity.setDistribuidor(distribuidor);
        entity.setVehiculoPatente(dto.getVehiculoPatente());
        entity.setFechaSalida(dto.getFechaSalida());
        entity.setFechaEstimadaEntrega(dto.getFechaEstimadaEntrega());
        entity.setFechaEntregaReal(null);
        entity.setEstado(EstadoDespacho.PREPARADO);
        entity.setTemperaturaMinimaPermitida(dto.getTemperaturaMinimaPermitida());
        entity.setTemperaturaMaximaPermitida(dto.getTemperaturaMaximaPermitida());
        entity.setObservaciones(dto.getObservaciones());
        entity.setLote(lote);
        entity.setEnlaceCuit(enlace);

        return despachoRepo.save(entity);
    }

    @Transactional
    public DespachoLogistico update(Long id, DespachoLogisticoRequestDTO dto) {
        DespachoLogistico existente = getById(id);

        if (existente.getEstado() == EstadoDespacho.CANCELADO || existente.getEstado() == EstadoDespacho.RECIBIDO) {
            throw new IllegalStateException("No se puede modificar un despacho en estado terminal: " + existente.getEstado());
        }

        if (dto.getEstado() != null && dto.getEstado() != existente.getEstado()) {
            throw new IllegalStateException("No está permitido modificar el estado del despacho mediante actualización general. Use los endpoints de transición de estado específicos.");
        }

        String remito = dto.getNumeroRemito() != null ? dto.getNumeroRemito().trim() : null;
        if (remito == null || remito.isBlank()) {
            throw new IllegalArgumentException("El número de remito es obligatorio.");
        }
        if (!remito.equals(existente.getNumeroRemito())) {
            if (despachoRepo.existsByNumeroRemitoAndIdNot(remito, id)) {
                throw new DataIntegrityViolationException("Ya existe otro despacho logístico registrado con el número de remito: " + remito);
            }
            existente.setNumeroRemito(remito);
        }

        String tracking = dto.getCodigoSeguimiento() != null && !dto.getCodigoSeguimiento().isBlank()
                ? dto.getCodigoSeguimiento().trim() : null;
        if (tracking != null && !tracking.equals(existente.getCodigoSeguimiento())) {
            if (despachoRepo.existsByCodigoSeguimientoAndIdNot(tracking, id)) {
                throw new DataIntegrityViolationException("Ya existe otro despacho logístico registrado con el código de seguimiento: " + tracking);
            }
            existente.setCodigoSeguimiento(tracking);
        } else if (dto.getCodigoSeguimiento() != null && dto.getCodigoSeguimiento().isBlank()) {
            existente.setCodigoSeguimiento(null);
        }

        if (dto.getTemperaturaMinimaPermitida() != null && dto.getTemperaturaMaximaPermitida() != null
                && dto.getTemperaturaMinimaPermitida() > dto.getTemperaturaMaximaPermitida()) {
            throw new IllegalArgumentException("La temperatura mínima permitida no puede superar la temperatura máxima permitida.");
        }

        existente.setOrigen(dto.getOrigen());
        existente.setDestino(dto.getDestino());
        existente.setVehiculoPatente(dto.getVehiculoPatente());
        existente.setFechaEstimadaEntrega(dto.getFechaEstimadaEntrega());
        existente.setTemperaturaMinimaPermitida(dto.getTemperaturaMinimaPermitida());
        existente.setTemperaturaMaximaPermitida(dto.getTemperaturaMaximaPermitida());
        existente.setObservaciones(dto.getObservaciones());

        return despachoRepo.save(existente);
    }

    @Transactional
    public DespachoLogistico iniciarDespacho(Long id, TransicionDespachoRequestDTO dto) {
        DespachoLogistico despacho = getById(id);

        if (despacho.getEstado() != EstadoDespacho.PREPARADO) {
            throw new IllegalStateException("Solo se puede iniciar un despacho en estado PREPARADO. Estado actual: " + despacho.getEstado());
        }

        LocalDateTime ahora = LocalDateTime.now(ZoneOffset.UTC);
        despacho.setEstado(EstadoDespacho.EN_TRANSITO);
        if (despacho.getFechaSalida() == null) {
            despacho.setFechaSalida(ahora);
        }

        if (dto != null && dto.getObservaciones() != null && !dto.getObservaciones().isBlank()) {
            despacho.setObservaciones(dto.getObservaciones());
        }

        DespachoLogistico actualizado = despachoRepo.save(despacho);

        // Registro atómico de evento de custodia por cambio de estado
        String desc = (dto != null && dto.getObservaciones() != null && !dto.getObservaciones().isBlank())
                ? dto.getObservaciones()
                : "Custodia asumida por distribuidor al iniciar el transporte del despacho.";
        registrarEventoInterno(actualizado, TipoEventoCustodia.CUSTODIA_ASUMIDA, ahora, actualizado.getDistribuidor(), desc);

        return actualizado;
    }

    @Transactional
    public DespachoLogistico entregarDespacho(Long id, TransicionDespachoRequestDTO dto) {
        DespachoLogistico despacho = getById(id);

        if (despacho.getEstado() != EstadoDespacho.EN_TRANSITO) {
            throw new IllegalStateException("Solo se puede registrar entrega/recepción de un despacho en estado EN_TRANSITO. Estado actual: " + despacho.getEstado());
        }

        LocalDateTime ahora = LocalDateTime.now(ZoneOffset.UTC);
        despacho.setEstado(EstadoDespacho.RECIBIDO);
        despacho.setFechaEntregaReal(ahora);

        if (dto != null && dto.getObservaciones() != null && !dto.getObservaciones().isBlank()) {
            despacho.setObservaciones(dto.getObservaciones());
        }

        DespachoLogistico actualizado = despachoRepo.save(despacho);

        Organizacion farmacia = (actualizado.getEnlaceCuit() != null) ? actualizado.getEnlaceCuit().getFarmacia() : null;
        String desc = (dto != null && dto.getObservaciones() != null && !dto.getObservaciones().isBlank())
                ? dto.getObservaciones()
                : "Recepción logística del despacho confirmada en destino.";
        registrarEventoInterno(actualizado, TipoEventoCustodia.RECEPCION_CONFIRMADA, ahora, farmacia, desc);

        return actualizado;
    }

    @Transactional
    public DespachoLogistico cancelarDespacho(Long id, TransicionDespachoRequestDTO dto) {
        DespachoLogistico despacho = getById(id);

        if (despacho.getEstado() == EstadoDespacho.CANCELADO || despacho.getEstado() == EstadoDespacho.RECIBIDO) {
            throw new IllegalStateException("No se puede cancelar un despacho en estado terminal: " + despacho.getEstado());
        }

        despacho.setEstado(EstadoDespacho.CANCELADO);
        if (dto != null && dto.getObservaciones() != null && !dto.getObservaciones().isBlank()) {
            despacho.setObservaciones(dto.getObservaciones());
        }

        return despachoRepo.save(despacho);
    }

    @Transactional
    public void delete(Long id) {
        DespachoLogistico existente = getById(id);

        if (eventoRepo.existsByDespachoId(id)) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el despacho logístico con ID " + id + " porque tiene eventos de custodia asociados.");
        }

        try {
            despachoRepo.delete(existente);
            despachoRepo.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "No es posible eliminar el despacho logístico con ID " + id + " debido a dependencias con otros registros.");
        }
    }

    @Transactional(readOnly = true)
    public List<EventoCustodia> getEventosByDespachoId(Long despachoId) {
        // Valida que el despacho exista
        getById(despachoId);
        return eventoRepo.findByDespachoIdOrderByFechaHoraEventoAscIdAsc(despachoId);
    }

    @Transactional
    public EventoCustodia registrarEvento(Long despachoId, EventoCustodiaRequestDTO dto) {
        DespachoLogistico despacho = getById(despachoId);

        if (dto.getTipo() == null) {
            throw new IllegalArgumentException("El tipo de evento de custodia es obligatorio.");
        }

        Organizacion actor = null;
        if (dto.getOrganizacionActorId() != null) {
            actor = organizacionRepo.findById(dto.getOrganizacionActorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Organización actora no encontrada con ID: " + dto.getOrganizacionActorId()));

            Long actorId = actor.getId();
            Long labId = (despacho.getLote() != null && despacho.getLote().getLaboratorio() != null)
                    ? despacho.getLote().getLaboratorio().getId() : null;
            Long distId = despacho.getDistribuidor() != null ? despacho.getDistribuidor().getId() : null;
            Long farmId = (despacho.getEnlaceCuit() != null && despacho.getEnlaceCuit().getFarmacia() != null)
                    ? despacho.getEnlaceCuit().getFarmacia().getId() : null;

            boolean perteneceContexto = (labId != null && labId.equals(actorId))
                    || (distId != null && distId.equals(actorId))
                    || (farmId != null && farmId.equals(actorId));

            if (!perteneceContexto) {
                throw new IllegalArgumentException("La organización actora con ID " + actorId
                        + " no pertenece al contexto del despacho (laboratorio: " + labId
                        + ", distribuidor: " + distId + ", farmacia: " + farmId + ").");
            }
        }

        LocalDateTime fechaHora = dto.getFechaHoraEvento() != null
                ? dto.getFechaHoraEvento()
                : LocalDateTime.now(ZoneOffset.UTC);

        return registrarEventoInterno(despacho, dto.getTipo(), fechaHora, actor, dto.getDescripcion());
    }

    @Transactional
    public EventoCustodia registrarEventoInterno(DespachoLogistico despacho,
                                                 TipoEventoCustodia tipo,
                                                 LocalDateTime fechaHora,
                                                 Organizacion actor,
                                                 String descripcion) {
        Optional<EventoCustodia> ultimoEvento = eventoRepo.findTopByDespachoIdOrderByFechaHoraEventoDescIdDesc(despacho.getId());
        String hashAnterior = ultimoEvento.map(EventoCustodia::getHashEvento).orElse(null);

        Long actorId = actor != null ? actor.getId() : null;
        String hashEvento = calcularHashLocal(despacho.getId(), tipo, fechaHora, actorId, descripcion, hashAnterior);

        EventoCustodia evento = new EventoCustodia();
        evento.setDespacho(despacho);
        evento.setTipo(tipo);
        evento.setFechaHoraEvento(fechaHora);
        evento.setOrganizacionActor(actor);
        evento.setDescripcion(descripcion);
        evento.setHashAnterior(hashAnterior);
        evento.setHashEvento(hashEvento);

        return eventoRepo.save(evento);
    }

    private String calcularHashLocal(Long despachoId,
                                    TipoEventoCustodia tipo,
                                    LocalDateTime fechaHora,
                                    Long actorId,
                                    String descripcion,
                                    String hashAnterior) {
        try {
            String canonical = String.format("%d|%s|%s|%s|%s|%s",
                    despachoId,
                    tipo != null ? tipo.name() : "",
                    fechaHora != null ? fechaHora.toString() : "",
                    actorId != null ? actorId.toString() : "",
                    descripcion != null ? descripcion.trim() : "",
                    hashAnterior != null ? hashAnterior : "");

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(canonical.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Error al calcular el hash local SHA-256 del evento de custodia.", e);
        }
    }
}
