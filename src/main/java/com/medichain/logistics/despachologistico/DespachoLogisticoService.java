package com.medichain.logistics.despachologistico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DespachoLogisticoService {

    @Autowired
    private DespachoLogisticoRepository repo;

    public List<DespachoLogistico> getAll() {
        return repo.findAll();
    }

    public DespachoLogistico getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public DespachoLogistico create(DespachoLogistico entidad) {
        return repo.save(entidad);
    }

    public DespachoLogistico update(UUID id, DespachoLogistico entidad) {
        DespachoLogistico existente = this.getById(id);
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
