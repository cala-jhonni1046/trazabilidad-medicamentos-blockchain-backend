package ies.belgrano.medicamentos.recepcionfarmacia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionFarmaciaRepository extends JpaRepository<RecepcionFarmacia, Long> {

    boolean existsByNumeroComprobante(String numeroComprobante);

    boolean existsByNumeroComprobanteAndIdNot(String numeroComprobante, Long id);

    boolean existsByDespachoLogisticoId(Long despachoLogisticoId);

    List<RecepcionFarmacia> findByDespachoLogisticoId(Long despachoLogisticoId);

    List<RecepcionFarmacia> findByFarmaciaId(Long farmaciaId);

    List<RecepcionFarmacia> findByEstadoConformidad(EstadoRecepcion estadoConformidad);

    List<RecepcionFarmacia> findByFarmaciaIdAndEstadoConformidad(Long farmaciaId, EstadoRecepcion estadoConformidad);

    List<RecepcionFarmacia> findByDespachoLogisticoLoteIdAndFarmaciaId(Long loteId, Long farmaciaId);

    List<RecepcionFarmacia> findAllByOrderByFechaHoraRecepcionDescIdDesc();
}
