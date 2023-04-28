package br.com.ffscompany.marketplacemanager.repository;

import br.com.ffscompany.marketplacemanager.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
}
