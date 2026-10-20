package ies.belgrano.medicamentos.cuarentena;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuarentenaRepository extends JpaRepository<Cuarentena, Long> {

    boolean existsByCodigoCuarentena(String codigoCuarentena);

    boolean existsByCodigoCuarentenaAndIdNot(String codigoCuarentena, Long id);

    boolean existsByLoteIdAndEstado(Long loteId, EstadoCuarentena estado);

    Optional<Cuarentena> findFirstByLoteIdAndEstado(Long loteId, EstadoCuarentena estado);

    List<Cuarentena> findByLoteId(Long loteId);

    List<Cuarentena> findByEstado(EstadoCuarentena estado);

    List<Cuarentena> findByLoteIdAndEstado(Long loteId, EstadoCuarentena estado);
}
