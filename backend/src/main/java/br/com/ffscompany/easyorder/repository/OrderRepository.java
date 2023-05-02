package br.com.ffscompany.easyorder.repository;

import br.com.ffscompany.easyorder.model.ClientModel;
import br.com.ffscompany.easyorder.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    List<OrderModel> findAllByClient(ClientModel clientModel);
}
