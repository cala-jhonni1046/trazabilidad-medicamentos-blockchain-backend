package ies.belgrano.medicamentos.blockchain.registroblockchain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroBlockchainService {

    @Autowired
    private RegistroBlockchainRepository repo;

    public List<RegistroBlockchain> getAll() {
        return repo.findAll();
    }

    public RegistroBlockchain getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public RegistroBlockchain create(RegistroBlockchain registro) {
        return repo.save(registro);
    }

    public RegistroBlockchain update(Long id, RegistroBlockchain registro) {
        RegistroBlockchain existente = this.getById(id);
        if (existente == null) {
            return null;
        } else {
            registro.setId(id);
            return repo.save(registro);
        }
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
