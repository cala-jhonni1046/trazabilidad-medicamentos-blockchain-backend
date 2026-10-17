package ies.belgrano.medicamentos.lote;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Long> {

    boolean existsByCodigoLote(String codigoLote);

    boolean existsByCodigoLoteAndIdNot(String codigoLote, Long id);

    boolean existsByMedicamentoId(Long medicamentoId);

    List<Lote> findByMedicamentoId(Long medicamentoId);
}

