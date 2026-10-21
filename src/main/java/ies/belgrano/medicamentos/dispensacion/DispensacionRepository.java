package ies.belgrano.medicamentos.dispensacion;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispensacionRepository extends JpaRepository<Dispensacion, Long> {

    boolean existsByUnidadTrazableId(Long unidadTrazableId);

    Optional<Dispensacion> findByUnidadTrazableId(Long unidadTrazableId);

    List<Dispensacion> findByFarmaciaId(Long farmaciaId);

    List<Dispensacion> findByFarmaciaIdAndUnidadTrazableId(Long farmaciaId, Long unidadTrazableId);

    List<Dispensacion> findAllByOrderByFechaDispensacionDescIdDesc();
}
