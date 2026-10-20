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

    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("SELECT l FROM Lote l WHERE l.id = :id")
    java.util.Optional<Lote> findByIdWithLock(@org.springframework.data.repository.query.Param("id") Long id);
}

