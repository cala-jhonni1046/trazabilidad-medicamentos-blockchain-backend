package ies.belgrano.medicamentos.inspector;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectorANMATRepository extends JpaRepository<InspectorANMAT, Long> {

	Optional<InspectorANMAT> findByUsuarioId(Long usuarioId);

}
