package ies.belgrano.medicamentos.inspector;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InspectorANMATRepository extends JpaRepository<InspectorANMAT, Long> {

	Optional<InspectorANMAT> findByUsuarioId(Long usuarioId);

	boolean existsByUsuarioId(Long usuarioId);

	boolean existsByUsuarioIdAndIdNot(Long usuarioId, Long id);

	boolean existsByLegajoOficial(String legajoOficial);

	Optional<InspectorANMAT> findByLegajoOficial(String legajoOficial);

	boolean existsByLegajoOficialAndIdNot(String legajoOficial, Long id);
}
