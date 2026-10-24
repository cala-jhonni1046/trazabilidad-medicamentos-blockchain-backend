package ies.belgrano.medicamentos.blockchain.registroblockchain;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ies.belgrano.medicamentos.exceptions.ResourceNotFoundException;

@Service
public class RegistroBlockchainService {

    private final RegistroBlockchainRepository repo;

    public RegistroBlockchainService(RegistroBlockchainRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<RegistroBlockchain> getAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public RegistroBlockchain getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro demostrativo no encontrado con ID: " + id));
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
