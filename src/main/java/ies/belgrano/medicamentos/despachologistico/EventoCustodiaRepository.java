package ies.belgrano.medicamentos.despachologistico;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoCustodiaRepository extends JpaRepository<EventoCustodia, Long> {

    List<EventoCustodia> findByDespachoIdOrderByFechaHoraEventoAscIdAsc(Long despachoId);

    Optional<EventoCustodia> findTopByDespachoIdOrderByFechaHoraEventoDescIdDesc(Long despachoId);

    boolean existsByDespachoId(Long despachoId);

    boolean existsByOrganizacionActorId(Long organizacionActorId);
}
