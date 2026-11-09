package ies.belgrano.medicamentos.blockchain.registroblockchain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroBlockchainRepository extends JpaRepository<RegistroBlockchain, Long> {

    /** Registros de los lotes que la organización puede ver (los registros sin lote son solo para ANMAT/SUPERADMIN). */
    @org.springframework.data.jpa.repository.Query("""
            select r from RegistroBlockchain r
            where r.lote is not null
              and (r.lote.laboratorio.id = :orgId
                   or exists (select d.id from DespachoLogistico d
                              where d.lote = r.lote
                                and (d.distribuidor.id = :orgId or d.enlaceCuit.farmacia.id = :orgId)))
            """)
    java.util.List<RegistroBlockchain> findVisiblesPorOrganizacion(
            @org.springframework.data.repository.query.Param("orgId") Long orgId);
}
