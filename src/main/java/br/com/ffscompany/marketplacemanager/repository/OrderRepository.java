package br.com.ffscompany.marketplacemanager.repository;

import br.com.ffscompany.marketplacemanager.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
}
