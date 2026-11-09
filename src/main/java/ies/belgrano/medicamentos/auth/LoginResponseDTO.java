package ies.belgrano.medicamentos.auth;

import ies.belgrano.medicamentos.inspector.InspectorANMATResponseDTO;
import ies.belgrano.medicamentos.organizacion.OrganizacionResponseDTO;
import ies.belgrano.medicamentos.usuario.UsuarioResponseDTO;

public class LoginResponseDTO {

    private String token;
    private String tipo;
    private Long expiraEnSegundos;
    private UsuarioResponseDTO usuario;
    private OrganizacionResponseDTO organizacion;
    private InspectorANMATResponseDTO inspector;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String tipo, Long expiraEnSegundos,
                            UsuarioResponseDTO usuario, OrganizacionResponseDTO organizacion,
                            InspectorANMATResponseDTO inspector) {
        this.token = token;
        this.tipo = tipo;
        this.expiraEnSegundos = expiraEnSegundos;
        this.usuario = usuario;
        this.organizacion = organizacion;
        this.inspector = inspector;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getExpiraEnSegundos() { return expiraEnSegundos; }
    public void setExpiraEnSegundos(Long expiraEnSegundos) { this.expiraEnSegundos = expiraEnSegundos; }

    public UsuarioResponseDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioResponseDTO usuario) { this.usuario = usuario; }

    public OrganizacionResponseDTO getOrganizacion() { return organizacion; }
    public void setOrganizacion(OrganizacionResponseDTO organizacion) { this.organizacion = organizacion; }

    public InspectorANMATResponseDTO getInspector() { return inspector; }
    public void setInspector(InspectorANMATResponseDTO inspector) { this.inspector = inspector; }
}

