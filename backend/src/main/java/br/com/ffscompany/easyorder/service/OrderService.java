package br.com.ffscompany.easyorder.service;

import br.com.ffscompany.easyorder.dto.OrderDTO;
import br.com.ffscompany.easyorder.dto.ProductDTO;
import br.com.ffscompany.easyorder.model.ClientModel;
import br.com.ffscompany.easyorder.model.OrderModel;
import br.com.ffscompany.easyorder.model.ProductModel;
import br.com.ffscompany.easyorder.repository.ClientRepository;
import br.com.ffscompany.easyorder.repository.OrderRepository;
import br.com.ffscompany.easyorder.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements br.com.ffscompany.easyorder.service.Service<OrderModel, OrderDTO> {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Registers a new order.
     *
     * @param orderToRegister the order DTO containing the order data
     * @return the newly created order
     */
    public OrderModel register(OrderDTO orderToRegister) {

        List<ProductModel> productModelList = productRepository.findAllById(orderToRegister.products().stream().map(ProductDTO::id).toList());
        Optional<ClientModel> clientModel = clientRepository.findById(orderToRegister.client().id());

        OrderModel orderModel = new OrderModel(
                null,
                null,
                orderToRegister.description(),
                clientModel.orElse(null),
                productModelList,
                calculateAmount(productModelList.stream().map(ProductModel::getPrice).collect(Collectors.toList()))
        );
        orderRepository.save(orderModel);
        return orderModel;
    }

    /**
     * Edits an existing order.
     *
     * @param orderToEdit the order DTO containing the updated order data
     * @return an optional containing the updated order, or an empty optional if the order was not found
     */
    @Override
    public Optional<OrderModel> edit(OrderDTO orderToEdit) {

        Optional<OrderModel> orderModel = orderRepository.findById(orderToEdit.id());
        List<ProductModel> productModel = productRepository.findAllById(orderToEdit.products().stream().map(ProductDTO::id).toList());

        orderModel.ifPresent(order -> {
                    order.setDescription(orderToEdit.description());
                    order.setProducts(productModel);
                    order.setAmount(calculateAmount(productModel.stream().map(ProductModel::getPrice).collect(Collectors.toList())));
                    orderRepository.save(order);
                }
        );
        return orderModel;
    }

    /**
     * Removes an existing order.
     *
     * @param id the ID of the order to remove
     */
    @Override
    public void remove(Long id) {
        Optional<OrderModel> orderModel = orderRepository.findById(id);
        orderModel.ifPresent(model -> orderRepository.delete(model));
    }

    /**
     * Returns a list of all orders.
     *
     * @return a list containing all orders
     */
    @Override
    public List<OrderModel> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Returns an optional containing the order with the given ID, or an empty optional if the order was not found.
     *
     * @param id the ID of the order to find
     * @return an optional containing the order with the given ID, or an empty optional if the order was not found
     */
    @Override
    public Optional<OrderModel> findById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Calculates the total amount of a list of product prices.
     *
     * @param productsPrice the list of product prices to calculate the total amount from
     * @return the total amount as a BigDecimal
     */
    private BigDecimal calculateAmount(List<BigDecimal> productsPrice) {

        BigDecimal amount = new BigDecimal("0.00");
        for (BigDecimal price : productsPrice) {
            amount = amount.add(price).setScale(2, RoundingMode.HALF_UP);
        }

        return amount;
    }
}
