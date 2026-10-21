package ies.belgrano.medicamentos.consultapublica;

import java.time.LocalDateTime;
import java.util.List;

public class ConsultaPublicaQRResponseDTO {

    private String codigoConsultado;
    private String estadoUnidad;
    private MedicamentoConsultaDTO medicamento;
    private LoteConsultaDTO lote;
    private CuarentenaConsultaDTO cuarentena;
    private List<CustodiaConsultaDTO> historialCustodia;
    private String informacionRegulatoria;

    public ConsultaPublicaQRResponseDTO() {
    }

    public String getCodigoConsultado() {
        return codigoConsultado;
    }

    public void setCodigoConsultado(String codigoConsultado) {
        this.codigoConsultado = codigoConsultado;
    }

    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    public MedicamentoConsultaDTO getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoConsultaDTO medicamento) {
        this.medicamento = medicamento;
    }

    public LoteConsultaDTO getLote() {
        return lote;
    }

    public void setLote(LoteConsultaDTO lote) {
        this.lote = lote;
    }

    public CuarentenaConsultaDTO getCuarentena() {
        return cuarentena;
    }

    public void setCuarentena(CuarentenaConsultaDTO cuarentena) {
        this.cuarentena = cuarentena;
    }

    public List<CustodiaConsultaDTO> getHistorialCustodia() {
        return historialCustodia;
    }

    public void setHistorialCustodia(List<CustodiaConsultaDTO> historialCustodia) {
        this.historialCustodia = historialCustodia;
    }

    public String getInformacionRegulatoria() {
        return informacionRegulatoria;
    }

    public void setInformacionRegulatoria(String informacionRegulatoria) {
        this.informacionRegulatoria = informacionRegulatoria;
    }

    public static class MedicamentoConsultaDTO {
        private String nombre;
        private String principioActivo;
        private boolean requiereCadenaFrio;

        public MedicamentoConsultaDTO() {
        }

        public MedicamentoConsultaDTO(String nombre, String principioActivo, boolean requiereCadenaFrio) {
            this.nombre = nombre;
            this.principioActivo = principioActivo;
            this.requiereCadenaFrio = requiereCadenaFrio;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPrincipioActivo() {
            return principioActivo;
        }

        public void setPrincipioActivo(String principioActivo) {
            this.principioActivo = principioActivo;
        }

        public boolean isRequiereCadenaFrio() {
            return requiereCadenaFrio;
        }

        public void setRequiereCadenaFrio(boolean requiereCadenaFrio) {
            this.requiereCadenaFrio = requiereCadenaFrio;
        }
    }

    public static class LoteConsultaDTO {
        private String codigoLote;
        private LocalDateTime fechaFabricacion;
        private String estadoLote;

        public LoteConsultaDTO() {
        }

        public LoteConsultaDTO(String codigoLote, LocalDateTime fechaFabricacion, String estadoLote) {
            this.codigoLote = codigoLote;
            this.fechaFabricacion = fechaFabricacion;
            this.estadoLote = estadoLote;
        }

        public String getCodigoLote() {
            return codigoLote;
        }

        public void setCodigoLote(String codigoLote) {
            this.codigoLote = codigoLote;
        }

        public LocalDateTime getFechaFabricacion() {
            return fechaFabricacion;
        }

        public void setFechaFabricacion(LocalDateTime fechaFabricacion) {
            this.fechaFabricacion = fechaFabricacion;
        }

        public String getEstadoLote() {
            return estadoLote;
        }

        public void setEstadoLote(String estadoLote) {
            this.estadoLote = estadoLote;
        }
    }

    public static class CuarentenaConsultaDTO {
        private boolean enCuarentena;
        private String codigoCuarentena;
        private String motivo;
        private LocalDateTime fechaInicio;

        public CuarentenaConsultaDTO() {
        }

        public CuarentenaConsultaDTO(boolean enCuarentena, String codigoCuarentena, String motivo, LocalDateTime fechaInicio) {
            this.enCuarentena = enCuarentena;
            this.codigoCuarentena = codigoCuarentena;
            this.motivo = motivo;
            this.fechaInicio = fechaInicio;
        }

        public boolean isEnCuarentena() {
            return enCuarentena;
        }

        public void setEnCuarentena(boolean enCuarentena) {
            this.enCuarentena = enCuarentena;
        }

        public String getCodigoCuarentena() {
            return codigoCuarentena;
        }

        public void setCodigoCuarentena(String codigoCuarentena) {
            this.codigoCuarentena = codigoCuarentena;
        }

        public String getMotivo() {
            return motivo;
        }

        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }

        public LocalDateTime getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(LocalDateTime fechaInicio) {
            this.fechaInicio = fechaInicio;
        }
    }

    public static class CustodiaConsultaDTO {
        private String tipoEvento;
        private LocalDateTime fechaHoraEvento;
        private String organizacion;
        private String descripcion;
        private String hashDemostrativo;

        public CustodiaConsultaDTO() {
        }

        public CustodiaConsultaDTO(String tipoEvento, LocalDateTime fechaHoraEvento, String organizacion,
                                  String descripcion, String hashDemostrativo) {
            this.tipoEvento = tipoEvento;
            this.fechaHoraEvento = fechaHoraEvento;
            this.organizacion = organizacion;
            this.descripcion = descripcion;
            this.hashDemostrativo = hashDemostrativo;
        }

        public String getTipoEvento() {
            return tipoEvento;
        }

        public void setTipoEvento(String tipoEvento) {
            this.tipoEvento = tipoEvento;
        }

        public LocalDateTime getFechaHoraEvento() {
            return fechaHoraEvento;
        }

        public void setFechaHoraEvento(LocalDateTime fechaHoraEvento) {
            this.fechaHoraEvento = fechaHoraEvento;
        }

        public String getOrganizacion() {
            return organizacion;
        }

        public void setOrganizacion(String organizacion) {
            this.organizacion = organizacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getHashDemostrativo() {
            return hashDemostrativo;
        }

        public void setHashDemostrativo(String hashDemostrativo) {
            this.hashDemostrativo = hashDemostrativo;
        }
    }
}
