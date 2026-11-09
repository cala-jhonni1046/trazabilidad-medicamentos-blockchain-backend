package ies.belgrano.medicamentos.blockchain.registroblockchain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;
import ies.belgrano.medicamentos.lote.LoteRepository;
import ies.belgrano.medicamentos.security.AccesoOrganizacion;

@Service
public class RegistroBlockchainService {

    private final RegistroBlockchainRepository repo;
    private final LoteRepository loteRepo;
    private final AccesoOrganizacion acceso;

    public RegistroBlockchainService(RegistroBlockchainRepository repo, LoteRepository loteRepo,
                                     AccesoOrganizacion acceso) {
        this.repo = repo;
        this.loteRepo = loteRepo;
        this.acceso = acceso;
    }

    @Transactional(readOnly = true)
    public List<RegistroBlockchain> getAll() {
        if (!acceso.esAdminOGlobal()) {
            Long orgId = acceso.organizacionActualId();
            return orgId == null ? List.of() : repo.findVisiblesPorOrganizacion(orgId);
        }
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public RegistroBlockchain getById(Long id) {
        RegistroBlockchain registro = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro demostrativo no encontrado con ID: " + id));
        if (!acceso.esAdminOGlobal()) {
            Long orgId = acceso.organizacionActualId();
            if (orgId == null || registro.getLote() == null
                    || !loteRepo.esVisiblePorOrganizacion(registro.getLote().getId(), orgId)) {
                throw new ResourceNotFoundException("Registro demostrativo no encontrado con ID: " + id);
            }
        }
        return registro;
    }

    @Transactional
    public RegistroBlockchain create(RegistroBlockchain registro) {
        return repo.save(registro);
    }

    @Transactional
    public RegistroBlockchain update(Long id, RegistroBlockchain registro) {
        getById(id);
        registro.setId(id);
        return repo.save(registro);
    }

    @Transactional
    public void delete(Long id) {
        getById(id);
        repo.deleteById(id);
    }
}
