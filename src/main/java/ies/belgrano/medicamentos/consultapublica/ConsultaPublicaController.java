package ies.belgrano.medicamentos.consultapublica;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/api/public/consulta-qr", "/api/consulta-publica/qr" })
public class ConsultaPublicaController {

    private final ConsultaPublicaService service;

    public ConsultaPublicaController(ConsultaPublicaService service) {
        this.service = service;
    }

    @GetMapping("/{codigoQr}")
    public ResponseEntity<ConsultaPublicaQRResponseDTO> consultarPorQR(@PathVariable String codigoQr) {
        ConsultaPublicaQRResponseDTO resultado = service.consultarPorQR(codigoQr);
        return ResponseEntity.ok(resultado);
    }
}
