package ies.belgrano.medicamentos.cuarentena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuarentenaRepository extends JpaRepository<Cuarentena, Long> {
}
