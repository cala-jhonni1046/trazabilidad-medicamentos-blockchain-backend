package com.medichain.logistics.telemetriatemperatura;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelemetriaTemperaturaService {

    @Autowired
    private TelemetriaTemperaturaRepository repo;

    public List<TelemetriaTemperatura> getAll() {
        return repo.findAll();
    }

    public TelemetriaTemperatura getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public TelemetriaTemperatura create(TelemetriaTemperatura entidad) {
        return repo.save(entidad);
    }

    public TelemetriaTemperatura update(UUID id, TelemetriaTemperatura entidad) {
        TelemetriaTemperatura existente = this.getById(id);
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
