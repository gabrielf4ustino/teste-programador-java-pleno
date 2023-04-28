package br.com.ffscompany.marketplacemanager.repository;

import br.com.ffscompany.marketplacemanager.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
