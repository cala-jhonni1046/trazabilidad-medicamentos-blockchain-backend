package ies.belgrano.medicamentos.blockchain.registroblockchain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroBlockchainRepository extends JpaRepository<RegistroBlockchain, Long> {
}
