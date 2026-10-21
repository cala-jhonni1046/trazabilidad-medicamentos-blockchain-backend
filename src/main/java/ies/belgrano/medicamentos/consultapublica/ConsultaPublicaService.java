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
import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.Lote;
import ies.belgrano.medicamentos.medicamento.Medicamento;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazable;
import ies.belgrano.medicamentos.unidadTrazable.UnidadTrazableRepository;

@Service
public class ConsultaPublicaService {

    private static final String AVISO_REGULATORIO = "Consulta pública informativa con fines demostrativos y académicos (MVP). "
            + "La información provista no constituye certificación sanitaria regulatoria oficial de la ANMAT "
            + "ni certificación de autenticidad en red blockchain distribuida.";

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

            // B07: Integración de estado de cuarentena si existe
            Optional<Cuarentena> cuarentenaOpt = cuarentenaRepo.findFirstByLoteIdAndEstado(lote.getId(), EstadoCuarentena.ABIERTA);
            if (cuarentenaOpt.isPresent()) {
                Cuarentena c = cuarentenaOpt.get();
                response.setCuarentena(new ConsultaPublicaQRResponseDTO.CuarentenaConsultaDTO(
                        true,
                        c.getCodigoCuarentena(),
                        c.getMotivo(),
                        c.getFechaInicio()));
            } else {
                response.setCuarentena(new ConsultaPublicaQRResponseDTO.CuarentenaConsultaDTO(
                        false,
                        null,
                        null,
                        null));
            }

            // B04: Integración de resumen/historial de custodia excluyendo datos personales y coordenadas GPS
            List<DespachoLogistico> despachos = despachoRepo.findByLoteId(lote.getId());
            List<ConsultaPublicaQRResponseDTO.CustodiaConsultaDTO> eventosDto = new ArrayList<>();

            for (DespachoLogistico despacho : despachos) {
                List<EventoCustodia> eventos = eventoCustodiaRepo.findByDespachoIdOrderByFechaHoraEventoAscIdAsc(despacho.getId());
                for (EventoCustodia evento : eventos) {
                    String actorNombre = evento.getOrganizacionActor() != null
                            ? evento.getOrganizacionActor().getNombre()
                            : null;
                    eventosDto.add(new ConsultaPublicaQRResponseDTO.CustodiaConsultaDTO(
                            evento.getTipo() != null ? evento.getTipo().name() : null,
                            evento.getFechaHoraEvento(),
                            actorNombre,
                            evento.getDescripcion(),
                            evento.getHashEvento()));
                }
            }

            response.setHistorialCustodia(eventosDto);
        }

        return response;
    }
}
