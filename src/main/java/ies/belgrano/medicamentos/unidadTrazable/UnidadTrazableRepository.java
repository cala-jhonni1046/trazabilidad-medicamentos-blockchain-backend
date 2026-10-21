package ies.belgrano.medicamentos.unidadTrazable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadTrazableRepository extends JpaRepository<UnidadTrazable, Long> {

    boolean existsByLoteId(Long loteId);

    boolean existsByCodigoUnicoQR(String codigoUnicoQR);

    boolean existsByCodigoUnicoQRAndIdNot(String codigoUnicoQR, Long id);

    java.util.Optional<UnidadTrazable> findByCodigoUnicoQR(String codigoUnicoQR);

    java.util.List<UnidadTrazable> findByLoteId(Long loteId);

    java.util.List<UnidadTrazable> findByLoteIdAndEstado(Long loteId, EstadoUnidadTrazable estado);
}

