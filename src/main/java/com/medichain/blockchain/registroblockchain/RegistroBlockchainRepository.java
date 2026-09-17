package com.medichain.blockchain.registroblockchain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegistroBlockchainRepository extends JpaRepository<RegistroBlockchain, UUID> {
}
