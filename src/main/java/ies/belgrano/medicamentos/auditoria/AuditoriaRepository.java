package ies.belgrano.medicamentos.auditoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {

    Page<Auditoria> findAllByOrderByFechaDesc(Pageable pageable);

    Page<Auditoria> findByUsuarioIdOrderByFechaDesc(Long usuarioId, Pageable pageable);

    Page<Auditoria> findByOrganizacionIdOrderByFechaDesc(Long organizacionId, Pageable pageable);
}

