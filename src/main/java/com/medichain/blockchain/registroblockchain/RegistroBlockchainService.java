package com.medichain.blockchain.registroblockchain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroBlockchainService {

    @Autowired
    private RegistroBlockchainRepository repo;

    public List<RegistroBlockchain> getAll() {
        return repo.findAll();
    }

    public RegistroBlockchain getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public RegistroBlockchain create(RegistroBlockchain entidad) {
        return repo.save(entidad);
    }

    public RegistroBlockchain update(UUID id, RegistroBlockchain entidad) {
        RegistroBlockchain existente = this.getById(id);
        if (existente == null) {
            return null;
        } else {
            entidad.setId(id);
            return repo.save(entidad);
        }
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
