package br.com.ffscompany.marketplacemanager.service;

import br.com.ffscompany.marketplacemanager.dto.OrderDTO;
import br.com.ffscompany.marketplacemanager.model.OrderModel;
import br.com.ffscompany.marketplacemanager.model.ProductModel;
import br.com.ffscompany.marketplacemanager.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void register(OrderDTO orderToRegister) {

        OrderModel orderModel = new OrderModel(
                null,
                null,
                orderToRegister.description(),
                orderToRegister.products(),
                calculateAmount(orderToRegister.products().stream().map(ProductModel::getPrice).collect(Collectors.toList()))
        );
        orderRepository.save(orderModel);
    }

    public List<OrderModel> findAllOrders() {
        return orderRepository.findAll();
    }

    private Double calculateAmount(List<Double> productsPrice) {

        double amount = 0.0;
        for (Double price : productsPrice) {
            amount = Double.sum(amount, price);
        }

        return amount;
    }
}
