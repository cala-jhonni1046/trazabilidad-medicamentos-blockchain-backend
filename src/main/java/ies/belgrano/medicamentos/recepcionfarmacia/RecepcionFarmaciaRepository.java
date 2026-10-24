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

    @org.springframework.data.jpa.repository.Query("""
            select r from RecepcionFarmacia r
            where (:despachoLogisticoId is null or r.despachoLogistico.id = :despachoLogisticoId)
              and (:farmaciaId is null or r.farmacia.id = :farmaciaId)
              and (:estadoConformidad is null or r.estadoConformidad = :estadoConformidad)
            order by r.fechaHoraRecepcion desc, r.id desc
            """)
    List<RecepcionFarmacia> findByFiltros(
            @org.springframework.data.repository.query.Param("despachoLogisticoId") Long despachoLogisticoId,
            @org.springframework.data.repository.query.Param("farmaciaId") Long farmaciaId,
            @org.springframework.data.repository.query.Param("estadoConformidad") EstadoRecepcion estadoConformidad);
}
