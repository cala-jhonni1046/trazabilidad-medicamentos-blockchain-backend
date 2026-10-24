package ies.belgrano.medicamentos.consultapublica;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.cuarentena.Cuarentena;
import ies.belgrano.medicamentos.cuarentena.CuarentenaRepository;
import ies.belgrano.medicamentos.cuarentena.EstadoCuarentena;
import ies.belgrano.medicamentos.despachologistico.DespachoLogistico;
import ies.belgrano.medicamentos.despachologistico.DespachoLogisticoRepository;
import ies.belgrano.medicamentos.despachologistico.EventoCustodia;
import ies.belgrano.medicamentos.despachologistico.EventoCustodiaRepository;
import ies.belgrano.medicamentos.despachologistico.TipoEventoCustodia;
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.EstadoLote;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.medicamento.Medicamento;
import ies.belgrano.medicamentos.unidadTrazable.EstadoUnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazableRepository;

@Service
public class ConsultaPublicaService {

    private static final String AVISO_REGULATORIO = "Consulta pública informativa con fines demostrativos y académicos (MVP). "
            + "La información provista no constituye certificación sanitaria regulatoria oficial de la ANMAT "
            + "ni certificación de autenticidad en red blockchain distribuida. "
            + "Los hashes mostrados son demostrativos y no prueban autenticidad ni existencia de una blockchain distribuida.";

    private final UnidadTrazableRepository unidadRepo;
    private final CuarentenaRepository cuarentenaRepo;
    private final DespachoLogisticoRepository despachoRepo;
    private final EventoCustodiaRepository eventoCustodiaRepo;

    public ConsultaPublicaService(
            UnidadTrazableRepository unidadRepo,
            CuarentenaRepository cuarentenaRepo,
            DespachoLogisticoRepository despachoRepo,
            EventoCustodiaRepository eventoCustodiaRepo) {
        this.unidadRepo = unidadRepo;
        this.cuarentenaRepo = cuarentenaRepo;
        this.despachoRepo = despachoRepo;
        this.eventoCustodiaRepo = eventoCustodiaRepo;
    }

    @Transactional(readOnly = true)
    public ConsultaPublicaQRResponseDTO consultarPorQR(String codigoQr) {
        if (codigoQr == null || codigoQr.trim().isEmpty()) {
            throw new IllegalArgumentException("El código QR a consultar no puede estar vacío.");
        }

        String qrNormalizado = codigoQr.trim();
        UnidadTrazable unidad = unidadRepo.findByCodigoUnicoQR(qrNormalizado)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ninguna unidad trazable para el código QR: " + qrNormalizado));

        ConsultaPublicaQRResponseDTO response = new ConsultaPublicaQRResponseDTO();
        response.setCodigoConsultado(unidad.getCodigoUnicoQR());
        response.setEstadoUnidad(unidad.getEstado() != null ? unidad.getEstado().name() : null);
        response.setInformacionRegulatoria(AVISO_REGULATORIO);

        List<String> advertencias = new ArrayList<>();
        if (unidad.getEstado() == EstadoUnidadTrazable.BLOQUEADA) {
            advertencias.add("La unidad figura BLOQUEADA en el sistema.");
        } else if (unidad.getEstado() == EstadoUnidadTrazable.DISPENSADA) {
            advertencias.add("La unidad figura DISPENSADA en el sistema.");
        }

        Lote lote = unidad.getLote();
        if (lote != null) {
            Medicamento med = lote.getMedicamento();
            if (med != null) {
                response.setMedicamento(new ConsultaPublicaQRResponseDTO.MedicamentoConsultaDTO(
                        med.getNombre(),
                        med.getPrincipioActivo(),
                        med.isRequiereCadenaFrio()));
            }

            response.setLote(new ConsultaPublicaQRResponseDTO.LoteConsultaDTO(
                    lote.getCodigoLote(),
                    lote.getFechaHoraFabricacion(),
                    lote.getEstado() != null ? lote.getEstado().name() : null));
            if (lote.getEstado() == EstadoLote.EN_CUARENTENA) {
                advertencias.add("El lote figura EN_CUARENTENA en el sistema.");
            } else if (lote.getEstado() == EstadoLote.CERRADO) {
                advertencias.add("El lote figura CERRADO en el sistema.");
            }

            // B07: Integración de estado de cuarentena si existe
            Optional<Cuarentena> cuarentenaOpt = cuarentenaRepo.findFirstByLoteIdAndEstado(lote.getId(), EstadoCuarentena.ABIERTA);
            if (cuarentenaOpt.isPresent()) {
                Cuarentena c = cuarentenaOpt.get();
                response.setCuarentena(new ConsultaPublicaQRResponseDTO.CuarentenaConsultaDTO(
                        true,
                        "Cuarentena abierta del lote.",
                        c.getFechaInicio()));
            } else {
                response.setCuarentena(new ConsultaPublicaQRResponseDTO.CuarentenaConsultaDTO(
                        false,
                        null,
                        null));
            }

            // B04: Integración de resumen/historial de custodia excluyendo datos personales y coordenadas GPS
            List<DespachoLogistico> despachos = despachoRepo.findByLoteId(lote.getId());
            List<ConsultaPublicaQRResponseDTO.CustodiaConsultaDTO> eventosDto = new ArrayList<>();

            for (DespachoLogistico despacho : despachos) {
                List<EventoCustodia> eventos = eventoCustodiaRepo.findByDespachoIdOrderByFechaHoraEventoAscIdAsc(despacho.getId());
                for (EventoCustodia evento : eventos) {
                    eventosDto.add(new ConsultaPublicaQRResponseDTO.CustodiaConsultaDTO(
                            evento.getTipo() != null ? evento.getTipo().name() : null,
                            evento.getFechaHoraEvento(),
                            descripcionPublica(evento.getTipo()),
                            evento.getHashEvento()));
                }
            }

            response.setHistorialCustodia(eventosDto);
        }

        response.setAdvertencias(advertencias);
        return response;
    }

    private String descripcionPublica(TipoEventoCustodia tipo) {
        if (tipo == null) {
            return "Hito de custodia registrado.";
        }
        return switch (tipo) {
            case LOTE_DESPACHADO -> "Lote despachado.";
            case CUSTODIA_ASUMIDA -> "Inicio del transporte registrado.";
            case TELEMETRIA_REGISTRADA -> "Lectura de telemetría registrada.";
            case RECEPCION_CONFIRMADA -> "Recepción en farmacia registrada.";
            case RECEPCION_RECHAZADA -> "Recepción rechazada registrada.";
            case DESPACHO_CANCELADO -> "Despacho cancelado.";
            case CUARENTENA_ABIERTA -> "Cuarentena abierta.";
            case CUARENTENA_RESUELTA -> "Cuarentena resuelta.";
            case UNIDAD_DISPENSADA -> "Dispensación registrada.";
            case INCIDENTE_EN_RUTA -> "Incidente o demora en ruta registrado.";
        };
    }
}
