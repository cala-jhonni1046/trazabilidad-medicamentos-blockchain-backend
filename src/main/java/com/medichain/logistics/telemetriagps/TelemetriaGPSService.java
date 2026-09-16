package com.medichain.logistics.telemetriagps;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelemetriaGPSService {

    @Autowired
    private TelemetriaGPSRepository repo;

    public List<TelemetriaGPS> getAll() {
        return repo.findAll();
    }

    public TelemetriaGPS getById(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public TelemetriaGPS create(TelemetriaGPS entidad) {
        return repo.save(entidad);
    }

    public TelemetriaGPS update(UUID id, TelemetriaGPS entidad) {
        TelemetriaGPS existente = this.getById(id);
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
