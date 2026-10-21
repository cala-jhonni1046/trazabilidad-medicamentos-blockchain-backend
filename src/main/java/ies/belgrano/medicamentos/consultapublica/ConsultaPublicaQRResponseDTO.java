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
    private List<String> advertencias;
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

    public List<String> getAdvertencias() {
        return advertencias;
    }

    public void setAdvertencias(List<String> advertencias) {
        this.advertencias = advertencias;
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
        private String resumen;
        private LocalDateTime fechaInicio;

        public CuarentenaConsultaDTO() {
        }

        public CuarentenaConsultaDTO(boolean enCuarentena, String resumen, LocalDateTime fechaInicio) {
            this.enCuarentena = enCuarentena;
            this.resumen = resumen;
            this.fechaInicio = fechaInicio;
        }

        public boolean isEnCuarentena() {
            return enCuarentena;
        }

        public void setEnCuarentena(boolean enCuarentena) {
            this.enCuarentena = enCuarentena;
        }

        public String getResumen() {
            return resumen;
        }

        public void setResumen(String resumen) {
            this.resumen = resumen;
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
        private String descripcion;
        private String hashDemostrativo;

        public CustodiaConsultaDTO() {
        }

        public CustodiaConsultaDTO(String tipoEvento, LocalDateTime fechaHoraEvento,
                                  String descripcion, String hashDemostrativo) {
            this.tipoEvento = tipoEvento;
            this.fechaHoraEvento = fechaHoraEvento;
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
