package ies.belgrano.medicamentos.organizacion;

 feature/b01-medicamentos-lotes

import java.util.Optional;
 main
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {
 feature/b01-medicamentos-lotes

    Optional<Organizacion> findByCuit(String cuit);

    boolean existsByCuit(String cuit);

    boolean existsByCuitAndIdNot(String cuit, Long id);
 main
}
