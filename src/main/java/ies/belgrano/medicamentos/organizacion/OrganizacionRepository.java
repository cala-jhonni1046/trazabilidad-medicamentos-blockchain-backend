package ies.belgrano.medicamentos.organizacion;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

    Optional<Organizacion> findByCuit(String cuit);

    boolean existsByCuit(String cuit);

    boolean existsByCuitAndIdNot(String cuit, Long id);
}
