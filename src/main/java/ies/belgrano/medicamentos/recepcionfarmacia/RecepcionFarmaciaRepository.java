package ies.belgrano.medicamentos.recepcionfarmacia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionFarmaciaRepository extends JpaRepository<RecepcionFarmacia, Long> {
}
