package br.com.ffscompany.marketplacemanager.service;

import br.com.ffscompany.marketplacemanager.dto.OrderDTO;
import br.com.ffscompany.marketplacemanager.model.OrderModel;
import br.com.ffscompany.marketplacemanager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void register(OrderDTO orderToRegister) {
        OrderModel orderModel = new OrderModel(
                null,
                orderToRegister.dateOfIssue(),
                orderToRegister.description(),
                orderToRegister.products(),
                orderToRegister.amount()
        );
        orderRepository.save(orderModel);
    }

    public List<OrderModel> findAllOrders(){
        return orderRepository.findAll();
    }
}
