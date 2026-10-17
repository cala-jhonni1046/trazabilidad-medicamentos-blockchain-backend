package ies.belgrano.medicamentos.unidadTrazable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadTrazableRepository extends JpaRepository<UnidadTrazable, Long> {

    boolean existsByLoteId(Long loteId);
}

